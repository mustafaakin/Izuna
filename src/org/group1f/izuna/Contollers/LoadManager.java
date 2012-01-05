package org.group1f.izuna.Contollers;

import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.Random;
import javax.imageio.ImageIO;
import org.group1f.izuna.Contollers.XML.*;
import org.group1f.izuna.GUI.MenuElement;
import org.group1f.izuna.GameComponents.Drawing.Animation;
import org.group1f.izuna.GameComponents.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * 
 * @author Mustafa
 */
public class LoadManager {

    private static HashMap<String, Animation> animationBucket;
    private static HashMap<String, Weapon> weaponBucket;
    private static HashMap<String, Enemy> enemyBucket;
    private static HashMap<String, SoundEffect> soundBucket;
    private static HashMap<String, Image> imageBucket;
    private static HashMap<String, Image> menuBucket;
    private static Queue<Level> levelBucket;
    private static HashMap<String, Integer> weaponDefaultCountBucket;

    private LoadManager() {
        // Making it singleton
    }

    /**
     * 
     * @param key
     * @return
     */
    public static Animation getAnim(String key) {
        Animation a = animationBucket.get(key);
        if (a == null) {
            return null;
        }
        return a.clone();
    }

    /**
     * 
     * @throws Exception
     */
    public static void init() throws Exception {
        menuBucket = new HashMap<String, Image>();
        imageBucket = new HashMap<String, Image>();
        soundBucket = new HashMap<String, SoundEffect>();
        animationBucket = new HashMap<String, Animation>();
        weaponBucket = new HashMap<String, Weapon>();
        enemyBucket = new HashMap<String, Enemy>();
        weaponDefaultCountBucket = new HashMap<String, Integer>();

        readMenus();
        readSounds();
        initShipsImages();
        readWeaponAnims();
        
        // XML Information files. 
        Serializer serializer = new Persister();
        File enemiesSource = new File("data/enemies.xml");
        File weaponsSource = new File("data/weapons.xml");

        EnemyList enemies = serializer.read(EnemyList.class, enemiesSource);
        WeaponList weapons = serializer.read(WeaponList.class, weaponsSource);

        initilazieSources(enemies, weapons);
        loadLevels();
        readExplosions();
    }
    
    public static SoundEffect getAnExplosionSound(){
        Random r = new Random();
        return soundBucket.get("explosion_" + (r.nextInt(5) + 1));
    }

    /**
     * 
     * @param isBig
     * @param position
     * @return
     */
    public static GameObject getExplosion(boolean isBig, Point position) {
        Animation anim = getAnim("explosion_" + (isBig ? "big" : "small"));
        GameObject explosion = new GameObject(anim) {

            @Override
            public void checkStateToAnimate() {
                // Does not need body
            }
        };
        explosion.setPosition(position);
        return explosion;
    }

    private static void readExplosions() {
        File rootSmall = new File("data/image/animation/explosion_small");
        File rootBig = new File("data/image/animation/explosion_big");

        Animation small = new Animation();
        Animation big = new Animation();
        small.setAnimType(Animation.AnimationType.SMOOTH);
        big.setAnimType(Animation.AnimationType.SMOOTH);

        try {
            for (File f : rootSmall.listFiles()) {
                Image frame = ImageIO.read(f);
                small.addFrame(frame, frame);
            }
            for (File f : rootBig.listFiles()) {
                Image frame = ImageIO.read(f);
                big.addFrame(frame, frame);
            }
            animationBucket.put("explosion_big", big);
            animationBucket.put("explosion_small", small);

        } catch (IOException ex) {
            System.err.println("Could not read explosions: " + ex.getMessage());
        }

    }

    /**
     * 
     */
    public static void loadLevels() {
        try {
            levelBucket = new ArrayDeque<Level>();
            Serializer serializer = new Persister();
            File levelsSource = new File("data/levels.xml");
            LevelList waves = serializer.read(LevelList.class, levelsSource);
            for (LevelInfo levelData : waves.getList()) {
                Level level = new Level();
                for (WaveInfo waveData : levelData.getWaves()) {
                    AttackWave wave = new AttackWave();
                    for (WaveEnemy enemyData : waveData.getEnemies()) {
                        System.out.println("Adding Enemy:" + enemyData.getKey());
                        Enemy enemy = LoadManager.getEnemy(enemyData.getKey());
                        for (WavePath pathData : enemyData.getPaths()) {
                            String pathType = pathData.getType();
                            if (pathType.equals("linear")) {
                                Point startPoint = new Point(pathData.getStartX(), pathData.getStartY());
                                Point endPoint = new Point(pathData.getEndX(), pathData.getEndY());

                                LinearPath path = new LinearPath(startPoint, endPoint, pathData.getDuration());
                                enemy.addPath(path);
                            } else if (pathType.equals("quadratic")) {
                                Point startPoint = new Point(pathData.getStartX(), pathData.getStartY());
                                Point middlePoint = new Point(pathData.getMidX(), pathData.getMidY());
                                Point endPoint = new Point(pathData.getEndX(), pathData.getEndY());

                                QuadraticPath path = new QuadraticPath(startPoint, endPoint, middlePoint, pathData.getDuration());
                                enemy.addPath(path);
                            } else {
                                System.out.println("WTF PATH");
                            }
                        }
                        wave.addEnemy(enemy);
                    }
                    level.addWave(wave);
                }
                levelBucket.add(level);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readWeaponAnims() {
        File root = new File("data/image/animation/weapons/");
        for (File f : root.listFiles()) {
            if (f.isDirectory()) {
                String weaponKey = f.getName();
                try {
                    readSingleWeaponAnim(weaponKey);
                } catch (IOException ex) {
                    System.err.println("Error at reading weapon: " + weaponKey + ": " + ex.getMessage());
                }
            }
        }
    }

    private static void readSingleWeaponAnim(String key) throws IOException {
        File root = new File("data/image/animation/weapons/" + key);
        Animation stillAnim = new Animation();

        for (File f : root.listFiles()) {
            stillAnim.addFrame(ImageIO.read(f), ImageIO.read(f));
        }

        LoadManager.animationBucket.put("weapons/" + key, stillAnim);
    }

    private static void initilazieSources(EnemyList enemies, WeaponList weapons) {
        for (WeaponInfo weapon : weapons.getList()) {
            String key = weapon.getKey();
            
            Animation still = LoadManager.getAnim("weapons/" + key).clone();
            still.setAnimType(Animation.AnimationType.SMOOTH);
            
            SoundEffect fire = LoadManager.getSoundEffect(weapon.getFireSound());
            
            Weapon w = new Weapon(still, weapon.getCausedDamage(), weapon.getRateOfFire(), fire, weapon.getSpeed(), weapon.getType());
            w.setVisible(false);
            
            weaponBucket.put(key, w);
            weaponDefaultCountBucket.put(key, weapon.getDefaultAmount()); // Storing it for further use.
        }
        
        for (EnemyInfo enemy : enemies.getList()) {
            String key = enemy.getKey();
            
            Animation still = LoadManager.getAnim("ships/" + key + "/default");
            Animation leftRoll = LoadManager.getAnim("ships/" + key + "/left");
            Animation rightRoll = LoadManager.getAnim("ships/" + key + "/right");
            
            Weapon weapon = LoadManager.getWeapon(enemy.getWeapon());
            weapon.setDoesBelongEnemy(true);
            
            Enemy e = new Enemy(still, leftRoll, rightRoll, enemy.getHealth(), weapon);
            enemyBucket.put(key, e);
        }
    }

    /**
     * 
     * @param no
     * @return
     */
    public static Player getPlayer(int no) {
        String key = "player" + no;
        Animation still = LoadManager.getAnim("ships/" + key + "/default");
        Animation leftRoll = LoadManager.getAnim("ships/" + key + "/left");
        Animation rightRoll = LoadManager.getAnim("ships/" + key + "/right");
        Point position = no == 1 ? new Point(100,100) : new Point(100,300);
        Player p = new Player(position, still.clone(), leftRoll.clone(), rightRoll.clone());
        p.addWeapon("proton_player" + no, -1);
        p.addWeapon("plasma_player" + no, 100);
        p.addWeapon("particle", 25);
        p.addWeapon("dark_matter", 5);
        p.addWeapon("super_desperation", 1);
        p.setHealth(100);
        return p;
    }

    /**
     * 
     * @return
     */
    public static Level getNextLevel() {
        return levelBucket.poll();
    }

    /**
     * 
     * @param menu
     * @param key
     * @return
     */
    public static Image getMenuImage(String menu, String key) {
        return (Image) menuBucket.get(menu + "-" + key);
    }

    /**
     * 
     * @param menu
     * @param key
     * @return
     */
    public static MenuElement getMenuElement(String menu, String key) {
        Image normal = LoadManager.getMenuImage(menu, key);
        Image roll = LoadManager.getMenuImage(menu, key + "R");
        return new MenuElement(roll, normal);
    }

    /**
     * 
     * @param key
     * @return
     */
    public static SoundEffect getSoundEffect(String key) {
        SoundEffect effect = soundBucket.get(key);
        if (effect == null) {
            return null;
        }
        return effect.clone();
    }

    private static void readMenus() throws IOException {
        File level_cleared = new File("data/image/level_cleared.png");
        File wave_cleared = new File("data/image/wave_cleared.png");
        
        imageBucket.put("level_cleared", ImageIO.read(level_cleared));
        imageBucket.put("wave_cleared", ImageIO.read(wave_cleared));
        
        File background = new File("data/image/menu/background.png");
        imageBucket.put("menu_background", ImageIO.read(background));
        File root = new File("data/image/menu/");
        for (File f : root.listFiles()) {
            if (f.isDirectory()) {
                for (File k : f.listFiles()) {
                    Image img = ImageIO.read(k);
                    if (!k.getName().contains(".png")) {
                        System.err.println(k.getAbsolutePath() + " is not a PNG image.");
                        continue;
                    }
                    String imageName = k.getName().substring(0, k.getName().indexOf(".png"));
                    menuBucket.put(f.getName() + "-" + imageName, img);
                }
            }
        }
    }

    private static void readSounds() throws IOException {
        File root = new File("data/sounds");
        for (File f : root.listFiles()) {
            if (f.isFile()) {
                SoundEffect se = new SoundEffect(f.getAbsolutePath());
                String s = f.getName().substring(0, f.getName().indexOf(".mp3")); // Removing MP3
                soundBucket.put(s, se);
            }
        }
    }

    private static void initShipsImages() {
        File root = new File("data/image/animation/ships");
        for (File ship : root.listFiles()) {
            if (ship.isDirectory()) {
                try {
                    Animation still = readSingleShip(ship, "default");
                    Animation left = readSingleShip(ship, "left");
                    Animation right = readSingleShip(ship, "right");

                    left.setAnimType(Animation.AnimationType.SMOOTH);
                    right.setAnimType(Animation.AnimationType.SMOOTH);

                    String shipName = "ships/" + ship.getName() + "/";
                    animationBucket.put(shipName + "default", still);
                    animationBucket.put(shipName + "left", left);
                    animationBucket.put(shipName + "right", right);
                } catch (IOException ioe) {
                    System.err.println("Could not load ship: " + ship.getName() + ", because:" + ioe.getMessage());
                }
            }
        }
    }

    private static Animation readSingleShip(File root, String animation) throws IOException {
        boolean isPlayerShip = root.getName().startsWith("player");
        Animation anim = new Animation();

        File triD_root = new File(root.getAbsolutePath() + "/" + animation + "/3D");
        File normal_root = new File(root.getAbsolutePath() + "/" + animation + "/normal");

        File[] images3D = triD_root.listFiles();
        File[] imagesNormal = normal_root.listFiles();

        if (images3D == null || imagesNormal == null) {
            return anim;
        }

        for (int i = 0; i < images3D.length; i++) {
            BufferedImage tri = ImageIO.read(images3D[i]);
            BufferedImage norm = ImageIO.read(imagesNormal[i]);
            if (!isPlayerShip) {
                tri = transform(tri);
                norm = transform(norm);
            }
            anim.addFrame(norm, tri);
        }
        return anim;
    }

    /**
     * 
     * @param input
     * @return
     */
    public static BufferedImage transform(BufferedImage input) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-input.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(input, null);
    }

    /**
     * 
     * @param key
     * @return
     */
    public static Enemy getEnemy(String key) {
        return enemyBucket.get(key).clone();
    }

    /**
     * 
     * @param key
     * @return
     */
    public static Weapon getWeapon(String key) {
        System.out.println(key);
        return weaponBucket.get(key).clone();
    }

    /**
     * 
     * @param key
     * @return
     */
    public static Bonus getBonus(String key) {
        return null;
    }

    /**
     * 
     * @param key
     * @return
     */
    public static Image getImage(String key) {
        return imageBucket.get(key);
    }
}

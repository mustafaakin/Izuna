package org.group1f.izuna.Contollers;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import javax.imageio.ImageIO;
import org.group1f.izuna.Contollers.XML.*;
import org.group1f.izuna.GUI.MenuElement;
import org.group1f.izuna.GameComponents.Drawing.Animation;
import org.group1f.izuna.GameComponents.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class LoadManager {

    private static HashMap<String, Animation> animationBucket;
    private static HashMap<String, Weapon> weaponBucket;
    private static HashMap<String, SoundEffect> soundBucket;
    private static HashMap<String, Image> imageBucket;
    private static HashMap<String, Image> menuBucket;
    private static Queue<Level> levelBucket;

    private LoadManager() {
        // Making it singleton
    }

    public static Animation getAnim(String key) {
        Animation a = animationBucket.get(key);
        if (a == null) {
            return null;
        }
        return a.clone();
    }

    public static void init() throws Exception {
        menuBucket = new HashMap<String, Image>();
        imageBucket = new HashMap<String, Image>();
        soundBucket = new HashMap<String, SoundEffect>();
        animationBucket = new HashMap<String, Animation>();
        weaponBucket = new HashMap<String, Weapon>();

        readMenus();
        readSounds();
        initShipsImages();
        // XML Information files. 
        Serializer serializer = new Persister();
        File enemiesSource = new File("data/enemies.xml");
        File weaponsSource = new File("data/weapons.xml");

        EnemyList enemies = serializer.read(EnemyList.class, enemiesSource);
        WeaponList weapons = serializer.read(WeaponList.class, weaponsSource);

        initilazieSources(enemies, weapons);
        loadLevels();
    }

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
                        System.out.println(enemyData.getKey());
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

    private static void initilazieSources(EnemyList enemies, WeaponList weapons) {
        for (WeaponInfo weapon : weapons.getList()) {
            String key = weapon.getKey();

            Animation still = LoadManager.getAnim("weapon/" + key + "/still");
            Animation explode = LoadManager.getAnim("weapon/" + key + "/explode");
            Animation die = LoadManager.getAnim("weapon/" + key + "/die");

            SoundEffect fire = LoadManager.getSoundEffect(weapon.getFireSound());
            SoundEffect explosion = LoadManager.getSoundEffect(weapon.getExplodeSound());

            Weapon w = new Weapon(still, die, explode, weapon.getCausedDamage(), weapon.getRateOfFire(), fire, explosion);
            w.setVisible(false);
            weaponBucket.put(key, w);
        }
    }

    public static Level getNextLevel() {
        return levelBucket.poll();
    }

    public static Image getMenuImage(String menu, String key) {
        return (Image) menuBucket.get(menu + "-" + key);
    }

    public static MenuElement getMenuElement(String menu, String key) {
        Image normal = LoadManager.getMenuImage(menu, key);
        Image roll = LoadManager.getMenuImage(menu, key + "R");
        return new MenuElement(roll, normal);
    }

    public static SoundEffect getSoundEffect(String key) {
        SoundEffect effect = soundBucket.get(key);
        if (effect == null) {
            return null;
        }
        return effect.clone();
    }

    private static void readMenus() throws IOException {
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
        Animation anim = new Animation();

        File triD_root = new File(root.getAbsolutePath() + "/" + animation + "/3D");
        File normal_root = new File(root.getAbsolutePath() + "/" + animation + "/normal");

        File[] images3D = triD_root.listFiles();
        File[] imagesNormal = normal_root.listFiles();

        System.out.println(root.getPath()); 
        System.out.println("\t" + animation);

        if (images3D == null || imagesNormal == null) {
            return anim;
        }

        for (int i = 0; i < images3D.length; i++) {
            Image tri = ImageIO.read(images3D[i]);
            Image norm = ImageIO.read(imagesNormal[i]);
            anim.addFrame(norm, tri);
        }
        return anim;
    }

    public static Enemy getEnemy(String key) {
        Animation still = LoadManager.getAnim("ships/" + key + "/default");
        Animation leftRoll = LoadManager.getAnim("ships/" + key + "/left");
        Animation rightRoll = LoadManager.getAnim("ships/" + key + "/right");
        SoundEffect enteringSound = LoadManager.getSoundEffect("enterance/" + key);
        Enemy e = new Enemy(still, leftRoll, rightRoll, enteringSound);
        return e;
    }

    public static Weapon getWeapon(String key) {
        return null;
    }

    public static Bonus getBonus(String key) {
        return null;
    }

    public static Image getImage(String key) {
        return imageBucket.get(key);
    }
}

package org.group1f.izuna.Contollers;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.group1f.izuna.Contollers.XML.*;
import org.group1f.izuna.GameComponents.*;
import org.group1f.izuna.GameComponents.SoundEffect;
import org.group1f.izuna.GameComponents.Drawing.Animation;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class LoadManager {

    private static Hashtable<String, Animation> animationBucket;
    private static Hashtable<String, SoundEffect> soundBucket;
    private static Hashtable<String, Image> imageBucket;
    private static Hashtable<String, Image> menuBucket;

    private LoadManager() {
        // Making it singleton
    }

    public static void init() throws Exception {
        Graphics2D g = FullScreenManager.getGraphics();
        g.drawString("LOADING GAME FILES", 100, 100);
        FullScreenManager.update();
        g.dispose();

        menuBucket = new Hashtable<String, Image>();
        imageBucket = new Hashtable<String, Image>();
        soundBucket = new Hashtable<String, SoundEffect>();
        animationBucket = new Hashtable<String, Animation>();

        readMenus();
        readSounds();
        initShipsImages();

        // XML Information files. 
        Serializer serializer = new Persister();
        File levelsSource = new File("data/levels.xml");
        File enemiesSource = new File("data/enemies.xml");
        File weaponsSource = new File("data/weapons.xml");

        LevelList waves = serializer.read(LevelList.class, levelsSource);
        EnemyList enemies = serializer.read(EnemyList.class, enemiesSource);
        WeaponList weapons = serializer.read(WeaponList.class, weaponsSource);
        
    }

    public static Image getMenuImage(String menu, String key) {
        return (Image) menuBucket.get(menu + "-" + key);
    }

    public static SoundEffect getSoundEffect(String key) {
        return new SoundEffect(soundBucket.get(key));
    }

    private static void readMenus() throws IOException {
        File background = new File("data/image/menu/background.png");
        imageBucket.put("menu_background", ImageIO.read(background));
        File root = new File("data/image/menu/");
        for (File f : root.listFiles()) {
            if (f.isDirectory()) {
                for (File k : f.listFiles()) {
                    Image img = ImageIO.read(k);
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
                    Animation roll = readSingleShip(ship, "roll");

                    String shipName = "ships/" + ship.getName() + "/";
                    animationBucket.put(shipName + "default", still);
                    animationBucket.put(shipName + "left", left);
                    animationBucket.put(shipName + "roll", roll);
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
        return null;
    }

    public static Weapon getWeapon(String key) {
        return null;
    }

    public static Bonus getBonus(String key) {
        Bonus b = new Bonus(null, null);
        return null;
    }

    public static Image getImage(String key) {
        return imageBucket.get(key);
    }
}

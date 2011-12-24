package org.group1f.izuna.Contollers;

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

    public static void init() throws Exception{
        Serializer serializer = new Persister();
        File levelsSource = new File("data/levels.xml");
        File enemiesSource = new File("data/enemies.xml");
        File weaponsSource = new File("data/weapons.xml");

        LevelList waves = serializer.read(LevelList.class, levelsSource);
        EnemyList enemies = serializer.read(EnemyList.class, enemiesSource);
        WeaponList weapons = serializer.read(WeaponList.class, weaponsSource);
        
        menuBucket = new Hashtable<String, Image>();
        imageBucket = new Hashtable<String, Image>();
        soundBucket = new Hashtable<String, SoundEffect>();
        
        readMenus();
        readSounds();

    }

    public static Image getMenuImage(String menu, String key) {
        return (Image) menuBucket.get(menu + "-" + key);
    }

    public static SoundEffect getSoundEffect(String key){
        return new SoundEffect(soundBucket.get(key));
    }
    
    private static void initAnimation(String key) {
        
    }

    
   
    private static void readMenus() throws IOException {
        File background = new File("data/image/menu/background.png");
        imageBucket.put("menu_background", ImageIO.read(background));
        File root = new File("data/image/menu/");
        for (File f : root.listFiles()) {
            if (f.isDirectory()) {
                for (File k : f.listFiles()) {
                    Image img = ImageIO.read(k);
                    String imageName = k.getName().substring(0,k.getName().indexOf(".png"));
                    menuBucket.put(f.getName() + "-" + imageName, img);
                }
            }
        }
    }
    
    private static void readSounds() throws IOException{
        File root = new File("data/sounds");
        for (File f : root.listFiles()) {
            if ( f.isFile()){
                SoundEffect se = new SoundEffect(f.getAbsolutePath());
                String s = f.getName().substring(0,f.getName().indexOf(".mp3")); // Removing MP3
                soundBucket.put(s, se);
            }
        }
    }

    private static Animation getAnimationFromFolder(String folder) {
        Animation anim = new Animation();
        File root = new File(folder);
        File[] images3D = null;
        File[] imagesNormal = null;
        for (File f : root.listFiles()) {
            if (f.isDirectory()) {
                if (f.getName().equals("3D")) {
                    images3D = f.listFiles();
                } else if (f.getName().equals("normal")) {
                    imagesNormal = f.listFiles();
                }
            }
        }
        for (int i = 0; i < imagesNormal.length; i++) {
            try {
                Image imgNorm = ImageIO.read(imagesNormal[i]);
                Image img3D = null;
                if (images3D != null) {
                    img3D = ImageIO.read(images3D[i]);
                }
                anim.addFrame(imgNorm, img3D);
            } catch (IOException ioe) {
                System.err.println("Error reading files '" + imagesNormal[i] + "' and " + images3D[i] + "' :" + ioe.getMessage());
            }
        }
        return anim;
    }

    public static Enemy getEnemy(String key) {
        Enemy e = new Enemy(null, null);
        return e;
    }

    public static Weapon getWeapon(String key) {
        Weapon w = new Weapon(null, null);
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

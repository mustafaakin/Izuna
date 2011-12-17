package org.group1f.izuna.Contollers;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.imageio.ImageIO;

import org.group1f.izuna.GameComponents.SoundEffect;
import org.group1f.izuna.GameComponents.Drawing.Animation;

public class LoadManager {

    private static Hashtable<String, Animation> animationBucket;
    private static Hashtable<String, SoundEffect> soundBucket;
    private static Hashtable<String, Image> imageBucket;

    private LoadManager() {
        // Making it singleton
    }

    public static void init() {
        initAnimations();
    }

    private static void initAnimations() {
        Animation a = getAnimationFromFolder(new File("data/image/animation/enemies/deneme/images"));
    }

    private static Animation getAnimationFromFolder(File folder) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("Animations must be in a folder.");
        } else {
            Animation anim = new Animation();
            File[] frames = folder.listFiles();
            for (File frame : frames) {
                if (!frame.isDirectory()) {
                    try {
                        Image buffImage = ImageIO.read(frame);
                        anim.addFrame(buffImage, Animation.FRAME_DURATION);
                        System.out.println("Loaded: " + frame.getName());
                    } catch (IOException ioe) {
                        System.err.println("'" + frame.getAbsolutePath() + "' is not a valid image file.");
                    }
                }
            }
            return anim;
        }
    }
}

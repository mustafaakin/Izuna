package org.group1f.izuna.GameComponents;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

public class SoundEffect {

    private String filename;
    private Player player;

    public SoundEffect(String filename) {
        this.filename = filename;
    }
    
    public void close() {
        if (player != null) {
            player.close();
        }       
    }
    
    public void play() {
        try {
            FileInputStream fis = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);       
        } catch (Exception e) {
            System.out.println("Problem playing file: " + filename);
            System.out.println(e);
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();
    }
}
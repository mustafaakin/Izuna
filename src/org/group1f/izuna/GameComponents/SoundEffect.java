package org.group1f.izuna.GameComponents;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.Player;
import org.group1f.izuna.GameCore;

/**
 *
 * @author Mustafa
 */
public class SoundEffect {

    private String filename;
    private Player player;

    /**
     * Constructs a SoundEffect with given filename, but no initialization is
     * occurred until .play() is called.
     *
     * @param filename
     */
    public SoundEffect(String filename) {
        this.filename = filename;
    }

    @Override
    public SoundEffect clone() {
        return new SoundEffect(filename);
    }

    /**
     * Shuts down the music.
     */
    public void close() {
        if (player != null) {
            player.close();
        }
    }

    /**
     * Plays the music within a new thread.
     */
    public void play() {
        boolean doPlay = GameCore.preferences().getBoolean("sound", true);
        if (!doPlay) {
            return;
        }
        try {
            FileInputStream fis = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        } catch (Exception e) {
            System.err.println("Problem playing file: " + filename + ":" + e.getMessage());
        }
        new Thread() {

            @Override
            public void run() {
                try {
                    player.play();
                } catch (Exception e) {
                    System.err.println("SOUND ERROR:" + e.getMessage());
                }
            }
        }.start();
    }
}
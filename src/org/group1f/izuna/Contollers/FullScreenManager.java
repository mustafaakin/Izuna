package org.group1f.izuna.Contollers;

import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 * The FullScreenManager class creates a JFrame for the game, and arrange fullscreen bussiness in it.
 * @author Nail AKINCI
 */
public class FullScreenManager {

    private static GraphicsDevice vc;

    private FullScreenManager() {
    }

    /**
     * Initializes the Graphic environment for the game.
     */
    public static void initGraphics() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = env.getDefaultScreenDevice();
        setFullScreen();
    }

    private static void setFullScreen() {
        DisplayMode chosenDm = null;
        for (DisplayMode dm : vc.getDisplayModes()) { // Chooses neastill resolution.
            if (dm != null && dm.getWidth() >= 1280 && dm.getHeight() >= 720) {
                chosenDm = new DisplayMode(dm.getWidth(), dm.getHeight(), 32, 0);
                break;
            }
        }
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        frame.setForeground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vc.setFullScreenWindow(frame);

        if (chosenDm != null && vc.isDisplayChangeSupported()) {
            try {
                vc.setDisplayMode(chosenDm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        frame.createBufferStrategy(2);
    }

    /**
     * checks whether graphics is initialized then returns the 2d Graphics in the buffer.
     * @return Graphics2D
     */
    public static Graphics2D getGraphics() {
        if (vc == null) {
            initGraphics();
        }
        Window w = vc.getFullScreenWindow();
        if (w != null) {
            BufferStrategy buffer = w.getBufferStrategy();
            return (Graphics2D) buffer.getDrawGraphics();
        } else {
            return null;
        }
    }

    /**
     * Applies double buffering to prevent screen tearing.
     */
    public static void update() {
        Window window = vc.getFullScreenWindow();
        if (window != null) {
            BufferStrategy strategy = window.getBufferStrategy();
            if (!strategy.contentsLost()) {
                strategy.show();
            }

        }
    }

    /**
     * Returns the JFrame that is currently used in the game.
     * @return JFrame
     */
    public static JFrame getFullScreenWindow() {
        return (JFrame) vc.getFullScreenWindow();
    }
}
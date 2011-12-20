package org.group1f.izuna.Contollers;

import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class FullScreenManager {

    private static GraphicsDevice vc;

    private FullScreenManager() {
    }

    public static void initGraphics() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = env.getDefaultScreenDevice();
        setFullScreen();
    }

    private static void setFullScreen() {
        DisplayMode dm = new DisplayMode(1280, 800, 32, 0);
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        vc.setFullScreenWindow(frame);

        if (dm != null && vc.isDisplayChangeSupported()) {
            try {
                vc.setDisplayMode(dm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        frame.createBufferStrategy(2);
    }

    public static Graphics2D getGraphics() {
        Window w = vc.getFullScreenWindow();
        if (w != null) {
            BufferStrategy buffer = w.getBufferStrategy();
            return (Graphics2D) buffer.getDrawGraphics();
        } else {
            return null;
        }
    }

    public static void update() {
        Window window = vc.getFullScreenWindow();
        if (window != null) {
            BufferStrategy strategy = window.getBufferStrategy();
            if (!strategy.contentsLost()) {
                strategy.show();
            }
        }
    }

    public static JFrame getFullScreenWindow() {
        return (JFrame) vc.getFullScreenWindow();
    }
}

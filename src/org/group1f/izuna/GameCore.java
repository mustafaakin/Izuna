package org.group1f.izuna;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import org.group1f.izuna.Contollers.FullScreenManager;
import org.group1f.izuna.Contollers.KeyboardHandler;
import org.group1f.izuna.Contollers.KeyboardHandler.Key;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.Contollers.PhysicsHandler;
import org.group1f.izuna.GameComponents.*;
import org.group1f.izuna.GameComponents.Drawing.Animation;
import org.group1f.izuna.GameComponents.Drawing.Sprite;

/**
 * @author Mustafa
 *
 */
public class GameCore {

    private static Preferences prefs = Preferences.userNodeForPackage(GameCore.class);
    public GameState game = new GameState();
    public boolean inMenu = true;
    public KeyboardHandler input;
    public Level currentLevel;
    long startTime;
    long currentTime;

    public static Preferences preferences() {
        return prefs;
    }

    public static void main(String[] args) {
        GameCore core = new GameCore();
        core.initialize();
        while (true) {
            core.gameLoop();
            try {
                Thread.sleep(Animation.FRAME_DURATION);
            } catch (InterruptedException e) {
            }
        }
    }

    /*
     * in an infinite loop calculate time in miliseconds call updateBattlefield
     */
    int active = 0;
    long counter = 0;

    private void gameLoop() {
        if (inMenu) {
        } else {
            System.out.println("hi, game loop in fight mode.");
            long elapsedTime = System.currentTimeMillis() - currentTime;
            currentTime += elapsedTime;

            // system time is needed
//            updateBattlefield(elapsedTime);

            renderBattlefield();
            //          movePlayer();
        }
    }

    private void startGame(boolean isSinglePlayer) {
        currentLevel = LoadManager.getNextLevel();
        AttackWave wave = currentLevel.startLevel();
        List<Enemy> enemies = wave.startWave(System.currentTimeMillis());
        for (Enemy enemy : enemies) {
            enemy.setPathActivationTime(System.currentTimeMillis());
            enemy.setPosition();
            game.enemies.add(enemy);
            System.out.println(game.enemies);
        }
        System.out.println("FOUND ENEMIES: " + game.enemies.size());
        inMenu = false;
    }

    /*
     * initialize full screen window initialize inputs set backround pictures
     * that will be used in levels
     */
    private void initialize() {
        input = new KeyboardHandler(this);
        try {
            LoadManager.init();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Game files could not be loaded: " + e.getMessage(),
                    "Error At Loading Files",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        FullScreenManager.getFullScreenWindow().addKeyListener(input);
        game.backgroundMusic = LoadManager.getSoundEffect("main_menu");
        game.backgroundMusic.play();
        startTime = System.currentTimeMillis();
        currentTime = startTime;
    }

    // check player inputs for two players
    public void inputFromKeyboard(Key key, boolean isPressed) {
        if (key == null) {
            return;
        }
        if (key.equals(Key.Escape)) {
            System.exit(0);
        }

        if (inMenu && isPressed) {
            if (key.equals(Key.Player1_Down)) {
                startGame(true);
            }
        }
    }

    /*
     * In this class the operations below needs to be implemented in this order
     * 1) if the level is over load next level 2) update all gameobjects
     * (sprites and sounds) 3) check neccessary collisions using physics class
     * and change states of the gameobjects accordingly
     */
    private void updateBattlefield(long elapsedTime) {
        // Check If wave is finished, load new enemies or new level



        // Neccesary collisions needed to be calculated::
        // Players - Enemies
        for (Enemy e : game.enemies) {
            if (PhysicsHandler.checkSpriteCollisions(e, game.p1)) {
            }
            if (game.p2 != null && PhysicsHandler.checkSpriteCollisions(e, game.p2)) {
            }
            // Enemies - UserWeapons
            for (Weapon w : game.userWeapons) {
                if (PhysicsHandler.checkSpriteCollisions(w, e)) {
                }
            }
        }
        // Players - EnemyWeapons
        for (Weapon w : game.enemyWeapons) {
            if (PhysicsHandler.checkSpriteCollisions(w, game.p1)) {
            }
            if (game.p2 != null && PhysicsHandler.checkSpriteCollisions(w, game.p2)) {
            }
        }
        // Players - Bonuses        
        for (Bonus b : game.bonuses) {
            if (PhysicsHandler.checkSpriteCollisions(b, game.p1)) {
            }
            if (game.p2 != null && PhysicsHandler.checkSpriteCollisions(b, game.p2)) {
            }
        }


    }

    private void movePlayer() {
    }

    private void renderBattlefield() {
        Graphics2D g = FullScreenManager.getGraphics();
        for (int i = 0; i < game.backgroundLayers.length; i++) {
            Image background = game.backgroundLayers[i];
            g.drawImage(background, 0, 0, null);
        }
        for (Enemy e : game.enemies) {
            Animation currentAnimation = e.getCurrentAnim();
            Image currentImage = currentAnimation.getImage();
            int x = e.getPosition().x;
            int y = e.getPosition().y;
            System.out.println(x + " - " + y);
            g.drawImage(currentImage, x, y, null);
        }
        FullScreenManager.update();
        FullScreenManager.getGraphics().dispose();
    }
}

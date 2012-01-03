package org.group1f.izuna;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import org.group1f.izuna.Contollers.FullScreenManager;
import org.group1f.izuna.Contollers.KeyboardHandler;
import org.group1f.izuna.Contollers.KeyboardHandler.Key;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.Contollers.PhysicsHandler;
import org.group1f.izuna.GUI.MainMenu;
import org.group1f.izuna.GUI.Menu;
import org.group1f.izuna.GameComponents.Drawing.Animation;
import org.group1f.izuna.GameComponents.*;

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
    public Menu currentMenu;
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
        }
    }

    /*
     * in an infinite loop calculate time in miliseconds call updateBattlefield
     */
    int active = 0;
    long counter = 0;

    private void gameLoop() {
        if (inMenu) {
            List<Image> images = currentMenu.getImagesToDraw();
            Graphics2D g = FullScreenManager.getGraphics();
            g.clearRect(0, 0, 2560, 1600);
            Image background = LoadManager.getImage("menu_background");
            g.drawImage(background, 0, 0, null);
            for (Image i : images) {
                g.drawImage(i, 0, 0, null);
            }
            g.dispose();
            FullScreenManager.update();
        } else {
            long elapsedTime = System.currentTimeMillis() - currentTime;
            currentTime += elapsedTime;

            // system time is needed
//            updateBattlefield(elapsedTime);

            renderBattlefield(elapsedTime);
            //          movePlayer();
        }
    }

    public void startGame(boolean isSinglePlayer) {
        if (isSinglePlayer) {
            game.p1 = LoadManager.getPlayer(1);
        } else {
        }
        LoadManager.loadLevels();
        currentLevel = LoadManager.getNextLevel();
        AttackWave wave = currentLevel.startLevel();
        List<Enemy> enemies = wave.startWave(System.currentTimeMillis());
        for (Enemy enemy : enemies) {
            enemy.setPathActivationTime(System.currentTimeMillis());
            enemy.setPosition();
            game.getEnemies().add(enemy);
        }
        inMenu = false;
        game.backgroundMusic.close();
    }

    /*
     * initialize full screen window initialize inputs set backround pictures
     * that will be used in levels
     */
    private void initialize() {
        FullScreenManager.initGraphics();
        input = new KeyboardHandler(this);
        try {
            LoadManager.init();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Unrecovarable error has just happened. Please see the log for details. ",
                    "Error At Loading Files",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        FullScreenManager.getFullScreenWindow().addKeyListener(input);
        enterMainMenuState();
    }

    public void enterMainMenuState() {
        game.backgroundMusic = LoadManager.getSoundEffect("main_menu");
        game.backgroundMusic.play();
        startTime = System.currentTimeMillis();
        currentMenu = new MainMenu(this);
        currentTime = startTime;
        inMenu = true;
    }

    public void killEnemy(Enemy e) {
        List<Enemy> enemies = game.getEnemies();
        synchronized (enemies) {
            enemies.remove(e);
        }
        this.currentLevel.killEnemy(e);
        if (currentLevel.isFinished()) {
            currentLevel = LoadManager.getNextLevel();
            if (currentLevel == null) {
                enterMainMenuState();
            } else {
                AttackWave wave = this.currentLevel.startLevel();
                addWaveToGame(wave);
            }
        } else if (this.currentLevel.getCurrentWave().isFinished()) {
            AttackWave wave = this.currentLevel.swapNextWave();
            addWaveToGame(wave);
        }
    }

    private void addWaveToGame(AttackWave wave) {
        List<Enemy> newEnemies = wave.startWave(System.currentTimeMillis());
        for (Enemy enemy : newEnemies) {
            enemy.setPathActivationTime(System.currentTimeMillis());
            enemy.setPosition();
            game.getEnemies().add(enemy);
        }
    }

    // check player inputs for two players
    public void inputFromKeyboard(Key key, boolean isPressed) {
        if (key == null) {
            return;
        }
        if (key.equals(Key.Escape)) {
            System.exit(0);
        }
        if (!inMenu) {
            if (key.equals(Key.Player1_Weapon1)) {
                if (!game.getEnemies().isEmpty()) {
                    Enemy e = game.getEnemies().get(0);
                    killEnemy(e);
                }
            }
            Set<Key> pressed = input.getActive();
            if (pressed.contains(Key.Player1_Left)) {
                game.p1.setvX(-2);
            }
            if (pressed.contains(Key.Player1_Right)) {
                game.p1.setvX(2);
            }
            if (pressed.contains(Key.Player1_Up)) {
                game.p1.setvY(-2);
            }
            if (pressed.contains(Key.Player1_Down)) {
                game.p1.setvY(2);
            }
            if (pressed.contains(Key.Player1_Weapon2)) {
                fireWeapon("proton_player1");
            }
            if (pressed.contains(Key.Player1_Weapon3)) {
                fireWeapon("plasma_player1");
            }
            if (pressed.contains(Key.Player1_Weapon4)) {
                fireWeapon("particle_player1");
            }
        }
        if (!inMenu && !isPressed) {
            game.p1.setvX(0);
            game.p1.setvY(0);
        }
        if (inMenu && isPressed) {
            currentMenu.onClicked(key);
        }
    }

    private void fireWeapon(String key) {
        Weapon ok = game.p1.fire(key, System.currentTimeMillis());
        if (ok != null) {
            ok.playFire();
        }
        ArrayList<Weapon> userWeapons = game.getUserWeapons();
        synchronized (userWeapons) {
            userWeapons.add(ok);
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
        /*
         * for (Enemy e : game.getEnemies()) { if
         * (PhysicsHandler.checkSpriteCollisions(e, game.p1)) { } if (game.p2 !=
         * null && PhysicsHandler.checkSpriteCollisions(e, game.p2)) { } //
         * Enemies - UserWeapons for (Weapon w : game.userWeapons) { if
         * (PhysicsHandler.checkSpriteCollisions(w, e)) { } } } // Players -
         * EnemyWeapons for (Weapon w : game.enemyWeapons) { if
         * (PhysicsHandler.checkSpriteCollisions(w, game.p1)) { } if (game.p2 !=
         * null && PhysicsHandler.checkSpriteCollisions(w, game.p2)) { } } //
         * Players - Bonuses for (Bonus b : game.bonuses) { if
         * (PhysicsHandler.checkSpriteCollisions(b, game.p1)) { } if (game.p2 !=
         * null && PhysicsHandler.checkSpriteCollisions(b, game.p2)) { } }
         */
    }

    private void movePlayer() {
    }
    private boolean isRendering = false;

    private void renderBattlefield(long elapsedTime) {
        isRendering = true;
        Graphics2D g = FullScreenManager.getGraphics();
        g.clearRect(0, 0, 2560, 1600);
        g.drawImage(LoadManager.getImage("menu_background"), 0, 0, null);
        game.p1.update(elapsedTime);

        g.drawImage(game.p1.getCurrentAnim().getImage(), game.p1.getPosition().x, game.p1.getPosition().y, null);
        for (int i = 0; i < game.backgroundLayers.length; i++) {
            Image background = game.backgroundLayers[i];
            g.drawImage(background, 0, 0, null);
        }
        ArrayList<Weapon> userWeapons = game.getUserWeapons();
        synchronized (userWeapons) {
            if (!userWeapons.isEmpty()) {
                try {
                    for (Weapon k : userWeapons) {
                        if (k != null) {
                            if (System.currentTimeMillis() - k.getLastFire() > k.getAnimationDuration()) {
                                userWeapons.remove(k);
                                continue;
                            }
                            k.update(elapsedTime);
                            g.drawImage(k.getCurrentImage(), k.getPosition().x, k.getPosition().y, null);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
        List<Enemy> enemies = game.getEnemies();
        synchronized (enemies) {
            for (Enemy e : enemies) {
                Point pos = e.getPosition();
                e.update(elapsedTime);
                Animation currentAnimation = e.getCurrentAnim();
                Image currentImage = currentAnimation.getImage();
                g.drawImage(currentImage, pos.x, pos.y, null);
            }
        }
        g.dispose();
        FullScreenManager.update();
        isRendering = false;
    }
}

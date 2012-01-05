package org.group1f.izuna;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
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
import org.group1f.izuna.GameComponents.*;

/**
 * @author Mustafa
 *
 */
public class GameCore {

    private static Preferences prefs = Preferences.userNodeForPackage(GameCore.class);
    /**
     *
     */
    public GameState game = new GameState();
    /**
     *
     */
    public boolean inMenu = true;
    /**
     *
     */
    public KeyboardHandler input;
    /**
     *
     */
    public Level currentLevel;
    /**
     *
     */
    public Menu currentMenu;
    private long startTime;
    private long currentTime;
    private long lastEnemyDeath;
    private boolean doShowLevelCleared = false;
    private boolean doShowWaveCleared = false;

    /**
     *
     * @return
     */
    public static Preferences preferences() {
        return prefs;
    }

    /**
     *
     * @param args
     */
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

            renderBattlefield(elapsedTime);
            updateBattlefield(elapsedTime);
            //          movePlayer();
        }
    }

    /**
     *
     * @param isSinglePlayer
     */
    public void startGame(boolean isSinglePlayer) {
        game.p1 = LoadManager.getPlayer(1);
        if (!isSinglePlayer) {
            game.p2 = LoadManager.getPlayer(2);
        }
        LoadManager.loadLevels();
        currentLevel = LoadManager.getNextLevel();
        AttackWave wave = currentLevel.startLevel();
        List<Enemy> enemies = wave.startWave(System.currentTimeMillis());
        for (Enemy enemy : enemies) {
            enemy.setPathActivationTime(System.currentTimeMillis());
            enemy.setStartingPosition();
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

    /**
     *
     */
    public void enterMainMenuState() {
        game.backgroundMusic = LoadManager.getSoundEffect("main_menu");
        game.backgroundMusic.play();
        startTime = System.currentTimeMillis();
        currentMenu = new MainMenu(this);
        currentTime = startTime;
        inMenu = true;
    }

    public void tryToLoadNewLevel() {
        long timeDifference = System.currentTimeMillis() - lastEnemyDeath;
        if (timeDifference > 5000) {
            if (currentLevel.isFinished()) {
                currentLevel = LoadManager.getNextLevel();
                if (currentLevel == null) {
                    enterMainMenuState();
                } else {
                    AttackWave wave = this.currentLevel.startLevel();
                    addWaveToGame(wave);
                }
                doShowLevelCleared = false;
                doShowWaveCleared = false;
            } else if (this.currentLevel.getCurrentWave().isFinished()) {
                AttackWave wave = this.currentLevel.swapNextWave();
                addWaveToGame(wave);
                doShowLevelCleared = false;
                doShowWaveCleared = false;
            }
        } else {
            if (currentLevel.isFinished()) {
                doShowLevelCleared = true;
            } else if (currentLevel.getCurrentWave().isFinished()) {
                doShowWaveCleared = true;
            } else {
                doShowLevelCleared = false;
                doShowWaveCleared = false;
            }
        }
    }

    /**
     *
     * @param e
     */
    public void killEnemy(Enemy e) {
        List<Enemy> enemies = game.getEnemies();
        synchronized (enemies) {
            enemies.remove(e);
        }
        this.currentLevel.killEnemy(e);
        lastEnemyDeath = System.currentTimeMillis();
    }

    private void addWaveToGame(AttackWave wave) {
        List<Enemy> newEnemies = wave.startWave(System.currentTimeMillis());
        for (Enemy enemy : newEnemies) {
            enemy.setPathActivationTime(System.currentTimeMillis());
            enemy.setStartingPosition();
            game.getEnemies().add(enemy);
        }
    }

    // check player inputs for two players
    /**
     *
     * @param key
     * @param isPressed
     */
    public void inputFromKeyboard(Key key, boolean isPressed) {
        if (key == null) {
            return;
        }
        if (!inMenu && isPressed) {
            Set<Key> pressed = input.getActive();
            if (pressed.contains(Key.Player1_Left)) {
                game.p1.setvX(-3);
            }
            if (pressed.contains(Key.Player1_Right)) {
                game.p1.setvX(3);
            }
            if (pressed.contains(Key.Player1_Up)) {
                game.p1.setvY(-3);
            }
            if (pressed.contains(Key.Player1_Down)) {
                game.p1.setvY(3);
            }
            if (pressed.contains(Key.Player1_Weapon1)) {
                fireUserWeapon("proton_player1");
            }
            if (pressed.contains(Key.Player1_Weapon2)) {
                fireUserWeapon("plasma_player1");
            }
            if (pressed.contains(Key.Player1_Weapon3)) {
                fireUserWeapon("particle");
            }
            if (pressed.contains(Key.Player1_Weapon4)) {
                fireUserWeapon("dark_matter");
            }
            if (pressed.contains(Key.Player1_Weapon5)) {
                fireUserWeapon("super_desperation");
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

    private void fireUserWeapon(String key) {
        Weapon weapon = game.p1.fire(key, System.currentTimeMillis());
        if (weapon != null) {
            weapon.playFire();
        }
        ArrayList<Weapon> userWeapons = game.getUserWeapons();
        synchronized (userWeapons) {
            userWeapons.add(weapon);
        }
    }

    private void fireEnemyWeapon(Enemy enemy) {
        Weapon weapon = enemy.fire("", System.currentTimeMillis());
        if (weapon != null) {
            weapon.playFire();
        }
        ArrayList<Weapon> enemyWeapons = game.getEnemyWeapons();
        synchronized (enemyWeapons) {
            enemyWeapons.add(weapon);
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
        synchronized (game.getEnemies()) {
            for (Enemy enemy : game.getEnemies()) {
                if (enemy == null) {
                    continue;
                }
                fireEnemyWeapon(enemy);
                if (PhysicsHandler.checkSpriteCollisions(enemy, game.p1)) {
                }
                if (game.p2 != null && PhysicsHandler.checkSpriteCollisions(enemy, game.p2)) {
                } //
                for (int i = 0; i < game.getUserWeapons().size(); i++) {
                    Weapon w = game.getUserWeapons().get(i);
                    if (w != null) {
                        if (PhysicsHandler.checkSpriteCollisions(w, enemy)) {
                            w.applyDamage(enemy);
                            if (enemy.getHealth() <= 0) {
                            }
                            if (w.getDamageAmount() < 100 && System.currentTimeMillis() - w.getLastFire() > w.getRateOfFire()) {
                                game.getUserWeapons().remove(w);
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < game.getEnemyWeapons().size(); i++) {
            Weapon w = game.getEnemyWeapons().get(i);
            if (w == null) {
                continue;
            }
            if (PhysicsHandler.checkSpriteCollisions(w, game.p1)) {
                w.applyDamage(game.p1);
                System.out.println("USER HEALTH: " + game.p1.getHealth());
                if (game.p1.getHealth() <= 0) {
                    Point p = new Point(game.p1.getPosition());
                    GameObject explosion = LoadManager.getExplosion(true, p);
                    game.getExplosions().add(explosion);
                }
                game.getEnemyWeapons().remove(w);
            }
            if (game.p2 != null && PhysicsHandler.checkSpriteCollisions(w, game.p2)) {
            }
        }

        for (Bonus b : game.bonuses) {
            if (PhysicsHandler.checkSpriteCollisions(b, game.p1)) {
            }
            if (game.p2 != null && PhysicsHandler.checkSpriteCollisions(b, game.p2)) {
            }
        }

        game.getUserWeapons().removeAll(Collections.singletonList(null));
        game.getEnemyWeapons().removeAll(Collections.singletonList(null));

        for (int i = 0; i < game.getEnemies().size(); i++) {
            Enemy enemy = game.getEnemies().get(i);
            if (enemy != null && enemy.getHealth() <= 0) {
                Point p = new Point(enemy.getPosition());
                killEnemy(enemy);
                GameObject explosion = LoadManager.getExplosion(true, p);
                game.getExplosions().add(explosion);
            } else {
            }
        }
        tryToLoadNewLevel();

    }

    private void movePlayer() {
    }

    private void renderBattlefield(long elapsedTime) {
        Graphics2D g = FullScreenManager.getGraphics();
        g.clearRect(0, 0, 2560, 1600);
        g.drawImage(LoadManager.getImage("menu_background"), 0, 0, null);
        game.p1.update(elapsedTime);
        game.p1.paint(g);
        for (int i = 0; i < game.backgroundLayers.length; i++) {
            Image background = game.backgroundLayers[i];
            g.drawImage(background, 0, 0, null);
        }

        List<Enemy> enemies = game.getEnemies();
        synchronized (enemies) {
            for (Enemy e : enemies) {
                if (e == null) {
                    continue;
                }
                e.update(elapsedTime);
                e.paint(g);
            }
        }

        List<GameObject> explosions = game.getExplosions();
        synchronized (explosions) {
            for (int i = 0; i < explosions.size(); i++) {
                GameObject explosion = explosions.get(i);
                explosion.update(elapsedTime);
                if (explosion.getCurrentAnim().isLastFrame()) {
                    explosions.remove(explosion);
                    continue;
                }
                explosion.paint(g);
            }
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
                            k.paint(g);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }

        ArrayList<Weapon> enemyWeapons = game.getEnemyWeapons();
        synchronized (enemyWeapons) {
            if (!enemyWeapons.isEmpty()) {
                try {
                    for (Weapon k : enemyWeapons) {
                        if (k != null) {
                            if (System.currentTimeMillis() - k.getLastFire() > k.getAnimationDuration()) {
                                enemyWeapons.remove(k);
                                continue;
                            }
                            k.update(elapsedTime);
                            k.paint(g);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }

        if (doShowLevelCleared) {
            Image i = LoadManager.getImage("level_cleared");
            g.drawImage(i, 600, 600, null);
        } else if (doShowWaveCleared) {
            Image i = LoadManager.getImage("wave_cleared");
            g.drawImage(i, 600, 600, null);

        }


        g.dispose();
        FullScreenManager.update();
    }
}

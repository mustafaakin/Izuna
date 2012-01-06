package org.group1f.izuna;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.*;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import org.group1f.izuna.Contollers.*;
import org.group1f.izuna.Contollers.KeyboardHandler.Key;
import org.group1f.izuna.GUI.*;
import org.group1f.izuna.GameComponents.*;
import org.group1f.izuna.GameComponents.Drawing.Animation;

/**
 * @author Mustafa
 *
 */
public class GameCore {

    private static Preferences prefs = Preferences.userNodeForPackage(GameCore.class);
    private GameState game = new GameState();
    private boolean inMenu = true;
    private KeyboardHandler input;
    private Menu currentMenu;
    private GameObject backLayer;
    private GameObject frontLayer;
    private String enteredCharsSoFar = "";
    private int lastGameScore = 0;

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    /**
     * The game preferences.
     *
     * @return this game's preferences which are only specific to the user in
     * the computer
     */
    public static Preferences preferences() {
        return prefs;
    }

    /**
     * Starts the game.
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

    /**
     * 0: Easy, 1:Medium, 2: Hard
     *
     * @return the current difficulty
     */
    public static int getDifficulty() {
        return GameCore.preferences().getInt("difficulty", 0);
    }

    /*
     * in an infinite loop calculate time in miliseconds call updateBattlefield
     */
    private void gameLoop() {
        if (inMenu) {
            List<Image> images = currentMenu.getImagesToDraw();
            Graphics2D g = FullScreenManager.getGraphics();
            g.setFont(new Font("Arial", Font.BOLD, 26));
            g.clearRect(0, 0, 2560, 1600);
            Image background = LoadManager.getImage("menu_background");
            g.drawImage(background, 0, 0, null);
            for (Image i : images) {
                g.drawImage(i, 0, 0, null);
            }
            if (currentMenu instanceof HighScores) {
                HighScores h = (HighScores) currentMenu;
                int pos = 310;
                for (Score score : h.getHighScores()) {
                    String str = score.getName() + "  " + score.getHighScore();
                    g.drawString(str, 555, pos);
                    pos = pos + 35;
                }
            } else if (currentMenu instanceof GameOver) {
                g.drawImage(LoadManager.getImage("game_over"), 460, 230, null);
                g.drawString("YOUR SCORE IS: " + getLastGameScore(), 500, 500);
                g.drawString("Enter your name: " + enteredCharsSoFar, 500, 560);
            } else if (currentMenu instanceof Password) {
                g.drawString("Enter Password: " + enteredCharsSoFar, 500, 400);
            }
            g.dispose();
            FullScreenManager.update();
        } else {
            long elapsedTime = System.currentTimeMillis() - game.getCurrentTime();
            game.setCurrentTime(game.getCurrentTime() + elapsedTime);
            try {
                renderBattlefield(elapsedTime);
                updateBattlefield(elapsedTime);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Starts a game with either one player or two players.
     *
     * @param isSinglePlayer true if only one player will play, false if two
     * player is intended to play.
     */
    public void startGame(boolean isSinglePlayer) {
        game.setP1(LoadManager.getPlayer(1));
        if (!isSinglePlayer) {
            game.setP2(LoadManager.getPlayer(2));
        }
        LoadManager.loadLevels();

        game.setCurrentLevel(LoadManager.getNextLevel());
        AttackWave wave = game.getCurrentLevel().startLevel();
        initBackgrounds();

        List<Enemy> enemies = wave.startWave(System.currentTimeMillis());
        for (Enemy enemy : enemies) {
            int difficulty = GameCore.getDifficulty();
            enemy.setHealth(enemy.getHealth() + difficulty * 10);
            enemy.setPathActivationTime(System.currentTimeMillis());
            enemy.setStartingPosition();
            game.getEnemies().add(enemy);
        }
        inMenu = false;
        if (game.getBackgroundMusic() != null) {
            game.getBackgroundMusic().close();
        }
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
        enterMainMenu();
    }

    private void initBackgrounds() {
        Animation back = new Animation();
        Image i_back = LoadManager.getImage("background/" + game.getCurrentLevelNo());
        back.addFrame(i_back, i_back);

        this.backLayer = new GameObject(back) {

            int count = 0;

            @Override
            public void checkStateToAnimate() {
            }

            @Override
            public void update(long elapsedTime) {
                count++;
                if (count == 2) {
                    count = 0;
                } else {
                    return;
                }
                int width = this.getCurrentImage().getWidth(null);
                Point pos = getPosition();
                if (pos.x + width < 1280) {
                    return;
                }
                pos.translate(-1, 0);

                super.update(elapsedTime);

            }
        };

        Animation front = new Animation();
        Image i_front = LoadManager.getImage("background/stars.png");
        front.addFrame(i_front, i_front);

        this.frontLayer = new GameObject(front) {

            int count = 0;

            @Override
            public void checkStateToAnimate() {
            }

            @Override
            public void update(long elapsedTime) {
                count++;
                if (count == 5) {
                    count = 0;
                } else {
                    return;
                }
                int width = this.getCurrentImage().getWidth(null);
                Point pos = getPosition();
                if (pos.x + width < 1280) {
                    return;
                }
                pos.translate(-1, 0);
                super.update(elapsedTime);
            }
        };
    }

    /**
     * Game enters the main menu state
     */
    public void enterMainMenu() {
        game.setBackgroundMusic(LoadManager.getSoundEffect("main_menu"));
        game.getBackgroundMusic().play();
        game.setStartTime(System.currentTimeMillis());
        currentMenu = new MainMenu(this);
        game.setCurrentTime(game.getStartTime());
        inMenu = true;
    }

    /**
     * Game enters the Game over menu state
     */
    public void enterGameOver() {
        game.setBackgroundMusic(LoadManager.getSoundEffect("main_menu"));
        game.getBackgroundMusic().play();
        lastGameScore = game.getScore();
        currentMenu = new GameOver(this);
        inMenu = true;
        game = new GameState();
    }

    /**
     * For entering user name or level password, it returns the entered
     * characters by user so far
     *
     * @return
     */
    public String getEnteredCharsSoFar() {
        return enteredCharsSoFar;
    }

    /**
     * Different from game score, it holds the last score to store the score
     * when game state is lost.
     *
     * @return Last game score.
     */
    public int getLastGameScore() {
        return lastGameScore;
    }

    /**
     * GameState, which holds all the GameObject and provides synchronized
     * access to them
     *
     * @return all the game objects that is in the battlefield
     */
    public GameState getGame() {
        return game;
    }

    /**
     * If all the enemies are died, it waits for 5 seconds, and then loads the
     * next attack wave or the enemy or it finishes the game.
     */
    public void tryToLoadNewLevel() {
        long timeDifference = System.currentTimeMillis() - game.getLastEnemyDeath();
        if (timeDifference > 5000) {
            if (game.getCurrentLevel().isFinished()) {
                game.setCurrentLevel(LoadManager.getNextLevel());
                if (game.getCurrentLevel() == null) {
                    enterGameOver();
                } else {
                    game.setCurrentLevelNo(game.getCurrentLevelNo() + 1);
                    AttackWave wave = game.getCurrentLevel().startLevel();
                    initBackgrounds();
                    addWaveToGame(wave);
                }
                game.setShowLevelCleared(false);
                game.setShowWaveCleared(false);
            } else if (game.getCurrentLevel().getCurrentWave().isFinished()) {
                AttackWave wave = game.getCurrentLevel().swapNextWave();
                addWaveToGame(wave);
                game.setShowLevelCleared(false);
                game.setShowWaveCleared(false);
            }
        } else {
            if (game.getCurrentLevel().isFinished()) {
                game.setShowLevelCleared(true);
            } else if (game.getCurrentLevel().getCurrentWave().isFinished()) {
                game.setShowWaveCleared(true);
            } else {
                game.setShowLevelCleared(false);
                game.setShowWaveCleared(false);
            }
        }
    }

    /**
     * Removes the enemy from battlefield and tries to genereate a bonus from
     * the enemy.
     *
     * @param enemy the enemy to be removed
     */
    public void killEnemy(Enemy enemy) {
        List<Enemy> enemies = game.getEnemies();
        synchronized (enemies) {
            enemies.remove(enemy);
        }
        Random rand = new Random();
        int i = rand.nextInt(100);
        if (i < 10) { // 10% probability of having bonus
            Point position = new Point(enemy.getPosition());
            Bonus bonus = new Bonus(LoadManager.getAnim("bonus").clone(), (i % 4 + 1) * 10, i % 2 == 0); // %50 health, %50 bonus, added health varies from 10 to 30.

            Point start = new Point(position);
            Point end = new Point(position);

            end.y = start.y;
            end.x = -200; // The position that the weapon will disappear        
            LinearPath path = new LinearPath(start, end, Bonus.DEFAULT_BONUS_FALL_DURATION);
            bonus.addPath(path);
            bonus.setPathActivationTime(System.currentTimeMillis());
            this.game.getBonuses().add(bonus);
        }
        game.getCurrentLevel().killEnemy(enemy);
        game.setLastEnemyDeath(System.currentTimeMillis());

        int difficulty = GameCore.getDifficulty();
        double factor = 1;
        if (difficulty == 1) {
            factor = 1.25;
        } else if (difficulty == 2) {
            factor = 1.75;
        }
        game.increaseScore((int) (enemy.getDefaultHealth() * factor));
    }

    private void addWaveToGame(AttackWave wave) {
        List<Enemy> newEnemies = wave.startWave(System.currentTimeMillis());
        for (Enemy enemy : newEnemies) {
            enemy.setPathActivationTime(System.currentTimeMillis());
            enemy.setStartingPosition();
            game.getEnemies().add(enemy);
        }
    }

    /**
     * Checks whether the given password is valid for any levels and loads if a
     * password is for some level.
     */
    public void checkPassword() {
        LoadManager.loadLevels();
        if (LoadManager.isValidPassword(enteredCharsSoFar)) {
            int i = 0;
            while (i < 20000) {
                Level level = LoadManager.getNextLevel();
                if (level.getPassword().equals(enteredCharsSoFar)) {
                    game = new GameState();
                    game.setP1(LoadManager.getPlayer(1));
                    game.setCurrentLevel(level);
                    game.setCurrentLevelNo(i);
                    AttackWave wave = game.getCurrentLevel().startLevel();
                    initBackgrounds();
                    addWaveToGame(wave);
                    inMenu = false;
                    return;
                }
                i++;
            }
        }
        this.currentMenu = new MainMenu(this);
    }

    /**
     * Adds the typed character, if its key code is between 32 and 128.
     *
     * @param character the character typed
     */
    public void keyTypeFromKeyboard(char character) {
        if (character > 32 && character < 128) {
            if (inMenu) {
                if (currentMenu instanceof GameOver) {
                    enteredCharsSoFar += character;
                } else if (currentMenu instanceof Password && enteredCharsSoFar.length() < 4) {
                    enteredCharsSoFar += character;
                    enteredCharsSoFar = enteredCharsSoFar.toUpperCase();
                }
            }
        }
    }

    /**
     * The observer, that is notified from the keyboard handler
     *
     * @param key The key that is pressed or released
     * @param isPressed whether key is pressed or released
     */
    public void inputFromKeyboard(Key key, boolean isPressed) {
        if (key == null) {
            return;
        }
        if (key.equals(Key.Backspace)
                && isPressed && inMenu
                && currentMenu instanceof GameOver
                && enteredCharsSoFar.length() > 0) {
            enteredCharsSoFar = enteredCharsSoFar.substring(0, enteredCharsSoFar.length() - 1);
            return;
        }
        Set<Key> pressed = input.getActive();
        if (!inMenu && isPressed) {
            if (pressed.contains(Key.Player1_Weapon1)) {
                fireUserWeapon("proton_player1", true);
            }
            if (pressed.contains(Key.Player1_Weapon2)) {
                fireUserWeapon("plasma_player1", true);
            }
            if (pressed.contains(Key.Player1_Weapon3)) {
                fireUserWeapon("particle", true);
            }
            if (pressed.contains(Key.Player1_Weapon4)) {
                fireUserWeapon("dark_matter", true);
            }
            if (pressed.contains(Key.Player1_Weapon5)) {
                fireUserWeapon("super_desperation", true);
            }


            if (game.getP2() != null) {
                if (pressed.contains(Key.Player2_Weapon1)) {
                    fireUserWeapon("proton_player2", false);
                }
                if (pressed.contains(Key.Player2_Weapon2)) {
                    fireUserWeapon("plasma_player2", false);
                }
                if (pressed.contains(Key.Player2_Weapon3)) {
                    fireUserWeapon("particle", false);
                }
                if (pressed.contains(Key.Player2_Weapon4)) {
                    fireUserWeapon("dark_matter", false);
                }
                if (pressed.contains(Key.Player2_Weapon5)) {
                    fireUserWeapon("super_desperation", false);
                }
            }
        }

        movePlayer(true, pressed, isPressed);
        if (game.getP2() != null) {
            movePlayer(false, pressed, isPressed);
        }

        if (inMenu && isPressed) {
            currentMenu.onClicked(key);
        }
    }

    private void fireUserWeapon(String key, boolean isFirstPlayer) {
        Weapon weapon = (isFirstPlayer ? game.getP1() : game.getP2()).fire(key, System.currentTimeMillis());
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

    private void updateBattlefield(long elapsedTime) {
        // Neccesary collisions needed to be calculated::
        // Players - Enemies
        synchronized (game.getEnemies()) {
            for (Enemy enemy : game.getEnemies()) {
                if (enemy == null) {
                    continue;
                }
                fireEnemyWeapon(enemy);
                if (PhysicsHandler.checkSpriteCollisions(enemy, game.getP1())) {
                    enterGameOver();
                }
                if (game.getP2() != null && PhysicsHandler.checkSpriteCollisions(enemy, game.getP2())) {
                    enterGameOver();
                } //
                for (int i = 0; i < game.getUserWeapons().size(); i++) {
                    Weapon w = game.getUserWeapons().get(i);
                    if (w != null) {
                        if (PhysicsHandler.checkSpriteCollisions(w, enemy)) {
                            w.applyDamage(enemy);

                            LoadManager.getAnExplosionSound().play();
                            GameObject explosion = LoadManager.getExplosion(false, w.getPosition());
                            game.getExplosions().add(explosion);

                            if (enemy.getHealth() <= 0) {
                                LoadManager.getAnExplosionSound().play();
                            }
                            if (w.getType() == 0) {
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
            if (PhysicsHandler.checkSpriteCollisions(w, game.getP1())) {
                w.applyDamage(game.getP1());
                if (game.getP1().getHealth() <= 0) {
                    enterGameOver();
                }

                Point p = new Point(game.getP1().getPosition());
                GameObject explosion = LoadManager.getExplosion(game.getP1().getHealth() <= 0, p);
                game.getExplosions().add(explosion);

                LoadManager.getAnExplosionSound().play();
                game.getEnemyWeapons().remove(w);
            }

            if (game.getP2() != null && PhysicsHandler.checkSpriteCollisions(w, game.getP2())) {
                w.applyDamage(game.getP2());
                if (game.getP2().getHealth() <= 0) {
                    enterGameOver();
                }

                Point p = new Point(game.getP2().getPosition());
                GameObject explosion = LoadManager.getExplosion(game.getP2().getHealth() <= 0, p);
                game.getExplosions().add(explosion);

                LoadManager.getAnExplosionSound().play();
                game.getEnemyWeapons().remove(w);
            }
        }

        synchronized (game.getBonuses()) {
            for (Bonus b : game.getBonuses()) {
                if (PhysicsHandler.checkSpriteCollisions(b, game.getP1())) {
                    b.applyBonus(game.getP1());
                    game.getBonuses().remove(b);
                    LoadManager.getSoundEffect("bonus").play();
                }
                if (game.getP2() != null && PhysicsHandler.checkSpriteCollisions(b, game.getP2())) {
                    b.applyBonus(game.getP2());
                    game.getBonuses().remove(b);
                    LoadManager.getSoundEffect("bonus").play();
                }
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

    /**
     * tries to move player
     *
     * @param isFirstPlayer true if P1 false if P2
     * @param activeKeys the keys that have been pressed but not released yet
     * @param isPressed whether the action is a press or release action
     */
    private void movePlayer(boolean isFirstPlayer, Set<Key> activeKeys, boolean isPressed) {
        Player player = isFirstPlayer ? game.getP1() : game.getP2();
        if (!inMenu && isPressed) {
            if (activeKeys.contains(isFirstPlayer ? Key.Player1_Left : Key.Player2_Left)) {
                player.setvX(-3);
            }
            if (activeKeys.contains(isFirstPlayer ? Key.Player1_Right : Key.Player2_Right)) {
                player.setvX(3);
            }
            if (activeKeys.contains(isFirstPlayer ? Key.Player1_Up : Key.Player2_Up)) {
                player.setvY(-3);
            }
            if (activeKeys.contains(isFirstPlayer ? Key.Player1_Down : Key.Player2_Down)) {
                player.setvY(3);
            }
        }
        if (!inMenu && !isPressed) {
            if (!activeKeys.contains(isFirstPlayer ? Key.Player1_Down : Key.Player2_Down) || activeKeys.contains(isFirstPlayer ? Key.Player1_Up : Key.Player2_Up)) {
                player.setvY(0);
            }
            if (!activeKeys.contains(isFirstPlayer ? Key.Player1_Left : Key.Player2_Left) || activeKeys.contains(isFirstPlayer ? Key.Player1_Right : Key.Player2_Right)) {
                player.setvX(0);
            }
        }
    }

    /**
     * Renders the battlefield from game objects
     *
     * @param elapsedTime
     */
    private void renderBattlefield(long elapsedTime) {
        Graphics2D g = FullScreenManager.getGraphics();
        g.clearRect(0, 0, 2560, 1600);

        this.backLayer.update(elapsedTime);
        this.frontLayer.update(elapsedTime);

        this.backLayer.paint(g);
        this.frontLayer.paint(g);

        game.getP1().update(elapsedTime);
        game.getP1().paint(g);

        if (game.getP2() != null) {
            game.getP2().update(elapsedTime);
            game.getP2().paint(g);
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
                            if (k.getType() > 0) {
                                if (System.currentTimeMillis() - k.getLastFire() > k.getAnimationDuration()) {
                                    userWeapons.remove(k);
                                    continue;
                                }
                            } else {
                                if (k.getPosition().x >= 1280) {
                                    userWeapons.remove(k);
                                    continue;
                                }
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
                            if (k.getType() > 0) {
                                if (System.currentTimeMillis() - k.getLastFire() > k.getAnimationDuration()) {
                                    enemyWeapons.remove(k);
                                    continue;
                                }
                            } else {
                                if (k.getPosition().x <= 0) {
                                    enemyWeapons.remove(k);
                                    continue;
                                }
                            }
                            k.update(elapsedTime);
                            k.paint(g);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }


        ArrayList<Bonus> bonus = game.getBonuses();
        synchronized (bonus) {
            if (!bonus.isEmpty()) {
                try {
                    for (Bonus k : bonus) {
                        if (k != null) {
                            k.update(elapsedTime);
                            k.paint(g);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (game.isShowLevelCleared()) {
            Image i = LoadManager.getImage("level_cleared");
            g.drawImage(i, 390, 200, null);
        } else if (game.isShowWaveCleared()) {
            Image i = LoadManager.getImage("wave_cleared");
            g.drawImage(i, 390, 200, null);

        }

        g.setFont(new Font("Arial", Font.BOLD, 14));

        g.drawString(game.getP1().getStatusText(1), 15, 700);
        if (game.getP2() != null) {
            g.drawString(game.getP2().getStatusText(2), 15, 15);
        }

        g.dispose();
        FullScreenManager.update();
    }
}

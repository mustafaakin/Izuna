/**
 *
 */
package org.group1f.izuna;

import java.awt.Graphics2D;
import java.awt.Image;
import org.group1f.izuna.GameComponents.Drawing.*;

import java.awt.Point;
import java.io.File;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.group1f.izuna.Contollers.FullScreenManager;
import org.group1f.izuna.Contollers.KeyboardHandler;
import org.group1f.izuna.Contollers.KeyboardHandler.Key;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.Contollers.PhysicsHandler;
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
    private void gameLoop() {       
        if (inMenu) {
            Image background = LoadManager.getImage("menu_background");
            Image a[] = new Image[12];
            
            a[0] = LoadManager.getMenuImage("main", "startGame");
            a[1] = LoadManager.getMenuImage("main", "options");
            a[2] = LoadManager.getMenuImage("main", "password");
            a[3] = LoadManager.getMenuImage("main", "highScores");
            a[4] = LoadManager.getMenuImage("main", "help");           
            a[5] = LoadManager.getMenuImage("main", "exit");
                        
            a[6+0] = LoadManager.getMenuImage("main", "startGameR");
            a[6+1] = LoadManager.getMenuImage("main", "optionsR");
            a[6+2] = LoadManager.getMenuImage("main", "passwordR");
            a[6+3] = LoadManager.getMenuImage("main", "highScoresR");
            a[6+4] = LoadManager.getMenuImage("main", "helpR");           
            a[6+5] = LoadManager.getMenuImage("main", "exitR");
                        
            Graphics2D g = FullScreenManager.getGraphics();
            g.drawImage(background, 0, 0, null);
            
            for (int i = 0; i < 6; i++) {
                if ( i != active){
                    g.drawImage(a[i], 0, 0, null);
                } else{
                    g.drawImage(a[i+6], 0, 0, null);                    
                }
            }
            
            FullScreenManager.update();
            g.dispose();
            
        } else {
            renderBattlefield();
            updateBattlefield();
            movePlayer();
        }
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
    }

    // check player inputs for two players
    public void inputFromKeyboard(Key key, boolean isPressed) {
        if ( key == null)
            return;
        if ( key.equals(Key.Escape))
            System.exit(0);
        
        if ( inMenu && isPressed){
            if ( key.equals(Key.Player1_Down)){
                active = (active + 1) % 6;
            } else if ( key.equals(Key.Player1_Up)){
                active = (active + 5) % 6;
            }
        } else{
            
        }
    }

    /*
     * In this class the operations below needs to be implemented in this order
     * 1) if the level is over load next level 2) update all gameobjects
     * (sprites and sounds) 3) check neccessary collisions using physics class
     * and change states of the gameobjects accordingly
     */
    private void updateBattlefield() {
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
        for (Sprite gameObj : game.getAll()) {
            Animation currentAnimation = gameObj.getCurrentAnim();
            Image currentImage = currentAnimation.getImage();
            int x = gameObj.getPosition().x;
            int y = gameObj.getPosition().y;
            g.drawImage(currentImage, x, y, null);
        }
        FullScreenManager.update();
        FullScreenManager.getGraphics().dispose();
    }
}

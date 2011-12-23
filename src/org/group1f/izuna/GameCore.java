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
import org.group1f.izuna.Contollers.FullScreenManager;
import org.group1f.izuna.Contollers.KeyboardHandler;
import org.group1f.izuna.Contollers.KeyboardHandler.Key;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.Contollers.PhysicsHandler;
import org.group1f.izuna.GUI.Menu;
import org.group1f.izuna.GUI.MenuElement;
import org.group1f.izuna.GameComponents.*;

/**
 * @author Mustafa
 *
 */
public class GameCore {

    private static Preferences prefs = Preferences.userNodeForPackage(GameCore.class);
    public GameState game = new GameState();
    public boolean inMenu = true;
    public Menu activeMenu = null;
    public KeyboardHandler input;

    public static Preferences preferences() {
        return prefs;
    }

    public static void main(String[] args) {
        GameCore core = new GameCore();
        core.initialize();
      /*
        while (true) {
            core.gameLoop();
            try {
                Thread.sleep(Animation.FRAME_DURATION);
            } catch (InterruptedException e) {
            }
        } */
    }

    /*
     * in an infinite loop calculate time in miliseconds call updateBattlefield
     */
    private void gameLoop() {
        checkInput();
        if (inMenu) {
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
        input = new KeyboardHandler();
        
        //FullScreenManager.initGraphics();
        //FullScreenManager.getFullScreenWindow().addKeyListener(input);
        LoadManager.init();
    }

    // check player inputs for two players
    private void checkInput() {
        if (input.getKeys().contains(Key.Player1_Down)) {
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
        FullScreenManager.getGraphics().dispose();
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
    }
}

/**
 *
 */
package org.group1f.izuna;

import org.group1f.izuna.GameComponents.Drawing.*;

import java.awt.Point;
import java.util.prefs.Preferences;
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

    public static Preferences preferences() {
        return prefs;
    }

    /**
     * @param args
     */
    /*
     * initialize then call gameloop
     */
    public static void main(String[] args) throws Exception {
        Menu main = new Menu();
    }
    /*
     * in an infinite loop calculate time in miliseconds call updateBattlefield
     */

    public void gameLoop() {
    }

    /*
     * initialize full screen window initialize inputs set backround pictures
     * that will be used in levels
     */
    public void initialize() {
        LoadManager.init();
    }

    // check player inputs for two players
    public void checkInput() {
    }

    /*
     * In this class the operations below needs to be implemented in this order
     * 1) if the level is over load next level 2) update all gameobjects
     * (sprites and sounds) 3) check neccessary collisions using physics class
     * and change states of the gameobjects accordingly
     */
    public void updateBattlefield() {
        // Neccesary collisions needed to be calculated::
        // Players - Enemies
        // Players - EnemyWeapons
        // Enemies - UserWeapons
        // Players - Bonuses
        for (Enemy e : game.enemies) {
            if (PhysicsHandler.checkSpriteCollisions(e, game.p1)) {
            }
            if (game.p2 != null && PhysicsHandler.checkSpriteCollisions(e, game.p2)) {
            }
        }
    }

    public void movePlayer() {
    }
}

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
    Graphics2D g;
    SoundEffect backMusic;

    public static void main(String[] args) throws Exception {
        GameCore core = new GameCore();
        core.initialize();
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
        FullScreenManager.initGraphics();
        LoadManager.init();
        g = FullScreenManager.getGraphics();
        Image i = LoadManager.getImage("menu_background");
        backMusic = new SoundEffect("data/sounds/main_menu.mp3");
        backMusic.play();

        Image ship = null;
        try {
            ship = ImageIO.read(new File("data/image/animation/enemies/deneme/images/redShip03.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        QuadraticPath path = new QuadraticPath(new Point(100, 100), new Point(520,620), new Point(135, 110), 2500);
        long start = System.currentTimeMillis();
        path.setStartTime(start);
        while (true) {
            long t = System.currentTimeMillis();
            if ( t > start + 2500)
                break;
            Point p = path.getPosition(t);
            g = FullScreenManager.getGraphics();
            g.drawImage(i, 0, 0, null);
            g.drawImage(ship, p.y, p.x, null);
            g.dispose();
            FullScreenManager.update();
            try {
                Thread.sleep(1000 / 60);
            } catch (Exception e) {
            }
        }
        System.out.println("hello..");
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

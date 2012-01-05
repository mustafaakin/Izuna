package org.group1f.izuna.Contollers;

import java.awt.Point;
import java.awt.Rectangle;
import org.group1f.izuna.GameComponents.Drawing.Sprite;
import org.group1f.izuna.GameComponents.Player;
import org.group1f.izuna.GameState;

/**
 * 
 * @author Mustafa
 */
public final class PhysicsHandler {

    /**
     * 
     * @param s1
     * @param s2
     * @return
     */
    public static boolean checkSpriteCollisions(Sprite s1, Sprite s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        if (s1 == s2) {
            return false;
        }
        Rectangle r1 = s1.getCollisionRectangle();
        Rectangle r2 = s2.getCollisionRectangle();
        return r1.intersects(r2);
    }
    /*
     * TODO need to implement a method to make sure player is always in the
     * screen
     */

    /**
     * 
     * @param player
     * @param position
     */
    public static void movePlayer(Player player, Point position) {
    }

    /**
     * 
     * @param gs
     */
    public static void moveAI(GameState gs) {
        // Moves AI to the proper places according to their paths and the current time..
    }
}

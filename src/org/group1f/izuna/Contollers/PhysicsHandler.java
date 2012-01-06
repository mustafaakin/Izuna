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
     * Checks if the given two sprite collides or not.
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
}

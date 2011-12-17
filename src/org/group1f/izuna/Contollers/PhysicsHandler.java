package org.group1f.izuna.Contollers;

import java.awt.Point;
import java.awt.Rectangle;
import org.group1f.izuna.GameComponents.Drawing.*;
import org.group1f.izuna.GameComponents.Player;
import org.group1f.izuna.GameState;

public final class PhysicsHandler {

    public static boolean checkSpriteCollisions(Sprite s1, Sprite s2) {
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

    public static void movePlayer(Player player, Point position) {
    }

    public static void moveAI(GameState gs) {
        // Moves AI to the proper places according to their paths and the current time..
    }
}

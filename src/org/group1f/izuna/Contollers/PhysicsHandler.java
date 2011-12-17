package org.group1f.izuna.Contollers;

import org.group1f.izuna.GameComponents.Drawing.*;


public final class PhysicsHandler {
    
    static boolean checkSpriteCollisions (Sprite s1, Sprite s2)
    {
        if(s1 == s2)
            return false;
        return s1.getCollisionRectangle().intersects(s2.getCollisionRectangle());
    }
    
    /*
     * need to implement a method to make sure player is always in the screen
     */
    

}

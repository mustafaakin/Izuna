package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Enemy extends AIControllable implements SpaceShip {
	
    public Enemy(Point currentPos, Animation rest)
    {
            super(currentPos, rest);
    }
    
    
    public void checkStateToAnimate()
    {
            
    }
}

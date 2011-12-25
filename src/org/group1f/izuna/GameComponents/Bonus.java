package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Bonus extends AIControllable {
    
    private boolean bonusType; // default = health, 1 = weapon
    private int bonusValue;
	
    public Bonus(Point currentPos, Animation rest)
    {
        super(currentPos, rest);
    }
    
     public void checkStateToAnimate()
     {
            
     }
}

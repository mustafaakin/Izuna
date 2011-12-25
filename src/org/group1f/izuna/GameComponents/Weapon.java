package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Weapon extends AIControllable {
    
    private int damageAmount;
    private int rateOfFire;
    
    public Weapon(Point currentPos, Animation rest, int damageAmount, int rateOfFire)
    {
            super(currentPos, rest);
            this.damageAmount = damageAmount;
            this.rateOfFire = rateOfFire;
    }
  /*  
    public Weapon copy()
    {
        Animation tmp = getRestAnimation();
        return new Weapon(pos, tmp.clone(), damageAmount, rateOfFire );
    }
    */
    
    public void checkStateToAnimate()
    {
            
    }
    
    
    
}

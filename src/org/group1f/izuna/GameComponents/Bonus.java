package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Bonus extends AIControllable {
    
    public static final int STATE_FIRED = 0;
    public static final int STATE_HIT = 1;
    
    private int state;
    private boolean bonusType; // default = health, 1 = weapon
    private int bonusValue;
	
    public Bonus(Point currentPos, Animation rest, int value, boolean type)
    {
        super(currentPos, rest);
        bonusValue = value;
        bonusType = type;
        state = STATE_FIRED;
    }
    
    public Bonus clone()
    {
        return new Bonus(getPosition(), getRestAnimation().clone(), bonusValue, bonusType);
    }
    
    public void checkStateToAnimate()
    {
        currentAnimation = getRestAnimation();
    }
     
    public void update( long elapsedTime )
    {
        checkStateToAnimate();
        super.update( elapsedTime);
    }
     
    public void setState(int state) 
    {
        if (this.state != state) 
        {
            this.state = state;
            if (state == STATE_HIT) 
            {
                setvX(0);
                setvY(0);
            }
        }
    }
     
    public void applyBonus(Player p)
    {
        if(bonusType) {
            p.setHealth(p.getHealth() + bonusValue);
        } else { // later on add random generator
            p.incrDMcount();
            p.incrPScount();
            p.incrSPcount();
        }
    }
    
    public float getMaxSpeed()
    {
        return 0.5f;
    }

}

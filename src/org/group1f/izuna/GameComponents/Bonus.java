package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Bonus extends AIControllable {
    
    public enum BonusState {
        STATE_FIRED, STATE_HIT
    }
    
    private final static long DEFAULT_BONUS_FALL_DURATION = 750;
    private BonusState state;
    
    private boolean bonusType; // default = health, 1 = weapon
    private int bonusValue;
	
    public Bonus(Point currentPos, Animation rest, SoundEffect dieSound, Point spawnPosition, int value, boolean type)
    {
        super(currentPos, rest);
        Point endPosition = new Point(spawnPosition);
        endPosition.x = 1280 + 50; // Outside the Canvas
        LinearPath path = new LinearPath(spawnPosition, endPosition, DEFAULT_BONUS_FALL_DURATION);
        
        bonusValue = value;
        bonusType = type;
        state = BonusState.STATE_FIRED;
    }
    
    public Bonus clone() // ses sorunu varsa clone methodundandır
    {
        return null;
// TODO Will be fixed.      
        //return new Bonus(getPosition(), getStillAnimation().clone(), dieSound, defaultPath, bonusValue, bonusType);
    }
    
    public void checkStateToAnimate()
    {
        currentAnimation = getStillAnimation();
    }
     
    public void update( long elapsedTime )
    {
        checkStateToAnimate();
        super.update( elapsedTime);
    }
     
    public void setState(BonusState state) 
    {
        if (this.state != state) 
        {
            this.state = state;
            if (state == BonusState.STATE_HIT) 
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

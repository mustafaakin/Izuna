package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Bonus extends AIControllable {
    
    enum State {
        STATE_FIRED, STATE_HIT
    }
    
    private State state;
    private boolean bonusType; // default = health, 1 = weapon
    private int bonusValue;
	
    public Bonus(Point currentPos, Animation rest, SoundEffect dieSound,Path path, int value, boolean type)
    {
        super(currentPos, rest, dieSound,path);
        bonusValue = value;
        bonusType = type;
        state = State.STATE_FIRED;
    }
    
    public Bonus clone() // ses sorunu varsa clone methodundandır
    {
        return new Bonus(getPosition(), getRestAnimation().clone(), dieSound, defaultPath, bonusValue, bonusType);
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
     
    public void setState(State state) 
    {
        if (this.state != state) 
        {
            this.state = state;
            if (state == State.STATE_HIT) 
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

package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Weapon extends AIControllable {
    
    public static final int STATE_FIRED = 0;
    public static final int STATE_HIT = 1;
    public static final int STATE_EXPLODE = 2;
    
    private int state;
    private int damageAmount;
    private int rateOfFire;
    private Animation die; //this animation shows up when it damages to spaceship
    private Animation explode; // this animation shows up when it cause spaceship explode,
    //so there wont be needed extra sprites for other spaceships
    
    public Weapon(Point currentPos, Animation rest, Animation die, Animation explode, int damageAmount, int rateOfFire)
    {
            super(currentPos, rest);
            this.die = die;
            this.explode = explode;
            state = STATE_FIRED;
            this.damageAmount = damageAmount;
            this.rateOfFire = rateOfFire;
    }
    
    public Weapon clone()
    {
        return new Weapon(getPosition(), getRestAnimation().clone(), die.clone(), explode.clone(), damageAmount, rateOfFire );
    }
    
    
    public void checkStateToAnimate()
    {
        Animation newAnim = currentAnimation;
         
        if(state == STATE_HIT) {
             newAnim = die;
             setvX(0);
             setvY(0);
        }
           
        else if (state == STATE_EXPLODE) {
            newAnim = die;
            setvX(0);
            setvY(0);
        }
        
        if(currentAnimation != newAnim) {
            currentAnimation = newAnim;
            currentAnimation.startOver();
        }
    }
    
    public void update (long elapsedTime)
    {
         checkStateToAnimate();
         super.update( elapsedTime);
    }
    

    public void applyDamege(SpaceShip a)
    {
        if(damageAmount >= a.getHealth())
            state = STATE_EXPLODE;
        else
            state = STATE_HIT;
        a.setHealth(a.getHealth() - damageAmount);
    }
    
    public int getState()
    {
        return state;
    }
 }

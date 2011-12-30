package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Weapon extends AIControllable {
    
    enum State {
        STATE_FIRED, STATE_HIT, STATE_EXPLODE
    }

    State state;
    private int damageAmount;
    private int rateOfFire;
    private Animation die; //this animation shows up when it damages to spaceship
    private Animation explode; // this animation shows up when it cause spaceship explode,
    //so there wont be needed extra sprites for other spaceships
    private SoundEffect fireSound;
    private SoundEffect explodeSound;
    
    public Weapon(Point currentPos, Animation rest, SoundEffect dieSound,Path path, Animation die, Animation explode, int damageAmount, int rateOfFire, SoundEffect fireSound,SoundEffect explodeSound)
    {
            super(currentPos, rest, dieSound,path);
            this.die = die;
            this.explode = explode;
            this.fireSound = fireSound;
            this.explodeSound = explodeSound;
            state = State.STATE_FIRED;
            this.damageAmount = damageAmount;
            this.rateOfFire = rateOfFire;
    }
    
    @Override
    public Weapon clone()
    {
        return new Weapon(getPosition(), getRestAnimation().clone(),dieSound, defaultPath,
                die.clone(), explode.clone(), damageAmount, rateOfFire ,fireSound ,explodeSound);
    }
    
    
    @Override
    public void checkStateToAnimate()
    {
        Animation newAnim = currentAnimation;
         
        if(state == State.STATE_HIT) {
             newAnim = die;
             setvX(0);
             setvY(0);
        }
           
        else if (state == State.STATE_EXPLODE) {
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
            state = State.STATE_EXPLODE;
        else
            state = State.STATE_HIT;
        a.setHealth(a.getHealth() - damageAmount);
    }
    
    public State getState()
    {
        return state;
    }
    
    public int getExplodeTime()
    {
        return 1000;
    }
 }

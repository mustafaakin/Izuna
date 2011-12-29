package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Enemy extends AIControllable implements SpaceShip {
    private boolean isDying;
    private Animation rollLeft;
    private Animation rollRight;
    private SoundEffect rollSound;
    private int health;
    
    public Enemy(Point currentPos, Animation rest, Animation rollLeft, Animation rollRight, int health)
    {
        super(currentPos, rest);
        this.rollLeft = rollLeft;
        this.rollRight = rollRight;
        this.health = health;
        isDying = false;
    }
    
    // will be fixed // for now its broken
    public void checkStateToAnimate()
    {
        Animation newAnim = currentAnimation;

        if(getvY() < 0) {
            newAnim = rollLeft;
            currentSound = rollSound;
        }
            
        if(getvY() > 0) {
            newAnim = rollRight;
            currentSound = rollSound;
        }
        
        if(health < 1) {
            setState();
            currentSound = dieSound;
            isDying = true;
        }
        
        if(isDying && currentAnimation.getElapsedTime() >= getDieTime()) {
            setVisible(false);
        }
        
        if(currentAnimation != newAnim) {
            currentAnimation = newAnim;
            currentAnimation.startOver();
        }
    }
    
    public void update(long elapsedTime) {
        checkStateToAnimate();
        super.update(elapsedTime);
    }
    
    private void setState() {
        if(health < 1) {
            setvX(0.0f);
            setvY(0.0f);
        }
    }
    
    @Override
    public int getDieTime() {
        return 1000;
    }
                
    @Override
     public void setHealth( int newValue ) {
        if(newValue > 100 )
            health = 100;
        else
            health = newValue;
    }
    
    @Override
    public int getHealth() {
        return health;
    }    
    
    @Override
    public float getMaxSpeed()
    {
        return 1.0f;
    }
    
    
}

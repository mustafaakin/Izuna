package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Player extends GameObject implements SpaceShip {
    private boolean isDying;
    private Animation rollLeft;
    private Animation rollRight;
    private SoundEffect rollSound;
    private Animation dying;
    private int health;
    //proton cannon and plasma cutter is infinite
    private int particleSeparatorCount;
    private int darkMatterCount;
    private int superDesperationMoveCount;
    

    public Player(Point currentPos, Animation rest, Animation rollLeft, Animation rollRight)
    {
        super(currentPos, rest);
        this.rollLeft = rollLeft;
        this.rollRight = rollRight;
        health = 100;
        isDying = false;
        particleSeparatorCount = 4;
        darkMatterCount = 2;
        superDesperationMoveCount = 1;
    }

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
            newAnim = dying;
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
    
    public int getPScount()
    {
        return particleSeparatorCount;
    }
    
    public int getDMcount()
    {
        return darkMatterCount;
    }
    
     public int getSPcount()
    {
        return superDesperationMoveCount;
    }
     
     public void decrPScount()
     {
         particleSeparatorCount--;
     }
     
      public void decrSPcount()
     {
         superDesperationMoveCount--;
     }
      
     public void decrDMcount()
     {
         darkMatterCount--;
     }
     
      public void incrPScount()
     {
         particleSeparatorCount++;
     }
     
      public void incrSPcount()
     {
         superDesperationMoveCount++;
     }
      
     public void incrDMcount()
     {
         darkMatterCount++;
     }
    
    
    @Override
    public int getDieTime() {
        return 1000;
    }
                
    @Override
    public void setHealth( int damage ) {
        health -= damage;
    }
    
    @Override
    public int getHealth() {
        return health;
    }    
    
    
  
    @Override
    public float getMaxSpeed()
    {
        return 0.8f;
    }
}

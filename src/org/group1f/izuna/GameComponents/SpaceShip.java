package org.group1f.izuna.GameComponents;
import org.group1f.izuna.GameComponents.Drawing.*;

public interface SpaceShip {

    float getMaxSpeed();
    
    void setHealth( int damage );
    
    public int getHealth();
    
    int getDieTime();
    
}

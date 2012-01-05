package org.group1f.izuna.GameComponents;

/**
 * 
 * @author Mustafa
 */
public interface SpaceShip {

    /**
     * 
     * @return
     */
    float getMaxSpeed();

    /**
     * 
     * @param damage
     */
    void setHealth(int damage);

    /**
     * 
     * @return
     */
    public int getHealth();

    /**
     * 
     * @return
     */
    int getDieTime();

    /**
     * 
     * @param key
     * @param time
     * @return
     */
    Weapon fire(String key, long time);
}

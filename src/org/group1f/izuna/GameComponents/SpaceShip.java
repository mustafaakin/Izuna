package org.group1f.izuna.GameComponents;

public interface SpaceShip {

    float getMaxSpeed();

    void setHealth(int damage);

    public int getHealth();

    int getDieTime();

    Weapon fire(String key, long time);
}

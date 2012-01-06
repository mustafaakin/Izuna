package org.group1f.izuna.GameComponents;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;
import org.group1f.izuna.GameComponents.Drawing.*;
import org.group1f.izuna.GameCore;

/**
 * The Enemy class inherits from AIControllable class and SpaceShip class. 
 * As different from spaceship, it has a list of Weapon to damage to the
 * user and enters the scene with a sound.
 * @author Nail AKINCI
 */
public class Enemy extends AIControllable implements SpaceShip {

    private long lastFired = 0;
    private Weapon defaultWeapon;
    private boolean isDying;
    private Animation rollLeft;
    private Animation rollRight;
    private SoundEffect enteringSound;
    private int health;
    private boolean isRFinished = true;
    private int defaultHealth;

    /**
     * Constructs a default enemy object with given parameters.
     *
     * @param still default animation
     * @param rollLeft left roll animation
     * @param rollRight right roll animation
     * @param health Health of the enemy
     * @param defaultWeapon default weapon of the enemy
     */
    public Enemy(Animation still, Animation rollLeft, Animation rollRight, int health, Weapon defaultWeapon) {
        super(still);
        this.rollLeft = rollLeft;
        this.rollRight = rollRight;
        this.health = health;
        this.defaultHealth = health;
        isDying = false;
        this.defaultWeapon = defaultWeapon;
    }

    /**
     * 
     * @return
     */
    public Weapon getDefaultWeapon() {
        return defaultWeapon;
    }

    /**
     * 
     * @return
     */
    public int getDefaultHealth() {
        return defaultHealth;
    }

   /**
     * Sets the default weapon.
     *
     * @param defaultWeapon weapon
     */
    public void setDefaultWeapon(Weapon defaultWeapon) {
        this.defaultWeapon = defaultWeapon;
    }

    @Override
    public Enemy clone() {
        return new Enemy(super.getStillAnimation().clone(), rollLeft.clone(), rollRight.clone(), health, defaultWeapon.clone());
    }

    /**
     * Arranges and changes the current animation with respect to their speed and direction.
     */
    @Override
    public void checkStateToAnimate() {
        Animation newAnim = currentAnimation;
        if (!isRFinished) {
            isRFinished = currentAnimation.finished();
        }

        if (getvY() < 0) {
            if (getOldvY() > 0) { //
                isRFinished = currentAnimation.refine();
            } else {
                newAnim = rollLeft;
            }
        } else if (getvY() > 0) {
            if (getOldvY() < 0) { //
                isRFinished = currentAnimation.refine();
            } else {
                newAnim = rollRight;
            }
        } else { // vy = 0
            if (getOldvY() != 0) {
                isRFinished = currentAnimation.refine();
            } else {
                newAnim = getStillAnimation();
            }
        }

        if (health < 1) {
            setState();
            if (!isDying) {
                //      getDieSound().play();
            }
            isDying = true;
        }

        if (isDying && currentAnimation.getElapsedTime() >= getDieTime()) {
            setVisible(false);
        }

        if (isRFinished && currentAnimation != newAnim) {
            currentAnimation = newAnim;
            currentAnimation.startOver();
        }
        // oldvY = getvY();
    }

    /**
     * Updates the animation and position according to elapsed time.
     * @param elapsedTime passed time.
     */
    @Override
    public void update(long elapsedTime) {
        checkStateToAnimate();
        super.update(elapsedTime);
    }

    private void setState() {
        if (health < 1) {
            //setvX(0.0f);
            // setvY(0.0f);
        }
    }

    /**
     * Returns dying time before its disposed.
     * @return dying time in milliseconds.
     */
    @Override
    public int getDieTime() {
        return 1000;
    }
    
    /**
     * Changes Health with its new value to damage enemies.
     * @param newValue integer Health
     */
    @Override
    public void setHealth(int newValue) {
        health = newValue;
    }
    
    /**
     * Returns Remaining health of the enemy.
     * @return integer Health
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     *
     * @return max speed that an enemy spaceship can reach.
     */
    @Override
    public float getMaxSpeed() {
        return 1.0f;
    }

    /**
     * Gives a brand new Weapon object if the enemy can fire a weapon.
     * @param key weapon type
     * @param time time
     * @return Weapon
     */
    @Override
    public Weapon fire(String key, long time) {
        if (time - lastFired < defaultWeapon.getRateOfFire()) {
            return null;
        }
        Random rand = new Random();

        int difficulty = GameCore.getDifficulty();
        int randomLength = 30 - 10 * difficulty;
        int randomNumber = rand.nextInt(randomLength);
        if (randomNumber == 0) {
            lastFired = time;
            Weapon weapon = defaultWeapon.clone();
            weapon.setDoesBelongEnemy(true);

            Image img = getCurrentImage();
            int shipHeight = img.getHeight(null);
            int weaponHeight = weapon.getCurrentImage().getHeight(null);
            int place = Math.abs(weaponHeight - shipHeight) / 2;

            weapon.startFiring(getPosition(), time, img.getWidth(null) - 30, place);
            return weapon;
        }
        return null;
    }
}
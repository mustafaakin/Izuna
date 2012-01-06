package org.group1f.izuna.GameComponents;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;
import org.group1f.izuna.GameComponents.Drawing.*;
import org.group1f.izuna.GameCore;

/**
 *
 * @author Mustafa
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
     * @param still
     * @param rollLeft
     * @param rollRight
     * @param health
     * @param defaultWeapon
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
     * @param defaultWeapon
     */
    public void setDefaultWeapon(Weapon defaultWeapon) {
        this.defaultWeapon = defaultWeapon;
    }

    @Override
    public Enemy clone() {
        return new Enemy(super.getStillAnimation().clone(), rollLeft.clone(), rollRight.clone(), health, defaultWeapon.clone());
    }

    /**
     *
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
     *
     * @param elapsedTime
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
     *
     * @return
     */
    @Override
    public int getDieTime() {
        return 1000;
    }

    /**
     *
     * @param newValue
     */
    @Override
    public void setHealth(int newValue) {
        health = newValue;
    }

    /**
     *
     * @return
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     *
     * @return
     */
    @Override
    public float getMaxSpeed() {
        return 1.0f;
    }

    /**
     *
     * @param key
     * @param time
     * @return
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
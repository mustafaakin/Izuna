package org.group1f.izuna.GameComponents;

import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Player extends GameObject implements SpaceShip {

    private HashMap<String, Integer> weapons;
    private HashMap<String, Long> lastFired;
    private boolean isDying;
    private Animation rollLeft;
    private Animation rollRight;
    private SoundEffect rollSound;
    private int health;
    //proton cannon and plasma cutter is infinite
    private float oldvY = 0.0f;
    private boolean isRFinished = true;

    public Player(Point currentPos, Animation still, Animation rollLeft, Animation rollRight, SoundEffect roll) {
        super(still);
        this.setPosition(currentPos);
        weapons = new HashMap<String, Integer>();
        this.rollLeft = rollLeft;
        this.rollRight = rollRight;
        lastFired = new HashMap<String, Long>();
        rollRight.setAnimType(Animation.AnimationType.SMOOTH);
        rollLeft.setAnimType(Animation.AnimationType.SMOOTH);

        rollSound = roll;
        health = 100;
        isDying = false;
    }

    public void addWeapon(String key, int amount) {
        weapons.put(key, amount);
    }

    @Override
    public void checkStateToAnimate() {
        Animation newAnim = currentAnimation;
        if (!isRFinished) {
            isRFinished = currentAnimation.finished();
        }

        if (getvY() < 0) {
            if (oldvY > 0) { //
                isRFinished = currentAnimation.refine();
            } else {
                newAnim = rollLeft;
//                rollSound.play();
            }
        } else if (getvY() > 0) {
            if (oldvY < 0) { //
                isRFinished = currentAnimation.refine();
            } else {
                newAnim = rollRight;
//                rollSound.play();
            }
        } else { // vy = 0
            if (oldvY != 0) {
                isRFinished = currentAnimation.refine();
            } else {
                newAnim = getStillAnimation();
            }
        }

        if (health < 1) {
            setState();
            if (!isDying) {
                //getDieSound().play();
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
        oldvY = getvY();
    }

    @Override
    public void update(long elapsedTime) {
        checkStateToAnimate();
        super.update(elapsedTime);
    }

    private void setState() {
        if (health < 1) {
            setvX(0.0f);
            setvY(0.0f);
        }
    }

    @Override
    public int getDieTime() {
        return 1000;
    }

    @Override
    public void setHealth(int newValue) {
        if (newValue > 100) {
            health = 100;
        } else {
            health = newValue;
        }
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public float getMaxSpeed() {
        return 0.8f;
    }

    @Override
    public Weapon fire(String key, long time) {
        Integer i = weapons.get(key);
        if (i == null) {
            throw new IllegalArgumentException("There is no such weapon '" + key + "' available ones:" + weapons.keySet());
        }
        if (i <= -1 || i > 0) {
            Long lastFireTime = lastFired.get(key);
            Weapon weapon = LoadManager.getWeapon(key).clone();
            if (lastFireTime != null) {
                System.out.println("FIRE:" + (time - lastFireTime));
            }

            if (lastFireTime != null && time - lastFireTime < weapon.getRateOfFire()) {
                return null;
            }
            lastFired.put(key, time);
            Image img = getCurrentImage();
            int shipHeight = img.getHeight(null);
            int weaponHeight = weapon.getCurrentImage().getHeight(null);
            int place = Math.abs(weaponHeight - shipHeight) / 2;
            
            weapon.startFiring(getPosition(), time, img.getWidth(null) - 30, place);
            weapons.put(key, (i - 1));
            return weapon;
        } else {
            return null;
        }
    }
}

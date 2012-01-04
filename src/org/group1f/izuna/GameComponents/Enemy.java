package org.group1f.izuna.GameComponents;

import java.awt.Point;
import java.util.Random;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Enemy extends AIControllable implements SpaceShip {

    private Weapon defaultWeapon;
    private boolean isDying;
    private Animation rollLeft;
    private Animation rollRight;
    private SoundEffect enteringSound;
    private int health;
    private boolean isRFinished = true;

    public Enemy(Animation still, Animation rollLeft, Animation rollRight, SoundEffect enteringSound, int health) {
        super(still);
        this.rollLeft = rollLeft;
        this.rollRight = rollRight;
        this.enteringSound = enteringSound;
        this.health = health;
        isDying = false;
    }

    public Enemy clone() {
        return new Enemy(super.getStillAnimation().clone(), rollLeft.clone(), rollRight.clone(), enteringSound, health);
    }

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

    @Override
    public int getDieTime() {
        return 1000;
    }

    @Override
    public void setHealth(int newValue) {
        System.out.println("SET HELATH:" + newValue);
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
        return 1.0f;
    }

    @Override
    public Weapon fire(String key, long time) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
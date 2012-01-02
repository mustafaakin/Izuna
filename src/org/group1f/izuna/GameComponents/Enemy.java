package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Enemy extends AIControllable implements SpaceShip {

    private String weaponKey;
    private boolean isDying;
    private Animation rollLeft;
    private Animation rollRight;
    private SoundEffect enteringSound;
    private int health;
    private boolean isRFinished = true;

    public Enemy(Animation still, Animation rollLeft, Animation rollRight, SoundEffect enteringSound) {
        super(still);
        this.rollLeft = rollLeft;
        this.rollRight = rollRight;
        this.enteringSound = enteringSound;
        isDying = false;
    }

    @Override
    public Enemy clone() {
        return new Enemy(super.getStillAnimation().clone(), rollLeft.clone(), rollRight.clone(), enteringSound.clone());
    }
    
    float abc = 0;
    @Override
    public void checkStateToAnimate() {
        abc++;
        if ( abc > 200)
            abc = -200;
        setvY(abc);

        System.out.println("Animation vY\t" + getvY());
        Animation newAnim = currentAnimation;
        if (!isRFinished) {
            isRFinished = currentAnimation.finished();
        }

        if (getvY() < 0) {
            if (getOldvY() > 0) { //
                isRFinished = currentAnimation.refine();
            } else {
                System.out.println("ROLL LEFT INITIATED");

                newAnim = rollLeft;
            }
        } else if (getvY() > 0) {
            if (getOldvY() < 0) { //
                isRFinished = currentAnimation.refine();
            } else {
                System.out.println("ROLL RIGHT INITIATED");
                newAnim = rollRight;
            }
        } else { // vy = 0
            if (getvY() != 0) {
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
}

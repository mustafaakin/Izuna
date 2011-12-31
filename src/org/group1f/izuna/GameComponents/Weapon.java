package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.Animation;

public class Weapon extends AIControllable {

    public enum WeaponState {

        STATE_FIRED, STATE_HIT, STATE_EXPLODE
    }
    WeaponState state;
    private int damageAmount;
    private int rateOfFire;
    private Animation die; //this animation shows up when it damages to spaceship
    private Animation explode; // this animation shows up when it cause spaceship explode,
    //so there wont be needed extra sprites for other spaceships
    private SoundEffect fireSound;
    private SoundEffect explodeSound;

    // Weapon un die sound u olması garip
    public Weapon(Point currentPos, int duration, Animation rest, Animation die, Animation explode, int damageAmount, int rateOfFire, SoundEffect fireSound, SoundEffect explodeSound) {
        super(currentPos, rest);
        Point endPosition = new Point(currentPos);
        endPosition.x = 1280 + 150; // Outside the Canvas
        LinearPath path = new LinearPath(currentPos, endPosition, duration);

        this.die = die;
        this.explode = explode;
        this.fireSound = fireSound;
        this.explodeSound = explodeSound;
        state = WeaponState.STATE_FIRED;
        this.damageAmount = damageAmount;
        this.rateOfFire = rateOfFire;
    }

    @Override
    public void checkStateToAnimate() {
        Animation newAnim = currentAnimation;

        if (state == WeaponState.STATE_HIT) {
            newAnim = die;
            setvX(0);
            setvY(0);
        } else if (state == WeaponState.STATE_EXPLODE) {
            newAnim = die;
            setvX(0);
            setvY(0);
        }

        if (currentAnimation != newAnim) {
            currentAnimation = newAnim;
            currentAnimation.startOver();
        }
    }

    public void update(long elapsedTime) {
        checkStateToAnimate();
        super.update(elapsedTime);
    }

    public void applyDamege(SpaceShip a) {
        if (damageAmount >= a.getHealth()) {
            state = WeaponState.STATE_EXPLODE;
        } else {
            state = WeaponState.STATE_HIT;
        }
        a.setHealth(a.getHealth() - damageAmount);
    }

    public WeaponState getState() {
        return state;
    }

    public int getExplodeTime() {
        return 1000;
    }
}

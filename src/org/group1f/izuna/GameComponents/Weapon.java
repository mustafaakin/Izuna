package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.Animation;

public class Weapon extends AIControllable {

    private static long DEFAULT_WEAPON_DURATION = 500;

    public enum WeaponState {

        STATE_FIRED, STATE_HIT, STATE_EXPLODE
    }
    private WeaponState state;
    private int damageAmount;
    private int rateOfFire;
    private Animation die; //this animation shows up when it damages to spaceship
    private Animation explode; // this animation shows up when it cause spaceship explode,
    //so there wont be needed extra sprites for other spaceships
    private SoundEffect fireSound;
    private SoundEffect explodeSound;

    // Weapon un die sound u olması garip
    public Weapon(Animation still, Animation explode, int damageAmount, int rateOfFire, SoundEffect fireSound, SoundEffect explodeSound) {
        super(null, still);
        this.explode = explode;
        this.fireSound = fireSound;
        this.explodeSound = explodeSound;
        this.damageAmount = damageAmount;
        this.rateOfFire = rateOfFire;
    }

    public void startFiring(Point position, long time) {
        Point end = new Point(position);
        end.y = 1280 + 50; // The position that the weapon will disappear        
        LinearPath path = new LinearPath(position, end, DEFAULT_WEAPON_DURATION);
        this.addPath(path);
        this.setPathActivationTime(time);
    }

    @Override
    public void checkStateToAnimate() {
        Animation newAnim = currentAnimation;

        if (state == WeaponState.STATE_HIT) {
            newAnim = explode;
            setvX(0);
            setvY(0);
        } else if (state == WeaponState.STATE_EXPLODE) {
            newAnim = null;
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

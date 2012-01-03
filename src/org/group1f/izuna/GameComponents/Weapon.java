package org.group1f.izuna.GameComponents;

import java.awt.Image;
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
    private Animation spaceShipDies; //this animation shows up when it damages to spaceship
    private Animation weaponExplode;// this animation shows up when it cause spaceship explode,
    //so there wont be needed extra sprites for other spaceships
    private SoundEffect fireSound;
    private SoundEffect explodeSound;
    private int speed;
    private long lastFire;

    public void playFire() {
        fireSound.play();
    }

    public int getRateOfFire() {
        return rateOfFire;
    }

    public int getDamageAmount() {
        return damageAmount;
    }

    public long getLastFire() {
        return lastFire;
    }


    public Weapon(Animation still, Animation die, Animation explode, int damageAmount, int rateOfFire, SoundEffect fireSound, SoundEffect explodeSound, int speed) {
        super(still);
        this.weaponExplode = explode;
        this.spaceShipDies = die;
        this.fireSound = fireSound;
        this.explodeSound = explodeSound;
        this.damageAmount = damageAmount;
        this.rateOfFire = rateOfFire;
        this.speed = speed;
    }

    @Override
    public Weapon clone() {
        return new Weapon(super.getStillAnimation(), spaceShipDies, weaponExplode, damageAmount, rateOfFire, fireSound, explodeSound, speed);
    }

    public void startFiring(Point position, long time, int offsetX, int offsetY) {
        lastFire = time;
        Point start = new Point(position);
        Point end = new Point(position);
        start.x += offsetX;
        start.y += offsetY;

        end.y = start.y;
        end.x = 1500; // The position that the weapon will disappear        
        if (this.speed == -1) {
            end.x = start.x;
            end.y = start.y;
            LinearPath path = new LinearPath(start, end, 100);
            this.addPath(path);
            this.setPathActivationTime(time);
        } else {
            LinearPath path = new LinearPath(start, end, speed);
            this.addPath(path);
            this.setPathActivationTime(time);
        }
    }

    @Override
    public void checkStateToAnimate() {
        currentAnimation = getStillAnimation();
    }

    @Override
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

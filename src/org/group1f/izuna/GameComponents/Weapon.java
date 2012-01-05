package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.Animation;

/**
 *
 * @author Mustafa
 */
public class Weapon extends AIControllable {

    public static int USER_WEAPON_END_POSITION = 1400;
    public static int ENEMY_WEAPON_END_POSITION = -200;

    /**
     *
     */
    public enum WeaponState {

        /**
         *
         */
        STATE_FIRED,
        /**
         *
         */
        STATE_HIT,
        /**
         *
         */
        STATE_EXPLODE
    }
    private WeaponState state;
    private int damageAmount;
    private int rateOfFire;
    private int type;
    private SoundEffect fireSound;
    private int speed;
    private long lastFire;
    private boolean doesBelongEnemy;

    /**
     *
     */
    public void playFire() {
        if (fireSound != null) {
            fireSound.play();
        }
    }

    /**
     *
     * @param doesBelongEnemy
     */
    public void setDoesBelongEnemy(boolean doesBelongEnemy) {
        this.doesBelongEnemy = doesBelongEnemy;
    }

    /**
     *
     * @return
     */
    public int getRateOfFire() {
        return rateOfFire;
    }

    /**
     *
     * @return
     */
    public int getDamageAmount() {
        return damageAmount;
    }

    /**
     *
     * @return
     */
    public long getLastFire() {
        return lastFire;
    }

    /**
     *
     * @param still
     * @param die
     * @param explode
     * @param damageAmount
     * @param rateOfFire
     * @param fireSound
     * @param explodeSound
     * @param speed
     */
    public Weapon(Animation still, int damageAmount, int rateOfFire, SoundEffect fireSound, int speed, int type) {
        super(still);
        this.fireSound = fireSound;
        this.damageAmount = damageAmount;
        this.rateOfFire = rateOfFire;
        this.speed = speed;
        this.type = type;
    }

    @Override
    public Weapon clone() {
        return new Weapon(super.getStillAnimation(), damageAmount, rateOfFire, fireSound, speed, type);
    }

    public int getType() {
        return type;
    }

    /**
     *
     * @param position
     * @param time
     * @param offsetX
     * @param offsetY
     */
    public void startFiring(Point position, long time, int offsetX, int offsetY) {
        lastFire = time;
        Point start = new Point(position);
        Point end = new Point(position);

        if (type == 2) {
            start.x = 0;
            start.y = 0;
        } else {
            start.x += offsetX;
            start.y += offsetY;
        }

        end.y = start.y;
        end.x = doesBelongEnemy ? ENEMY_WEAPON_END_POSITION : USER_WEAPON_END_POSITION; // The position that the weapon will disappear        
        if (type == 1 || type == 2) {
            end.x = start.x;
            end.y = start.y;
            LinearPath path = new LinearPath(start, end, 100);
            this.addPath(path);
            this.setPathActivationTime(time);
        } else {
            System.out.println("WEAPON END:" + end);
            LinearPath path = new LinearPath(start, end, speed);
            this.addPath(path);
            this.setPathActivationTime(time);
        }
    }

    /**
     *
     */
    @Override
    public void checkStateToAnimate() {
        currentAnimation = getStillAnimation();
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

    /**
     *
     * @param a
     */
    public void applyDamage(SpaceShip a) {
        if (damageAmount >= a.getHealth()) {
            state = WeaponState.STATE_EXPLODE;
        } else {
            state = WeaponState.STATE_HIT;
        }
        a.setHealth(a.getHealth() - damageAmount);
    }

    /**
     *
     * @return
     */
    public WeaponState getState() {
        return state;
    }

    /**
     *
     * @return
     */
    public int getExplodeTime() {
        return 1000;
    }
}

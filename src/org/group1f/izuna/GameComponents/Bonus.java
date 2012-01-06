package org.group1f.izuna.GameComponents;

import java.awt.Point;
import java.util.Random;
import org.group1f.izuna.GameComponents.Drawing.*;

/**
 *
 * @author Mustafa
 */
public class Bonus extends AIControllable {

    /**
     * 
     */
    public final static long DEFAULT_BONUS_FALL_DURATION = 750;
    private boolean bonusType; // default = health, 1 = weapon
    private int bonusValue;

    /**
     *
     * @param still
     * @param value
     * @param isWeapon
     */
    public Bonus(Animation still, int value, boolean isWeapon) {
        super(still);
        bonusValue = value;
        bonusType = isWeapon;
    }

    @Override
    public Bonus clone() // ses sorunu varsa clone methodundandır
    {
        return new Bonus(getStillAnimation().clone(), bonusValue, bonusType);
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
    public void update(long elapsedTime) {
        checkStateToAnimate();
        super.update(elapsedTime);
    }

    /**
     *
     * @param p
     */
    public void applyBonus(Player p) {
        if (bonusType) {
            p.setHealth(p.getHealth() + bonusValue);
        } else {
            Random r = new Random();
            int value = r.nextInt(100);
            if (value < 5) {
                p.increaseWeapon("super_desperation", 1);
            } else if (value < 20) {
                p.increaseWeapon("particle", 2);
            } else if (value < 50) {
                p.increaseWeapon("dark_mattter", 5);
            } else {
                try {
                    p.increaseWeapon("plasma_player1", 15);
                } catch (Exception e) {
                    p.increaseWeapon("plasma_player2", 15);
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public float getMaxSpeed() {
        return 0.5f;
    }
}

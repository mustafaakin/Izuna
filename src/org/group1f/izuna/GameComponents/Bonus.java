package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

/**
 * 
 * @author Mustafa
 */
public class Bonus extends AIControllable {

    /**
     * 
     */
    public enum BonusState {

        /**
         * 
         */
        STATE_FIRED,
        /**
         *
         */
        STATE_HIT
    }
    private final static long DEFAULT_BONUS_FALL_DURATION = 750;
    private BonusState state;
    private boolean bonusType; // default = health, 1 = weapon
    private int bonusValue;

    /**
     * 
     * @param still
     * @param value
     * @param type
     */
    public Bonus(Animation still, int value, boolean type) {
        super(still);
        bonusValue = value;
        bonusType = type;
        state = BonusState.STATE_FIRED;
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
     * @param state
     */
    public void setState(BonusState state) {
        if (this.state != state) {
            this.state = state;
            if (state == BonusState.STATE_HIT) {
                setvX(0);
                setvY(0);
            }
        }
    }

    /**
     * 
     * @param p
     */
    public void applyBonus(Player p) {
        if (bonusType) {
            p.setHealth(p.getHealth() + bonusValue);
        } else { // later on add random generator
            
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

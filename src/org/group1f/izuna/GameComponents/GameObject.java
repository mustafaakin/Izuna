package org.group1f.izuna.GameComponents;

import java.awt.Graphics2D;
import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

/**
 * 
 * @author Mustafa
 */
public abstract class GameObject extends Sprite {

    private Animation stillAnimation;
    private boolean isVisible;
    private SoundEffect dieSound;
    private Point prevPosition;
    
    /**
     * Very simple GameObject that can be drawn on screen
     * @param still Animation to be displayed when no any other animation is being shown.
     */
    public GameObject(Animation still) {
        setPosition(new Point(0, 0));
        stillAnimation = still == null ? null : still.clone();
        currentAnimation = still == null ? null : still.clone();
        isVisible = false;
    }

    /**
     * 
     * @return
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * 
     * @param isVisible
     */
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * 
     * @return
     */
    public Animation getStillAnimation() {
        return stillAnimation;
    }

    /**
     * 
     * @param stillAnimation
     */
    public void setStillAnimation(Animation stillAnimation) {
        this.stillAnimation = stillAnimation;
    }

    /**
     * 
     * @param dieSound
     */
    public void setDieSound(SoundEffect dieSound) {
        this.dieSound = dieSound;
    }

    /**
     * 
     * @return
     */
    public SoundEffect getDieSound() {
        return dieSound;
    }

    /**
     * 
     * @return
     */
    public Point getPrevPosition() {
        return prevPosition;
    }
    
    
    @Override
    public Point getPosition() {
        prevPosition = new Point(super.getPosition());
        super.getPosition().translate((int) getvX(), (int) getvY());
        return super.getPosition();
    }

    /**
     * 
     */
    public abstract void checkStateToAnimate();

    /**
     * Draws this image to the screen by using the given graphics object
     * @param g the graphics that this object will be drawn to
     */
    public void paint(Graphics2D g) {
        g.drawImage(getCurrentImage(), getPosition().x, getPosition().y, null);
    }
}

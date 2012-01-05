package org.group1f.izuna.GameComponents.Drawing;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * 
 * @author Mustafa
 */
abstract public class Sprite {

    private Point position;
    private float vX;
    private float vY;
    private float oldvY = 0.0f;
    /**
     * 
     */
    protected Animation currentAnimation;
    private Rectangle collisionRectangle;

    /**
     * 
     */
    public Sprite() {
        this(new Point(0, 0));
    }

    /**
     * 
     * @param position
     */
    public Sprite(Point position) {
        this.position = position;
        vX = 0;
        vY = 0;
        this.collisionRectangle = new Rectangle(position.x, position.y, 0, 0);
    }

    /**
     * 
     * @return
     */
    public int getHeight() {
        return currentAnimation.getImage().getHeight(null);
    }

    /**
     * 
     * @return
     */
    public int getWidth() {
        return currentAnimation.getImage().getWidth(null);
    }

    /**
     * 
     * @param elapsedTime
     */
    public void update(long elapsedTime) {
        currentAnimation.update(elapsedTime);
        setCollisionRectangle(new Rectangle(position.x, position.y, getWidth(), getHeight()));
    }

    /**
     * 
     * @return
     */
    public Image getCurrentImage() {
        return currentAnimation.getImage();
    }

    /**
     * 
     * @return
     */
    public Animation getCurrentAnim() {
        return currentAnimation;
    }

    /**
     * 
     * @return
     */
    public long getAnimationDuration() {
        return currentAnimation.getFrames().size() * Animation.FRAME_DURATION;
    }

    /**
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * @return the vX
     */
    public float getvX() {
        return vX;
    }

    /**
     * @param vX the vX to set
     */
    public void setvX(float vX) {
        this.vX = vX;
    }

    /**
     * @return the vY
     */
    public float getvY() {
        return vY;
    }

    /**
     * 
     * @return
     */
    public float getOldvY() {
        return oldvY;
    }

    /**
     * 
     * @param oldvY
     */
    public void setOldvY(float oldvY) {
        this.oldvY = oldvY;
    }

    /**
     * @param vY the vY to set
     */
    public void setvY(float vY) {
        oldvY = this.vY;
        this.vY = vY;
    }

    /**
     * 
     * @return
     */
    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    /**
     * 
     * @param collisionRectangle
     */
    public void setCollisionRectangle(Rectangle collisionRectangle) {
        this.collisionRectangle = collisionRectangle;
    }
}
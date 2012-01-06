package org.group1f.izuna.GameComponents.Drawing;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Sprite Class is positions the animation sequences that are created by the animation class. It is the
 * parent class of the GameObject class that provides all game objects animations and movement
 * ability on the screen. It has also critical role on the physics since it creates collision rectangles with
 * respect to the sizes of the animation frames.
 * @author Nail AKINCI
 */
abstract public class Sprite {

    private Point position;
    private float vX;
    private float vY;
    private float oldvY = 0.0f;
    /**
     * Current animation of the Sprite.
     */
    protected Animation currentAnimation;
    private Rectangle collisionRectangle;

    /**
     * Initialize Sprite at the position (0,0)
     */
    public Sprite() {
        this(new Point(0, 0));
    }

    /**
     * Initialize Sprite at the given position
     * @param position Point
     */
    public Sprite(Point position) {
        this.position = position;
        vX = 0;
        vY = 0;
        this.collisionRectangle = new Rectangle(position.x, position.y, 0, 0);
    }

    /**
     * Returns the height of the image from current animation.
     * @return currentAnimation's Image height in int.
     */
    public int getHeight() {
        return currentAnimation.getImage().getHeight(null);
    }

    /**
     * Returns the width of the image from current animation.
     * @return currentAnimation's Image width in int.
     */
    public int getWidth() {
        return currentAnimation.getImage().getWidth(null);
    }

    /**
     * Updates the position of the collisionRectangle and the Image of the currentAnimation 
     * @param elapsedTime elapsed time in milliseconds.
     */
    public void update(long elapsedTime) {
        currentAnimation.update(elapsedTime);
        setCollisionRectangle(new Rectangle(position.x, position.y, getWidth(), getHeight()));
    }

    /**
     * 
     * @return Image of the currentAnimation
     */
    public Image getCurrentImage() {
        return currentAnimation.getImage();
    }

    /**
     * 
     * @return currentAnimation
     */
    public Animation getCurrentAnim() {
        return currentAnimation;
    }

    /**
     * 
     * @return duration of the currentAnimation
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
     * @return previous velocity of the Y
     */
    public float getOldvY() {
        return oldvY;
    }

    /**
     * Sets previous velocity of the Y
     * @param oldvY previous velocity of the Y
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
     * Returns the collision rectangle that needs to be used in physic tests.
     * @return the collisionRectangle
     */
    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    /**
     * Sets the collision rectangle that needs to be used in physic tests.
     * @param collisionRectangle Rectangle
     */
    public void setCollisionRectangle(Rectangle collisionRectangle) {
        this.collisionRectangle = collisionRectangle;
    }
}
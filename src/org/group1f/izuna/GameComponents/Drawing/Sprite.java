package org.group1f.izuna.GameComponents.Drawing;

import java.awt.Point;
import java.awt.Image;
import java.awt.Rectangle;

abstract public class Sprite {

    private Point position;
    private float vX;
    private float vY;
    private float oldvY = 0.0f;
    protected Animation currentAnimation;
    private Rectangle collisionRectangle;

    public Sprite() {
        this(new Point(0, 0));
    }

    public Sprite(Point position) {
        this.position = position;
        vX = 0;
        vY = 0;
        this.collisionRectangle = new Rectangle(position.x, position.y, 0, 0);
    }

    public int getHeight() {
        return currentAnimation.getImage().getHeight(null);
    }

    public int getWidth() {
        return currentAnimation.getImage().getWidth(null);
    }

    public void update(long elapsedTime) {
        currentAnimation.update(elapsedTime);
        setCollisionRectangle(new Rectangle(position.x,
                position.y, getWidth(), getHeight()));
    }

    public Image getCurrentImage() {
        return currentAnimation.getImage();
    }

    public Animation getCurrentAnim() {
        return currentAnimation;
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

    public float getOldvY() {
        return oldvY;
    }

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

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public void setCollisionRectangle(Rectangle collisionRectangle) {
        this.collisionRectangle = collisionRectangle;
    }
}

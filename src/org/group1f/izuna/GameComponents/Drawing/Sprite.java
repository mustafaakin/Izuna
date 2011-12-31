package org.group1f.izuna.GameComponents.Drawing;

import java.awt.Point;
import java.awt.Image;
import java.awt.Rectangle;

abstract public class Sprite {

    private Point position;
    private float vX;
    private float vY;
    protected Animation currentAnimation;
    private Rectangle collisionRectangle;

    public Sprite() {
        this.position = new Point();
    }

    public Sprite(Point position) {
        this.position = position;
        vX = 0;
        vY = 0;
        setCollisionRectangle(new Rectangle(position.x,
                position.y, 0, 0));
    }

    public int getHeight() {
        return currentAnimation.getImage().getHeight(null);
    }

    public int getWidth() {
        return currentAnimation.getImage().getWidth(null);
    }

    public void update(long elapsedTime) {
        position.x += (vX * elapsedTime);
        position.y += (vY * elapsedTime);
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

    /**
     * @param vY the vY to set
     */
    public void setvY(float vY) {
        this.vY = vY;
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public void setCollisionRectangle(Rectangle collisionRectangle) {
        this.collisionRectangle = collisionRectangle;
    }
}

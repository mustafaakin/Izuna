package org.group1f.izuna.GUI;

import java.awt.Image;

/**
 * This class is used for creation of menu element.
 * @author Mustafa
 */
public class MenuElement {

    private Image rollOver;
    private Image normal;

    /**
     * 
     * @param rollOver: This image is displayed when this button is focused.
     * @param normal: This image is displayed when this button is not focused.
     */
    public MenuElement(Image rollOver, Image normal) {
        this.rollOver = rollOver;
        this.normal = normal;
    }

    /**
     * This function gets normal.
     * @return normal: This image is displayed when this button is not focused.
     */
    public Image getNormal() {
        return normal;
    }

    /**
     * This function gets roll over.
     * @return roll over: This image is displayed when this button is focused.
     */
    public Image getRollOver() {
        return rollOver;
    }
}
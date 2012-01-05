package org.group1f.izuna.GUI;

import java.awt.Image;

/**
 * 
 * @author Mustafa
 */
public class MenuElement {

    private Image rollOver;
    private Image normal;

    /**
     * 
     * @param rollOver
     * @param normal
     */
    public MenuElement(Image rollOver, Image normal) {
        this.rollOver = rollOver;
        this.normal = normal;
    }

    /**
     * 
     * @return
     */
    public Image getNormal() {
        return normal;
    }

    /**
     * 
     * @return
     */
    public Image getRollOver() {
        return rollOver;
    }
}

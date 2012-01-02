package org.group1f.izuna.GUI;

import java.awt.Image;

public class MenuElement {

    private Image rollOver;
    private Image normal;

    public MenuElement(Image rollOver, Image normal) {
        this.rollOver = rollOver;
        this.normal = normal;
    }

    public Image getNormal() {
        return normal;
    }

    public Image getRollOver() {
        return rollOver;
    }
}

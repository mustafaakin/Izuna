package org.group1f.izuna.GUI;

import java.awt.Graphics2D;
import java.awt.Image;
import org.group1f.izuna.GameComponents.Drawing.*;
import java.util.*;

public class Menu {

    private Image background;
    private int activeElement;
    private ArrayList<MenuElement> menuElements;

    public Menu() {
        activeElement = 0;
        menuElements = new ArrayList<MenuElement>();
    }
    
    public void addMenuElement(MenuElement element) {
        element.setOwner(this);
        this.menuElements.add(element);
    }

    public void navigate(int move) {
        activeElement = move;
    }

    public void print(Graphics2D g) {
        g.drawImage(background, 0, 0, null);
        for (int i = 0; i < menuElements.size(); i++) {
            MenuElement element = menuElements.get(i);
            Image toDraw = element.getImage(i == activeElement);
            g.drawImage(toDraw, 0, 0, null);
        }
    }
}

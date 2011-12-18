package org.group1f.izuna.GUI;

import java.awt.Image;
import org.group1f.izuna.GameComponents.Drawing.*;
import java.util.*;
import org.group1f.izuna.Contollers.KeyboardHandler;

abstract public class MenuElement {
    private Menu owner;
    
    public MenuElement() {
    }

    public void setOwner(Menu owner) {
        this.owner = owner;
    }

    public Menu getOwner() {
        return owner;
    }

    abstract public Image getImage(boolean rollOver);

    abstract public void onClicked(KeyboardHandler.Key key);
}

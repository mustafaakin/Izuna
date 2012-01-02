package org.group1f.izuna.GUI;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import org.group1f.izuna.Contollers.KeyboardHandler.Key;

abstract public class MenuButton {

    private int active;
    private MenuElement defaultElement;
    private ArrayList<MenuElement> elements;

    public MenuButton(MenuElement defaultElement) {
        this.defaultElement = defaultElement;
        elements = new ArrayList<MenuElement>();
        active = 0;
    }

    public void addElement(MenuElement element) {
        elements.add(element);
    }

    public void onClick(Key key) {
        if (key.equals(Key.Player1_Right)) {
            active = (active + 1) % elements.size();
        } else if (key.equals(Key.Player1_Left)) {
            active = (active - 1 + elements.size()) % elements.size();
        }
        onInteracted(key);
    }

    abstract public void onInteracted(Key key);

    public List<Image> getImages(boolean rollOver) {
        ArrayList<Image> result = new ArrayList<Image>();
        result.add(rollOver ? defaultElement.getRollOver() : defaultElement.getNormal());
        for (int i = 0; i < elements.size(); i++) {
            MenuElement currElement = elements.get(i);
            result.add(i == active ? currElement.getRollOver() : currElement.getNormal()); // Nothing to do with general rollovers.
        }
        return result;
    }
}

package org.group1f.izuna.GUI;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import org.group1f.izuna.Contollers.KeyboardHandler.Key;

/**
 * This abstract class is used for creation of button.
 *
 * @author Mustafa
 */
abstract public class MenuButton {

    private int active;
    private MenuElement defaultElement;
    private ArrayList<MenuElement> elements;

    /**
     * This function gets the default element which only shows ui text and it
     * converts element to button which can gain focus and have a click action.
     *
     * @param defaultElement : default element shows ui text.
     */
    public MenuButton(MenuElement defaultElement) {
        this.defaultElement = defaultElement;
        elements = new ArrayList<MenuElement>();
        active = 0;
    }

    /**
     * This function adds element.
     *
     * @param element: element represents a gui element that has no click
     * action.
     */
    public void addElement(MenuElement element) {
        elements.add(element);
    }

    /**
     * This function handles key is pressed.
     *
     * @param key: pressed key
     */
    public void onClick(Key key) {
        if (!elements.isEmpty()) {
            if (key.equals(Key.Player1_Right)) {
                active = (active + 1) % elements.size();
            } else if (key.equals(Key.Player1_Left)) {
                active = (active - 1 + elements.size()) % elements.size();
            }
        }
        onInteracted(key);
    }

    abstract public void onInteracted(Key key);

    /**
     * This function gets images.
     *
     * @param rollOver
     * @return result
     */
    public List<Image> getImages(boolean rollOver) {
        ArrayList<Image> result = new ArrayList<Image>();
        result.add(rollOver ? defaultElement.getRollOver() : defaultElement.getNormal());
        for (int i = 0; i < elements.size(); i++) {
            MenuElement currElement = elements.get(i);
            result.add(i == active ? currElement.getRollOver() : currElement.getNormal()); // Nothing to do with general rollovers.
        }
        return result;
    }

    /**
     * This function gets active.
     *
     * @return active
     */
    public int getActive() {
        return active;
    }

    /**
     * This function sets active.
     *
     * @param active
     */
    public void setActive(int active) {
        this.active = active;
    }
}

package org.group1f.izuna.GUI;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import org.group1f.izuna.Contollers.KeyboardHandler.Key;
import org.group1f.izuna.GameCore;

/**
 *
 * @author Mustafa
 */
public class Menu {

    private GameCore owner;
    private ArrayList<MenuButton> buttons;
    private ArrayList<MenuElement> elements;
    
    private int active;

    /**
     * 
     * @param owner
     */
    public Menu(GameCore owner) {
        this.buttons = new ArrayList<MenuButton>();
        this.elements = new ArrayList<MenuElement>();
        this.owner = owner;
        active = 0;
    }

    /**
     * 
     * @param key
     */
    public void onClicked(Key key) {
        if (key.equals(Key.Player1_Down)) {
            active = (active + 1) % buttons.size();
        } else if (key.equals(Key.Player1_Up)) {
            active = (active - 1 + buttons.size()) % buttons.size();
        } else {
            buttons.get(active).onClick(key);
        }
    }

    /**
     * 
     * @param button
     */
    public void addButton(MenuButton button) {
        this.buttons.add(button);
    }

    /**
     * 
     * @param element
     */
    public void addElement(MenuElement element) {
        this.elements.add(element);
    }

    /**
     * 
     * @return
     */
    public List<Image> getImagesToDraw() {
        ArrayList<Image> images = new ArrayList<Image>();
        for(MenuElement element : this.elements){
            images.add(element.getNormal());
        }
        for (int i = 0; i < buttons.size(); i++) {
            MenuButton button = buttons.get(i);
            images.addAll(button.getImages(i == active));
        }
        return images;
    }

    /**
     * 
     * @return
     */
    public GameCore getOwner() {
        return owner;
    }
}

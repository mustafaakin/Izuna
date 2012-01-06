package org.group1f.izuna.GUI;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import org.group1f.izuna.Contollers.KeyboardHandler.Key;
import org.group1f.izuna.GameCore;

/**
 * Super class of game menus.
 * @author Mustafa
 */
public class Menu {

    /**
     * Core of the game which is the main controller of the game.
     */
    private GameCore owner;
    private ArrayList<MenuButton> buttons;
    private ArrayList<MenuElement> elements;
    
    private int active;

    /**
     * This function creates buttons and elements at the creation of the object.
     * @param owner {@link Menu #owner}:
     * Core of the game which is the main controller of the game.
     */
    public Menu(GameCore owner) {
        this.buttons = new ArrayList<MenuButton>();
        this.elements = new ArrayList<MenuElement>();
        this.owner = owner;
        active = 0;
    }

    /**
     * This function handles button press action.
     * @param key: pressed key
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
     * This funtion adds button.
     * @param button
     */
    public void addButton(MenuButton button) {
        this.buttons.add(button);
    }

    /**
     * This function adds element.
     * @param element: element represents a gui element that has no click action.
     * It just displays text file.
     */
    public void addElement(MenuElement element) {
        this.elements.add(element);
    }

    /**
     * This function gets images to draw.
     * @return images: images which will be drawn to menu screens.
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
     * This function gets game core.
     * @return: owner: game core of the game.
     */
    public GameCore getOwner() {
        return owner;
    }
}
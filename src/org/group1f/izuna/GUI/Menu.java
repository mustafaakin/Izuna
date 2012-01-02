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

    public enum MenuActions {

        START_GAME, HIGH_SCORES,}
    private GameCore owner;
    private ArrayList<MenuButton> buttons;
    private int active;

    public Menu(GameCore owner) {
        this.buttons = new ArrayList<MenuButton>();
        this.owner = owner;
    }

    public void onClicked(Key key) {
        if (key.equals(Key.Player1_Down)) {
            active = (active + 1) % buttons.size();
        } else if (key.equals(Key.Player1_Right)) {
            active = (active + 1 + buttons.size()) % buttons.size();
        } else {
            buttons.get(active).onClick(key);
        }
    }

    public void addButton(MenuButton button) {
        this.buttons.add(button);
    }

    public List<Image> getImagesToDraw() {
        ArrayList<Image> images = new ArrayList<Image>();
        for (int i = 0; i < buttons.size(); i++) {
            MenuButton button = buttons.get(i);
            images.addAll(button.getImages(i == active));            
        }
        return images;
    }
}

package org.group1f.izuna.GUI;

import java.awt.Image;
import java.util.ArrayList;
import org.group1f.izuna.Contollers.KeyboardHandler.Key;

public class ToggleButton extends MenuElement {
    private ArrayList<RollableButton> toggles;
    private int current = 0;

    public ToggleButton() {
        toggles = new ArrayList<RollableButton>();
    }

    public void addButton(RollableButton buton) {
        toggles.add(buton);
    }

    public void setCurrent(int current) {
        if (current < 0 || current >= this.toggles.size()) {
            throw new IllegalArgumentException(
                    "Current menu element must be positive and "
                    + "less than available toggles which is:"
                    + this.toggles.size());
        }
    }

    @Override
    public Image getImage(boolean rollOver) {
        RollableButton buton = toggles.get(current);
        return rollOver ? buton.rollOver : buton.still;
    }

    @Override
    public void onClicked(Key key) {
        switch (key) {
            case Player1_Left:
                current = (current + 1) % this.toggles.size();
                break;
            case Player1_Right:
                current = (current - 1) % this.toggles.size();
                break;
        }
    }
}

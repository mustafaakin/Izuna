package org.group1f.izuna.GUI;

import org.group1f.izuna.Contollers.KeyboardHandler;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.GameCore;

/**
 *
 * @author mcad
 */
public class Help extends Menu {

    /**
     * This function creates help text and back button with their interaction
     * keyboard at the creation of the object.
     *
     * @param gameCore: core of the game which is the main controller of the
     * game.
     */
    public Help(final GameCore gameCore) {
        super(gameCore);
        MenuElement helpText = LoadManager.getMenuElement("help", "help");

        MenuButton back = new MenuButton(LoadManager.getMenuElement("help", "back")) {

            @Override
            public void onInteracted(KeyboardHandler.Key key) {
                gameCore.setCurrentMenu(new MainMenu(gameCore));
            }
        };

        this.addElement(helpText);
        this.addButton(back);
    }
}

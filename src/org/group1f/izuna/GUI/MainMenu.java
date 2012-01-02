package org.group1f.izuna.GUI;

import java.awt.Image;
import org.group1f.izuna.Contollers.KeyboardHandler.Key;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.GameCore;

/**
 *
 * @author Mustafa
 */
public class MainMenu extends Menu {

    public MainMenu(GameCore game) {
        super(game);
        // START GAME
        MenuButton startGame = new MenuButton(LoadManager.getMenuElement("main", "startGame")) {

            @Override
            public void onInteracted(Key key) {
                if (key.equals(Key.Enter)) {
                    getOwner().startGame(true);
                }
            }
        };
        MenuButton help = new MenuButton(LoadManager.getMenuElement("main", "help")) {

            @Override
            public void onInteracted(Key key) {
            }
        };

        MenuButton anaglyph = new MenuButton(LoadManager.getMenuElement("options", "3DAnagyphDefault")) {

            @Override
            public void onInteracted(Key key) {
                int i = this.getActive();
                if (i == 0) {
                    GameCore.preferences().putBoolean("3D", true);
                } else {
                    GameCore.preferences().putBoolean("3D", false);
                }
            }
        };
        anaglyph.setActive(GameCore.preferences().getBoolean("3D", true) ? 0 : 1);
        anaglyph.addElement(LoadManager.getMenuElement("options", "3DAnagyphOn"));
        anaglyph.addElement(LoadManager.getMenuElement("options", "3DAnagyphOff"));

        this.addButton(startGame);
        this.addButton(anaglyph);
        this.addButton(help);

    }
}

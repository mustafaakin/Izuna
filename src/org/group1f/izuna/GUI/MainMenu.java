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
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        MenuButton help = new MenuButton(LoadManager.getMenuElement("main", "help")){
            @Override
            public void onInteracted(Key key) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
        };
        
        this.addButton(startGame);
        this.addButton(help);
    }
}

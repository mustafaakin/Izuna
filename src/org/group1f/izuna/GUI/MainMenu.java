package org.group1f.izuna.GUI;

import org.group1f.izuna.Contollers.KeyboardHandler.Key;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.GameCore;

/**
 *
 * @author Mustafa
 */
public class MainMenu extends Menu {

    public MainMenu(final GameCore game) {
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
        MenuButton options = new MenuButton(LoadManager.getMenuElement("main", "options")) {

            @Override
            public void onInteracted(Key key) {
                    game.currentMenu = new Options(game);
            }
        };
        MenuButton password = new MenuButton(LoadManager.getMenuElement("main", "password")) {

            @Override
            public void onInteracted(Key key) {
                    game.currentMenu = new Password(game);
            }
        };
        MenuButton highScores = new MenuButton(LoadManager.getMenuElement("main", "highScores")) {

            @Override
            public void onInteracted(Key key) {
                if(key.equals(Key.Enter)){
                    game.currentMenu = new HighScores(game);
                }
            }
        };
        MenuButton help = new MenuButton(LoadManager.getMenuElement("main", "help")) {

            @Override
            public void onInteracted(Key key) {
                if(key.equals(Key.Enter)){
                    game.currentMenu = new Help(game);
                }
            }
        };
        MenuButton exit = new MenuButton(LoadManager.getMenuElement("main", "exit")) {

            @Override
            public void onInteracted(Key key) {
                if(key.equals(Key.Enter))
                    System.exit(0);
            }
        };

        this.addButton(startGame);
        this.addButton(options);
        this.addButton(password);
        this.addButton(highScores);
        this.addButton(help);
        this.addButton(exit);
    }
}

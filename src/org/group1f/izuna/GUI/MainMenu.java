package org.group1f.izuna.GUI;

import org.group1f.izuna.Contollers.KeyboardHandler.Key;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.GameCore;

/**
 *
 * @author Mustafa
 */
public class MainMenu extends Menu {

    /**
     * This function creates two start game buttons, options button, password
     * button, high scores button, help button and exit button with their
     * interaction with keyboard at the creation of the object.
     *
     * @param game: core of the game which is the main controller of the game.
     */
    public MainMenu(final GameCore game) {
        super(game);
        MenuButton startGame1Player = new MenuButton(LoadManager.getMenuElement("main", "startGame1Player")) {

            @Override
            public void onInteracted(Key key) {
                if (key.equals(Key.Enter)) {
                    getOwner().startGame(true);
                }
            }
        };

        MenuButton startGame2Players = new MenuButton(LoadManager.getMenuElement("main", "startGame2Players")) {

            @Override
            public void onInteracted(Key key) {
                if (key.equals(Key.Enter)) {
                    getOwner().startGame(false);
                }
            }
        };

        MenuButton options = new MenuButton(LoadManager.getMenuElement("main", "options")) {

            @Override
            public void onInteracted(Key key) {
                getOwner().setCurrentMenu(new Options(game));
            }
        };
        MenuButton password = new MenuButton(LoadManager.getMenuElement("main", "password")) {

            @Override
            public void onInteracted(Key key) {
                 getOwner().setCurrentMenu(new Password(game));
            }
        };
        MenuButton highScores = new MenuButton(LoadManager.getMenuElement("main", "highScores")) {

            @Override
            public void onInteracted(Key key) {
                if (key.equals(Key.Enter)) {
                     getOwner().setCurrentMenu(new HighScores(game));
                }
            }
        };
        MenuButton help = new MenuButton(LoadManager.getMenuElement("main", "help")) {

            @Override
            public void onInteracted(Key key) {
                if (key.equals(Key.Enter)) {
                     getOwner().setCurrentMenu(new Help(game));
                }
            }
        };
        MenuButton exit = new MenuButton(LoadManager.getMenuElement("main", "exit")) {

            @Override
            public void onInteracted(Key key) {
                if (key.equals(Key.Enter)) {
                    System.exit(0);
                }
            }
        };

        this.addButton(startGame1Player);
        this.addButton(startGame2Players);
        this.addButton(options);
        this.addButton(password);
        this.addButton(highScores);
        this.addButton(help);
        this.addButton(exit);
    }
}

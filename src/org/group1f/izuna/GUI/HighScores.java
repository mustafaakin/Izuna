package org.group1f.izuna.GUI;

import java.util.ArrayList;
import org.group1f.izuna.Contollers.KeyboardHandler;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.Contollers.Score;
import org.group1f.izuna.GameCore;

/**
 *
 * @author mcad
 */
public class HighScores extends Menu {

    private ArrayList<Score> highScores;

 /**
     * This function creates high scores text and back button with interaction 
     * keyboard at the creation of the object. It only shows high scores to user.
     * @param game: core of the game which is the main controller of the game.
     */
    public HighScores(final GameCore game) {
        super(game);
        highScores = LoadManager.getHighScores();
        MenuElement helpText = LoadManager.getMenuElement("highscores", "highScores");

        MenuButton back = new MenuButton(LoadManager.getMenuElement("highscores", "back")) {

            @Override
            public void onInteracted(KeyboardHandler.Key key) {
                getOwner().setCurrentMenu(new MainMenu(game));
            }
        };
        this.addElement(helpText);
        this.addButton(back);
    }

    /**
     * 
     * @return
     */
    public ArrayList<Score> getHighScores() {
        return highScores;
    }
}

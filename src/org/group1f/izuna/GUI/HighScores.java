/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
     *
     * @param game
     */
    public HighScores(final GameCore game) {
        super(game);
        highScores = LoadManager.getHighScores();
        MenuElement helpText = LoadManager.getMenuElement("highscores", "highScores");

        MenuButton back = new MenuButton(LoadManager.getMenuElement("highscores", "back")) {

            @Override
            public void onInteracted(KeyboardHandler.Key key) {
                game.currentMenu = new MainMenu(game);
            }
        };
        this.addElement(helpText);
        this.addButton(back);
    }

    public ArrayList<Score> getHighScores() {
        return highScores;
    }
}

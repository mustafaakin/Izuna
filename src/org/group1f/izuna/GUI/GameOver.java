/**
 *
 * @author Mustafa
 */
package org.group1f.izuna.GUI;

import org.group1f.izuna.Contollers.KeyboardHandler;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.GameCore;

/**
 *
 * @author mcad
 */
public class GameOver extends Menu {

    /**
     * This menu is shown when users game is ended in anyway. User is asked to
     * enter his name for high-score submission
     *
     * @param gameCore
     */
    public GameOver(final GameCore gameCore) {
        super(gameCore);

        MenuButton back = new MenuButton(LoadManager.getMenuElement("help", "back")) {

            @Override
            public void onInteracted(KeyboardHandler.Key key) {
                if (key.equals(KeyboardHandler.Key.Enter)) {
                    LoadManager.submitHighScore(gameCore.getLastGameScore(), gameCore.getEnteredCharsSoFar());
                    gameCore.setCurrentMenu(new MainMenu(gameCore));
                }
            }
        };

        this.addButton(back);
    }
}

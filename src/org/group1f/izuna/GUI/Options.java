/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.group1f.izuna.GUI;

import org.group1f.izuna.Contollers.KeyboardHandler;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.GameCore;

/**
 * This class is used for displaying options menu.
 * @author Naime
 */
public class Options extends Menu{
    /**
     * This value is used for property key that holds sound value for game.
     */
    public static final String SOUND = "sound";
    /**
     * This value is used for property key that holds difficulty value for game.
     */
    public static final String DIFFICULTY = "difficulty";
    /**
     * This value is used for property key that holds 3D feature, if game runs in 3D mode.
     */
    public static final String _3D = "3D";
    
    /**
     * This function creates sound button, anaglyph button, difficulty button and 
     * back button with their interactions keyboard at the creation of the object.
     * @param game: core of the game which is the main controller of the game.
     */
    public Options(final GameCore game) {
        super(game);
        MenuButton sound = new MenuButton(LoadManager.getMenuElement("options", "soundDefault")) {
            @Override
            public void onInteracted(KeyboardHandler.Key key) {
                int i = this.getActive();
                if (i == 0) {
                    GameCore.preferences().putBoolean(SOUND, true);
                } else {
                    GameCore.preferences().putBoolean(SOUND, false);
                }
            }
        };
        sound.setActive(GameCore.preferences().getBoolean(SOUND, true) ? 0 : 1);
        sound.addElement(LoadManager.getMenuElement("options", "soundOn"));
        sound.addElement(LoadManager.getMenuElement("options", "soundOff"));
        MenuButton anaglyph = new MenuButton(LoadManager.getMenuElement("options", "3DAnagyphDefault")) {
            @Override
            public void onInteracted(KeyboardHandler.Key key) {
                GameCore.preferences().putBoolean(_3D, this.getActive() == 0);
            }
        };
        anaglyph.setActive(GameCore.preferences().getBoolean(_3D, true) ? 0 : 1);
        anaglyph.addElement(LoadManager.getMenuElement("options", "3DAnagyphOn"));
        anaglyph.addElement(LoadManager.getMenuElement("options", "3DAnagyphOff"));
        
        MenuButton difficulty = new MenuButton(LoadManager.getMenuElement("options", "difficultyDefault")) {
            int i = this.getActive();

            @Override
            public void onInteracted(KeyboardHandler.Key key) {
                GameCore.preferences().putInt(DIFFICULTY, this.getActive());
            }
        };
        difficulty.setActive(GameCore.preferences().getInt(DIFFICULTY, 0));
        
        difficulty.addElement(LoadManager.getMenuElement("options", "difficultyOne"));
        difficulty.addElement(LoadManager.getMenuElement("options", "difficultyTwo"));
        difficulty.addElement(LoadManager.getMenuElement("options", "difficultyThree"));
        
        MenuButton back = new MenuButton(LoadManager.getMenuElement("highscores", "back")) {

            @Override
            public void onInteracted(KeyboardHandler.Key key) {
                 getOwner().setCurrentMenu(new MainMenu(game)); 
            }
        };
        this.addButton(sound);
        this.addButton(difficulty);
        this.addButton(anaglyph);
        this.addButton(back);

    }    
}

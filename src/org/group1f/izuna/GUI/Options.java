/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.group1f.izuna.GUI;

import org.group1f.izuna.Contollers.KeyboardHandler;
import org.group1f.izuna.Contollers.LoadManager;
import org.group1f.izuna.GameCore;

/**
 *
 * @author mcad
 */
public class Options extends Menu{
    /**
     * 
     */
    public static final String SOUND = "sound";
    /**
     * 
     */
    public static final String DIFFICULTY = "difficulty";
    /**
     * 
     */
    public static final String _3D = "3D";
    
    /**
     * 
     * @param game
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
                game.currentMenu = new MainMenu(game); 
            }
        };
        this.addButton(sound);
        this.addButton(difficulty);
        this.addButton(anaglyph);
        this.addButton(back);

    }
    
}

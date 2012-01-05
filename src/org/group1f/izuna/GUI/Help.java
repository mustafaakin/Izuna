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
public class Help extends Menu{
    
    
    /**
     * 
     * @param gameCore
     */
    public Help(final GameCore gameCore){
        super(gameCore);
        MenuElement helpText = LoadManager.getMenuElement("help", "help");
        
        MenuButton back = new MenuButton(LoadManager.getMenuElement("help", "back")) {

            @Override
            public void onInteracted(KeyboardHandler.Key key) {
                gameCore.currentMenu = new MainMenu(gameCore); 
            }
        };
        
        this.addElement(helpText);
        this.addButton(back);
    }
}

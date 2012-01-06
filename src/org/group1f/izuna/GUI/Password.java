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
public class Password extends Menu{

    /**
     * 
     * @param owner
     */
    public Password(final GameCore owner) {
        super(owner);
        MenuElement title = LoadManager.getMenuElement("pass", "enterLevelPass");
        
        MenuButton back = new MenuButton(LoadManager.getMenuElement("help", "back")) {

            @Override
            public void onInteracted(KeyboardHandler.Key key) {
                if ( key.equals(KeyboardHandler.Key.Enter)){
                    owner.checkPassword();
                }
            }
        };
        this.addElement(title);
        this.addButton(back);
    }
    
}

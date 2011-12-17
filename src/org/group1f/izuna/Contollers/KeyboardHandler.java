package org.group1f.izuna.Contollers;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyboardHandler {

    public enum Key {

        Player1_Up, Player1_Down, Player1_Left, Player1_Right,
        Player1_Weapon1, Player1_Weapon2, Player1_Weapon3, Player1_Weapon4,
        Player2_Up, Player2_Down, Player2_Left, Player2_Right, Player2_Weapon1,
        Player2_Weapon2, Player2_Weapon3, Player2_Weapon4
    }

    // For both released & pressed keys, this method will be invoked.
    public static ArrayList<Key> getKeys(KeyEvent e) {
        ArrayList<Key> keys = new ArrayList<Key>();
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys.add(Key.Player1_Left);
        }
        return keys;
    }
 
        
}

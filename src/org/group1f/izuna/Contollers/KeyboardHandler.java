package org.group1f.izuna.Contollers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyboardHandler implements KeyListener {

    private ArrayList<Key> pressed;
    private ArrayList<Key> released;

    public KeyboardHandler() {
        pressed = new ArrayList<Key>();
        released = new ArrayList<Key>();
    }

    public ArrayList<Key> getPressed() {
        return pressed;
    }

    public ArrayList<Key> getReleased() {
        return released;
    }

 
    @Override
    public void keyTyped(KeyEvent e) { // Dont'need
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressed = resolveKey(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        released = resolveKey(e);
    }

    public enum Key {
        Player1_Up, Player1_Down, Player1_Left, Player1_Right,
        Player1_Weapon1, Player1_Weapon2, Player1_Weapon3, Player1_Weapon4,
        Player2_Up, Player2_Down, Player2_Left, Player2_Right, Player2_Weapon1,
        Player2_Weapon2, Player2_Weapon3, Player2_Weapon4
    }

    // For both released & pressed keys, this method will be invoked.
    public static ArrayList<Key> resolveKey(KeyEvent e) {
        ArrayList<Key> keys = new ArrayList<Key>();
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys.add(Key.Player1_Left);
        } else if ( e.getKeyCode() == KeyEvent.VK_SPACE){
            keys.add(Key.Player1_Weapon1);
        } else if ( e.getKeyCode() == KeyEvent.VK_DOWN){
            keys.add(Key.Player1_Down);
        }
        return keys;
    }

    
    
}

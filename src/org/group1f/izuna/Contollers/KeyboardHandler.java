package org.group1f.izuna.Contollers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.group1f.izuna.GameCore;

public class KeyboardHandler implements KeyListener {

    private GameCore owner;
    private Hashtable<Integer, Key> map;

    public KeyboardHandler(GameCore owner) {
        this.owner = owner;
        map = new Hashtable<Integer, Key>();
        map.put(KeyEvent.VK_DOWN, Key.Player1_Down);
        map.put(KeyEvent.VK_RIGHT, Key.Player1_Right);
        map.put(KeyEvent.VK_UP, Key.Player1_Up);
        map.put(KeyEvent.VK_LEFT, Key.Player1_Left);
        map.put(KeyEvent.VK_ESCAPE, Key.Escape);
        map.put(KeyEvent.VK_ENTER, Key.Enter);
        map.put(KeyEvent.VK_B, Key.Player1_Weapon1);

    }

    @Override
    public void keyTyped(KeyEvent e) { // Dont'need
    }

    @Override
    public void keyPressed(KeyEvent e) {
        owner.inputFromKeyboard(resolveKey(e), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        owner.inputFromKeyboard(resolveKey(e), false);
    }

    public enum Key {

        Player1_Up, Player1_Down, Player1_Left, Player1_Right,
        Player1_Weapon1, Player1_Weapon2, Player1_Weapon3, Player1_Weapon4,
        Player2_Up, Player2_Down, Player2_Left, Player2_Right, Player2_Weapon1,
        Player2_Weapon2, Player2_Weapon3, Player2_Weapon4,
        Escape, Enter
    }

    // For both released & pressed keys, this method will be invoked.
    public Key resolveKey(KeyEvent e) {
        return map.get(e.getKeyCode());
    }
}

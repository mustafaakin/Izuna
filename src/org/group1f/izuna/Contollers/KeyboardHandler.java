package org.group1f.izuna.Contollers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import org.group1f.izuna.GameCore;

/**
 *
 * @author Mustafa
 */
public class KeyboardHandler implements KeyListener {

    private EnumMap<Key, Boolean> active;
    private GameCore owner;
    private Hashtable<Integer, Key> map;

    /**
     *
     * @param owner
     */
    public KeyboardHandler(GameCore owner) {
        this.owner = owner;
        map = new Hashtable<Integer, Key>();
        active = new EnumMap<Key, Boolean>(Key.class);

        map.put(KeyEvent.VK_ESCAPE, Key.Escape);
        map.put(KeyEvent.VK_ENTER, Key.Enter);


        map.put(KeyEvent.VK_DOWN, Key.Player1_Down);
        map.put(KeyEvent.VK_RIGHT, Key.Player1_Right);
        map.put(KeyEvent.VK_UP, Key.Player1_Up);
        map.put(KeyEvent.VK_LEFT, Key.Player1_Left);
        map.put(KeyEvent.VK_H, Key.Player1_Weapon1);
        map.put(KeyEvent.VK_U, Key.Player1_Weapon2);
        map.put(KeyEvent.VK_K, Key.Player1_Weapon3);
        map.put(KeyEvent.VK_L, Key.Player1_Weapon4);
        map.put(KeyEvent.VK_SPACE, Key.Player1_Weapon5);


        map.put(KeyEvent.VK_S, Key.Player2_Down);
        map.put(KeyEvent.VK_D, Key.Player2_Right);
        map.put(KeyEvent.VK_W, Key.Player2_Up);
        map.put(KeyEvent.VK_A, Key.Player2_Left);
        map.put(KeyEvent.VK_1, Key.Player2_Weapon1);
        map.put(KeyEvent.VK_2, Key.Player2_Weapon2);
        map.put(KeyEvent.VK_3, Key.Player2_Weapon3);
        map.put(KeyEvent.VK_4, Key.Player2_Weapon4);
        map.put(KeyEvent.VK_TAB, Key.Player2_Weapon5);

    }

    /**
     *
     * @return
     */
    public Set<Key> getActive() {
        return active.keySet();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        owner.keyTypeFromKeyboard(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Key key = resolveKey(e);
        if (key == null) {
            return;
        }
        active.put(key, true);
        owner.inputFromKeyboard(key, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Key key = resolveKey(e);
        if (key == null) {
            return;
        }
        active.remove(key);
        owner.inputFromKeyboard(key, false);
    }

    /**
     *
     */
    public enum Key {

        /**
         *
         */
        Player1_Up,
        /**
         *
         */
        Player1_Down,
        /**
         *
         */
        Player1_Left,
        /**
         *
         */
        Player1_Right,
        /**
         *
         */
        Player1_Weapon1,
        /**
         *
         */
        Player1_Weapon2,
        /**
         *
         */
        Player1_Weapon3,
        /**
         *
         */
        Player1_Weapon4,
        Player1_Weapon5,
        /**
         *
         */
        Player2_Up,
        /**
         *
         */
        Player2_Down,
        /**
         *
         */
        Player2_Left,
        /**
         *
         */
        Player2_Right,
        /**
         *
         */
        Player2_Weapon1,
        /**
         *
         */
        Player2_Weapon2,
        /**
         *
         */
        Player2_Weapon3,
        /**
         *
         */
        Player2_Weapon4,
        Player2_Weapon5,
        /**
         *
         */
        Escape,
        /**
         *
         */
        Enter
    }

    // For both released & pressed keys, this method will be invoked.
    /**
     *
     * @param e
     * @return
     */
    public Key resolveKey(KeyEvent e) {
        return map.get(e.getKeyCode());
    }
}

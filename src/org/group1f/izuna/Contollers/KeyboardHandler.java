package org.group1f.izuna.Contollers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import org.group1f.izuna.GameCore;

public class KeyboardHandler implements KeyListener {

    private GameCore owner;

    public KeyboardHandler(GameCore owner) {
        this.owner = owner;
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
        Escape
    }

    // For both released & pressed keys, this method will be invoked.
    public static Key resolveKey(KeyEvent e) {

        // TODO Change with switch-break
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            return Key.Player1_Left;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            return Key.Player1_Weapon1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            return Key.Player1_Down;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            return Key.Escape;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            return Key.Player1_Up;
        }
        return null;
    }
}

/**
 *
 */
package org.group1f.izuna;

import org.group1f.izuna.GameComponents.Drawing.*;

import java.util.prefs.Preferences;
import org.group1f.izuna.GameComponents.LinearPath;

/**
 * @author Mustafa
 *
 */
public class GameCore {

    private static Preferences prefs = Preferences.userNodeForPackage(GameCore.class);

    public static Preferences preferences() {
        return prefs;
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        Point start = new Point(100,300);
        Point end = new Point(100,500);
        LinearPath path = new LinearPath(start, end, 150);
        System.out.println("It is alive!");
    }
}

/**
 *
 */
package org.group1f.izuna;

import org.group1f.izuna.GameComponents.Drawing.*;

import java.awt.Point;
import java.util.prefs.Preferences;
import org.group1f.izuna.GameComponents.LinearPath;
import org.group1f.izuna.GameComponents.QuadraticPath;


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

        Point start = new Point(100,150);
        Point middle = new Point(150,250);
        Point end = new Point(200,500);
        
        QuadraticPath path = new QuadraticPath(start, end, middle, 10000);
        path.setStartTime(1500);
        for (int i = 0; i < 100 ; i++) {
           Point p = path.getPosition(1500+ i * 50);
            System.out.println(p.x+ "\t" + p.y);
        }
        
    }
}

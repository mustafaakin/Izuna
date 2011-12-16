/**
 * 
 */
package org.group1f.izuna;

import java.util.prefs.Preferences;

import org.group1f.izuna.Contollers.LoadManager;

/**
 * @author Mustafa
 *
 */
public class GameCore {
	private static Preferences prefs = Preferences.userNodeForPackage(GameCore.class);
	
	public static Preferences preferences(){
		return prefs;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("It is alive!");
	}

}

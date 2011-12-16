package org.group1f.izuna.GUI;

import org.group1f.izuna.GameComponents.Drawing.*;
import java.util.*;

public abstract class Menu {
	private int activeElements;
	private ArrayList<Sprite> menuElement;

	public Menu() {
		activeElements = 0;
		menuElement = new ArrayList<Sprite>();
	}	
}

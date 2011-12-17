package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public class Player extends GameObject implements SpaceShip {
	
	public Player(Point currentPos, Animation rest)
        {
            super(currentPos, rest);
	}
        
        public void checkStateToAnimate()
        {
            
        }
}

package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.Animation;

public abstract class AIControllable extends GameObject {

        public AIControllable(Point currentPos, Animation rest)
        {
            super(currentPos, rest);
        }
    
	public Point getPosition(){
		return getPosition();
	}

        public void checkStateToAnimate()
        {
            
        }
}

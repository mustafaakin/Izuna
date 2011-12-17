package org.group1f.izuna.GameComponents;

import org.group1f.izuna.GameComponents.Drawing.*;

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

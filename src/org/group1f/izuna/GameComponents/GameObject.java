package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public abstract class GameObject extends Sprite{

	private Animation restAnimation;
       // private Animation dieAnimation;
	private boolean isVisible;
        SoundEffect currentSound;
        SoundEffect dieSound;
        
	
	public GameObject(Point currentPos, Animation rest){
            super(currentPos);
            restAnimation = rest;
            currentAnimation = rest;
            isVisible = true;
            checkStateToAnimate();
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Animation getRestAnimation() {
		return restAnimation;
	}

	public void setRestAnimation(Animation restAnimation) {
		this.restAnimation = restAnimation;
	}
        
        @Override
        public void update(long elapsedTime) {
                playSound();
		super.update(elapsedTime);
	}
        
        public void playSound() {
                if(currentSound != null)
                    currentSound.play();
        }
        
        public abstract void checkStateToAnimate();
}

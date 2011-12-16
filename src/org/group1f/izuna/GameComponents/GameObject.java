package org.group1f.izuna.GameComponents;

import org.group1f.izuna.GameComponents.Drawing.*;

public abstract class GameObject extends Sprite{
	private Animation restAnimation;
	private boolean isVisible;
	
	public GameObject(){
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
}

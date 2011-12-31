package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public abstract class GameObject extends Sprite {

    private Animation stillAnimation;
    private boolean isVisible;
    private SoundEffect dieSound;

    /*
     * Her Game objesi genel bir ses çıkarmıyor, yani currentSound a gerek yok,
     * dieSound da yok olduklarında çıkacak ses fakat o da null olabilir o
     * yüzden constructora koymadım
     */
    public GameObject(Animation still) {
        setPosition(new Point(0, 0));
        stillAnimation = still;
        currentAnimation = still;
        isVisible = false;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Animation getStillAnimation() {
        return stillAnimation;
    }

    public void setStillAnimation(Animation stillAnimation) {
        this.stillAnimation = stillAnimation;
    }

    public void setDieSound(SoundEffect dieSound) {
        this.dieSound = dieSound;
    }

    public SoundEffect getDieSound() {
        return dieSound;
    }

    public abstract void checkStateToAnimate();
}

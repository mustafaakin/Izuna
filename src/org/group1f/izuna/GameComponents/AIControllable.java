package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.Animation;

public abstract class AIControllable extends GameObject {

    public Path defaultPath;

    public AIControllable(Point currentPos, Animation rest, SoundEffect dieSound, Path path) {
        super(currentPos, rest, dieSound);
        defaultPath = path;
    }

    @Override
    public Point getPosition() {
        return getPosition();
    }

    @Override
    public void checkStateToAnimate() {
    }
}

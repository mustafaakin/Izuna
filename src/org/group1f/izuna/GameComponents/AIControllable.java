package org.group1f.izuna.GameComponents;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import org.group1f.izuna.GameComponents.Drawing.Animation;

public abstract class AIControllable extends GameObject {

    private ArrayList<Path> paths; // Tek bir path değil, path listesi olması gerekiyor

    public AIControllable(Animation still) {
        super(still);
        this.paths = new ArrayList<Path>();
    }


    public void setPosition() {
        setPosition(this.paths.get(0).getStartPoint());
    }

    public void addPath(Path p) {
        this.paths.add(p);
    }

    // Pathlerin ilkinin time ı x ise diğerinin start ı x.start + x.duration olmalı.
    public void setPathActivationTime(long startTime) {
        if (!paths.isEmpty()) {
            paths.get(0).setStartTime(startTime);
            for (int i = 1; i < paths.size(); i++) {
                Path current = paths.get(i);
                Path before = paths.get(i - 1);
                current.setStartTime(before.getDuration() + before.getStartTime());
            }
        }
    }

    // AIControllable lar PathList te hangisnin sırası geldiyse onun getPositionunu çağırılarak getirilecek
    @Override
    public Point getPosition() {
        long time = System.currentTimeMillis();
        for (int i = 0; i < paths.size(); i++) {
            Path p = paths.get(i);
            if (p.isValidTime(time)) {
                return p.getPosition(time);
            }
        }
        return null;
    }

    @Override
    public void checkStateToAnimate() {
    }
}

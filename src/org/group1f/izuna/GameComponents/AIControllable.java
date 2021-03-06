package org.group1f.izuna.GameComponents;

import java.awt.Point;
import java.util.ArrayList;
import org.group1f.izuna.GameComponents.Drawing.Animation;

/**
 * This class will just provide a list of Path objects, for which needed position calculation. 
 * All the CPU controlled objects must extend this abstract class.
 * @author Mustafa
 */
public abstract class AIControllable extends GameObject {

    private Point prevPosition;
    private ArrayList<Path> paths; // Tek bir path değil, path listesi olması gerekiyor

    /**
     * 
     * @param still still(initial) animation
     */
    public AIControllable(Animation still) {
        super(still);
        this.paths = new ArrayList<Path>();
    }

    /**
     * Sets the starting position of the path.
     */
    public void setStartingPosition() {
        setPosition(this.paths.get(0).getStartPoint());
    }
    
    /**
     * Adds Path p to the path list
     * @param p
     */
    public void addPath(Path p) {
        this.paths.add(p);
    }

    // Pathlerin ilkinin time ı x ise diğerinin start ı x.start + x.duration olmalı.
    /**
     * Sets the activation time for the paths.
     * @param startTime start time of the path in milliseconds
     */
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
                Point nextPosition = p.getPosition(time);
                if (prevPosition != null) {
                    int diffX = nextPosition.x - prevPosition.x;
                    int diffY = nextPosition.y - prevPosition.y;
                    this.setvX((float) (diffX));
                    this.setvY((float) (diffY));
                }
                this.setPosition(nextPosition);
                prevPosition = nextPosition;
                return nextPosition;
            }
        }
        this.setvX(0.0f);
        this.setvY(0.0f);
        return prevPosition;
    }
}

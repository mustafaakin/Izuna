package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

/**
 * 
 * @author Mustafa
 */
public abstract class Path {

    private long startTime;
    private long duration;
    private Point startPoint;
    private Point endPoint;

    /**
     * 
     * @param start
     * @param end
     * @param duration
     */
    public Path(Point start, Point end, long duration) {
        this.duration = duration;
        this.startPoint = start;
        this.endPoint = end;
    }

    /**
     * 
     * @param time
     * @return
     */
    public boolean isValidTime(long time) {
        long limit = duration + startTime;
        return limit > time;
    }

    /**
     * 
     * @param time
     * @return
     */
    abstract public Point getPosition(long time);

    /**
     * 
     * @param startTime
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     * @return
     */
    public long getDuration() {
        return duration;
    }

    /**
     * 
     * @return
     */
    public Point getEndPoint() {
        return endPoint;
    }

    /**
     * 
     * @return
     */
    public Point getStartPoint() {
        return startPoint;
    }

    /**
     * 
     * @return
     */
    public long getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "Starts @" + getStartTime() + "\tlasts:" + getDuration();
    }
}

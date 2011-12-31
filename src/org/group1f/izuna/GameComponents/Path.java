package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public abstract class Path {

    private long startTime;
    private long duration;
    private Point startPoint;
    private Point endPoint;

    public Path(Point start, Point end, long duration) {
        this.duration = duration;
        this.startPoint = start;
        this.endPoint = end;
    }

    public boolean isValidTime(long time) {
        long limit = duration + startTime;
        return limit > time;
    }

    abstract public Point getPosition(long time);

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public long getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "Starts @" + getStartTime() + "\tlasts:" + getDuration();
    }
}

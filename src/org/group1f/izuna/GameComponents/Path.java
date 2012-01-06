package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

/**
 * This class is used for defining paths.
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
     * @param start : beginning of the path
     * @param end : end of the path
     * @param duration : duration of the path
     */
    public Path(Point start, Point end, long duration) {
        this.duration = duration;
        this.startPoint = start;
        this.endPoint = end;
    }

    /**
     * This function handles time is valid or not.
     *
     * @param time
     * @return limit > time
     */
    public boolean isValidTime(long time) {
        long limit = duration + startTime;
        return limit > time;
    }

    /**
     * This function gets position of point.
     *
     * @param time
     * @return position
     */
    abstract public Point getPosition(long time);

    /**
     * This function sets start time.
     *
     * @param startTime : starting time of the game
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * This function gets the duration.
     *
     * @return duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * This function gets end point.
     *
     * @return endPoint
     */
    public Point getEndPoint() {
        return endPoint;
    }

    /**
     * This function gets start point
     *
     * @return startPoint
     */
    public Point getStartPoint() {
        return startPoint;
    }

    /**
     * This function gets start time.
     *
     * @return startTime
     */
    public long getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "Starts @" + getStartTime() + "\tlasts:" + getDuration();
    }
}

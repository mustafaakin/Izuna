package org.group1f.izuna.GameComponents;

import java.awt.Point;

/**
 * 
 * @author Mustafa
 */
public class LinearPath extends Path {

    /**
     * 
     * @param start The position that the object is gonna start moving at this path
     * @param end The position that the object is gonna end moving at this path
     * @param duration The duration that the object is gonna move from start to end
     */
    public LinearPath(Point start, Point end, long duration) {
        super(start, end, duration);
    }

    /**
     * Calculates the position that the object is gonna be on the given time
     * @param time Timestamp which indicates the requested position
     * @return The position that object should be on.
     */
    @Override
    public Point getPosition(long time) {

        long timeDiff = time - super.getStartTime();

        if (timeDiff > super.getDuration() || timeDiff < 0) {
            throw new IllegalArgumentException("Given time should be at most duration times larger than starting time of the path.");
        }

        float incrRatio = (float) timeDiff / super.getDuration();

        Point result = new Point(super.getStartPoint());
        int diffX = super.getEndPoint().x - super.getStartPoint().x;
        int diffY = super.getEndPoint().y - super.getStartPoint().y;

        result.x += (diffX * incrRatio);
        result.y += (diffY * incrRatio);

        return result;
    }

}

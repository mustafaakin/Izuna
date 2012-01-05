package org.group1f.izuna.GameComponents;

import java.awt.Point;

/**
 * 
 * @author Mustafa
 */
public class LinearPath extends Path {

    /**
     * 
     * @param start
     * @param end
     * @param duration
     */
    public LinearPath(Point start, Point end, long duration) {
        super(start, end, duration);
    }

    /**
     * 
     * @param time
     * @return
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

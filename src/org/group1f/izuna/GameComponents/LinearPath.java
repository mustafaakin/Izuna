package org.group1f.izuna.GameComponents;

import java.awt.Point;

public class LinearPath extends Path {

    public LinearPath(Point start, Point end, long duration) {
        super(start, end, duration);
    }

    @Override
    public Point getPosition(long time) {

        long timeDiff = time - startTime;

        if (timeDiff > duration || timeDiff < 0) {
            throw new IllegalArgumentException("Given time should be at most duration times larger than starting time of the path.");
        }

        float incrRatio = (float)timeDiff / duration;

        Point result = new Point(startPoint);
        int diffX = endPoint.x - startPoint.x;
        int diffY = endPoint.y - startPoint.y;

        result.x += (diffX * incrRatio);
        result.y += (diffY * incrRatio);

        return result;
    }
}

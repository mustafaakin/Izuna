package org.group1f.izuna.GameComponents;

import org.group1f.izuna.GameComponents.Drawing.Point;

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
        int diffX = endPoint.getxCor() - startPoint.getxCor();
        int diffY = endPoint.getyCor() - startPoint.getyCor();

        result.incrX(diffX * incrRatio);
        result.incrY(diffY * incrRatio);

        return result;
    }
}

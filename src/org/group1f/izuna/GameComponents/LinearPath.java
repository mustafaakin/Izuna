package org.group1f.izuna.GameComponents;

import org.group1f.izuna.GameComponents.Drawing.Point;

public class LinearPath extends Path {

    public LinearPath(Point start, Point end, long duration) {
        super(start, end, duration);
    }

    @Override
    public Point getPosition(long time) {
        Point result = new Point(startPoint);
        int diffX = endPoint.getxCor() - startPoint.getxCor();
        int diffY = endPoint.getyCor() - startPoint.getyCor();

        long timeDiff = time - startTime;

        result.incrX(diffX / timeDiff);
        result.incrY(diffX / timeDiff);

        return result;
    }
}

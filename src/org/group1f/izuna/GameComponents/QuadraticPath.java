package org.group1f.izuna.GameComponents;

import java.awt.Point;
import Jama.Matrix;
import Jama.LUDecomposition;

/**
 *
 * @author Mustafa
 */
public class QuadraticPath extends Path {

    private float[] coEfficients = new float[3];
    private Point middlePoint;

    /**
     * @param start The position that the object is gonna start moving at this
     * path
     * @param middle The 3rd position needed to calculate quadratic path.
     * @param end The position that the object is gonna end moving at this path
     * @param duration The duration that the object is gonna move from start to
     * end
     */
    public QuadraticPath(Point start, Point end, Point middle, long duration) {
        super(start, end, duration);
        this.middlePoint = middle;
        setEquation();
    }

    /**
     * Calculates the position that the object is gonna be on the given time
     *
     * @param time Timestamp which indicates the requested position
     * @return The position that object should be on.
     */
    @Override
    public Point getPosition(long time) {
        long timeDiff = time - super.getStartTime();

        if (timeDiff > super.getDuration() || timeDiff < 0) {
            throw new IllegalArgumentException("Given time should be at most duration times larger than starting time of the path.");
        }
        Point result = new Point(super.getStartPoint());

        float incrRatio = (float) timeDiff / super.getDuration();
        int diffX = super.getEndPoint().x - super.getStartPoint().x;

        float resultingX = diffX * incrRatio + result.x;
        float resultingY = coEfficients[0] * resultingX * resultingX + coEfficients[1] * resultingX + coEfficients[2];

        result.move((int) resultingX, (int) resultingY);
        return result;
    }

    /**
     * Calculates the quadratic equation from the 3 points given.
     */
    private void setEquation() {
        int[] x = new int[3];
        int[] y = new int[3];

        x[0] = super.getStartPoint().x;
        x[1] = middlePoint.x;
        x[2] = super.getEndPoint().x;

        y[0] = super.getStartPoint().y;
        y[1] = middlePoint.y;
        y[2] = super.getEndPoint().y;

        /*
         * Adapted from Solution:
         * http://stackoverflow.com/questions/1992638/java-inverse-matrix-calculation
         */
        double[][] values = {{x[0] * x[0], x[0], 1}, {x[1] * x[1], x[1], 1}, {x[2] * x[2], x[2], 1}};  // each array is a row in the matrix
        double[] rhs = {y[0], y[1], y[2]}; // rhs vector

        Matrix a = new Matrix(values);
        LUDecomposition luDecomposition = new LUDecomposition(a);

        Matrix b = new Matrix(rhs, rhs.length);
        Matrix solved = luDecomposition.solve(b); // solve Ax = b for the unknown vector x

        for (int i = 0; i < 3; i++) {
            coEfficients[i] = (float) solved.get(i, 0);
        }
    }
}

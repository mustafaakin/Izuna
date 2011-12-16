package org.group1f.izuna.GameComponents;

import org.group1f.izuna.GameComponents.Drawing.Point;

public class QuadraticPath extends Path {
	private Point middlePoint;

	public QuadraticPath(Point start, Point end, Point middle, long duration) {
		super(start, end, duration);
		this.middlePoint = middle;
	}

	@Override
	public Point getPosition(long time) {
		// TODO Auto-generated method stub
		return null;
	}
}

package org.group1f.izuna.GameComponents;

import org.group1f.izuna.GameComponents.Drawing.Point;

public class LinearPath extends Path {
	public LinearPath(Point start, Point end, long duration){
		super(start,end,duration);
	}
	
	@Override
	public Point getPosition(long time) {
		long elapsed = time - startTime;		
		return null;
	}
}

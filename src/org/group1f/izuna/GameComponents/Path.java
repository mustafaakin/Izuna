package org.group1f.izuna.GameComponents;

import java.awt.Point;
import org.group1f.izuna.GameComponents.Drawing.*;

public abstract class Path {
	long startTime;
	long duration;
	Point startPoint;
	Point endPoint;
	
	public Path(Point start, Point end, long duration){
		this.duration = duration;
		this.startPoint = start;
		this.endPoint = end;
	}
	abstract public Point getPosition(long time);

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
        
        
}

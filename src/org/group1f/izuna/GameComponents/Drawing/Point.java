package org.group1f.izuna.GameComponents.Drawing;

public class Point {
	private int xCor;
	private int yCor;
	public int getxCor() {
		return xCor;
	}
	public void setxCor(int xCor) {
		this.xCor = xCor;
	}
	public int getyCor() {
		return yCor;
	}
	public void setyCor(int yCor) {
		this.yCor = yCor;
	}
	public Point(int xCor, int yCor){
		this.xCor = xCor;
		this.yCor = yCor;
	}
	
	public void incrX(float f){
		this.xCor += f;
	}

	public void incrY(float y){
		this.yCor += y;
	}
	
	static public Point getOutOfBounds(){
		return new Point(-1000,-1000);
	}
}

package partOne;

import partOne.Coordinate;

public class Coordinate {
	
	private float xCoord;
	
	private float yCoord;
	
	
	/**
	 * Constructeur de coordonnées
	 * @param xCoord : abscisse
	 * @param yCoord : ordonnée
	 */
	public Coordinate(float xCoord, float yCoord){
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	
	public Coordinate() {
		this.xCoord=0;
		this.yCoord=0;
	}
	
	public Coordinate(double d, double e) {
		this.xCoord = (float) xCoord;
		this.yCoord = (float) yCoord;
	}

	public void setxCoord(float xCoord) {
		this.xCoord = xCoord;
	}
	public void setyCoord(float yCoord) {
		this.yCoord = yCoord;
	}
	public float getxCoord() {
		return xCoord;
	}
	public float getyCoord() {
		return yCoord;
	}
	
	/**
	 * Calcule la distance entre le point courant et le point c
	 * @param c
	 * @return
	 */
	public double dist(Coordinate c) {
		return Math.pow(Math.pow(this.getxCoord()-c.getxCoord(), 2)+Math.pow(this.getyCoord()-c.getyCoord(), 2),0.5);
	}
	
	public String toString() {
		return "["+xCoord+","+yCoord+"]";
	}
	
	@Override
	public boolean equals(Object o) {
		Coordinate c=(Coordinate) o;
		return (this.xCoord==c.xCoord && this.yCoord==c.yCoord);
	}

}
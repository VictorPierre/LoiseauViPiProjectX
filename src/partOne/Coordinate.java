package partOne;

import partOne.Coordinate;

public class Coordinate {
	
	private float xCoord;
	
	private float yCoord;
	
	
	/**
	 * Constructeur de coordonn�es
	 * @param xCoord : abscisse
	 * @param yCoord : ordonn�e
	 */
	public Coordinate(float xCoord, float yCoord){
		this.xCoord = xCoord;
		this.yCoord = yCoord;
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

}
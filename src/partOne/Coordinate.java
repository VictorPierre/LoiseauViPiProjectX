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
	Coordinate(float xCoord, float yCoord){
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
	
	public static void main(String[] args) {
		Coordinate c1 = new Coordinate(0,0);
		Coordinate c2 = new Coordinate(0,0);
		System.out.println(c1.dist(c2));
	}
	
	public String toString() {
		return "["+xCoord+","+yCoord+"]";
	}

}
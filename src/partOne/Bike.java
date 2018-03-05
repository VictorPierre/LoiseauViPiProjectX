package partOne;

public abstract class Bike {
	
	//Bike ID
	protected int bikeID;
	
	//Number which will be used to create the ID
	protected static int counter = 100000;
	
	//Last digit of the ID, representing the bike
	private static final int lastDigit = 2;
	
	Bike(){
		this.bikeID = 10*counter+lastDigit;
		counter ++;
	}

	public int getBikeID() {
		return bikeID;
	}

	public void setBikeID(int bikeID) {
		this.bikeID = bikeID;
	}
	
}
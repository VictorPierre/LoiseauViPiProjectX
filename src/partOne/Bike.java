package partOne;

public abstract class Bike {
	
	//Bike ID
	protected int id;
	
	//Number which will be used to create the ID
	protected static int counter = 100000;
	
	//Last digit of the ID, representing the bike
	private static final int lastDigit = 2;
	
	Bike(){
		this.id = 10*counter+lastDigit;
		counter ++;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}
	
}
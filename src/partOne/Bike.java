package partOne;

public abstract class Bike {
	
	//Bike ID
	protected int id;
	
	//Number which will be used to create the ID
	protected static int counter = 1;
	
	Bike(){
		this.id = counter;
		counter ++;
	}

	public int getId() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}
	
	//Get speed
	public double getSpeed() {
		return 15;
	};
	
	
}
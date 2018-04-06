package partOne;

public class ElectricBike extends Bike{
		
	//Constructor
	public ElectricBike(){
		super();
	}
	
	//toString method
	public String toString() {
		return "V�lo �lectrique n� "+this.id;
	}
	
	public String getType() {
		return "Electric";
	}
	
	@Override public double getSpeed() {
	        return 20;
	    }
	
}

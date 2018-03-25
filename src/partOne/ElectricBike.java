package partOne;

public class ElectricBike extends Bike{
		
	//Constructor
	ElectricBike(){
		super();
	}
	
	//toString method
	public String toString() {
		return "Vélo électrique n° "+this.id;
	}
	
	public String getType() {
		return "Electric";
	}
	
	@Override public double getSpeed() {
	        return 20;
	    }
	
}

package partOne;

public class MechanicBike extends Bike{
	
	//Constructor
	public MechanicBike(){
		super();
	}
	
//toString method
	public String toString() {
		return "V�lo m�chanique n� "+this.id;
	}	
	
	public String getType() {
		return "Mechanic";
	}

}

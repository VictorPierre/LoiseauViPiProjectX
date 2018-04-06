package partOne;

public class MechanicBike extends Bike{
	
	//Constructor
	public MechanicBike(){
		super();
	}
	
//toString method
	public String toString() {
		return "Vélo méchanique n° "+this.id;
	}	
	
	public String getType() {
		return "Mechanic";
	}

}

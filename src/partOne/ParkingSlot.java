package partOne;

import Exceptions.EmptySlotException;
import Exceptions.FullSlotException;
import Exceptions.OutOfOrderException;

public class ParkingSlot {
	
	
	//Parkin slot ID
	private int id;
	
	//Number which will be used to create the ID
	private static int counter = 100000;
	
	//Last digit of the ID, representing the parking slot
	private static final int lastDigit = 1;
	
	
	//Whether the slot is usable or not
	private boolean isOutOfOrder;
	
	//Ther bike the parking stores
	private Bike bike;
	
	//Constructors
		//Default one
	ParkingSlot(){
		this.id = 10*counter+lastDigit;
		this.isOutOfOrder = false;
		this.bike = null;
		counter ++;
	}
		//Constructor for a given bike
	ParkingSlot(Bike bike){
		this.id = 10*counter+lastDigit;
		this.isOutOfOrder = false;
		this.bike = bike;
		counter ++;
	}

	//Setters
	public void setOutOfOrder(boolean isOutOfOrder) {
		this.isOutOfOrder = isOutOfOrder;
	}

	//Getters
	public int getID() {
		return id;
	}
	public Bike getBike() {
		return bike;
	}
	public boolean isOutOfOrder() {
		return isOutOfOrder;
	}
	
	//toString method
	public String toString() {
		String str = "";
		str += "La place de parking n° "+this.id+" :\n";
		str += "État : " + ((this.isOutOfOrder)? "Hors service":"En service")+"\n";
		str += "Vélo Stocké : " + ((this.bike == null)? "Aucun":this.bike.toString())+"\n";
		return str;
	}
	
	//Methods to return and take a bike from the slot
	
	public void returnBike(Bike bike) throws FullSlotException, OutOfOrderException{
		if (this.isOutOfOrder) {
			throw new OutOfOrderException(this.id);}
		else if (this.bike != null)
		{throw new FullSlotException(this.id);}
		else {
			this.bike=bike;}
	}	
	public Bike takeBike() throws EmptySlotException, OutOfOrderException{
		if (this.isOutOfOrder) {
			throw new OutOfOrderException(this.id);}
		else if (this.bike == null)
		{throw new EmptySlotException(this.id);}
		else {
			Bike bikeTaken = this.bike;
			this.bike = null;
			return bikeTaken;
			
		}
	}
	
	
	public static void main(String[] args) {
		ParkingSlot p = new ParkingSlot(new ElectricBike());
		System.out.println(p.toString());
		ParkingSlot o = new ParkingSlot(null);
		System.out.println(o.toString());
		ParkingSlot m = new ParkingSlot(new MechanicBike());
		m.setOutOfOrder(true);
		System.out.println(m.toString());
		try {
			p.returnBike(new ElectricBike());
		} catch (FullSlotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutOfOrderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

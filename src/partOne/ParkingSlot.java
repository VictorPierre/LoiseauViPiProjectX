package partOne;

import Exceptions.EmptySlotException;
import Exceptions.FullSlotException;
import Exceptions.OutOfOrderException;

/**
 * Un parkingSlot est défini par un Id, peut contenir ou non un vélo, et peut être hors service
 *
 */

public class ParkingSlot {
	
	
	//Parkin slot ID
	private int id;
	
	//Number which will be used to create the ID
	private static int counter = 1;

	
	
	//Whether the slot is usable or not
	private boolean isOutOfOrder;
	
	//Ther bike the parking stores
	private Bike bike;
	
	//Constructors
		//Default one
	public ParkingSlot(){
		this.id = counter;
		this.isOutOfOrder = false;
		this.bike = null;
		counter ++;
	}
		//Constructor for a given bike
	public ParkingSlot(Bike bike){
		this.id = counter;
		this.isOutOfOrder = false;
		this.bike = bike;
		counter ++;
	}

	/**
	 * Setter pour mettre un ParkingSlot hors ou en service
	 * @param isOutOfOrder
	 */
	public void setOutOfOrder(boolean isOutOfOrder) {
		this.isOutOfOrder = isOutOfOrder;
	}

	//Getters
	public int getId() {
		return id;
	}
	public Bike getBike() {
		return bike;
	}
	public int getBikeId() {
		return bike.getId();
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
	
	/**
	 * Permet de déposer un vélo dans un parkingSlot
	 * @param bike : le vélo à déposer
	 * @throws FullSlotException : le slot est déjà occupé
	 * @throws OutOfOrderException : le slot est H-S
	 */
	
	public void returnBike(Bike bike) throws FullSlotException, OutOfOrderException{
		if (this.isOutOfOrder) {
			throw new OutOfOrderException(this.id);}
		else if (this.bike != null)
		{throw new FullSlotException(this.id);}
		else {
			this.bike=bike;}
	}	
	
	/**
	 * Permet de prendre le vélo d'un slot
	 * @return
	 * @throws EmptySlotException : le slot est vide
	 * @throws OutOfOrderException : le slot est H-S
	 */
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
}

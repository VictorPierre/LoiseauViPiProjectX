package partOne;

public class ParkingSlot {
	
	
	//Parkin slot ID
	private int parkingSlotID;
	
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
		this.parkingSlotID = 10*counter+lastDigit;
		this.isOutOfOrder = false;
		this.bike = null;
		counter ++;
	}
		//Constructor for a given bike
	ParkingSlot(Bike bike){
		this.parkingSlotID = 10*counter+lastDigit;
		this.isOutOfOrder = false;
		this.bike = bike;
		counter ++;
	}

	//Setters
	public void setOutOfOrder(boolean isOutOfOrder) {
		this.isOutOfOrder = isOutOfOrder;
	}
	public void setBike(Bike bike) {
		this.bike = bike;
	}

	//Getters
	public int getParkingSlotID() {
		return parkingSlotID;
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
		str += "La place de parking n° "+this.parkingSlotID+" :\n";
		str += "État : " + ((this.isOutOfOrder)? "Hors service":"En service")+"\n";
		str += "Vélo Stocké : " + ((this.bike == null)? "Aucun":this.bike.toString())+"\n";
		return str;
	}
	
	public static void main(String[] args) {
		ParkingSlot p = new ParkingSlot(new ElectricBike());
		System.out.println(p.toString());
		ParkingSlot o = new ParkingSlot(null);
		System.out.println(o.toString());
		ParkingSlot m = new ParkingSlot(new MechanicBike());
		m.setOutOfOrder(true);
		System.out.println(m.toString());
	}
}

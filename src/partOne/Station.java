package partOne;

import java.util.Arrays;
import java.util.TreeMap;

import Exceptions.EmptySlotException;
import Exceptions.FullSlotException;
import Exceptions.OutOfOrderException;
import stationType.*;


public class Station {
	
	//Name of the station
	private String name;
		
	//Location of the station
	private float[] location = {0,0};
	
	//Station numerical identifier
	private int id;
	
	//Number which will be used to create the ID
	private static int counter;
	
	//Last digit of the ID, representing the station
	private static final int lastDigit = 0;
	
	//Whether the station si Online or not
	private boolean isOnline;
	
	//List of the station parking slots
	private TreeMap<Integer,ParkingSlot> slotMap;
	
	//Number of parking slots in the station
	private int parkingSlotNb;
	
	//Total number of bike rent in the station
	private int totalRentNb;
	
	//Total number of bike returns in the station
	private int totalReturnNb;
	
	//Total number of available slots
	private int availableSlotNb;
	
	//Type of station
	private StationType stationType;
	
	
	//Default constructor
	public Station() {
		this.name = "default";
		this.location = new float[]{0,0};
		this.id = 10*counter + lastDigit;
		this.isOnline = true;
		this.slotMap = new TreeMap<Integer,ParkingSlot>();
		this.parkingSlotNb = 0;
		this.totalRentNb = 0;
		this.totalReturnNb = 0;
		this.availableSlotNb = 0;
		this.stationType = new StandardType();
	}
	
	//Constructor with name and station type
	public Station(String name, StationType stationType) {
		this.name = name;
		this.location = new float[]{0,0};
		this.id = 10*counter + lastDigit;
		this.isOnline = true;
		this.slotMap = new TreeMap<Integer,ParkingSlot>();
		this.parkingSlotNb = 0;
		this.totalRentNb = 0;
		this.totalReturnNb = 0;
		this.availableSlotNb = 0;
		this.stationType = stationType;
	}

	
	//Setters
	public void setStationName(String stationName) {
		this.name = stationName;
	}
	public void setStationLocation(float[] stationLocation) {
		this.location = stationLocation;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public void setStationType(StationType stationType) {
		this.stationType = stationType;
	}
	public void setSlotMap(TreeMap<Integer,ParkingSlot> slotMap) {
		this.slotMap= slotMap;
	}
	
	//Methods that affect the state of a parking slot
	
	/**
	 * Return the bike in the slot whose id is i
	 * @param i
	 * @param bike
	 */
	public void returnBike(int i, Bike bike) {
		ParkingSlot park = this.slotMap.get(i);
		this.slotMap.remove(i);
		try {
			park.returnBike(bike);
			this.totalReturnNb++;
			this.availableSlotNb--;
		} catch (FullSlotException | OutOfOrderException e) {
			e.printStackTrace();
		}
		this.slotMap.put(i,park);
	}
	/**
	 * Take the bike away from the slot whose id is i
	 * @param i
	 * @return
	 */
	public Bike takeBike(int i) {
		ParkingSlot park = this.slotMap.get(i);
		this.slotMap.remove(i);
		Bike bike=null;
		try {
			bike = park.takeBike();
			this.totalRentNb++;
			this.availableSlotNb++;
		} catch (EmptySlotException | OutOfOrderException e) {
			e.printStackTrace();
		}
		this.slotMap.put(i,park);
		return bike;
	}
	
	/**
	 * Set if the parking slot whose id is i is out of order or not
	 * @param i
	 * @param isOutOfOrder
	 */
	public void setOutOfOrder(int i, boolean isOutOfOrder) {
		ParkingSlot park = this.slotMap.get(i);
		int etatAvant = (park.isOutOfOrder()?-1:1);
		int etatApres = (isOutOfOrder?-1:1);
		int availableChange = (etatApres-etatAvant)/2;
		this.availableSlotNb += availableChange;
		this.slotMap.remove(i);
		park.setOutOfOrder(isOutOfOrder);
		this.slotMap.put(i,park);
		
	}
		
	
	
	//Getters
	public String getName() {
		return name;
	}
	public float[] getLocation() {
		return location;
	}
	public int getID() {
		return id;
	}
	public int getAvailableSlotNb() {
		return availableSlotNb;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public int getParkingSlotNb() {
		return parkingSlotNb;
	}
	public int getTotalRentNb() {
		return totalRentNb;
	}
	public int getTotalReturnNb() {
		return totalReturnNb;
	}
	public StationType getStationType() {
		return stationType;
	}
	public TreeMap<Integer,ParkingSlot> getSlotMap() {
		return slotMap;
	}
	
	
	//Getters of the elements of the list of parking slots
	public int getBikeID(int i) {
		ParkingSlot park = this.slotMap.get(i);
		Bike bike = park.getBike();
		return bike.getID();
	}
	public Bike getBike(int i) {
		return this.slotMap.get(i).getBike();
	}
	public boolean isParkingSlotOutOfOrder(int i) {
		return this.slotMap.get(i).isOutOfOrder();
	}
	
	
	//Methods to add parking slots
	
	/**
	 * Add an empty parking slot
	 */
	public void addEmptyParkingSlot() {
			ParkingSlot park = new ParkingSlot();
			int id = park.getID();
			this.slotMap.put(id,park);
			this.parkingSlotNb++;
			this.availableSlotNb++;
		}
	
	/**
	 * Add a parking slot filled with a bike
	 * @param bike
	 */
	public void addFilledParkingSlot(Bike bike) {
		ParkingSlot park = new ParkingSlot(bike);
		int id = park.getID();
		this.slotMap.put(id,park);
		this.parkingSlotNb++;
	}
	
	
	//For the initialisation of the sation
	/**
	 * Add n filled parking slots with the same type of bikes
	 * @param n
	 * @param bike
	 */
	public void addFleet(int n, String bikeType) {
		switch(bikeType) {
		case ("Electric"):
			for  (int i=0; i<n; i++) {
				addFilledParkingSlot(new ElectricBike());
			}
			break;
		case ("Mechanic"):
			for  (int i=0; i<n; i++) {
				addFilledParkingSlot(new MechanicBike());
			}
			break;
		default:
			for  (int i=0; i<n; i++) {
				addEmptyParkingSlot();
			}	
		}
	}


	
	public String toString() {
		String str = "";
		str +="Station n° "+this.id + " : "+this.name+"\n";
		str +="Location : "+ Arrays.toString(this.location)+"\n"; //Methode qui permet d'écrire la contenu de la matrice de façon plus lisible
		str +="Status : "+((this.isOnline)?"Online":"Offline")+"\n";
		str+="Total number of parking slots : "+this.parkingSlotNb+"\n";
		str+="\tAvailable slots : "+this.availableSlotNb+"\n\n";
		str += this.slotMap.values().toString();
		return str;
		
		
		
	}
public static void main(String[] args) {
	Station st = new Station();
	st.addFleet(10, "Electric");
	st.addFleet(5, "vide");
	st.addFleet(5, "Mechanic");
	Bike b = st.takeBike(1000151);
	st.returnBike(1000151,b);
	System.out.println(st);
	
	

}
	
	
	
}

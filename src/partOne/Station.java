package partOne;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;

import Exceptions.EmptySlotException;
import Exceptions.FullSlotException;
import Exceptions.OutOfOrderException;
import stationType.*;


public class Station {
	
	//Name of the station
	private String name;
		
	//Location of the station
	private float[] location;
	
	//Station numerical identifier
	private int id;
	
	//Number which will be used to create the ID
	private static int counter = 1;
	
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
		this.id = counter;
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
		this.id = counter;
		this.isOnline = true;
		this.slotMap = new TreeMap<Integer,ParkingSlot>();
		this.parkingSlotNb = 0;
		this.totalRentNb = 0;
		this.totalReturnNb = 0;
		this.availableSlotNb = 0;
		this.stationType = stationType;
	}

	
	//Setters
	public void setName(String stationName) {
		this.name = stationName;
	}
	public void setLocation(float[] stationLocation) {
		this.location = stationLocation;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public void setStationType(StationType stationType) {
		this.stationType = stationType;
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
	public int getId() {
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
	
	
	/*
	 * 
	//Getters of the elements of the list of parking slots
	public int getBikeId(int i) {
		return this.slotMap.get(i).getBikeId();	
	}
	public Bike getBike(int i) {
		return this.slotMap.get(i).getBike();
	}
	public boolean isParkingSlotOutOfOrder(int i) {
		return this.slotMap.get(i).isOutOfOrder();
	}
	*/
	
	//Methods to add parking slots
	
	/**
	 * Add an empty parking slot
	 */
	private void addEmptyParkingSlot() {
			ParkingSlot park = new ParkingSlot();
			int id = park.getId();
			this.slotMap.put(id,park);
			this.parkingSlotNb++;
			this.availableSlotNb++;
		}
	
	/**
	 * Add a parking slot filled with a bike
	 * @param bike
	 */
	private void addFilledParkingSlot(Bike bike) {
		ParkingSlot park = new ParkingSlot(bike);
		int id = park.getId();
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

	//Renvoie l'id de la première place vide et en service. Renvoie -1 s'il n'y en a pas.
	public int findEmptySlot() {
		for(Entry<Integer, ParkingSlot> entry : this.slotMap.entrySet()) {
			int id = entry.getKey();
			ParkingSlot park = entry.getValue();
			if (!park.isOutOfOrder() && park.getBike() == null) {
				return id;
			}
		}
		return -1;
	}
	
	//Renvoie l'id de la premiere place en service qui contient le vélo voulu
	public int findBike(String bikeType) {
		for(Entry<Integer, ParkingSlot> entry : this.slotMap.entrySet()) {
			int id = entry.getKey();
			ParkingSlot park = entry.getValue();
			if (!park.isOutOfOrder() && park.getBike() instanceof ElectricBike && bikeType == "Electric") {
				return id;
			}
			else if (!park.isOutOfOrder() && park.getBike() instanceof MechanicBike && bikeType == "Mechanic") {
				return id;
			}
		}
		return -1;
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
	/*public static void main(String[] args) {
	Station st = new Station();
	st.addFleet(10, "Electric");
	st.addFleet(5, "vide");
	st.addFleet(5, "Mechanic");
	Bike b = st.takeBike(1);
	st.returnBike(1,b);
	System.out.println(st);
	
	

}
	*/
	
	
}

package partOne;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;

import Exceptions.EmptySlotException;
import Exceptions.FullSlotException;
import Exceptions.OfflineException;
import Exceptions.OutOfOrderException;
import Exceptions.FullStationException;
import Exceptions.EmptyStationException;
import stationType.*;


public class Station {
	
	//Name of the station
	private String name;
		
	//Location of the station
	private Coordinate location;
	
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
	
	//Les id des users qui sont en route pour d�poser ou prendre un v�lo
	private ArrayList<Integer> incomingBikeTaker;
	private ArrayList<Integer> incomingBikeGiver;
	
	
	//Default constructor
	public Station() {
		this.name = "default";
		this.location = new Coordinate(0,0);
		this.id = counter;
		this.isOnline = true;
		this.slotMap = new TreeMap<Integer,ParkingSlot>();
		this.parkingSlotNb = 0;
		this.totalRentNb = 0;
		this.totalReturnNb = 0;
		this.availableSlotNb = 0;
		this.stationType = new StandardType();
		this.incomingBikeGiver = new ArrayList<Integer>();
		this.incomingBikeTaker = new ArrayList<Integer>();
		counter++;
	}
	
	//Constructor with name and station type
	public Station(String name, StationType stationType) {
		this.name = name;
		this.location = new Coordinate(0,0);
		this.id = counter;
		this.isOnline = true;
		this.slotMap = new TreeMap<Integer,ParkingSlot>();
		this.parkingSlotNb = 0;
		this.totalRentNb = 0;
		this.totalReturnNb = 0;
		this.availableSlotNb = 0;
		this.stationType = stationType;
		this.incomingBikeGiver = new ArrayList<Integer>();
		this.incomingBikeTaker = new ArrayList<Integer>();
		counter++;
	}
	
	public Station(String name, StationType stationType, Coordinate coord) {
		this.name = name;
		this.location = coord;
		this.id = counter;
		this.isOnline = true;
		this.slotMap = new TreeMap<Integer,ParkingSlot>();
		this.parkingSlotNb = 0;
		this.totalRentNb = 0;
		this.totalReturnNb = 0;
		this.availableSlotNb = 0;
		this.stationType = stationType;
		this.incomingBikeGiver = new ArrayList<Integer>();
		this.incomingBikeTaker = new ArrayList<Integer>();
		counter++;
	}

	
	//Setters
	public void setName(String stationName) {
		this.name = stationName;
	}
	public void setLocation(Coordinate coord) {
		this.location = coord;
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
	public void returnBike(int i, Bike bike) throws OfflineException{
		if (!this.isOnline){
			throw new OfflineException();
		}
		else {
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
	}
	/**
	 * Take the bike away from the slot whose id is i
	 * @param i
	 * @return
	 */
	public Bike takeBike(int i) throws OfflineException{
		if (!this.isOnline){
			throw new OfflineException();
		}
		else {
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
	}

	/**
	 * Show the bike away from the slot whose id is i
	 * @param i
	 * @return
	 */
	public Bike showBike(int i) throws OfflineException{
		if (!this.isOnline){
			throw new OfflineException();
		}
		else {
			ParkingSlot park = this.slotMap.get(i);
			Bike bike=park.getBike();
			return bike;
		}
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
		
	public void addIncomingBikeTaker(int userId) {
		this.incomingBikeTaker.add(userId);
	}
	
	public void addIncomingBikeGiver(int userId) {
		this.incomingBikeGiver.add(userId);
	}
	
	//Getters
	public String getName() {
		return name;
	}
	public Coordinate getLocation() {
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
	public ArrayList<Integer> getIncomingBikeTaker() {
		return incomingBikeTaker;
	}
	public ArrayList<Integer> getIncomingBikeGiver() {
		return incomingBikeGiver;
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
	
	
	//For the initialisation of the station
	/**
	 * Add n filled parking slots with the same type of bikes
	 * @param n
	 * @param bike
	 */
	public void addFleet(int n, String bikeType) {
		int[] idList = new int[n];
		switch(bikeType) {
		case ("Electric"):
			for  (int i=0; i<n; i++) {
				addFilledParkingSlot(new ElectricBike());
				idList[i] = id;
			}
			break;
		case ("Mechanic"):
			for  (int i=0; i<n; i++) {
				addFilledParkingSlot(new MechanicBike());
				idList[i] = id;
			}
			break;
		default:
			for  (int i=0; i<n; i++) {
				addEmptyParkingSlot();
				idList[i] = id;
			}	
		}
	}

	//Renvoie l'id de la premi�re place vide et en service. Renvoie -1 s'il n'y en a pas.
	public int findEmptySlot() throws FullStationException{
		for(Entry<Integer, ParkingSlot> entry : this.slotMap.entrySet()) {
			int id = entry.getKey();
			ParkingSlot park = entry.getValue();
			if (!park.isOutOfOrder() && park.getBike() == null) {
				return id;
			}
		}
		throw new FullStationException();
	}
	
	//Renvoie l'id de la premiere place en service qui contient le v�lo voulu
	public int findBike(String bikeType) throws EmptyStationException{
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
		throw new EmptyStationException();
	}
	
	public String toString() {
		String str = "";
		str +="Station n� "+this.id + " : "+this.name+"\n";
		str +="Location : "+ this.location.toString()+"\n"; //Methode qui permet d'�crire la contenu de la matrice de fa�on plus lisible
		str +="Status : "+((this.isOnline)?"Online":"Offline")+"\n";
		str+="Total number of parking slots : "+this.parkingSlotNb+"\n";
		str+="\tAvailable slots : "+this.availableSlotNb+"\n\n";
		str += this.slotMap.values().toString();
		return str;
	}
	
	public static void main(String[] args) {
		Station st= new Station();
		st.addFleet(10, "Electric");
		st.addFleet(5, "vide");
		st.addFleet(5, "Mechanic");
		System.out.print(st.getSlotMap());
		
	}

}
	

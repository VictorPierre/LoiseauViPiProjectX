package partOne;

import java.util.ArrayList;
import stationType.*;


public class Station {
	
	//Name of the station
	private String stationName;
		
	//Location of the station
	private float[] stationLocation = {0,0};
	
	//Station numerical identifier
	private int stationID;
	
	//Number which will be used to create the ID
	private static int counter;
	
	//Last digit of the ID, representing the station
	private static final int lastDigit = 0;
	
	//Whether the station si Online or not
	private boolean isOnline;
	
	//List of the station parking slots
	private ArrayList<ParkingSlot> slotList;
	
	//Number of parking slots in the station
	private int parkingSlotNumber;
	
	//Total number of bike rent in the station
	private int totalRentNb;
	
	//Total number of bike returns in the station
	private int totalReturnNb;
	
	//Type of station
	private StationType stationType;
	
	
	//Default constructor
	public Station() {
		this.stationName = "default";
		//this.stationLocation = {0,0};
		this.stationID = 10*counter + lastDigit;
		this.isOnline = true;
		this.slotList = new ArrayList<ParkingSlot>();
		this.parkingSlotNumber = 0;
		this.totalRentNb = 0;
		this.totalReturnNb = 0;
		this.stationType = new StandardType();
	}
	
	//Constructor with name and station type
	public Station(String name, StationType stationType) {
		this.stationName = name;
		//this.stationLocation = {0,0};
		this.stationID = 10*counter + lastDigit;
		this.isOnline = true;
		this.slotList = new ArrayList<ParkingSlot>();
		this.parkingSlotNumber = 0;
		this.totalRentNb = 0;
		this.totalReturnNb = 0;
		this.stationType = stationType;
	}

	
	//Setters
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public void setStationLocation(float[] stationLocation) {
		this.stationLocation = stationLocation;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public void setSlotList(ArrayList<ParkingSlot> slotList) {
		this.slotList = slotList;
	}
	public void setStationType(StationType stationType) {
		this.stationType = stationType;
	}
	
	//Getters
	public String getStationName() {
		return stationName;
	}
	public float[] getStationLocation() {
		return stationLocation;
	}
	public int getStationID() {
		return stationID;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public ArrayList<ParkingSlot> getSlotList() {
		return slotList;
	}
	public int getParkingSlotNumber() {
		return parkingSlotNumber;
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

	/**
	 * Add an empty parking slot
	 */
	public void addEmptyParkingSlot() {
			this.slotList.add(new ParkingSlot());
			this.parkingSlotNumber++;
		}
	
	/** 
	 * Add a filled parking slot with a bike
	 * @param bike
	 */
	public void addFilledParkingSlot(Bike bike) {
		this.slotList.add(new ParkingSlot(bike));
		this.parkingSlotNumber++;
	}
	
	/**
	 * Add n filled parking slots with the same type of bikes
	 * @ n
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


	/**
	 * Set the bike in the slot n°i . If the slot is empty, put "null" as argument
	 * Add 1 to the total number of rents if the bike is taken away
	 * Add 1 to the total number of returns if the bike is put in the parking
	 * Doesn't take into account what there was before on the slot
	 * @param i
	 * @param bike
	 */
	public void setBike(int i, Bike bike) {
		ParkingSlot p = this.slotList.get(i);
		this.slotList.remove(i);
		p.setBike(bike);
		this.slotList.add(p);
		if (bike instanceof Bike){
			this.totalReturnNb++;
		}
		else {
			this.totalRentNb++;
		}
	}
	
	/**
	 * Set if the parking slot n°i is out of order or not
	 * @param i
	 * @param isOutOfOrder
	 */
	public void setOutOfOrder(int i, boolean isOutOfOrder) {
		ParkingSlot p = this.slotList.get(i);
		this.slotList.remove(i);
		p.setOutOfOrder(isOutOfOrder);
		this.slotList.add(p);
	}
	
	public int getBikeID(int i) {
		ParkingSlot p = this.slotList.get(i);
		Bike b = p.getBike();
		return b.getBikeID();
	}
	
	
}

package partOne;

public class Ride {
	
	private int id;
	
	private static int counter=1;
	
	private int startingStationId;
	
	private int destinationStationId;
	
	private Coordinate startingLocation;
	
	private Coordinate wantedLocation;
	
	private String rideType;
	
	private double distance;
	
	private double time;
	
	private String bikeType;
	
	Ride(String rideType){
		this.destinationStationId = counter;
		counter++;
		this.startingStationId=0;
		this.destinationStationId=0;
		this.startingLocation = new Coordinate(0,0);
		this.wantedLocation = new Coordinate(0,0);
		this.rideType = rideType;
		this.distance=0;
		this.time=0;
		this.bikeType="";
	}
	
	Ride(Station startingStation, Station wantedStation, Coordinate startingLocation, Coordinate wantedLocation, String rideType, String bikeType, double distance, double time){
		this.destinationStationId = counter;
		counter++;
		this.startingStationId=startingStation.getId();
		this.destinationStationId=wantedStation.getId();
		this.startingLocation = startingLocation;
		this.wantedLocation = wantedLocation;
		this.rideType = rideType;
		this.distance=distance;
		this.time=time;
		this.bikeType=bikeType;
	}

	public int getStartingStationId() {
		return startingStationId;
	}

	public int getDestinationStationId() {
		return destinationStationId;
	}
	
	public String getBikeType() {
		return this.bikeType;
	}
	
	public void setStartingStationId(int startingStationId) {
		this.startingStationId = startingStationId;
	}

	public void setDestinationStationId(int destinationStationId) {
		this.destinationStationId = destinationStationId;
	}
	
	public String toString() {
		String str = "";
		str += "Trajet n° "+this.id+" :\n";
		str += "ID station de départ : " + this.startingStationId+ "\n";
		str += "ID station d'arrivée : " + this.destinationStationId+ "\n";
		str += "Ridetype " + this.rideType +"\n";
		str += "Distance totale " + this.distance +"\n";
		str += "Temps estimé " + this.time +"\n";
		return str;
	}
	
}

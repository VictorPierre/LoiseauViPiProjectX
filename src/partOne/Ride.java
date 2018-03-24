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
	}
	
	Ride(Station startingStation, Station wantedStation, Coordinate startingLocation, Coordinate wantedLocation, String rideType, double distance, double time){
		this.destinationStationId = counter;
		counter++;
		this.startingStationId=startingStation.getId();
		this.destinationStationId=wantedStation.getId();
		this.startingLocation = startingLocation;
		this.wantedLocation = wantedLocation;
		this.rideType = rideType;
		this.distance=distance;
		this.time=time;
	}

	public int getStartingStationId() {
		return startingStationId;
	}

	public int getDestinationStationId() {
		return destinationStationId;
	}

	public void setStartingStationId(int startingStationId) {
		this.startingStationId = startingStationId;
	}

	public void setDestinationStationId(int destinationStationId) {
		this.destinationStationId = destinationStationId;
	}
	
}

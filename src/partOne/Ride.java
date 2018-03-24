package partOne;

public class Ride {
	
	private int id;
	
	private static int counter=1;
	
	private int startingStationId;
	
	private int destinationStationId;
	
	private Coordinate startingLocation;
	
	private Coordinate wantedLocation;
	
	private String rideType;
	
	Ride(String rideType){
		this.destinationStationId = counter;
		counter++;
		this.startingStationId=0;
		this.destinationStationId=0;
		this.startingLocation = new Coordinate(0,0);
		this.wantedLocation = new Coordinate(0,0);
		this.rideType = rideType;
	}
	
	Ride(Station startingStation, Station wantedStation, Coordinate startingLocation, Coordinate wantedLocation, String rideType){
		this.destinationStationId = counter;
		counter++;
		this.startingStationId=startingStation.getId();
		this.destinationStationId=wantedStation.getId();
		this.startingLocation = startingLocation;
		this.wantedLocation = wantedLocation;
		this.rideType = rideType;
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

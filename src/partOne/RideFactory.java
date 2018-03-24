package partOne;

import java.util.TreeMap;
import java.util.Map.Entry;

import stationType.StandardType;

public interface RideFactory {
	
	public static Ride createShortestRide(TreeMap<Integer, Station> stationMap, Bike destinationBikeType,  Coordinate startingLocation, Coordinate destinationLocation) {
		Station startingStation = new Station("départ fictif", new StandardType(), new Coordinate(-9999,-9999));
		Station destinationStation = new Station("arrivée fictive", new StandardType(), new Coordinate(9999,9999));
		double totalDist = startingLocation.dist(startingStation.getLocation())+destinationStation.getLocation().dist(startingStation.getLocation())+destinationLocation.dist(destinationStation.getLocation());
		for(Entry<Integer, Station> entry : stationMap.entrySet()) {
			Station sst = entry.getValue();
			for(Entry<Integer, Station> entry2 : stationMap.entrySet()) {
				Station wst = entry2.getValue();
				double newDist = startingLocation.dist(sst.getLocation())+wst.getLocation().dist(sst.getLocation())+destinationLocation.dist(wst.getLocation());
				if (totalDist >= newDist) {
					totalDist = newDist;
					startingStation = sst;
					destinationStation = wst;
				}
			}
		}
		return new Ride(startingStation,destinationStation,startingLocation,destinationLocation,"Shortest path ride");
		
	}
	
	public static Ride createFastestRide() {
		return null;
		
	}
	
	public static Ride createAvoidPlusRide() {
		return null;
		
	}
	
	public static Ride createPreferPlusRide() {
		return null;
		
	}
	
	public static Ride createDistributionPreservationRide() {
		return null;
		
	}
	
}

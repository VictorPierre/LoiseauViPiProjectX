package partOne;

import java.util.TreeMap;
import java.util.Map.Entry;

import stationType.StandardType;

public interface RideFactory {
	

	public static Ride createShortestRide(TreeMap<Integer, Station> stationMap, Bike destinationBikeType,  Coordinate startingLocation, Coordinate destinationLocation) {
		Station startingStation = new Station("départ fictif", new StandardType(), new Coordinate(-9999,-9999));
		Station destinationStation = new Station("arrivée fictive", new StandardType(), new Coordinate(9999,9999));
		double totalDist = startingLocation.dist(startingStation.getLocation())+destinationStation.getLocation().dist(startingStation.getLocation())+destinationLocation.dist(destinationStation.getLocation());
		double totalTime = startingLocation.dist(startingStation.getLocation())/4+destinationStation.getLocation().dist(startingStation.getLocation())/destinationBikeType.getSpeed()+destinationLocation.dist(destinationStation.getLocation())/4;
		for(Entry<Integer, Station> entry : stationMap.entrySet()) {
			Station sst = entry.getValue();
			for(Entry<Integer, Station> entry2 : stationMap.entrySet()) {
				Station wst = entry2.getValue();
				double newDist = startingLocation.dist(sst.getLocation())+wst.getLocation().dist(sst.getLocation())+destinationLocation.dist(wst.getLocation());
				double newTime = startingLocation.dist(sst.getLocation())/4+wst.getLocation().dist(sst.getLocation())/destinationBikeType.getSpeed()+destinationLocation.dist(wst.getLocation())/4;
				if (totalDist >= newDist) {
					totalDist = newDist;
					totalTime = newTime;
					startingStation = sst;
					destinationStation = wst;
				}
			}
		}
		return new Ride(startingStation,destinationStation,startingLocation,destinationLocation,"Shortest path ride",totalDist,totalTime);
		
	}
	
	public static Ride createFastestRide(TreeMap<Integer, Station> stationMap, Bike destinationBikeType, Coordinate startingLocation, Coordinate destinationLocation, double distance) {
		Station startingStation = new Station("départ fictif", new StandardType(), new Coordinate(-9999,-9999));
		Station destinationStation = new Station("arrivée fictive", new StandardType(), new Coordinate(9999,9999));
		double totalDist = startingLocation.dist(startingStation.getLocation())+destinationStation.getLocation().dist(startingStation.getLocation())+destinationLocation.dist(destinationStation.getLocation());
		double totalTime = startingLocation.dist(startingStation.getLocation())/4+destinationStation.getLocation().dist(startingStation.getLocation())/destinationBikeType.getSpeed()+destinationLocation.dist(destinationStation.getLocation())/4;
		for(Entry<Integer, Station> entry : stationMap.entrySet()) {
			Station sst = entry.getValue();
			for(Entry<Integer, Station> entry2 : stationMap.entrySet()) {
				Station wst = entry2.getValue();
				double newDist = startingLocation.dist(sst.getLocation())+wst.getLocation().dist(sst.getLocation())+destinationLocation.dist(wst.getLocation());
				double newTime = startingLocation.dist(sst.getLocation())/4+wst.getLocation().dist(sst.getLocation())/destinationBikeType.getSpeed()+destinationLocation.dist(wst.getLocation())/4;
				if (totalTime >= newTime) {
					totalDist = newDist;
					totalTime = newTime;
					startingStation = sst;
					destinationStation = wst;
				}
			}
		}
		return new Ride(startingStation,destinationStation,startingLocation,destinationLocation,"Fastest ride",totalDist,totalTime);
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

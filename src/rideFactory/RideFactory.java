package rideFactory;

import java.util.TreeMap;

import partOne.Coordinate;
import partOne.Ride;
import partOne.Station;

public interface RideFactory {
	Ride createRide(TreeMap<Integer, Station> stationMap, String bikeType,  Coordinate startingLocation, Coordinate destinationLocation);
}
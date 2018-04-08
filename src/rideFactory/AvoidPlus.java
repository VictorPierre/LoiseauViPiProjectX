package rideFactory;

import java.util.TreeMap;
import java.util.Map.Entry;

import Exceptions.EmptyStationException;
import Exceptions.OfflineException;
import Exceptions.RideImpossibleException;
import partOne.Bike;
import partOne.Coordinate;
import partOne.Ride;
import partOne.Station;
import stationType.PlusType;
import stationType.StandardType;

public class AvoidPlus implements RideFactory{
	@Override
	public Ride createRide(TreeMap<Integer, Station> stationMap, String  bikeType, Coordinate startingLocation, Coordinate destinationLocation) throws RideImpossibleException{
		Station startingStation = new Station("d�part fictif", new StandardType(), new Coordinate(-9999,-9999));
		Station destinationStation = new Station("arriv�e fictive", new StandardType(), new Coordinate(9999,9999));
		double totalDist = 99999;
		double totalTime = 99999;
		Bike bike=null;
		int parkId=-1;
		for(Entry<Integer, Station> entry : stationMap.entrySet()) {
			Station sst = entry.getValue();
			try {parkId=sst.findBike(bikeType);
			//On v�rifie qu'un v�lo est disponible pour pouvoir �tre une station de d�part
				bike=sst.showBike(parkId);
				for(Entry<Integer, Station> entry2 : stationMap.entrySet()) {
					Station wst = entry2.getValue();
					//On �vite les stations plus
					if (!(wst.getStationType() instanceof PlusType) && wst != sst) {
						double newDist = startingLocation.dist(sst.getLocation())+wst.getLocation().dist(sst.getLocation())+destinationLocation.dist(wst.getLocation());
						double newTime = startingLocation.dist(sst.getLocation())/4+wst.getLocation().dist(sst.getLocation())/bike.getSpeed()+destinationLocation.dist(wst.getLocation())/4;
						if (totalTime >= newTime) {
							totalDist = newDist;
							totalTime = newTime;
							startingStation = sst;
							destinationStation = wst;
						}
					}
				}
			} catch (EmptyStationException|OfflineException e) {}
		}
		if (startingStation.equals(destinationStation)||startingStation.getName()=="d�part fictif"||destinationStation.getName()=="arriv�e fictive") {
			throw new RideImpossibleException();
		}
		else {
			return new Ride(startingStation,destinationStation,startingLocation,destinationLocation,"Avoid Plus ride",bikeType,totalDist,totalTime);
		}	
	}
}
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

public class PreferPlus implements RideFactory{
	@Override
	public Ride createRide(TreeMap<Integer, Station> stationMap, String bikeType,  Coordinate startingLocation, Coordinate destinationLocation) throws RideImpossibleException{
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
					if (wst != sst) {
						double newDist = startingLocation.dist(sst.getLocation())+wst.getLocation().dist(sst.getLocation())+destinationLocation.dist(wst.getLocation());
						//On cr��e une distance fictive pour pond�rer les distances
						double fictiveDist=newDist;
						//On r�duit de 10% la distance fictive si la station d'arriv�e est de type Plus
						if (wst.getStationType() instanceof PlusType) {
							fictiveDist=0.9*fictiveDist;
						}
						double newTime = startingLocation.dist(sst.getLocation())/4+wst.getLocation().dist(sst.getLocation())/bike.getSpeed()+destinationLocation.dist(wst.getLocation())/4;
						if (totalDist >= fictiveDist) {
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
			return new Ride(startingStation,destinationStation,startingLocation,destinationLocation,"Prefer Plus ride",bikeType,totalDist,totalTime);
		}	
	}
}

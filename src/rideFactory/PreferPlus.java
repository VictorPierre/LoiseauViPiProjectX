package rideFactory;

import java.util.TreeMap;
import java.util.Map.Entry;

import Exceptions.EmptyStationException;
import Exceptions.OfflineException;
import partOne.Bike;
import partOne.Coordinate;
import partOne.Ride;
import partOne.Station;
import stationType.PlusType;
import stationType.StandardType;

public class PreferPlus implements RideFactory{
	@Override
	public Ride createRide(TreeMap<Integer, Station> stationMap, String bikeType,  Coordinate startingLocation, Coordinate destinationLocation) {
		Station startingStation = new Station("d�part fictif", new StandardType(), new Coordinate(-9999,-9999));
		Station destinationStation = new Station("arriv�e fictive", new StandardType(), new Coordinate(9999,9999));
		double totalDist = 99999;
		double totalTime = 99999;
		Bike bike=null;
		int parkId=-1;
		for(Entry<Integer, Station> entry : stationMap.entrySet()) {
			Station sst = entry.getValue();
			try {parkId=sst.findBike(bikeType);} catch (EmptyStationException e) {e.printStackTrace();}
			//On v�rifie qu'un v�lo est disponible pour pouvoir �tre une station de d�part
			if (parkId!=-1) {
				try {bike=sst.showBike(parkId);} catch (OfflineException e) {e.printStackTrace();}
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
			}
		}
		return new Ride(startingStation,destinationStation,startingLocation,destinationLocation,"Prefer Plus ride",bikeType,totalDist,totalTime);
	}
}

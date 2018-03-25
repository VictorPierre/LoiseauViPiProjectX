package partOne;

import java.util.TreeMap;
import java.util.Map.Entry;

import Exceptions.OfflineException;
import stationType.PlusType;
import stationType.StandardType;
import stationType.StationType;
public class RideFactory {
	
	//Paramètres d'entrée
	private TreeMap<Integer, Station> stationMap;
	private String bikeType;
	private Coordinate startingLocation;
	private Coordinate destinationLocation;
	private boolean avoidPlus;
	private boolean preferPlus;
	private boolean uniformitySaver;
	private String rideType;
	
	//Paramètres internes
	private Station startingStation;
	private Station destinationStation;
	private double totalDist;
	private double totalTime;
	private Bike bike;
	private int parkId;
	private Ride ride;
	
	
	/**
	 * Initialization of a RideFactory
	 * @param stationMap
	 * @param bikeType
	 * @param startingLocation
	 * @param destinationLocation
	 */
	
	RideFactory(TreeMap<Integer, Station> stationMap, String bikeType,  Coordinate startingLocation, Coordinate destinationLocation) {
			this.stationMap=stationMap;
			this.bikeType=bikeType;
			this.startingLocation=startingLocation;
			this.destinationLocation=destinationLocation;
			this.rideType="Shortest";
	
			this.startingStation = new Station("départ fictif", new StandardType(), new Coordinate(-9999,-9999));
			this.destinationStation = new Station("arrivée fictive", new StandardType(), new Coordinate(9999,9999));
			this.bike=null;
			
			this.totalDist = 99999;
			this.totalTime = 99999;
			this.parkId=-1;
			this.ride=null;
			
			this.preferPlus=false;
			this.avoidPlus=false;
			this.uniformitySaver=false;		
	}
	
	RideFactory(TreeMap<Integer, Station> stationMap, String bikeType,  Coordinate startingLocation, Coordinate destinationLocation,String rideType, boolean preferPlus, boolean avoidPlus, boolean uniformitySaver) {
		this.stationMap=stationMap;
		this.bikeType=bikeType;
		this.startingLocation=startingLocation;
		this.destinationLocation=destinationLocation;
		this.rideType=rideType;

		this.startingStation = new Station("départ fictif", new StandardType(), new Coordinate(-9999,-9999));
		this.destinationStation = new Station("arrivée fictive", new StandardType(), new Coordinate(9999,9999));
		this.bike=null;
		
		this.totalDist = 99999;
		this.totalTime = 99999;
		this.parkId=-1;
		this.ride=null;
		
		this.preferPlus=preferPlus;
		this.avoidPlus=avoidPlus;
		this.uniformitySaver=uniformitySaver;		
	}
	
	private void updateRoute(Station sst, Station wst) {
		 if (wst != sst) {
			double newDist = this.startingLocation.dist(sst.getLocation())+wst.getLocation().dist(sst.getLocation())+this.destinationLocation.dist(wst.getLocation());
			double newTime = this.startingLocation.dist(sst.getLocation())/4+wst.getLocation().dist(sst.getLocation())/this.bike.getSpeed()+this.destinationLocation.dist(wst.getLocation())/4;
			switch (this.rideType) {
				case ("Shortest"):
					if (this.totalDist >= newDist) {
						this.totalDist = newDist;
						this.totalTime = newTime;
						this.startingStation = sst;
						this.destinationStation = wst;
					}
					break;
				case ("fastest"):
					if (this.totalTime >= newTime) {
						this.totalDist = newDist;
						this.totalTime = newTime;
						this.startingStation = sst;
						this.destinationStation = wst;
					}
					break;
			}
		}
	}
	
	
	public Ride createRide() {
		
		//Parcours de toutes les stations de départ possibles
		for(Entry<Integer, Station> entry : this.stationMap.entrySet()) {
			Station sst = entry.getValue();
			
			//On vérifie qu'un vélo est disponible pour pouvoir être une station de départ			
			this.parkId=sst.findBike(this.bikeType);
			if (this.parkId!=-1) {
				try {this.bike=sst.showBike(this.parkId);} catch (OfflineException e) {e.printStackTrace();}
				
				//Parcours de toutes les destinations possibles
				for(Entry<Integer, Station> entry2 : stationMap.entrySet()) {
					Station wst = entry2.getValue();
					
					//On update, ie on vérifie si ces deux stations optimisent le trajet
					//(sauf on doit éviter les stations Plus)
					if (!(this.avoidPlus && wst.getStationType() instanceof PlusType)) {
						updateRoute(sst, wst);
					}
				}
			}
		}
		return new Ride(startingStation,destinationStation,startingLocation,destinationLocation,"Shortest path ride",bikeType,totalDist,totalTime);
		
	}
	

	public static Ride createShortestRide(TreeMap<Integer, Station> stationMap, String bikeType,  Coordinate startingLocation, Coordinate destinationLocation) {
		Station startingStation = new Station("départ fictif", new StandardType(), new Coordinate(-9999,-9999));
		Station destinationStation = new Station("arrivée fictive", new StandardType(), new Coordinate(9999,9999));
		double totalDist = 99999;
		double totalTime = 99999;
		Bike bike=null;
		int parkId=-1;
		for(Entry<Integer, Station> entry : stationMap.entrySet()) {
			Station sst = entry.getValue();
			parkId=sst.findBike(bikeType);
			//On vérifie qu'un vélo est disponible pour pouvoir être une station de départ
			if (parkId!=-1) {
				try {bike=sst.showBike(parkId);} catch (OfflineException e) {e.printStackTrace();}
				for(Entry<Integer, Station> entry2 : stationMap.entrySet()) {
					Station wst = entry2.getValue();
					if (wst != sst) {
						double newDist = startingLocation.dist(sst.getLocation())+wst.getLocation().dist(sst.getLocation())+destinationLocation.dist(wst.getLocation());
						double newTime = startingLocation.dist(sst.getLocation())/4+wst.getLocation().dist(sst.getLocation())/bike.getSpeed()+destinationLocation.dist(wst.getLocation())/4;
						if (totalDist >= newDist) {
							totalDist = newDist;
							totalTime = newTime;
							startingStation = sst;
							destinationStation = wst;
						}
					}
				}
			}
		}
		return new Ride(startingStation,destinationStation,startingLocation,destinationLocation,"Shortest path ride",bikeType,totalDist,totalTime);
		
	}
	
	public static Ride createFastestRide(TreeMap<Integer, Station> stationMap, String  bikeType, Coordinate startingLocation, Coordinate destinationLocation) {
		Station startingStation = new Station("départ fictif", new StandardType(), new Coordinate(-9999,-9999));
		Station destinationStation = new Station("arrivée fictive", new StandardType(), new Coordinate(9999,9999));
		double totalDist = 99999;
		double totalTime = 99999;
		Bike bike=null;
		int parkId=-1;
		for(Entry<Integer, Station> entry : stationMap.entrySet()) {
			Station sst = entry.getValue();
			parkId=sst.findBike(bikeType);
			//On vérifie qu'un vélo est disponible pour pouvoir être une station de départ
			if (parkId!=-1) {
				try {bike=sst.showBike(parkId);} catch (OfflineException e) {e.printStackTrace();}
				for(Entry<Integer, Station> entry2 : stationMap.entrySet()) {
					Station wst = entry2.getValue();
					if (wst != sst) {
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
			}
		}
		return new Ride(startingStation,destinationStation,startingLocation,destinationLocation,"Fastest ride",bikeType,totalDist,totalTime);
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

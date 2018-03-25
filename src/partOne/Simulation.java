package partOne;

import java.util.Arrays;
import java.util.TreeMap;

import Exceptions.EmptySlotException;
import Exceptions.FullSlotException;
import Exceptions.OfflineException;
import stationType.PlusType;
import stationType.StandardType;
import stationType.StationType;

public class Simulation {
	
	/**
	 * La carte des Stations
	 */
	private TreeMap<Integer,Station> stationMap;
	
	/**
	 * La liste des utilisateurs
	 */
	private TreeMap<Integer, User> userMap;
	
	Simulation(){
		this.stationMap = new TreeMap<Integer,Station>();
		this.userMap = new TreeMap<Integer,User>();
	}
	
	/**
	 * Pour ajouter une station au r�seau
	 * @return : l'ID unique de la station ainsi cr��e
	 */
	public int addStation() {
		Station st = new Station();
		int id = st.getId();
		this.stationMap.put(id,st);
		return id;
	}
	
	/**
	 * POur ajouter une station
	 * @param name : nom de la station
	 * @param stationType : type de la station (PlusType ou StandardType)
	 * @return l'ID unique de la station cr��e
	 */
	public int addStation(String name, StationType stationType) {
		Station st = new Station(name,stationType);
		int id = st.getId();
		this.stationMap.put(id,st);
		return id;
	}
	/**
	 * POur ajouter une station
	 * @param name : nom de la station
	 * @param stationType : type de la station (PlusType ou StandardType)
	 * @param location : l'emplacement de la station
	 * @return l'ID unique de la station cr��e
	 */
	private int addStation(String name, StationType stationType, Coordinate location) {
		Station st = new Station(name,stationType,location);
		int id = st.getId();
		this.stationMap.put(id,st);
		return id;
	}


	/**
	 * Pour ajouter des v�los � une station
	 * @param id de la station
	 * @param n : nombre de v�los � ajouter
	 * @param bikeType: type de v�los
	 */
	public void addStationFleet(int id, int n, String bikeType) {
		Station st = this.stationMap.get(id);
		this.stationMap.remove(id);
		st.addFleet(n, bikeType);
		this.stationMap.put(id, st);
	}
	
	/**
	 * Change le nom de la station
	 * @param stationId
	 * @param stationName
	 */
	public void setStationName(int stationId, String stationName) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		st.setName(stationName);
		this.stationMap.put(stationId, st);
	}
	
	/**
	 * Change l'emplacement de la station
	 * @param stationId
	 * @param stationLocation
	 */
	public void setStationLocation(int stationId, Coordinate stationLocation) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		st.setLocation(stationLocation);
		this.stationMap.put(stationId, st);
	}
	
	/**
	 * Change le statut de la station
	 * @param stationId
	 * @param isOnline (boolean)
	 */
	public void setStationOnline(int stationId, boolean isOnline) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		st.setOnline(isOnline);
		this.stationMap.put(stationId, st);
		if (!isOnline) {
			this.alertIncomingBikeGiver(stationId, "La station "+this.stationMap.get(stationId).getName()+" est d�sormais hors-service. Vous devez recalculer votre itin�raire.");
			this.alertIncomingBikeTaker(stationId, "La station "+this.stationMap.get(stationId).getName()+" est d�sormais hors-service. Vous devez recalculer votre itin�raire.");
		}
	}
	/**
	 * Permet de mettre un slot d'une station H-S ou non
	 * @param stationId
	 * @param slotId
	 * @param isOutOfOrder
	 */
	public void setStationSlotOutOfOrder(int stationId, int slotId, boolean isOutOfOrder) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		st.setOutOfOrder(slotId, isOutOfOrder);
		this.stationMap.put(stationId, st);
		if (this.stationMap.get(stationId).findBike("Electric")==-1 && this.stationMap.get(stationId).findBike("Mechanic")==-1) {
			this.alertIncomingBikeGiver(stationId,"Il n'y a plus de v�los disponibles dans la station "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itin�raire.");
		}
		if (this.stationMap.get(stationId).findEmptySlot()==-1) {
			this.alertIncomingBikeGiver(stationId,"Il n'y a plus de place dans la destination souhait�e "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itin�raire.");
		}
		
	}
	
	/**
	 * Ajoute un utilisateur sur la liste des futurs preneurs de v�los (� avertir en cas de probl�me)
	 * @param stationId
	 * @param userId
	 */
	public void addIncomingBikeTaker(int stationId,int userId) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		st.addIncomingBikeTaker(userId);
		this.stationMap.put(stationId, st);
	}
	
	/**
	 * Ajoute un utilisateur sur la liste des futurs donneurs de v�los (� avertir en cas de probl�me)
	 * @param stationId
	 * @param userId
	 */
	public void addIncomingBikeGiver(int stationId,int userId) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		st.addIncomingBikeGiver(userId);
		this.stationMap.put(stationId, st);
	}
	
	/**
	 * Pour prendre un v�lo dans un slot donn� d'une station
	 * @param stationId
	 * @param slotId
	 * @return : le v�lo
	 */
	private Bike takeBikeStation(int stationId, int slotId) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		Bike b=null;
		try {
			b = st.takeBike(slotId);
		} catch (OfflineException e) {
			e.printStackTrace();
		}
		this.stationMap.put(stationId, st);
		return b;
	}
	
	/**
	 * Ajoute un user � la simulation
	 * @param user
	 * @return
	 */
	public int addUser(User user) {
		this.userMap.put(user.getId(),user);
		return user.getId();
	}
	
	/**
	 * Change le nom d'un user
	 * @param userId
	 * @param name
	 */
	
	public void setUserName(int userId, String name) {
		User us = this.userMap.get(userId);
		this.userMap.remove(userId);
		us.setName(name);
		this.userMap.put(userId,us);
	}
	
	/**
	 * Change la position d'un user
	 * @param userId
	 * @param location
	 */
	public void setUserLocation(int userId, Coordinate location) {
		User us = this.userMap.get(userId);
		this.userMap.remove(userId);
		us.setLocation(location);
		this.userMap.put(userId,us);
	}
	
	/**
	 * Change la carte de fid�lit� d'un user
	 * @param userId
	 * @param card
	 */
	public void setUserCard(int userId, Card card) {
		User us = this.userMap.get(userId);
		this.userMap.remove(userId);
		us.setCard(card);
		this.userMap.put(userId,us);
	}
	
	/**
	 * Permet de prendre un v�lo
	 * @param userId : ID de l'user
	 * @param bike
	 * @param time : heure de prise du v�lo
	 */
	private void takeBikeUser(int userId, Bike bike, int time) {
		User us = this.userMap.get(userId);
		this.userMap.remove(userId);
		try {
			us.takeBike(bike, time);
		} catch (FullSlotException e) {
			e.printStackTrace();
		}
		this.userMap.put(us.getId(),us);
	}
	
	/**public void setStationType(StationType stationType) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		st.setStationType(stationType);
		this.stationMap.put(stationId, st);
	}
	 */
	
	/**
	 * Remettre un v�lo dans une station
	 * @param stationId
	 * @param slotId
	 * @param bike
	 */
	private void returnBikeStation(int stationId, int slotId, Bike bike) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		try {
			st.returnBike(slotId, bike);
		} catch (OfflineException e) {
			e.printStackTrace();
		}
		this.stationMap.put(stationId, st);
	}

	/**
	 * Prend le v�lo d'un user
	 * @param userId
	 * @return
	 */
	private Bike returnBikeUser(int userId) {
		User us = this.userMap.get(userId);
		this.userMap.remove(userId);
		Bike b = null;
		try {
			b = us.returnBike();
		} catch (EmptySlotException e) {
			e.printStackTrace();
		}
		this.userMap.put(us.getId(),us);
		return b;
	}
	
	/**
	 * Donne un v�lo � un user
	 * @param userId
	 * @param stationId
	 * @param bikeType
	 * @param time : heure de prise du v�lo
	 */
	public void takeBike(int userId, int stationId, String bikeType, int time) {
		int slotId = this.stationMap.get(stationId).findBike(bikeType);
		Bike b = takeBikeStation(stationId, slotId);
		takeBikeUser(userId,b,time);
		if (this.stationMap.get(stationId).findBike("Electric")==-1 && this.stationMap.get(stationId).findBike("Mechanic")==-1) {
			this.alertIncomingBikeTaker(stationId,"Il n'y a plus de v�los disponibles dans la station "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itin�raire.");
		}
	}
	
	/**
	 * Un user rend un v�lo � une heure donn�e dans une station donn�e
	 * @param userId
	 * @param stationId
	 * @param time
	 */
	public void returnBike(int userId, int stationId, int time) {
		int slotId=this.stationMap.get(stationId).findEmptySlot();
		Bike b = returnBikeUser(userId);
		returnBikeStation(stationId, slotId,b);
		User us = userMap.get(userId);
		
		//Cr�dit bonus pour les PlusType
		if (this.stationMap.get(stationId).getStationType() instanceof PlusType && us.getCard()!=null) {
			this.userMap.remove(userId);
			Cost costuser=us.getCurrentCost();
			costuser.addTimeCredit(5);
			us.setCost(costuser);
			this.userMap.put(userId,us);
		}

		int rideDuration = time - us.getTime();
		Cost cost = us.getCurrentCost();
		int rideCost = cost.getRideCost(rideDuration);
		System.out.println("L'utilisateur sera d�bit� de "+rideCost+"�");
		
		if (this.stationMap.get(stationId).findEmptySlot()==-1) {
			this.alertIncomingBikeGiver(stationId,"Il n'y a plus de place dans la destination souhait�e "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itin�raire.");
		}
	}
	
	/**
	 * Alerte les futurs donneurs de v�lo
	 * @param stationId
	 * @param message
	 */
	private void alertIncomingBikeGiver(int stationId, String message) {
		String str = "Message envoy� aux utilisateurs suivants : ";
		str += this.stationMap.get(stationId).getIncomingBikeGiver().toString();
		str += "\n"+message;
		System.out.println(str);
	}
	/**
	 * Alerte les futurs preneurs de v�lo
	 * @param stationId
	 * @param message
	 */
	private void alertIncomingBikeTaker(int stationId, String message) {
		String str = "Message envoy� aux utilisateurs suivants : ";
		str += this.stationMap.get(stationId).getIncomingBikeTaker().toString();
		str += "\n"+message;
		System.out.println(str);
	}

	/**
	 * D�but d'un voyage pour un utilisateur : on l'indique � la station d'arriv�e
	 * @param userId
	 * @param ride
	 */
	public void startRide(int userId, Ride ride) {
		User user = this.userMap.get(userId);
		user.setCurrentRide(ride);
		//On indique ensuite aux stations que l'utilisateur est en train d'arriver
		int startingStationId = ride.getStartingStationId();
		int destinationStationId = ride.getDestinationStationId();
		this.addIncomingBikeTaker(startingStationId, userId);
		this.addIncomingBikeGiver(destinationStationId, userId);
	}
	
	
	
	public static void main(String[] args) {
		
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		int idLuxembourg = sm.addStation("Luxembourg", new PlusType(), new Coordinate(90,90));
		int idPortRoyal = sm.addStation("PortRoyal", new PlusType(), new Coordinate(50,50));
		sm.addStationFleet(idChatelet, 10, "Electric");
		sm.addStationFleet(idChatelet, 10, "Mechanic");
		sm.addStationFleet(idChatelet, 10, "Vide");
		sm.addStationFleet(idLuxembourg, 10, "Electric");
		sm.addStationFleet(idLuxembourg, 10, "Mechanic");
		sm.addStationFleet(idLuxembourg, 10, "Vide");
		
		System.out.println(sm.stationMap.get(idLuxembourg).toString());
		
		//Rental of a bike
		int idPaul = sm.addUser(new User("Paul"));
		int idLucas = sm.addUser(new User ("Lucas"));
		sm.setUserCard(idPaul, new VlibreCard());
		sm.takeBike(idPaul,idChatelet,"Electric",0);
		System.out.println(sm.userMap.get(idPaul).getCurrentBike());
		sm.returnBike(idPaul,idLuxembourg,100);
		System.out.println(sm.userMap.get(idPaul).getCurrentCost().getTimeCredit());
		
		Coordinate startingLocation = new Coordinate(0,0);
		Coordinate destinationLocation = new Coordinate(100,100);
		RideFactory rideFactory = new RideFactory(sm.stationMap, "Mechanic", startingLocation, destinationLocation);
		Ride ride = rideFactory.createRide();
		System.out.println(ride);
		
		//Simulation of planning a ride
		sm.startRide(idLucas, ride);
		sm.takeBike(idLucas, ride.getStartingStationId(), ride.getBikeType() , 0);
		sm.setStationOnline(ride.getDestinationStationId(), false);
	}
}

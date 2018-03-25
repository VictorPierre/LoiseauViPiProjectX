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
	
	private TreeMap<Integer,Station> stationMap;
	
	private TreeMap<Integer, User> userMap;
	
	Simulation(){
		this.stationMap = new TreeMap<Integer,Station>();
		this.userMap = new TreeMap<Integer,User>();
	}
	
	//Ajouter une station
	private int addStation() {
		Station st = new Station();
		int id = st.getId();
		this.stationMap.put(id,st);
		return id;
	}
	
	private int addStation(String name, StationType stationType) {
		Station st = new Station(name,stationType);
		int id = st.getId();
		this.stationMap.put(id,st);
		return id;
	}
	
	private int addStation(String name, StationType stationType, Coordinate location) {
		Station st = new Station(name,stationType,location);
		int id = st.getId();
		this.stationMap.put(id,st);
		return id;
	}
	//Methodes d'initialisation sur les stations:
	private void addStationFleet(int id, int n, String bikeType) {
		Station st = this.stationMap.get(id);
		this.stationMap.remove(id);
		st.addFleet(n, bikeType);
		this.stationMap.put(id, st);
	}
	
	//Methodes pour setter les élements d'une station avec un id précis
	public void setStationName(int stationId, String stationName) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		st.setName(stationName);
		this.stationMap.put(stationId, st);
	}
	public void setStationLocation(int stationId, Coordinate stationLocation) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		st.setLocation(stationLocation);
		this.stationMap.put(stationId, st);
	}
	public void setStationOnline(int stationId, boolean isOnline) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		st.setOnline(isOnline);
		this.stationMap.put(stationId, st);
		if (!isOnline) {
			this.alertIncomingBikeGiver(stationId, "La station "+this.stationMap.get(stationId).getName()+" est désormais hors-service. Vous devez recalculer votre itinéraire.");
			this.alertIncomingBikeTaker(stationId, "La station "+this.stationMap.get(stationId).getName()+" est désormais hors-service. Vous devez recalculer votre itinéraire.");
		}
	}
	
	public void setStationSlotOutOfOrder(int stationId, int slotId, boolean isOutOfOrder) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		st.setOutOfOrder(slotId, isOutOfOrder);
		this.stationMap.put(stationId, st);
		if (this.stationMap.get(stationId).findBike("Electric")==-1 && this.stationMap.get(stationId).findBike("Mechanic")==-1) {
			this.alertIncomingBikeGiver(stationId,"Il n'y a plus de vélos disponibles dans la station "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itinéraire.");
		}
		if (this.stationMap.get(stationId).findEmptySlot()==-1) {
			this.alertIncomingBikeGiver(stationId,"Il n'y a plus de place dans la destination souhaitée "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itinéraire.");
		}
		
	}
	
	public void addIncomingBikeTaker(int stationId,int userId) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		st.addIncomingBikeTaker(userId);
		this.stationMap.put(stationId, st);
	}
	
	public void addIncomingBikeGiver(int stationId,int userId) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		st.addIncomingBikeGiver(userId);
		this.stationMap.put(stationId, st);
	}
	
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
	
	public int addUser(User user) {
		this.userMap.put(user.getId(),user);
		return user.getId();
	}
	
	//Methodes pour setter/modifier un user
	
	public void setUserName(int userId, String name) {
		User us = this.userMap.get(userId);
		this.userMap.remove(userId);
		us.setName(name);
		this.userMap.put(userId,us);
	}
	
	public void setUserLocation(int userId, Coordinate location) {
		User us = this.userMap.get(userId);
		this.userMap.remove(userId);
		us.setLocation(location);
		this.userMap.put(userId,us);
	}
	
	public void setUserCard(int userId, Card card) {
		User us = this.userMap.get(userId);
		this.userMap.remove(userId);
		us.setCard(card);
		this.userMap.put(userId,us);
	}
	
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
	
	//Prendre et remettre un vélo dans un emplacement précis d'une station précise
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
	
	public void takeBike(int userId, int stationId, String bikeType, int time) {
		int slotId = this.stationMap.get(stationId).findBike(bikeType);
		Bike b = takeBikeStation(stationId, slotId);
		takeBikeUser(userId,b,time);
		if (this.stationMap.get(stationId).findBike("Electric")==-1 && this.stationMap.get(stationId).findBike("Mechanic")==-1) {
			this.alertIncomingBikeTaker(stationId,"Il n'y a plus de vélos disponibles dans la station "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itinéraire.");
		}
	}
	
	public void returnBike(int userId, int stationId, int time) {
		int slotId=this.stationMap.get(stationId).findEmptySlot();
		Bike b = returnBikeUser(userId);
		returnBikeStation(stationId, slotId,b);
		User us = userMap.get(userId);
		
		//Crédit bonus pour les PlusType
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
		System.out.println("L'utilisateur sera débité de "+rideCost+"€");
		
		if (this.stationMap.get(stationId).findEmptySlot()==-1) {
			this.alertIncomingBikeGiver(stationId,"Il n'y a plus de place dans la destination souhaitée "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itinéraire.");
		}
	}
	
	
	private void alertIncomingBikeGiver(int stationId, String message) {
		String str = "Message envoyé aux utilisateurs suivants : ";
		str += this.stationMap.get(stationId).getIncomingBikeGiver().toString();
		str += "\n"+message;
		System.out.println(str);
	}
	
	private void alertIncomingBikeTaker(int stationId, String message) {
		String str = "Message envoyé aux utilisateurs suivants : ";
		str += this.stationMap.get(stationId).getIncomingBikeTaker().toString();
		str += "\n"+message;
		System.out.println(str);
	}

	//Métode qui permet de démarrer un ride pour un user
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
		
		String bikeType="Mechanic";
		Coordinate startingLocation = new Coordinate(0,0);
		Coordinate destinationLocation = new Coordinate(100,100);
		RideFactory rideFactory = new RideFactory(sm.stationMap, bikeType, startingLocation, destinationLocation);

		
		Ride ride = rideFactory.createRide();
		Ride ride1 = rideFactory.createFastestRide(sm.stationMap, bikeType, startingLocation, destinationLocation);
		Ride ride2 = rideFactory.createShortestRide(sm.stationMap, bikeType, startingLocation, destinationLocation);
		System.out.println(ride);
		System.out.println(ride1);
		
		
		//Simulation of planning a ride
		sm.startRide(idLucas, ride2);
		sm.takeBike(idLucas, ride2.getStartingStationId(), ride2.getBikeType() , 0);
		sm.setStationOnline(ride2.getDestinationStationId(), false);
	}
}

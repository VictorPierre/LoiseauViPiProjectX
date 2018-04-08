package partOne;

import java.util.Arrays;
import java.util.TreeMap;

import Exceptions.EmptySlotException;
import Exceptions.EmptyStationException;
import Exceptions.FullSlotException;
import Exceptions.FullStationException;
import Exceptions.NoCardException;
import Exceptions.OfflineException;
import Exceptions.RideImpossibleException;
import stationType.PlusType;
import stationType.StandardType;
import stationType.StationType;
import rideFactory.*;

public class Simulation {
	
	/**
	 * La carte des Stations
	 */
	private TreeMap<Integer,Station> stationMap;
	
	/**
	 * La liste des utilisateurs
	 */
	private TreeMap<Integer, User> userMap;
	
	/**
	 * La RideFactory pour générer des itinéraires
	 */
	
	private RideFactory rideFactory;
	
	/**
	 * Nom de la simulation
	 */
	private String name;
	
	
	public Simulation(){
		this.name = "default";
		this.stationMap = new TreeMap<Integer,Station>();
		this.userMap = new TreeMap<Integer,User>();
		this.rideFactory=new Fastest();
	}
	
	public Simulation(String name){
		this.name = name;
		this.stationMap = new TreeMap<Integer,Station>();
		this.userMap = new TreeMap<Integer,User>();
		this.rideFactory=new Fastest();
	}
	
	
	public void setRideFactory(RideFactory rideFactory) {
		this.rideFactory=rideFactory;
	}
	
	public RideFactory getRideFactory() {
		return this.rideFactory;
	}
	
	public TreeMap<Integer, Station> getStationMap(){
		return this.stationMap;
	}
	
	public TreeMap<Integer, User> getUserMap(){
		return this.userMap;
	}

	
	/**
	 * Pour ajouter une station au réseau
	 * @return : l'ID unique de la station ainsi créée
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
	 * @return l'ID unique de la station créée
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
	 * @return l'ID unique de la station créée
	 */
	public int addStation(String name, StationType stationType, Coordinate location) {
		Station st = new Station(name,stationType,location);
		int id = st.getId();
		this.stationMap.put(id,st);
		return id;
	}


	/**
	 * Pour ajouter des vélos à une station
	 * @param id de la station
	 * @param n : nombre de vélos à ajouter
	 * @param bikeType: type de vélos
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
			this.alertIncomingBikeGiver(stationId, "La station "+this.stationMap.get(stationId).getName()+" est désormais hors-service. Vous devez recalculer votre itinéraire.");
			this.alertIncomingBikeTaker(stationId, "La station "+this.stationMap.get(stationId).getName()+" est désormais hors-service. Vous devez recalculer votre itinéraire.");
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
		/**
		if (this.stationMap.get(stationId).findBike("Electric")==-1 && this.stationMap.get(stationId).findBike("Mechanic")==-1) {
			this.alertIncomingBikeGiver(stationId,"Il n'y a plus de vélos disponibles dans la station "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itinéraire.");
		}
		if (this.stationMap.get(stationId).findEmptySlot()==-1) {
			this.alertIncomingBikeGiver(stationId,"Il n'y a plus de place dans la destination souhaitée "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itinéraire.");
		}
		**/
		try {
			int slotE=this.stationMap.get(stationId).findBike("Electric"); 
			int slotM=this.stationMap.get(stationId).findBike("Mechanic");
			int slotV=this.stationMap.get(stationId).findEmptySlot();
		}
		catch (EmptyStationException e) {
			this.alertIncomingBikeTaker(stationId,"Il n'y a plus de vélos disponibles dans la station "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itinéraire.");
		}
		catch (FullStationException e) {
			this.alertIncomingBikeGiver(stationId,"Il n'y a plus de place dans la destination souhaitée "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itinéraire.");
		}
		
	}
	
	/**
	 * Ajoute un utilisateur sur la liste des futurs preneurs de vélos (à avertir en cas de problème)
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
	 * Ajoute un utilisateur sur la liste des futurs donneurs de vélos (à avertir en cas de problème)
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
	 * Pour prendre un vélo dans un slot donné d'une station
	 * @param stationId
	 * @param slotId
	 * @return : le vélo
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
	 * Ajoute un user à la simulation
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
	 * Change la carte de fidélité d'un user
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
	 * Permet de prendre un vélo
	 * @param userId : ID de l'user
	 * @param bike
	 * @param time : heure de prise du vélo
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
	 * Remettre un vélo dans une station
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
	 * Prend le vélo d'un user
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
	 * Donne un vélo à un user
	 * @param userId
	 * @param stationId
	 * @param bikeType
	 * @param time : heure de prise du vélo
	 */
	public void takeBike(int userId, int stationId, String bikeType, int time) {
		int slotId=0;
		try {slotId=this.stationMap.get(stationId).findBike(bikeType);} catch (EmptyStationException e) {e.printStackTrace();}
		Bike b = takeBikeStation(stationId, slotId);
		takeBikeUser(userId,b,time);
		//if (this.stationMap.get(stationId).findBike("Electric")==-1 && this.stationMap.get(stationId).findBike("Mechanic")==-1) {
			//this.alertIncomingBikeTaker(stationId,"Il n'y a plus de vélos disponibles dans la station "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itinéraire.");
		//}
		try {
			int slotE=this.stationMap.get(stationId).findBike("Electric"); 
			int slotM=this.stationMap.get(stationId).findBike("Mechanic");
		}
		catch (EmptyStationException e) {
			this.alertIncomingBikeTaker(stationId,"Il n'y a plus de vélos disponibles dans la station "+this.stationMap.get(stationId).getName()+". Vous devez recalculer votre itinéraire.");
		}
		
	}
	
	/**
	 * Un user rend un vélo à une heure donnée dans une station donnée
	 * @param userId
	 * @param stationId
	 * @param time
	 */
	public void returnBike(int userId, int stationId, int time) {
		int slotId=0;
		try {slotId=this.stationMap.get(stationId).findEmptySlot();} catch (FullStationException e) {e.printStackTrace();}
		Bike b = returnBikeUser(userId);
		returnBikeStation(stationId, slotId,b);
		User us = userMap.get(userId);
		this.userMap.remove(userId);
		//Crédit bonus pour les PlusType
		if (us.getCard()!=null) {
			Cost newCost = us.getCurrentCost();
			try {
				newCost.addTimeCredit(this.stationMap.get(stationId).getStationType().getMinuteBonus());
			} 
			catch (NoCardException e) {
				e.printStackTrace();
			}
			us.setCost(newCost);
			this.userMap.put(userId,us);
		}

		int rideDuration = time - us.getTime();
		Cost cost = us.getCurrentCost();
		int rideCost = cost.getRideCost(rideDuration);
		System.out.println("L'utilisateur sera débité de "+rideCost+"€");
		us.addTotalCharge(rideCost);
		us.addTotalTime(rideDuration);
		
		this.userMap.put(userId,us);
	}
	
	/**
	 * Alerte les futurs donneurs de vélo
	 * @param stationId
	 * @param message
	 */
	private void alertIncomingBikeGiver(int stationId, String message) {
		if (this.stationMap.get(stationId).getIncomingBikeGiver().size()!=0) {
			String str = "Message envoyé aux utilisateurs suivants : ";
			str += this.stationMap.get(stationId).getIncomingBikeGiver().toString();
			str += "\n"+message;
			System.out.println(str);
		}
	}
	/**
	 * Alerte les futurs preneurs de vélo
	 * @param stationId
	 * @param message
	 */
	private void alertIncomingBikeTaker(int stationId, String message) {
		if (this.stationMap.get(stationId).getIncomingBikeTaker().size()!=0) {
			String str = "Message envoyé aux utilisateurs suivants : ";
			str += this.stationMap.get(stationId).getIncomingBikeTaker().toString();
			str += "\n"+message;
			System.out.println(str);
		}	
	}

	/**
	 * Début d'un voyage pour un utilisateur : on l'indique à la station d'arrivée
	 * @param userId
	 * @param ride
	 */
	public void startRide(int userId, Ride ride) {
		User user = this.userMap.get(userId);
		user.setCurrentRide(ride);
		user.addTotalNbRide();
		//On indique ensuite aux stations que l'utilisateur est en train d'arriver
		int startingStationId = ride.getStartingStationId();
		int destinationStationId = ride.getDestinationStationId();
		this.addIncomingBikeTaker(startingStationId, userId);
		this.addIncomingBikeGiver(destinationStationId, userId);
	}
	
	//Methodes de statistiques:
	
	public void getUserBalance(int userId) {
		String str = "";
		User us = this.userMap.get(userId);
		str+="Utilisateur :"+us.getName()+" n° "+userId+"\n";
		str+="Nombre total de trajets : "+us.getTotalNbRide()+"\n";
		str+="Somme totale dépensée : "+us.getTotalCharge()+"\n";
		str+="Temps total passé en vélo : "+us.getTotalTime();
		System.out.println(str);
	}
	
	public void getStationBalance(int stationId) {
		String str = "";
		Station st = this.stationMap.get(stationId);
		str += "Station : "+st.getName()+" n° "+stationId+"\n";
		str+="Nombre total de location : "+st.getTotalRentNb()+"\n";
		str+="Nombre total de retour : "+st.getTotalReturnNb()+"\n";
		System.out.println(str);
	}
	
	
	public static void main(String[] args) {
		
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		int idLuxembourg = sm.addStation("Luxembourg", new PlusType(), new Coordinate(90,90));
		int idPortRoyal = sm.addStation("PortRoyal", new PlusType(), new Coordinate(50,50));
		sm.addStationFleet(idChatelet, 30, "Electric");
		sm.addStationFleet(idChatelet, 40, "Mechanic");
		sm.addStationFleet(idChatelet, 30, "Vide");
		sm.addStationFleet(idLuxembourg, 30, "Electric");
		sm.addStationFleet(idLuxembourg, 40, "Mechanic");
		sm.addStationFleet(idLuxembourg, 30, "Vide");
		sm.addStationFleet(idPortRoyal, 30, "Electric");
		sm.addStationFleet(idPortRoyal, 40, "Mechanic");
		sm.addStationFleet(idPortRoyal, 30, "Vide");
		
		System.out.println("****************Etat de la station Luxembourg****************");
		System.out.println(sm.stationMap.get(idLuxembourg).toString()+"\n");
		
		//Rental of a bike
		int idPaul = sm.addUser(new User("Paul"));
		int idLucas = sm.addUser(new User ("Lucas"));
		sm.setUserCard(idPaul, new VlibreCard());
		sm.takeBike(idPaul,idChatelet,"Electric",0);
		System.out.println("****************Vélo emprunté, cout du trajet et crédit restant****************");
		System.out.println(sm.userMap.get(idPaul).getCurrentBike());
		sm.returnBike(idPaul,idLuxembourg,100);
		try {
		System.out.println("Credit de temps restant : "+ sm.userMap.get(idPaul).getCurrentCost().getTimeCredit()+"\n");
		} 
		catch (NoCardException e) {
			e.printStackTrace();
		}
		Coordinate startingLocation = new Coordinate(0,0);
		Coordinate destinationLocation = new Coordinate(100,100);
		
		//Ride planning policy
		sm.setRideFactory(new Fastest());
		
		//Route calculation
		Ride ride=new Ride();
		try {
			ride = sm.getRideFactory().createRide(sm.stationMap, "Mechanic", startingLocation, destinationLocation);
		} catch (RideImpossibleException e) {e.printStackTrace();}
		
		System.out.println("****************Ride calculé pour l'utilisateur****************");
		System.out.println(ride);
		
		//Simulation of planning a ride
		System.out.println("****************Essai de fermeture d'une station****************");
		sm.startRide(idLucas, ride);
		sm.takeBike(idLucas, ride.getStartingStationId(), ride.getBikeType() , 0);
		sm.setStationOnline(ride.getDestinationStationId(), false);
	}
}

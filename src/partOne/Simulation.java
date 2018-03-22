package partOne;

import java.util.TreeMap;

import Exceptions.EmptySlotException;
import Exceptions.FullSlotException;
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
	public void setStationLocation(int stationId, float[] stationLocation) {
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
		st.returnBike(slotId, bike);
		this.stationMap.put(stationId, st);
	}
	private Bike takeBikeStation(int stationId, int slotId) {
		Station st = this.stationMap.get(stationId);
		this.stationMap.remove(stationId);
		Bike b = st.takeBike(slotId);
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
	
	public void setUserLocation(int userId, float[] location) {
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
	
	public void takeBike(int userId, int stationId, int slotId, int time) {
		Bike b = takeBikeStation(stationId, slotId);
		takeBikeUser(userId,b,time);
	}
	
	public void returnBike(int userId, int stationId, int slotId, int time) {
		Bike b = returnBikeUser(userId);
		returnBikeStation(stationId, slotId,b);
		
		User us = userMap.get(userId);
		int rideDuration = time - us.getTime();
		Cost cost = us.getCurrentCost();
		int rideCost = cost.getRideCost(rideDuration);
		System.out.println("L'utilisateur sera débité de "+rideCost+"€");
		
	}
	
	public static void main(String[] args) {
		Simulation sm = new Simulation();
		int idChatelet = sm.addStation("Chatelet", new StandardType());
		int idLuxembourg = sm.addStation("Luxembourg", new PlusType());
		sm.addStationFleet(idChatelet, 10, "Electric");
		sm.addStationFleet(idChatelet, 10, "Mechanic");
		sm.addStationFleet(idChatelet, 10, "Vide");
		sm.addStationFleet(idLuxembourg, 10, "Electric");
		sm.addStationFleet(idLuxembourg, 10, "Mechanic");
		sm.addStationFleet(idLuxembourg, 10, "Vide");
		
		System.out.println(sm.stationMap.get(idLuxembourg).toString());
		
		int idPaul = sm.addUser(new User("Paul"));
		sm.setUserCard(idPaul, new VlibreCard());
		sm.takeBike(idPaul,idChatelet,1,0);
		System.out.println(sm.userMap.get(idPaul).getCurrentBike());
		sm.returnBike(idPaul,idLuxembourg,60,100);
		
	}
}

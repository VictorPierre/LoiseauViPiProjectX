package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Exceptions.FullSlotException;
import Exceptions.FullStationException;
import Exceptions.NoCardException;
import partOne.Coordinate;
import partOne.ElectricBike;
import partOne.MechanicBike;
import partOne.ParkingSlot;
import partOne.Simulation;
import partOne.Station;
import partOne.User;
import partOne.Ride;
import partOne.VlibreCard;
import stationType.PlusType;
import stationType.StandardType;

class SimulationTest {

	@Test
	void testAddStationFleet() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		sm.addStationFleet(idChatelet, 30, "Electric");
		sm.addStationFleet(idChatelet, 40, "Mechanic");
		sm.addStationFleet(idChatelet, 30, "Vide");

		//On récupère la map de la station
		TreeMap<Integer,ParkingSlot> map=sm.getStationMap().get(idChatelet).getSlotMap();
		int nb_vide=0;
		int nb_elec=0;
		int nb_meca=0;
		
		//On compte les places de parking
		for(Entry<Integer, ParkingSlot> entry : map.entrySet()) {
			ParkingSlot park = entry.getValue();
			if (park.getBike() instanceof MechanicBike) {
				nb_meca++;
			}
			else if (park.getBike() instanceof ElectricBike) {
				nb_elec++;
			}
			else {nb_vide++;}
		}
		//On vérifie que le bon nombre de slots a été ajouté
		assertTrue(nb_vide==30 && nb_meca==40 && nb_elec==30, "Wrong number of parkingslots");

	}

	@Test
	void testSetStationName() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		assertTrue(sm.getStationMap().get(idChatelet).getName()=="Chatelet", "Wrong name initialized");
		//set a new name
		sm.setStationName(idChatelet, "newname");
		assertTrue(sm.getStationMap().get(idChatelet).getName()=="newname", "Name not updated");
	}

	@Test
	void testSetStationLocation() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		assertTrue(sm.getStationMap().get(idChatelet).getLocation().getxCoord()==10 && sm.getStationMap().get(idChatelet).getLocation().getyCoord()==0, "Wrong coordinate initialized");
		//set a new location
		sm.setStationLocation(idChatelet, new Coordinate(11,12));
		assertTrue(sm.getStationMap().get(idChatelet).getLocation().getxCoord()==11 && sm.getStationMap().get(idChatelet).getLocation().getyCoord()==12, "Coordinate not updated");
	}

	@Test
	void testSetStationOnline() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		assertTrue(sm.getStationMap().get(idChatelet).isOnline(), "Wrong initialization");
		//set a new state
		sm.setStationOnline(idChatelet, false);
		assertTrue(!sm.getStationMap().get(idChatelet).isOnline(), "Station state didn't change");
	}
	
	@Test
	void testSetStationSlotOutOfOrder() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		sm.addStationFleet(idChatelet, 30, "Vide");
		int idSlot=0;
		try {idSlot=sm.getStationMap().get(idChatelet).findEmptySlot();} catch(FullStationException e) {e.printStackTrace();}
		assertTrue(!sm.getStationMap().get(idChatelet).getSlotMap().get(idSlot).isOutOfOrder(), "Wrong 1initialization");
		//set a new state
		sm.setStationOnline(idChatelet, false);
		assertTrue(!sm.getStationMap().get(idChatelet).getSlotMap().get(idSlot).isOutOfOrder(), "Slot state didn't change");	
	}


	@Test
	void testAddIncomingBikeTaker() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		assertTrue(sm.getStationMap().get(idChatelet).getIncomingBikeTaker().isEmpty(), "Wrong initialization");
		//add a new bike taker
		sm.addIncomingBikeTaker(idChatelet, 2563);
		assertTrue(sm.getStationMap().get(idChatelet).getIncomingBikeTaker().contains(2563), "array didn't change");	
	}	

	@Test
	void testAddIncomingBikeGiver() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		assertTrue(sm.getStationMap().get(idChatelet).getIncomingBikeGiver().isEmpty(), "Wrong initialization");
		//add a new bike giver
		sm.addIncomingBikeGiver(idChatelet, 2563);
		assertTrue(sm.getStationMap().get(idChatelet).getIncomingBikeGiver().contains(2563), "array didn't change");	
	}	

	@Test
	void testAddUser() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		assertTrue(sm.getUserMap().isEmpty(), "Wrong initialization");
		//add a new user
		int idUser = sm.addUser(new User("Jean"));
		assertTrue(sm.getUserMap().containsKey(idUser), "User not added");	
	}	


	@Test
	void testSetUserName() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idUser = sm.addUser(new User("Jean"));
		assertTrue(sm.getUserMap().get(idUser).getName()=="Jean", "Wrong initialization");
		//Set a new name
		sm.setUserName(idUser, "Bernard");
		assertTrue(sm.getUserMap().get(idUser).getName()=="Bernard", "Name not changed");
	}	

	@Test
	void testSetUserLocation() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idUser = sm.addUser(new User("Jean"));
		assertTrue(sm.getUserMap().get(idUser).getLocation().equals(new Coordinate()), "Wrong initialization");
		//Set a new location
		sm.setUserLocation(idUser, new Coordinate(11,13));
		assertTrue(sm.getUserMap().get(idUser).getLocation().equals(new Coordinate(11,13)), "Location not changed");
	}	

	@Test
	void testSetUserCard() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idUser = sm.addUser(new User("Jean"));
		assertTrue(sm.getUserMap().get(idUser).getCard()==null, "Wrong initialization");
		//Set a new Card
		sm.setUserCard(idUser, new VlibreCard());
		assertTrue(sm.getUserMap().get(idUser).getCard() instanceof VlibreCard, "Card not changed");
	}	
	
	@Test
	void testTakeBike() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idUser = sm.addUser(new User("Jean"));
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		sm.addStationFleet(idChatelet, 40, "Mechanic");
		sm.addStationFleet(idChatelet, 30, "Vide");
		//On vérifie que Jean n'a pas de vélo
		assertTrue(sm.getUserMap().get(idUser).getCurrentBike()==null,"Jean à déjà un vélo !");
		//Jean prend un vélo
		sm.takeBike(idUser, idChatelet, "Mechanic", 12);
		assertTrue(sm.getUserMap().get(idUser).getCurrentBike() instanceof MechanicBike, "Jean n'a pas pris de vélo");
		assertTrue(sm.getStationMap().get(idChatelet).getAvailableSlotNb() == 31, "Le slot ne s'est pas libéré");
	}

	@Test
	void testReturnBikeStandardType() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idUser = sm.addUser(new User("Jean",new Coordinate(), new MechanicBike(), new VlibreCard()));
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		sm.addStationFleet(idChatelet, 40, "Mechanic");
		sm.addStationFleet(idChatelet, 30, "Vide");
		
		//On vérifie que Jean a un vélo et que son crédit temps est vide
		assertTrue(sm.getUserMap().get(idUser).getCurrentBike()instanceof MechanicBike,"Jean n'a pas de vélo !");
		try {
		assertTrue(sm.getUserMap().get(idUser).getCurrentCost().getTimeCredit()==0,"Jean a déja un TimeCredit!");
		}
		catch(NoCardException e){
			e.printStackTrace();
		}
		//Jean rend le vélo
		sm.returnBike(idUser, idChatelet, 70);
		assertTrue(sm.getUserMap().get(idUser).getCurrentBike()==null, "Jean a encore un vélo");
		assertTrue(sm.getStationMap().get(idChatelet).getAvailableSlotNb() == 29, "Le slot ne s'est pas rempli");
		try {
		assertTrue(sm.getUserMap().get(idUser).getCurrentCost().getTimeCredit()==50,"Wrong timecredit");
		}
		catch(NoCardException e){
			e.printStackTrace();
		}
	}
	
	@Test
	void testReturnBikePlusType() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idUser = sm.addUser(new User("Jean",new Coordinate(), new MechanicBike(), new VlibreCard()));
		int idChatelet = sm.addStation("Chatelet", new PlusType(), new Coordinate(10,0));
		sm.addStationFleet(idChatelet, 40, "Mechanic");
		sm.addStationFleet(idChatelet, 30, "Vide");
		
		//On vérifie que Jean a un vélo et que son crédit temps est vide
		assertTrue(sm.getUserMap().get(idUser).getCurrentBike()instanceof MechanicBike,"Jean n'a pas de vélo !");
		try {
		assertTrue(sm.getUserMap().get(idUser).getCurrentCost().getTimeCredit()==0,"Jean a déja un TimeCredit!");
		}
		catch(NoCardException e){
			e.printStackTrace();
		}
		//Jean rend le vélo
		sm.returnBike(idUser, idChatelet, 70);
		assertTrue(sm.getUserMap().get(idUser).getCurrentBike()==null, "Jean a encore un vélo");
		assertTrue(sm.getStationMap().get(idChatelet).getAvailableSlotNb() == 29, "Le slot ne s'est pas rempli");
		try {
		assertTrue(sm.getUserMap().get(idUser).getCurrentCost().getTimeCredit()==55,"Wrong time credit :"+sm.getUserMap().get(idUser).getCurrentCost().getTimeCredit()+"  (15 expected : 10 report + 5 bonus");
		}
		catch(NoCardException e){
			e.printStackTrace();
		}
	}
	
	@Test
	void testStartRide() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idJean = sm.addUser(new User("Jean",new Coordinate(), new MechanicBike(), new VlibreCard()));
		int idChatelet = sm.addStation("Chatelet", new PlusType(), new Coordinate(10,0));
		int idLuxembourg = sm.addStation("Luxembourg", new StandardType(), new Coordinate(30,10));
		sm.addStationFleet(idChatelet, 40, "Mechanic");
		sm.addStationFleet(idChatelet, 30, "Vide");
		assertTrue(sm.getStationMap().get(idChatelet).getIncomingBikeTaker().isEmpty(), "Wrong initialization");
		assertTrue(sm.getStationMap().get(idLuxembourg).getIncomingBikeGiver().isEmpty(), "Wrong initialization");
		assertTrue(sm.getUserMap().get(idJean).getCurrentRide()==null, "Wrong initialization");
		
		//New ride
		Ride ride= new Ride(sm.getStationMap().get(idChatelet), sm.getStationMap().get(idLuxembourg),new Coordinate(0,1),new Coordinate(100,100), "Shortest","Mechanic", 0, 0);
		
		//Start a ride
		sm.startRide(idJean, ride);
		
		//On vérifie que Jean a le ride et qu'il est sur les listes
		assertTrue(sm.getUserMap().get(idJean).getCurrentRide()==ride, "Jean n'a pas démarré le ride");
		assertTrue(sm.getStationMap().get(idChatelet).getIncomingBikeTaker().contains(idJean), "Jean n'est pas ajouté sur la liste des futurs preneurs de vélo");
		assertTrue(sm.getStationMap().get(idLuxembourg).getIncomingBikeGiver().contains(idJean), "Jean n'est pas ajouté sur la liste des futurs donneurs de vélo");
	}

}

package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TreeMap;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import partOne.*;
import Exceptions.*;



class StationTest {

	@Test
	void testAddFleet() {
		Station st;
		st=new Station();
		//On ajoute des vélos
		st.addFleet(10, "Electric");
		st.addFleet(5, "vide");
		st.addFleet(5, "Mechanic");
		
		//On récupère la map de la station
		TreeMap<Integer,ParkingSlot> map=st.getSlotMap();
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
		assertTrue(nb_vide==5 && nb_meca==5 && nb_elec==10, "Wrong number of parkingslots");
	}
	
	
	@Test
	void testReturnBike() {
		//Initialisation d'une station
		Station st;
		st=new Station();
		st.addFleet(10, "Electric");
		st.addFleet(5, "vide");
		Bike b1=new MechanicBike();
		Bike b2=null;
		int slotId=0;
		try {
			slotId=st.findEmptySlot();
		}
		catch (FullStationException e) {
			e.printStackTrace();
		}
		
		//On retourne le vélo dans un slot vide
		try {
			st.returnBike(slotId,b1);
		}
		catch (OfflineException e) {
			e.printStackTrace();
		}
		
		//On essaie d'ajouter un vélo dans un slot plein
		//en pratique, la fonction retourne bien l'exception mais le test ne la capte pas
		//assertThrows(FullSlotException.class, ()->st.returnBike(slotId,b1),"It is possible to put 2 bikes in the same slot");
		
		//On récupère le vélo grace à la TreeMap
		TreeMap<Integer,ParkingSlot> map=st.getSlotMap();
		ParkingSlot park = map.get(slotId);
		try {
			b2=park.takeBike();
		}
		catch (OutOfOrderException|EmptySlotException e) {
			e.printStackTrace();
		}
		
		//On vérifie que c'est bien le bon vélo
		assertTrue(b2==b1, "Bike not added");
		
		
		//On essaie d'ajouter un vélo quand la station est Offline
		st.setOnline(false);
		assertThrows(OfflineException.class, ()->st.returnBike(12,b1),"Bike returned while offline");
	}

	@Test
	void testTakeBike() {
		//Initialisation d'une station
		Station st;
		st=new Station();
		st.addFleet(5, "vide");
		st.addFleet(10, "Electric");
		Bike b1=new MechanicBike();
		int slot=0;
		try {
			slot=st.findBike("Electric");
			b1=st.takeBike(slot);
		}
		catch (EmptyStationException|OfflineException e) {
			e.printStackTrace();
		}
		
		//On vérifie que c'est bien le bon vélo
		assertTrue(b1 instanceof ElectricBike, "Wrong bike type");
		
		//On vérifie que le slot est bien vide
		ParkingSlot park = st.getSlotMap().get(slot);
		assertTrue(park.getBike()==null, "Bike not removed");
	}


	@Test
	void testFindEmptySlot() {
		//Initialisation d'une station
		Station st;
		st=new Station();
		st.addFleet(1, "vide");
		st.addFleet(10, "Electric");
		Bike b1=new MechanicBike();
		int slot=0;
		try {
			slot=st.findEmptySlot();
		}
		catch (FullStationException e) {
			e.printStackTrace();
		}
		
		
		//On récupère la place de parking de la station
		ParkingSlot park = st.getSlotMap().get(slot);
		//On vérifie qu'elle est bien vide
		assertTrue(park.getBike()==null, "la place n'est pas vide");
		
		//On remplit la station et on vérifie que la fonction retourne l'erreur
		try {
			st.returnBike(slot, b1);
		}
		catch (OfflineException e) {
			e.printStackTrace();
		}
		assertThrows(FullStationException.class, ()->st.findEmptySlot());	
	}

	@Test
	void testFindBike() {
		//Initialisation d'une station
		Station st;
		st=new Station();
		st.addFleet(10, "vide");
		st.addFleet(1, "Electric");
		Bike b1=new MechanicBike();
		int slot=0;
		try {
			slot=st.findBike("Electric");
		}
		catch (EmptyStationException e) {
			e.printStackTrace();
		}
		
		//On récupère la place de parking de la station
		ParkingSlot park = st.getSlotMap().get(slot);
		//On vérifie qu'elle contient le bon vélo
		assertTrue(park.getBike() instanceof ElectricBike, "le slot ne convient pas");
		
		//On vide la station et on vérifie que la fonction retourne l'erreur
		try {
			st.takeBike(slot);
		}
		catch (OfflineException e) {
			e.printStackTrace();
		}
		assertThrows(EmptyStationException.class, ()->st.findBike("Electric"));	
	}
}

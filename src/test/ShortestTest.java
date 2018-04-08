package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceptions.EmptyStationException;
import Exceptions.RideImpossibleException;
import partOne.*;
import rideFactory.*;
import stationType.PlusType;
import stationType.StandardType;

class ShortestTest {
		
	/**
	 * Test in normal conditions
	 */
	@Test
	void testShortestNormal() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		int idLuxembourg = sm.addStation("Luxembourg", new PlusType(), new Coordinate(90,100));
		int idPortRoyal = sm.addStation("PortRoyal", new PlusType(), new Coordinate(30,70));
		
		//Ride planning policy
		sm.setRideFactory(new Shortest());
		
		//Normal conditions
		sm.addStationFleet(idChatelet, 5, "Mechanic");
		sm.addStationFleet(idChatelet, 5, "Vide");
		sm.addStationFleet(idLuxembourg, 5, "Mechanic");
		sm.addStationFleet(idLuxembourg, 5, "Vide");
		sm.addStationFleet(idPortRoyal, 5, "Mechanic");
		sm.addStationFleet(idPortRoyal, 5, "Vide");
		
		//Route calculation
		Ride ride=new Ride();
		
		try {
			ride = sm.getRideFactory().createRide(sm.getStationMap(), "Mechanic", new Coordinate(), new Coordinate (100,100));
		}
		catch (RideImpossibleException e) {e.printStackTrace();}
		
		assertTrue(ride.getStartingStationId()==idChatelet, "Wrong start");
		assertTrue(ride.getDestinationStationId()==idLuxembourg, "Wrong destination");
	}
	
	/**
	 * test when a station is empty : need to change the route
	 */
	
	@Test
	void testShortestEmptyStation() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		int idLuxembourg = sm.addStation("Luxembourg", new PlusType(), new Coordinate(90,100));
		int idPortRoyal = sm.addStation("PortRoyal", new PlusType(), new Coordinate(30,70));
		
		//Ride planning policy
		sm.setRideFactory(new Shortest());
		
		//Chatelet is empty
		sm.addStationFleet(idChatelet, 5, "Vide");
		sm.addStationFleet(idLuxembourg, 5, "Mechanic");
		sm.addStationFleet(idLuxembourg, 5, "Vide");
		sm.addStationFleet(idPortRoyal, 5, "Mechanic");
		sm.addStationFleet(idPortRoyal, 5, "Vide");
		
		//Route calculation
		Ride ride=new Ride();
		
		try {
			ride = sm.getRideFactory().createRide(sm.getStationMap(), "Mechanic", new Coordinate(), new Coordinate (100,100));
		}
		catch (RideImpossibleException e) {e.printStackTrace();}
		
		assertTrue(ride.getStartingStationId()==idPortRoyal, "Wrong start");
		assertTrue(ride.getDestinationStationId()==idLuxembourg, "Wrong destination");
	}
	
	/**
	 * Test with no available bike : ride is impossible
	 */
	
	@Test
	void testShortestImpossibleRide() {
		//Setting up myVelib
		Simulation sm = new Simulation();
		int idChatelet = sm.addStation("Chatelet", new StandardType(), new Coordinate(10,0));
		int idLuxembourg = sm.addStation("Luxembourg", new PlusType(), new Coordinate(90,100));
		int idPortRoyal = sm.addStation("PortRoyal", new PlusType(), new Coordinate(30,70));
		
		//Ride planning policy
		sm.setRideFactory(new Shortest());
		
		//No Mechanic available
		sm.addStationFleet(idChatelet, 5, "Vide");
		//sm.addStationFleet(idLuxembourg, 5, "Electric");
		sm.addStationFleet(idLuxembourg, 5, "Vide");
		//sm.addStationFleet(idPortRoyal, 5, "Mechanic");
		sm.addStationFleet(idPortRoyal, 5, "Vide");
		sm.setStationOnline(idPortRoyal, false);
		
		//Route calculation
		assertThrows(RideImpossibleException.class, ()->sm.getRideFactory().createRide(sm.getStationMap(), "Mechanic", new Coordinate(), new Coordinate (100,100)));
	}
	
}
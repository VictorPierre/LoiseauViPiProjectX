package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Exceptions.EmptySlotException;
import Exceptions.FullSlotException;
import Exceptions.NoCardException;
import Exceptions.OutOfOrderException;
import partOne.ElectricBike;
import partOne.MechanicBike;
import partOne.Bike;
import partOne.ParkingSlot;

class ParkingSlotTest {

	@BeforeAll
	static void setUpBeforeClass(){
		ParkingSlot p = new ParkingSlot();
	}
	
	@Test
	void testTakeBike() {
		Bike b=new MechanicBike();
		ParkingSlot p = new ParkingSlot(new ElectricBike());
		try {
			b=p.takeBike();
		}
		catch (EmptySlotException|OutOfOrderException e) {
			e.printStackTrace();
		}
		//Test de takebike avec un vélo
		assertTrue(b instanceof ElectricBike, "Issue on bike taken");
		//test s'il n'y a pas de vélo
		assertThrows(EmptySlotException.class,()->{Bike b1; b1=p.takeBike();});
		//test dans le cas H-S
		p.setOutOfOrder(true);
		assertThrows(OutOfOrderException.class,()->{Bike b1; b1=p.takeBike();});
	}
	
	@Test
	void testReturnBike() {
		Bike b=new MechanicBike();
		ParkingSlot p = new ParkingSlot();
		try {
			p.returnBike(b);
		}
		catch (FullSlotException|OutOfOrderException e) {
			e.printStackTrace();
		}
		//test de returnbike dans une place vide
		assertTrue(p.getBike() instanceof MechanicBike, "Issue on bike returned");
		//dans une place non vide
		Bike b1=new ElectricBike();
		assertThrows(FullSlotException.class,()->p.returnBike(b1));
		//dans une place HS
		p.setOutOfOrder(true);
		assertThrows(OutOfOrderException.class,()->p.returnBike(b1));
	}
}

package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Exceptions.FullSlotException;
import Exceptions.EmptySlotException;
import partOne.User;
import partOne.Bike;
import partOne.MechanicBike;
import partOne.ElectricBike;

class UserTest {

	@Test
	void testTakeBike() {
		User a=new User();
		Bike b=new MechanicBike();
		try {
			a.takeBike(b,10);
		}
		catch (FullSlotException e) {
			e.printStackTrace();
		}
		assertTrue(a.getCurrentBike() instanceof MechanicBike, "Wrong bike type");
		assertThrows(FullSlotException.class,()->a.takeBike(b,20),"User shouldn't be able to take 2 bikes");
	}
		

	@Test
	void testReturnBike() {
		User a=new User();
		Bike b1=new MechanicBike();
		try {
			a.takeBike(b1,10);
		}
		catch (FullSlotException e) {
			e.printStackTrace();
		}
		
		Bike b2=new ElectricBike();
		
		try {
			b2=a.returnBike();
		}
		catch (EmptySlotException e) {
			e.printStackTrace();
		}
		
		assertTrue(b2==b1, "Wrong bike");
		assertThrows(EmptySlotException.class,()->{Bike b3=new ElectricBike();b3=a.returnBike();});
	}
}

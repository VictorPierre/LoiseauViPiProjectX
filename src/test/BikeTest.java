package test;

import static org.junit.jupiter.api.Assertions.*;
import partOne.ElectricBike;
import partOne.MechanicBike;

import org.junit.jupiter.api.Test;


class BikeTest {

	@Test
	void testGetSpeed() {
		ElectricBike ebike=new ElectricBike();
		assertTrue(ebike.getSpeed()==20,"Invalid ElectricBike speed");
		
		MechanicBike mbike=new MechanicBike();
		if (mbike.getSpeed()!=15) {
			fail("Invalid MechanicBike speed");
		}
	}

}

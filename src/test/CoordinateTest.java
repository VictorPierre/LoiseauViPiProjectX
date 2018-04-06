package test;
import partOne.Coordinate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CoordinateTest {

	@Test
	void testDist() {
		Coordinate c1 = new Coordinate(-1,2);
		Coordinate c2 = new Coordinate(1,3);
		assertTrue(c1.dist(c2)==Math.sqrt(5.0),"Erreur sur la distance");
	}

}

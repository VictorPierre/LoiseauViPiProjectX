package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import partOne.Cost;
import partOne.ElectricBike;
import partOne.MechanicBike;
import partOne.VmaxCard;
import partOne.VlibreCard;

import Exceptions.NoCardException;

class CostTest {
	
	@Test
	void test1() {
		Cost c1 = new Cost(null, new MechanicBike());
		assertTrue(c1.getRideCost(70)==2,"Cost error");
		assertThrows(NoCardException.class,()->c1.getTimeCredit());
	}

	@Test
	void test2() {
		Cost c2 = new Cost(new VlibreCard(), new MechanicBike());
		assertTrue(c2.getRideCost(70)==1,"Cost error");
		int t=0;
		try {
			t=c2.getTimeCredit();
		} 
		catch (NoCardException e) {
			e.printStackTrace();
		}
		assertTrue(t==50,"TimeCreditError");
	}

	@Test
	void test3() {
		Cost c3 = new Cost(new VmaxCard(), new MechanicBike());
		assertTrue(c3.getRideCost(70)==1,"Cost error");
		try {
			assertTrue(c3.getTimeCredit()==50,"TimeCreditError");
		} 
		catch (NoCardException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void test4() {
		Cost c4 = new Cost(null, new ElectricBike());
		assertTrue(c4.getRideCost(70)==4,"Cost error");
		assertThrows(NoCardException.class,()->c4.getTimeCredit());
	}
	
	@Test
	void test5() {
		Cost c5 = new Cost(new VlibreCard(), new ElectricBike());
		assertTrue(c5.getRideCost(70)==3,"Cost error");
		try {
			assertTrue(c5.getTimeCredit()==50,"TimeCreditError");
		} 
		catch (NoCardException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void test6() {
		Cost c6 = new Cost(new VmaxCard(), new ElectricBike());
		assertTrue(c6.getRideCost(70)==1,"Cost error");
		try {
			assertTrue(c6.getTimeCredit()==50,"TimeCreditError");
		} 
		catch (NoCardException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 *Trajet d'1h pile
	 */
	@Test
	void test7() {
		Cost c = new Cost(new VmaxCard(), new ElectricBike());
		assertTrue(c.getRideCost(120)==1,"Cost error");
		try {
			assertTrue(c.getTimeCredit()==0,"TimeCreditError");
		} 
		catch (NoCardException e) {
			e.printStackTrace();
		}
	}
}

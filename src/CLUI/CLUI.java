package CLUI;

import java.util.Arrays;
import java.util.Scanner;

import partOne.*;
import Exceptions.*;
import rideFactory.*;
import stationType.*;

public class CLUI {
	
	public Simulation setup(String name, int nstations, int nslots, float side, int nbikes) {
		Simulation sm = new Simulation(name);
		for (int k=0; k<nstations;k++) {
			sm.addStation("station"+k+1, new StandardType(), new Coordinate(side*Math.random()- side/2,side*Math.random()-side/2));
			sm.addStationFleet(k+1, nslots, null);
		}
		int nBikeLeft = nbikes;
		
		while (nBike > 0) {
			sm.add
		}
		
	}
	
	public final static void run() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Bienvenue dans l'interface myVelib");
		String str = sc.nextLine();
		String values[] = str.split(" ");
		System.out.println(Arrays.deepToString(values));
		
		if (values[0] == "setup" ) {
			if (values.length == 2 ) {
				
			}
			else if (values.length == 6) {
				
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0] == "addUser") {
			if (values.length == 4) {
				
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");				
			}
		}
		
		else if (values[0] == "offline") {
			if (values.length == 3) {
				
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0] == "online") {
			if (values.length == 3) {
				
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0] == "rentBike") {
			if (values.length == 3) {
				
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0] == "returnBike") {
			if (values.length == 4) {
				
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0] == "displayStation") {
			if (values.length == 3) {
				
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0] == "displayUser") {
			if (values.length == 3) {
				
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0] == "sortStation") {
			if (values.length == 3) {
				
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0] == "display") {
			if (values.length == 2) {
				
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else {
			System.out.println("\""+str+"\" : "+"commande inconnue");
		}
	}

	public static void main(String[] args) {
		run();
	}
}

package CLUI;

import java.util.Arrays;
import java.util.Scanner;

import partOne.*;
import Exceptions.*;
import rideFactory.*;
import stationType.*;

public class CLUI {
	
	public static Simulation setup(String name, int nstations, int nslots, float side, int nbikes) {
		Simulation sm = new Simulation(name);
		for (int k=0; k<nstations;k++) {
			sm.addStation("station"+k+1, new StandardType(), new Coordinate(side*Math.random()- side/2,side*Math.random()-side/2));
			sm.addStationFleet(k+1, nslots-nslots/nbikes, null);
			sm.addStationFleet(k+1, nbikes/nslots, "Mechanical");
		}
		return sm;
	}
	
	public final static void run() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Bienvenue dans l'interface myVelib");
		String str = sc.nextLine();
		String values[] = str.split(" ");
		System.out.println(Arrays.deepToString(values));
		
		if (values[0] == "setup" ) {
			if (values.length == 2 ) {
				try{
					Simulation sm = setup(values[1], 10,10,4f,7 );
				}
				catch (Exception e) {
					System.out.println(values[0]+" : mauvais type d'argument");
				}
			}
			else if (values.length == 6) {
				try{
					Simulation sm = setup(values[1],Integer.parseInt(values[2]),Integer.parseInt(values[3]),Float.parseFloat(values[4]),Integer.parseInt(values[5]));
				}
				catch (Exception e) {
					System.out.println(values[0]+" : mauvais type d'argument");
				}
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

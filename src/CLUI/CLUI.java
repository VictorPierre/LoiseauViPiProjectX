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
			sm.addStationFleet(k+1, nslots-nslots/nbikes, "null");
			sm.addStationFleet(k+1, nbikes/nslots, "Mechanic");
		}
		return sm;
	}
	
	public final static void run() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Bienvenue dans l'interface myVelib");
		String str = sc.nextLine();
		String values[] = str.split(" ");
		
		Simulation sm = null;
		
		
		if (values[0].equals("setup" )) {
			if (values.length == 2 ) {
				try{
					sm = setup(values[1], 10,10,4f,7 );
					System.out.println("Simulation crée!");
				}
				catch (Exception e) {
					e.printStackTrace();
					System.out.println(values[0]+" : mauvais type d'argument");
				}
			}
			else if (values.length == 6) {
				try{
					sm = setup(values[1],Integer.parseInt(values[2]),Integer.parseInt(values[3]),Float.parseFloat(values[4]),Integer.parseInt(values[5]));
					System.out.println("Simulation crée!");
				}
				catch (Exception e) {
					System.out.println(values[0]+" : mauvais type d'argument");
				}
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0].equals("addUser")) {
			if (values.length == 3) {
				if (values[2].equals("vmax")){
					sm.addUser(new User(values[1],new Coordinate(0,0),null,new VmaxCard()));
					System.out.println("Utilisateur ajouté!");
				}
				else if (values[2].equals("vlibre")) {
					sm.addUser(new User(values[1],new Coordinate(0,0),null,new VlibreCard()));
				}
				else if (values[2].equals("none")) {
					sm.addUser(new User(values[1],new Coordinate(0,0),null,null));
					System.out.println("Utilisateur ajouté!");
				}
				else {
					System.out.println(values[0]+" : mauvais type d'arguments");
					System.out.println("Utilisateur ajouté!");
				}
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");				
			}
		}
		
		else if (values[0].equals("offline")) {
			if (values.length == 2) {
				sm.setStationOnline(Integer.parseInt(values[1]), true);
				System.out.println("Station mise offline!");
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0].equals("online")) {
			if (values.length == 2) {
				sm.setStationOnline(Integer.parseInt(values[1]), false);
				System.out.println("Station mise online!");
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0].equals("rentBike")) {
			if (values.length == 4) {
				sm.takeBike(Integer.parseInt(values[1]),Integer.parseInt(values[2]), "mechanical", Integer.parseInt(values[3]));
				System.out.println("Vélo loué!");
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0].equals("returnBike")) {
			if (values.length == 4) {
				sm.returnBike(Integer.parseInt(values[1]),Integer.parseInt(values[2]), Integer.parseInt(values[3]));
				System.out.println("Vélo rendu!");
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0].equals("displayStation")) {
			if (values.length == 2) {
				sm.getStationBalance(Integer.parseInt(values[1]));
			}
			else {
				System.out.println(values[0]+" : mauvais nombre d'arguments");
			}
		}
		
		else if (values[0].equals("displayUser")) {
			if (values.length == 2) {
				sm.getUserBalance(Integer.parseInt(values[1]));
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

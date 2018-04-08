package CLUI;

import java.util.Arrays;
import java.util.Scanner;

public class CLUI {
	
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
				
			}
		}
		
		else if (values[0] == "addUser") {
			if (values.length == 4) {
				
			}
			else {
				
			}
		}
		
		else if (values[0] == "offline") {
			
		}
		
		else if (values[0] == "online") {
			
		}
		
		else if (values[0] == "rentBike") {
			
		}
		
		else if (values[0] == "returnBike") {
			
		}
		
		else if (values[0] == "displayStation") {
			
		}
		
		else if (values[0] == "displayUser") {
			
		}
		
		else if (values[0] == "sortStation") {
			
		}
		
		else if (values[0] == "display") {
			
		}
		
		else {
			System.out.println("\""+str+"\" : "+"commande inconnue");
		}
	}

	public static void main(String[] args) {
		run();
	}
}

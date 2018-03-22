package partOne;

import Exceptions.EmptySlotException;
import Exceptions.FullSlotException;

public class User {

	//Name of the user
	private String name;
		
	//Location of the user
	private float[] location;
	
	//User numerical identifier
	private int id;
	
	//Number which will be used to create the ID
	private static int counter;
	
	//The bike th user uses. Null if the user doesn't have a bike at the moment
	private Bike currentBike;
	
	//Cost of the ongoing ride
	private Cost currentCost;
	
	//Card of the user
	private Card card;
	
	//Time when the user takes the bike
	private int time;

	
	public User() {
		this.name = "Default";
		this.location = new float[] {0,0};
		this.id = counter;
		counter++;
		this.currentBike = null;
		this.card = null;
		updateCost();
	}
	
	public User(String name) {
		this.name = name;
		this.location = new float[] {0,0};
		this.id = counter;
		counter++;
		this.currentBike = null;
		this.card = null;
		updateCost();
	}
	
	
	//Setters
	public void setName(String name) {
		this.name = name;
	}
	public void setLocation(float[] location) {
		this.location = location;
	}	
	public void setCard(Card card) {
		this.card = card;
		this.updateCost();
	}
	
	
	//Getters
	public String getName() {
		return name;
	}
	public float[] getLocation() {
		return location;
	}
	public int getId() {
		return id;
	}
	public Bike getCurrentBike() {
		return currentBike;
	}
	public Cost getCurrentCost() {
		return currentCost;
	}
	public Card getCard() {
		return card;
	}
	public int getTime() {
		return time;
	}
	
	//Update le cout du ride en cours, change quand le vélo change ou le type de carte change
	public void updateCost() {
		this.currentCost = new Cost(card,currentBike);
	}

	public void takeBike(Bike bike, int time) throws FullSlotException {
		if (currentBike != null) {
			throw new FullSlotException();
		}
		else {
			this.currentBike = bike;
			this.time = time;
			updateCost();
		}
	}
	
	public Bike returnBike() throws EmptySlotException {
		if (currentBike == null) {
			throw new EmptySlotException();
		}
		else {
			Bike b = currentBike;
			currentBike = null;
			return b;
		}
	}


}

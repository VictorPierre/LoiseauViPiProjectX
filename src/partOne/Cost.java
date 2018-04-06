package partOne;

import Exceptions.NoCardException;

public class Cost {

	//Stocke les couts selon la séparation du temps
	private int[] costs;
	
	//Stocke le temps bonus
	private int timeCredit;
	
	public Card card;
	
	
	/**
	 * Construction des coûts horaire en fonction du type de vélo et du type d'utilisateur (card)
	 * @param card
	 * @param bike
	 */
	public Cost(Card card, Bike bike) {
		this.card=card;
		timeCredit = 0;
		if (card == null) { //Couts pour un utilisateur normal (sans carte)
			if (bike ==null) {
				this.costs = new int[] {0,0}; //Cout pr
			}
			else if (bike instanceof ElectricBike) {
				this.costs = new int[] {2,2};
			}
			else {
				this.costs = new int[] {1,1};
			}
			
		}
		
		else if (card instanceof VlibreCard) { //Couts pour un utilisateur de Vlibre
			if (bike instanceof ElectricBike) {
				this.costs = new int[] {1,2};
			}
			else {
				this.costs = new int[] {0,1};
			}
		}

		else {
			if (bike instanceof ElectricBike) { //Couts pour un utilisateur de Vmax
				this.costs = new int[] {0,1};
			}
			else {
				this.costs = new int[] {0,1};
			}
		}
		
	}
	public int[] getCosts() {
		return costs;
	}
	public int getTimeCredit() throws NoCardException{
		if (this.card==null) {
			throw new NoCardException();
		}
		else {return this.timeCredit;}
	}
	
	public void addTimeCredit(int n) throws NoCardException{
		if (this.card==null) {
			throw new NoCardException();
			}
		else {this.timeCredit=this.timeCredit+ n;}
	}
	
	/**
	 * Calcul du cout d'un itinéraire, en fonction du temps de trajet
	 * @param rideDuration
	 * @return
	 */
	public int getRideCost(int rideDuration) {
		int chargedTime = rideDuration - timeCredit;
		int rideCost = 0;
		if (chargedTime <=60) {
			rideCost = costs[0];
			if (rideDuration>60 && this.card!=null) {
				timeCredit -= rideDuration - 60;
			}
		}
		else {
			int chargedHours = (( (((float)chargedTime)/60)==(float)(chargedTime/60) )?chargedTime / 60 :(chargedTime / 60) + 1);
			if (this.card!=null){
				timeCredit = chargedHours*60-chargedTime;
			}
			rideCost = ((chargedHours ==1)?costs[0]:costs[0]+(chargedHours-1)*costs[1]);
		}
		return rideCost;
	}
	
}

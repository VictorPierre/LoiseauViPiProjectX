package partOne;

public class Cost {

	//Stocke les couts selon la séparation du temps
	private int[] costs;
	
	//Stocke le temps bonus
	private int timeCredit;
	
	
	//Plusieurs constructeurs pour s'adapter au changements en fonction du couple (utilisateur/vélo)
	public Cost(Card card, Bike bike) {
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
	public int getTimeCredit() {
		return this.timeCredit;
	}
	
	public void addTimeCredit(int n) {
		this.timeCredit=this.timeCredit+ n;
	}
	
	public int getRideCost(int rideDuration) {
		int chargedTime = rideDuration - timeCredit;
		int rideCost = 0;
		if (chargedTime <=60) {
			rideCost = costs[0];
			if (rideDuration>60) {
				timeCredit -= rideDuration - 60;
			}
		}
		else {
			int chargedHours = (( (((float)chargedTime)/60)==(float)(chargedTime/60) )?chargedTime / 60 :(chargedTime / 60) + 1);
			timeCredit = chargedHours*60-chargedTime;
			rideCost = ((chargedHours ==1)?costs[0]:costs[0]+(chargedHours-1)*costs[1]);
		}
		return rideCost;
	}
	
	public static void main(String[] args) {
		Cost c = new Cost(new VmaxCard(), new ElectricBike());
		System.out.println("Ride cost : " + c.getRideCost(70) + "€");
		System.out.println("Time credit : " + c.getTimeCredit()+"\n");
		System.out.println("Ride cost : " + c.getRideCost(170) + "€");
		System.out.println("Time credit : " + c.getTimeCredit()+"\n");
	}
	
}

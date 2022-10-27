package tp1_exo1;

public class vPark {
	private int capacite;


	
	public static void main(String args[]) throws InterruptedException
	{
		vPark parking = new vPark(4);
		for(int i=0; i < 100; i++)
		{
			new Voiture(parking,i+"").start();
			Thread.sleep((long)Math.random()*5000);
			// à completer
		}
	}
	//************************************************************************
	public vPark() {

	}
	public vPark(int capacite) {
		super();
		this.capacite = capacite;
	}
	
	public synchronized void arriver(String nom)
	{ 
		while(capacite<=0) {
			//Si le parking est plein, la voiture attendra
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		//Sinon , La voiture entrera et nous réduirons la capacité
		capacite--;
		System.out.println("voiture '"+nom+"' arrive .... capacite="+capacite);
	}
	public synchronized void partir(String nom)
	{
		//La voiture part : nous augmentons la capacité et nous notifions les autres voitures
		capacite++;
		System.out.println("voiture '"+nom+"'part .... capacite="+capacite);
		notifyAll();


	}



}

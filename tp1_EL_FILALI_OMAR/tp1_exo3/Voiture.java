package tp1_exo3;

public class Voiture extends Thread{
	private vPark parking;
	private String nom;
	public Voiture (vPark parking,String nom)
	{
	this.parking = parking;
	this.nom=nom;
	}
	@Override
	public void run() {
	 
		try {
			parking.arrive(nom);
			sleep((int)Math.random()*20000);
			parking.partir(nom);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

}

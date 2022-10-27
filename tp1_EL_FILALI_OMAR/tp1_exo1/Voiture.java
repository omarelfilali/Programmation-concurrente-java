package tp1_exo1;

public class Voiture extends Thread{
	private vPark parking;
	private String nom;
	public Voiture (vPark parking,String nom)
	{
	this.parking = parking;
	this.nom=nom;
	}
	public void run()
	{
	parking.arriver(nom);
	try {
		sleep((int)Math.random()*20000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	parking.partir(nom);

	}

}

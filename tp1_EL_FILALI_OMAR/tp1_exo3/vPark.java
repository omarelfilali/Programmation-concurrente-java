package tp1_exo3;

import java.util.concurrent.Semaphore;



public class vPark {
	
	public Semaphore sem=new Semaphore(4);
	public int capacite ;
	
	public vPark() {
		capacite=4;
	}
	public void arrive(String nom) throws InterruptedException {
		
		sem.acquire();
		capacite--;
		System.out.println("voiture '"+nom+"' arrive .... capacite="+capacite);
	}
	
	public void partir(String nom) {
		sem.release();
		capacite++;
		System.out.println("voiture '"+nom+"'part .... capacite="+capacite);
	}
	
	public static void main(String args[]) throws InterruptedException
	{
	
		vPark parking = new vPark();
		for(int i=0; i < 100; i++)
		{
			new Voiture(parking,i+"").start();
			Thread.sleep((long)Math.random()*5000);
		
		}
	}

}

package tp1_exo4;

import java.util.concurrent.Semaphore;

public class chapeau {
	
	private Semaphore Nvide,Nplein/*Pmutex,Cmutex*/;
	private int Nv;//nbr de places vide
	private int Np; //nbr de jeton dans le chapeau
	
	public chapeau( int nv) {
		
		Nv = nv;
		Nvide = new Semaphore(nv);
		Nplein = new Semaphore(0);
	    Np=0;
		
	}
	
	public void produire(int num) throws InterruptedException {
		Nvide.acquire(); //Réserver une place dans le chapeau
		System.out.println("producteur#"+num+": reserve la place d'un jeton......");
		
		Nplein.release(); //deposer le jeton dans le chapeau 
		Np++; //incrementer le nombre de jeton  deposés
		System.out.println("producteur#"+num+" : depose un jeton .. nbr de jeton dans le chapeau: "+ Np);
		
	}
	
	public void consommer(int num) throws InterruptedException {
		Nplein.acquire(); //attendre la consommation d'un jeton 
		
		Nvide.release();//consommer le jeton et liberer la place
		Np--; //decrementer le nbr de jeton
		System.out.println("consommateur #"+num+":  libere la place d'un jeton ...... nbr de jeton dans le chapeau: "+Np);
		
	    	 
	     
		
	}
	
	public static void main(String args[]) throws InterruptedException
	{
		chapeau chap=new chapeau(2);
		
		Producteur P1=new Producteur(chap,1);
		Producteur P2=new Producteur(chap,2);
		Consommateur C1= new Consommateur(chap,1);
		Consommateur C2= new Consommateur(chap,2);
		
		P1.start();
		P2.start();
		C1.start();
		C2.start();
	}
	
	

}

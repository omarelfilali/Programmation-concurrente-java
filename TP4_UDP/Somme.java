package TP4_UDP;

import java.util.List;
/*La classe somme nous permet d'enregistrer le port de chaque client et le nombre 
qu'il a envoyé au serveur ,de cette manière s'il envoie 0 nous calculons la somme des nombres de la liste*/
public class Somme {
	int port;
	List<Long> opérandes;
	
	public Somme(int port, List<Long> opérandes) {
		super();
		this.port = port;
		this.opérandes = opérandes;
	}
	
	long sum () {
		long s=0;
		for(long n:this.opérandes) {
			s+=n;
			}
		return s;
	}
	

}

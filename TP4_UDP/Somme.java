package TP4_UDP;

import java.util.List;
/*La classe somme nous permet d'enregistrer le port de chaque client et le nombre 
qu'il a envoy� au serveur ,de cette mani�re s'il envoie 0 nous calculons la somme des nombres de la liste*/
public class Somme {
	int port;
	List<Long> op�randes;
	
	public Somme(int port, List<Long> op�randes) {
		super();
		this.port = port;
		this.op�randes = op�randes;
	}
	
	long sum () {
		long s=0;
		for(long n:this.op�randes) {
			s+=n;
			}
		return s;
	}
	

}

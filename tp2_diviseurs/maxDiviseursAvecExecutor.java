package tp2_diviseurs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class maxDiviseursAvecExecutor {
	private final static int MAX = 25000;

	//Une classe pour représenter le résultat d'une sous-tâche.
	private static class Resultat {
		int maxDiviseurParSousTache;  // Nombre maximal de diviseurs.
		int nombreAvecMaxSousTache;  // Quel entier a donné ce nombre maximal.
		//
		//
		public Resultat(int maxDiviseurParSousTache, int nombreAvecMaxSousTache) {
			this.maxDiviseurParSousTache = maxDiviseurParSousTache;
			this.nombreAvecMaxSousTache = nombreAvecMaxSousTache;
		}
	}


	private static class sousTache implements Callable<Resultat>{
		int min, max; // début et fin de la plage des entiers à traiter
		//La sous-tâche est exécutée lorsque la méthode call() est appelée
		public sousTache(int min, int max) {
			super();
			this.min = min;
			this.max = max;
		}
		public Resultat call() {
			int N;            
			int maxDiviseursTh=0;  
			int nombreAvecMaxTh=0;   



			for ( N = min;  N <= max;  N++ ) {

				int nbrDiviseurs=diviseurCompteur(N);
				if(nbrDiviseurs>maxDiviseursTh) {
					maxDiviseursTh=nbrDiviseurs;
					nombreAvecMaxTh=N;   
				}
			}

			return new Resultat(maxDiviseursTh,nombreAvecMaxTh);
		}
	} 


	private static void diviseurCompteurAvecExecutor(int nombreDeThread) throws InterruptedException, ExecutionException {

		// Créer l'ExecutorService et un ArrayList pour stocker les Futures

		long tDebut = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(nombreDeThread);

		ArrayList<Future<Resultat>> resultats = new ArrayList<>();

		// Créer les sous-tâches et ajouter-les à l'executor. Chaque sous-tâche traite une plage de 1000 entiers.
		int nombreDeTaches=MAX/1000;
		List<sousTache> listTaches=new ArrayList<>();
		for (int i = 0; i < nombreDeTaches; i++) {

			// Soumettre la sous-tâche à l'excutor--> retourne un objet de type Future
			// Ajouter l'objet de type Future dans ArrayList

			sousTache st= new sousTache(i*1000+1,(i+1)*1000);
			Future<Resultat> r= executor.submit(st);
			resultats.add(r);


		}

		// Au fur et à mesure que l'excutor exécute les tâches, les résultats deviennent disponibles dans les Futures qui sont stockés dans l'ArrayList. 
		// Obtenir les résultats et combiner-les pour produire le résultat final
		//
		Resultat finalresult=new Resultat(0,0);
		for (Future<Resultat> res : resultats) {
			Resultat temp=res.get();
			if(temp.maxDiviseurParSousTache>finalresult.maxDiviseurParSousTache) {
				finalresult.maxDiviseurParSousTache=temp.maxDiviseurParSousTache;
				finalresult.nombreAvecMaxSousTache=temp.nombreAvecMaxSousTache;
			}
		}


		long tempsEcoule = System.currentTimeMillis()-tDebut;

		// Imprimer le résultat final 
		 System.out.println("Parmi les nombres entiers compris entre 1 et "+MAX+" le nombre maximum de "
	         		+ "diviseurs est " + finalresult.maxDiviseurParSousTache +". Le plus petit nombre avec "+finalresult.maxDiviseurParSousTache +" diviseurs\r\n"
	         		+ "est "+finalresult.nombreAvecMaxSousTache+" ");
	         System.out.println("Temps total �coul� :"+tempsEcoule+" mili seconde(s)");

		// terminir l'executor.    
		executor.shutdown();
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Scanner clavier = new Scanner(System.in);
		
		// demander à l'utilisateur le nombre de threads dans le ThreadPool (entre 1 et 100)
		System.out.println("entrez le nombre de threads (entre 1 et 100) : ");
		int nombreDeThread =clavier.nextInt();
		diviseurCompteurAvecExecutor(nombreDeThread);
	}


	public static int diviseurCompteur(int N) {
		// Calculer le nombre de diviseurs pour un entier donnée N
		int nbrDiviseurs=1;
		for(int i=2;i<=N;i++) {
			if(N%i==0) {
				nbrDiviseurs++;   
			}	   
		}
		return nbrDiviseurs;
	}
} 

package tp2_diviseurs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class maxDiviseursAvecThreadPool {
    private static ConcurrentLinkedQueue<sousTache> tacheQueue;
    private static LinkedBlockingQueue<Resultat> resultatQueue;
    
    private final static int MAX = 25000;
    
    private static class sousTache {
        int min, max; // début et fin de la plage des entiers à traiter
        
        public sousTache(int min, int max) {
			super();
			this.min = min;
			this.max = max;
		}
        public void calcul() {
        	// Effectuer le calcul puis ajouter le résultat dans la queue resultatQueue avec:
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
        	
            resultatQueue.add( new Resultat(maxDiviseursTh, nombreAvecMaxTh));
			//
        }
		
    }
    
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

    private  static class diviseurCompteurThread extends Thread {
    	
        public void run() {
            while (true) {
                // Récupérer la sous-tâche à exécuter avec tacheQueue.poll()
            	if(tacheQueue.isEmpty()){
                    break;
                }
            	
				// effectuer le calcul sousTache.calcul()
            	tacheQueue.poll().calcul();
            }
        }
    }

    private static void diviseurCompteurAvecThreadPool(int numberOfThreads) throws InterruptedException {
    	int maxDiviseurs = 1;
    	int nombreAvecMax=1;
        
		long tDebut = System.currentTimeMillis();
		
		resultatQueue = new LinkedBlockingQueue<Resultat>();
        tacheQueue = new ConcurrentLinkedQueue<sousTache>();
        
		// Créer une liste de threads de type diviseurCompteurThread et de taille nombreDeThread
        diviseurCompteurThread th[]=new diviseurCompteurThread[numberOfThreads];
        for(int i=0;i<th.length;i++) {
        	th[i]=new diviseurCompteurThread();
        	
        }

        // Créer un certain nombre des sous-tâches, chaque sous-tâches s'occupe d'un certains nombre d'entiers 1000 par exemple.
       int nbrDePlages=MAX/1000;
       List<sousTache> listeTachs=new ArrayList<>();
       for(int i=0;i<nbrDePlages;i++) {
    	   sousTache st=new sousTache(i*1000+1, (i+1)*1000);
    	   listeTachs.add(st);
       }
        
		// ajouter les sous-tâches à tacheQueue --> tacheQueue.add(new sousTache(debut,fin));
        for(sousTache st:listeTachs) {
        	tacheQueue.add(st);
        }
		
        // Démmarer les nombreDeThread threads --> Les threads exécuteront les tâches et les résultats seront placés dans la Queue des résultats resultatQueue.
        for(int i=0;i<th.length;i++) {
        	th[i].start();
        }
        
		// Calculer le résultat final --> lire tous les résultats de la Queue des résultats et combinez-les pour donner la réponse finale.
        for (int i = 0; i < nbrDePlages; i++) {
        	
            Resultat resultat = resultatQueue.take();
            
            if(resultat.maxDiviseurParSousTache>maxDiviseurs) {
            	maxDiviseurs=resultat.maxDiviseurParSousTache;
            	nombreAvecMax=resultat.nombreAvecMaxSousTache;
            }
	
        }
		
		long tempsEcoule = System.currentTimeMillis()-tDebut;

		// Imprimer le résultat final     
		 System.out.println("Parmi les nombres entiers compris entre 1 et "+MAX+" le nombre maximum de "
	         		+ "diviseurs est " + maxDiviseurs +". Le plus petit nombre avec "+maxDiviseurs +" diviseurs\r\n"
	         		+ "est "+nombreAvecMax+" ");
	         System.out.println("Temps total �coul� :"+tempsEcoule+" mili seconde(s)");
		
		
	         
    }

    public static void main(String[] args) throws InterruptedException {
  	  System.out.println("entrez le nombre de threads : ");
      Scanner clavier = new Scanner(System.in);
      // demander à l'utilisateur le nombre de threads dans le ThreadPool (entre 1 et 100)
      int nombreDeThread=clavier.nextInt();
      diviseurCompteurAvecThreadPool(nombreDeThread);
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
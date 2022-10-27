package tp2_diviseurs;

import java.util.Scanner;

public class maxDiviseursAvecThreads {

   private final static int MAX = 25000;
   
   private volatile static int maxDiviseurs = 1;
   
   private volatile static int nombreAvecMax=1;
   
   private static  synchronized void  combinerResultat(int maxDiviseurParThread, int nombreAvecMaxParThread) {
      // Combiner les résultats de différents threads
	   if(maxDiviseurParThread>maxDiviseurs) {
		   maxDiviseurs=maxDiviseurParThread;
		   nombreAvecMax=nombreAvecMaxParThread;
	   }
	   
   }
   
   private static class diviseurCompteurThread extends Thread {
	   
     

	int min, max;
	 public diviseurCompteurThread(int min, int max) {
			super();
			this.min = min;
			this.max = max;
		}

      public void run() {
        // Calculer le nombre maximum de diviseurs pour les entiers entre min et max : maxDiviseursTh,nombreAvecMaxTh
    	  int N;            
          int maxDiviseursTh=1;  
          int nombreAvecMaxTh=1;   
       		
       		   
          
          for ( N = min;  N <= max;  N++ ) {
               
       	   int nbrDiviseurs=diviseurCompteur(N);
       	   if(nbrDiviseurs>maxDiviseursTh) {
       		maxDiviseursTh=nbrDiviseurs;
       		nombreAvecMaxTh=N;
       		   
       	   }
       	   }
              
          
 
         combinerResultat(maxDiviseursTh,nombreAvecMaxTh);
      }
   }
   
   
   private static void diviseurCompteurAvecThread(int nombreDeThread) {
      long tDebut = System.currentTimeMillis();
      diviseurCompteurThread[] th = new diviseurCompteurThread[nombreDeThread];
    
      // A compléter
      //calculer la taille de domaine ;
      int TailleDomaine=MAX/nombreDeThread;
      for (int i = 0; i < nombreDeThread; i++) {
    	  int min=i*TailleDomaine+1;
    	  int max=(i+1)*TailleDomaine;
          th[i]= new diviseurCompteurThread(min, max);
      }
       
      // Lancer les threads
      for(int i=0;i<nombreDeThread;i++) {
    	  th[i].start();
      }
      
      for (int i = 0; i < nombreDeThread; i++) {
        // Attendez que chaque thread soit mort
    	  while(th[i].isAlive());
    	
      }
      
      long tempsEcoule =(System.currentTimeMillis()-tDebut);

      // Imprimer le résultat final  
      System.out.println("Parmi les nombres entiers compris entre 1 et "+MAX+" le nombre maximum de "
         		+ "diviseurs est " + maxDiviseurs +". Le plus petit nombre avec "+maxDiviseurs +" diviseurs\r\n"
         		+ "est "+nombreAvecMax+" ");
         System.out.println("Temps total �coul� :"+tempsEcoule+" mili seconde(s)");
   }
   
   public static void main(String[] args) {
	  System.out.println("entrez le nombre de threads : ");
      Scanner clavier = new Scanner(System.in);
      // demander à l'utilisateur un nombre de threads entre 1 et 100
      int nombreDeThread=clavier.nextInt();
      diviseurCompteurAvecThread(nombreDeThread);
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

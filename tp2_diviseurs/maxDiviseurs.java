package tp2_diviseurs;

import java.sql.Date;

public class maxDiviseurs {

   public static void main(String[] args) {
   
	   final int MAX = 25000;

	   int N;            
       int maxDiviseurs=1;  
       int nombreAvecMax=1;   
       
      long tDebut =System.currentTimeMillis();
    		
    		   
       
       for ( N = 2;  N <= MAX;  N++ ) {
            
    	   int nbrDiviseurs=1;
    	   for(int i=2;i<=N;i++) {
    		   if(N%i==0) {
    			   nbrDiviseurs++;   
    		   }	   
    	   }
    	   if(nbrDiviseurs>maxDiviseurs) {
    		   maxDiviseurs=nbrDiviseurs;
    		   nombreAvecMax=N;
    		   
    	   }else if(nbrDiviseurs==maxDiviseurs) {
    		   if(N<nombreAvecMax) {
    			   nombreAvecMax=N;
    		   }
    	   }
           
    	   
    	   // Calculer le nombre de diviseurs de N.
		   // Mettre Ã  jour maxDiviseurs et nombreAvecMax si nÃ©cessaire

       
       }
       long tempsEcoule = System.currentTimeMillis()-tDebut;
       
       //imprimer le rÃ©sultat
       System.out.println("Parmi les nombres entiers compris entre 1 et "+MAX+" le nombre maximum de "
       		+ "diviseurs est " + maxDiviseurs +". Le plus petit nombre avec "+maxDiviseurs +" diviseurs\r\n"
       		+ "est "+nombreAvecMax+" ");
       System.out.println("Temps total écoulé :"+tempsEcoule+"mili seconde(s)");
   
   } 

}
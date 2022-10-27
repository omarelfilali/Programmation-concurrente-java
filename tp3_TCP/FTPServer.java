package tp3_TCP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {
	/*pour executer le server en ligne de commande :
	javac FTPServer.java 
	java FTPServer.java 5000    */
	

	private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    private static ServerSocket serverSocket ;
    private static Socket clientSocket ; 
    private static BufferedReader inFromClient ;;
    private static PrintWriter outToClien;
    
    public FTPServer(int port) {
    	  try{
          	  serverSocket = new ServerSocket(port);
              System.out.println("listening to port:"+port);
              clientSocket = serverSocket.accept();
              System.out.println(clientSocket+" connected.");
              dataInputStream = new DataInputStream(clientSocket.getInputStream());
              dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
               inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
               outToClien = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
  			
  			
                String ClientRequest="";
               
                while(true) {
          	    ClientRequest = inFromClient.readLine();
          	    //Si la requette reçue du client commence par get
          	    if(ClientRequest.startsWith("get")) {
          	    	System.out.println("Recu de client la requete :" + ClientRequest);
      				String file_name=ClientRequest.replaceAll("get", "");
      				file_name=file_name.replaceAll(" ", "");
      				System.out.println(file_name);
      				sendFile(file_name);
          	    }
          	    //Si la requette reçue du client commence par put
          	    else if(ClientRequest.startsWith("put")) {
          	    	String file_name=ClientRequest.replaceAll("put", "");
                 	 file_name=file_name.replaceAll(" ", "");
                 	 System.out.println(file_name);
                 	 receiveFile(file_name);
                 	
                 	 
          	    }
          	  //Si la requette reçue est LS
          	    else if(ClientRequest.equals("ls")) {
          	    	File dir  = new File("C:\\Users\\asus\\eclipse-workspace\\Programmation_reseau\\src\\tp3_TCP\\SERVEUR_DIR");
          	        File[] liste = dir.listFiles();
          	        System.out.println("Les fichiers dans le dossier du serveur sont : ");
          	        //la liste des fichier pou l'envoyer au client
          	      String l = "";
          	        for(File item : liste){
          	          if(item.isFile())
          	          { l+=item.getName()+"  ";
          	        	  
          	            System.out.format("Nom du fichier: %s%n", item.getName()); 
          	          } 
          	    }
          	      outToClien.println(l);
          	    outToClien.flush();
          	    
          	        
          	  outToClien.println("l’upload du fichier sur le serveur a bien été effectué");
          	outToClien.flush();
          	    
                
          	    }}
  				
  				
  				
  			
              
              
              
              
              
           
             
          } catch (Exception e){
              e.printStackTrace();
          }
	}
    public static void main(String[] args) {
    	if (args.length < 1) {
            System.out.println("java ServerFTP <PORT>");
            return;
        }
    	new FTPServer(Integer.parseInt(args[0]));
      
    }

    // methode pour recevoir un ficher du client
    private  static void receiveFile(String fileName) throws Exception{
        int bytes = 0;
        
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\asus\\eclipse-workspace\\Programmation_reseau\\src\\tp3_TCP\\SERVEUR_DIR\\"+fileName);
        //Lire la taille du fichier
        long size = dataInputStream.readLong();    
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            //lire jusqu'à la taille du fichier
            size -= bytes;     
        }
        
        outToClien.println("l’upload du fichier sur le serveur a bien été effectué");
        outToClien.flush();
        System.out.println("recieve file"+ fileName + " from client");
        fileOutputStream.close();
    }
    
    
    
    // methode pour envoyer un ficher au client
    private  static void sendFile(String fileName) throws Exception{
        int bytes = 0;
        
        File file = new File("C:\\Users\\asus\\eclipse-workspace\\Programmation_reseau\\src\\tp3_TCP\\SERVEUR_DIR\\"+fileName);
        
        FileInputStream fileInputStream = new FileInputStream(file);
       
        // on va envoiyer la taille du ficher 
        dataOutputStream.writeLong(file.length());  
        // diviser le fichier en morceaux
        byte[] buffer = new byte[4*1024];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            dataOutputStream.write(buffer,0,bytes);
            dataOutputStream.flush();
        }
        System.out.println("send file"+ fileName +" to client");
        fileInputStream.close();
        
    }
}
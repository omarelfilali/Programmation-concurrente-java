package tp3_TCP;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class FTPClient {
	/*pour executer le Client en ligne de commande :
	javac FTPClient.java 
	java FTPClient.java localhost 5000 Downloads1   
	
	ls
	get text1.txt
	put text2.txt
	
		*/
	private static Socket socket;
    
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    private static ServerSocket serSocket ;
    private static BufferedReader inFromUser ;
    private static PrintWriter   outToServer ;
    private static BufferedReader  infromServer ;
    private static String directory;

    
    
    
    public FTPClient(String serverIP, int serverPort, String directory) {
    
    	 try{
         	socket= new Socket(serverIP,serverPort);
         	
         	
  	     
  	   
         	   this.directory=directory ;
             dataInputStream = new DataInputStream(socket.getInputStream());
             dataOutputStream = new DataOutputStream(socket.getOutputStream());
              inFromUser = new BufferedReader(new InputStreamReader(System.in));
              outToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
              infromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             String str="";
             while(true) {	
             System.out.println("Entrez la requete : ");
              str = inFromUser.readLine();
              //si le client a entré une requette commence par get
             	if(str.startsWith("get")) {
             		 outToServer.println(str);
                 	 outToServer.flush();
                 	 String file_name=str.replaceAll("get", "");
                 	 file_name=file_name.replaceAll(" ", "");
                 	 receiveFile(file_name);
             	}
             	 //si le client a entré une requette commence par put
             	else if(str.startsWith("put")) {
             		outToServer.println(str);
                	    outToServer.flush();
     				String file_name=str.replaceAll("put", "");
     				file_name=file_name.replaceAll(" ", "");
     				sendFile(file_name);
         	    }
             	 //si le client a entré LS
             	else if(str.equals("ls")) {
         	    	
         	    	    outToServer.println(str);
                	    outToServer.flush();
                	    System.out.println(infromServer.readLine());
                	    
         	    }
             	else {
             		System.out.println("la requete n'existe pas ... essayer à nouveau ");
             	}}
             	
  	      }
      
         catch (Exception e){
             e.printStackTrace();
         }
	      }
	
    
    // methode pour envoyer un ficher au Serveur 
    private  static void sendFile(String fileName) throws Exception{
        int bytes = 0;
        File file = new File("C:\\Users\\asus\\eclipse-workspace\\Programmation_reseau\\src\\tp3_TCP\\"+directory+"\\"+fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        
        // send file size
        dataOutputStream.writeLong(file.length());  
        // break file into chunks
        byte[] buffer = new byte[4*1024];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            dataOutputStream.write(buffer,0,bytes);
            dataOutputStream.flush();
            
           
        }
        infromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message=infromServer.readLine();
        System.out.println(message);
        fileInputStream.close();
        
    }
    
    
    // methode pour recevoir un ficher du Serveur
    private  static void receiveFile(String fileName) throws Exception{
        int bytes = 0;
        
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\asus\\eclipse-workspace\\Programmation_reseau\\src\\tp3_TCP\\"+directory+"\\"+fileName);
         // read file size
        long size = dataInputStream.readLong(); 
       
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        }
        fileOutputStream.close();
        
    }
    public static void main(String[] args) throws IOException {
    	
    	 if (args.length != 3) {
             System.out.println("java ClientFTP <host> <port> <dir>");
             return;
         }
    	FTPClient t=new FTPClient(args[0], Integer.parseInt(args[1]), args[2]) ;
       
    }
}
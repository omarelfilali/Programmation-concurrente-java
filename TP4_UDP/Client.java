package TP4_UDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.ByteBuffer;
import java.security.Timestamp;

import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

public class Client {
	
	private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);    

    public static byte[] longToBytes(long x) {
    	buffer.clear();
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
    	buffer.clear();
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip 
        return buffer.getLong();
    }
	
    
    
    //
	public static class ReadFromServer extends Thread
	{
		public boolean active;
		public DatagramSocket datagramSocket;
		public ReadFromServer(DatagramSocket socket)
		{
			datagramSocket = socket;
			active = true;
		}
	public void run()
	{
		byte[] buffer = new byte[8];
		DatagramPacket incoming = new DatagramPacket(buffer,buffer.length);
		String receivedString;
		while(true)
		{
			try
			{
				// listen for incoming datagram packet
				datagramSocket.receive(incoming);
				
				//convertir la somme recue long
				long somme=bytesToLong(incoming.getData());
				
				//afficher la somme 
				System.out.println("Sum Received from server:"+somme);
			}
			catch(IOException e)
			{
				System.out.println(e);
				
			}
		}
	}

	}
	public static void main(String[] args)
	{
		InetAddress address = null;
		int port = 5000;
		DatagramSocket datagramSocket = null;
		BufferedReader keyboardReader = null;
		// Create a Datagram Socket...
		try
		{
			address = InetAddress.getByName("localhost");
			datagramSocket = new DatagramSocket();
			keyboardReader = new BufferedReader(new InputStreamReader(System.in));
		}
		catch (IOException e)
		{
			System.out.println(e);
			System.exit(1);
		}
		// Start the listening thread...
		ReadFromServer reader = new ReadFromServer(datagramSocket);
		reader.start();
		System.out.println("Ready to send your messages...");
		try
		{
			System.out.println("entrez les nombres que vous voulez (si vous voulez la somme entrez 0) : ");
			while (true)
			{
				// read input from the keyboard
			     Scanner sc=new Scanner(System.in);
			     long nbr=sc.nextLong();
			     //convertir l input en bytes
			     byte[] a=longToBytes(nbr);
			     //envoyer au server
				 DatagramPacket datagramPacket = new DatagramPacket (a,a.length, address, port);
				 datagramSocket.send(datagramPacket);
				 System.out.println(" "+nbr +" a envoyé au serveur .... autre !");
				 
				
			}
		
		   
			
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
}

package TP4_UDP;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Server {
	
private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);    
static List<Somme> listSommes =new ArrayList<Somme>();

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

	public static void main(String args[])
	{
		int port = 5000;
		// create the server...
		DatagramSocket serverDatagramSocket = null;
		try
		{
			serverDatagramSocket = new DatagramSocket(port);
			System.out.println("UDP  Server on port "+port);
		}
		catch(IOException e)
		{
			System.out.println(e);
			System.exit(1);
		}
		try
		{
			byte buffer[] = new byte[8];
			DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
			long input;
			
			
			
			while(true)
			{
				serverDatagramSocket.receive(datagramPacket);
				//tester si le port existe dans la liste ou non
				int test=0;
				Long sommeToclient=(long) 0;
				byte[] outToClient;
				DatagramPacket datagramPacketToSend;
				for(Somme s:listSommes) {
					if(s.port==datagramPacket.getPort()) {
						//si le port existe deja dans notre liste,on l'ajoute dans la liste et  on ajoute le nombre dans la liste des nombre 
						test++;
						if(bytesToLong(datagramPacket.getData())!=0){
							
							s.opérandes.add(bytesToLong(datagramPacket.getData()));
						}
						else {
							//call sum methode to calculate the sum of client's list numbers
							long sommetoclient=s.sum();
						
							outToClient=longToBytes(sommetoclient);
							datagramPacketToSend=new DatagramPacket(outToClient, outToClient.length,datagramPacket.getAddress(),datagramPacket.getPort());
							serverDatagramSocket.send(datagramPacketToSend);
						}
					}
				}
				
				
				//si le port n'existe pas dans la liste
				if(test==0) {
						List<Long> l=new ArrayList<Long>();
						l.add(bytesToLong(datagramPacket.getData()));
						Somme s=new Somme(datagramPacket.getPort(),l);
						listSommes.add(s);
						if(bytesToLong(datagramPacket.getData())==0) {
							long sommetoclient=0;
							outToClient=longToBytes(sommetoclient);
							datagramPacketToSend=new DatagramPacket(outToClient, outToClient.length,datagramPacket.getAddress(),datagramPacket.getPort());
							serverDatagramSocket.send(datagramPacketToSend);
						}
					
				}
			
				
				
			
			
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}


}
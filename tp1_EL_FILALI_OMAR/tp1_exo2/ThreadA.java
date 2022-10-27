package tp1_exo2;

import java.util.concurrent.Semaphore;

public class ThreadA extends Thread{

	public Semaphore semA;
	public Semaphore semB;
	public ThreadA(Semaphore semA,Semaphore semB) {
		 this.semA = semA;
		 this.semB = semB;
		 }

	@Override
	public void run() {
		
		
		for (int i = 0; i < 5; i++) {
			 try {
				semA.acquire();
				System.out.print("A");
				ThreadA.sleep((long) (Math.random()*1000));
				semB.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 } 
	} 

	
	
	
}

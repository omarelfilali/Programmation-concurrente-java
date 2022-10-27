package tp1_exo2;

import java.util.concurrent.Semaphore;

public class ThreadB extends Thread{

	public Semaphore semB ;
	public Semaphore semC;
	public ThreadB(Semaphore semB,Semaphore semC) {
		super();
		this.semB = semB;
		this.semC = semC;
	}

	@Override
	public void run() {
		
		
		for(int i=0;i<5;i++) {
			
			try {
				
				semB.acquire();
				System.out.print("B");
				ThreadA.sleep((long) (Math.random()*1000));
				semC.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}

package tp1_exo2;

import java.util.concurrent.Semaphore;

public class ThreadC extends Thread{
	public Semaphore semC ;
	public Semaphore semA ;

	public ThreadC(Semaphore semC,Semaphore semA) {
		super();
		this.semC = semC;
		this.semA = semA;
	}

	@Override
	public void run() {
	
		
		for(int i=0;i<5;i++) {
			
			try {
				semC.acquire();
				System.out.print("C");
				ThreadA.sleep((long) (Math.random()*1000));
				semA.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}

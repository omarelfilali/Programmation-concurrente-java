package tp1_exo2;

import java.util.concurrent.Semaphore;

public class testSemaphore {

	public static void main(String[] args) throws InterruptedException {
		Semaphore semA = new Semaphore(1);
		Semaphore semB = new Semaphore(0);
		Semaphore semC = new Semaphore(0);
		
		
		
		 new ThreadA(semA,semB).start();
		 new ThreadB(semB,semC).start();
		 new ThreadC(semC,semA).start();

	}

}

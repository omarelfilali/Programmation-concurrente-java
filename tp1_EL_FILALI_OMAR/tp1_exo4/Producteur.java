package tp1_exo4;

import java.util.Random;

public class Producteur extends Thread{
	private chapeau c;
	private int num;


	public Producteur(chapeau c,int num) {
		this.c = c;
		this.num=num;
	}


	@Override
	public void run() {
		while(true) {
			try {
				Random random = new Random();
				Thread.sleep(random.nextInt(5) * 2000);
				c.produire(num);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
	}




}

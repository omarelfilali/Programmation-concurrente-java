package tp1_exo4;

import java.util.Random;

public class Consommateur extends Thread{
private chapeau c;
private int num;
	

	public Consommateur(chapeau c,int n) {
		this.c = c;
		this.num=n;
	}


	@Override
	public void run() {
		while(true){
		try {
			
			c.consommer(this.num);
			Random random = new Random();
			Thread.sleep(random.nextInt(5) * 2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}}

	
}

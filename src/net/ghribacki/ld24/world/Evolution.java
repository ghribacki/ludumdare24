package net.ghribacki.ld24.world;

import java.util.Random;

public class Evolution implements Runnable {
	private Terrain terrain;
	private Random random;
	
	public Evolution(Terrain terrain) {
		this.terrain = terrain;
		this.random = new Random();
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			int i = this.random.nextInt(32);
			int j = this.random.nextInt(32);
			
			int type = this.random.nextInt(2);
			
			terrain.setCell(i, j, type);
		}
	}

}

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
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < this.terrain.getSize(); i++) {
				for (int j = 0; j < this.terrain.getSize(); j++) {
					int x = this.random.nextInt(32);
					int y = this.random.nextInt(32);
					int type = this.random.nextInt(2);
					this.terrain.setCell(i, j, x, y, type);
				}
			}
		}
	}

}

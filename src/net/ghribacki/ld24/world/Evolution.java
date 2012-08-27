package net.ghribacki.ld24.world;

import java.util.List;
import java.util.Random;

import net.ghribacki.ld24.entity.Turret;

public class Evolution implements Runnable {
	private Planet planet;
	
	Random random;
	
	private final int TURRET_PROLIFERATION_RATE = 5;
	private int proliferationTimer;
	
	private final int COMET_RATE = 5;
	private int cometTimer;
	
	public Evolution(Planet terrain) {
		this.planet = terrain;
		this.random = new Random();
	}
	
	@Override
	public void run() {
		this.planet.spawnStar(10, 10);
		this.planet.spawnStar(10, 20);
		this.planet.spawnStar(20, 20);
		this.planet.spawnStar(20, 10);
		
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			int n = 32;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					int current = this.planet.getCell(i, j);

					int north = (this.planet.getCell((i+n-1)%n, j) > 0) ? 1 : 0;
					int south = (this.planet.getCell((i+1)%n, j) > 0) ? 1 : 0;
					int east = (this.planet.getCell(i, (j+n-1)%n) > 0) ? 1 : 0;
					int west = (this.planet.getCell(i, (j+1)%n) > 0) ? 1 : 0;
					
					int neighborhood = north + south + east + west;
					
					if (current == 0) {
						if (neighborhood == 3) {
							this.planet.setCell(i, j, 1);
						}
					} else if (current > 0) {
						if ((neighborhood < 2) || (neighborhood > 3)) {
							this.planet.setCell(i, j, 0);
						}
					}
				}
			}
			this.planet.refresh();
			
			this.proliferationTimer++;
			if (this.proliferationTimer == this.TURRET_PROLIFERATION_RATE) {
				this.proliferationTimer = 0;
				List<Turret> turrets = this.planet.getTurrets();
				for (Turret turret : turrets) {
					int i = (int)turret.position.x;
					int j = (int)turret.position.z;
					if (this.planet.getCell(i, j) == 2) {
						boolean north = this.planet.getCell((i+n-1)%n, j) == 1;
						boolean south = this.planet.getCell((i+1)%n, j) == 1;
						boolean east = this.planet.getCell(i, (j+n-1)%n) == 1;
						boolean west = this.planet.getCell(i, (j+1)%n) == 1;
						
						if (north) {
							this.planet.spawnTurret((i+n-1)%n, j);
						}
						if (south) {
							this.planet.spawnTurret((i+1)%n, j);
						}
						if (east) {
							this.planet.spawnTurret(i, (j+n-1)%n);
						}
						if (west) {
							this.planet.spawnTurret(i, (j+1)%n);
						}
					}
				}
				this.planet.refreshTurrets();
			}
			
			this.cometTimer++;
			if (this.cometTimer == this.COMET_RATE) {
				int chance = this.random.nextInt(100);
				this.cometTimer = 0;
				
				if (chance < 100) {
					int x = this.random.nextInt(32);
					int y = this.random.nextInt(32);
					this.planet.spawnStar(x, y);
				}
			}
		}
	}
}

package net.ghribacki.ld24.world;

public class Terrain {
	private Chunk map[][];
	private int size;
	
	private Thread evolutionThread;
	
	public final int CELL_PLAIN = 0;
	public final int CELL_MOUNTAIN = 1;
	public final int CELL_WATER = 2;
	
	public Terrain(int size) {
		this.size = size;
		
		this.map = new Chunk[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.map[i][j] = new Chunk(32);
			}
		}
	}
	
	public void update() {
		// TODO Game of life thread!
	}
	
	public void render() {
		this.renderCells();
	}
	
	private void renderCells() {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.map[i][j].render();
			}
		}
	}
}

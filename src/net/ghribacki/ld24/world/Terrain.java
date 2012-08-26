package net.ghribacki.ld24.world;

import org.lwjgl.opengl.GL11;

public class Terrain {
	private Chunk map[][];
	private int size;
	
	private Evolution evolutionThread;
	
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
		
		this.evolutionThread = new Evolution(this);
		new Thread(this.evolutionThread).start();
	}
	
	public void update() {
		// TODO Game of life thread!
	}
	
	public void render() {
		this.renderChunks();
	}
	
	private void renderChunks() {
		for (int i = 0; i < this.size; i++) {
			GL11.glPushMatrix();
			GL11.glTranslatef(i*32.0f, 0.0f, 0.0f);
			for (int j = 0; j < this.size; j++) {
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0f, 0.0f, j*32.0f);
				this.map[i][j].render();
				GL11.glPopMatrix();
			}
			GL11.glPopMatrix();
		}
	}
	
	public void setCell(int x, int y, int type) {
		this.map[0][0].setCell(x, y, type);
	}
}

package net.ghribacki.ld24.world;

import java.util.List;

import net.ghribacki.ld24.entity.Entity;
import net.ghribacki.ld24.entity.ShootingStar;
import net.ghribacki.ld24.entity.Turret;

import org.lwjgl.opengl.GL11;

public class Planet {
	private Terrain terrain;
	private int size;
	
	private Evolution evolutionThread;
	
	public final int CELL_PLAIN = 0;
	public final int CELL_MOUNTAIN = 1;
	public final int CELL_WATER = 2;
	
	public Planet(int size) {
		this.size = size;
		
		Tesselator tesselator = new Tesselator();
		
		this.terrain = new Terrain(tesselator, 32);
	}
	
	public void init() {	
		this.evolutionThread = new Evolution(this);
		new Thread(this.evolutionThread).start();
	}
	
	public void update() {
		this.terrain.update();
	}
	
	public void render(float x, float y) {
		GL11.glPushMatrix();
		this.renderChunks(x, y);
		GL11.glPopMatrix();
	}
	
	private void renderChunks(float x, float y) {	
		GL11.glPushMatrix();
		GL11.glTranslatef(-32.0f, 0.0f, -32.0f);
		for (int i = 0; i < 3; i++) {
			GL11.glPushMatrix();
			GL11.glTranslatef(i*32.0f, 0.0f, 0.0f);
			for (int j = 0; j < 3; j++) {
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0f, 0.0f, j*32.0f);
				this.terrain.render();
				GL11.glPopMatrix();
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}
	
	public void setCell(int x, int y, int type) {
		this.terrain.setCell(x, y, type);
	}
	
	public int getCell(int x, int y) {
		x = x % 32;
		y = y % 32;
		return this.terrain.getCell(x, y);
	}
	
	public void refresh() {
		this.terrain.refresh();
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void addEntity(Entity entity) {
		this.terrain.addEntity(entity);
	}
	
	public void spawnTurret(int x, int y) {
		Turret turret = new Turret(this, x+0.5f, 0.45f, y+0.5f);
		this.terrain.addEntity(turret);
		this.terrain.addTurret(turret);
		this.terrain.setCell(x, y, 2);
	}
	
	public void spawnStar(int x, int y) {
		this.terrain.addEntity(new ShootingStar(this, x+0.5f, 10.0f, y+0.5f));
	}
	
	public List<Turret> getTurrets() {
		return this.terrain.getTurrets();
	}

	public void refreshTurrets() {
		this.terrain.refreshTurrets();
	}
}

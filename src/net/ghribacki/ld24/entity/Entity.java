package net.ghribacki.ld24.entity;

import org.lwjgl.util.vector.Vector3f;

public abstract class Entity {
	public Vector3f position;
	protected int lifeTime;
	
	public Entity(float x, float y, float z) {
		this.position = new Vector3f(x, y, z);
	}
	
	public abstract void update();
	
	public abstract void render();

	public boolean isAlive() {
		return (this.lifeTime > 0);
	}
}

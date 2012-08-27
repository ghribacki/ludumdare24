package net.ghribacki.ld24;

import net.ghribacki.ld24.entity.Entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position = null;
	private float yaw = 135.0f;
	private float pitch  = 35.264f;
	
	public Camera() {
		this.position = new Vector3f();
	}
	
	public void update(Entity target) {
		this.position = target.position;
	}
	
	public void lookThrough() {
		/*GL11.glRotatef(this.pitch, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(this.yaw, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(position.x, position.y, position.z);*/

		GL11.glTranslatef(0, 0, -12);
		GL11.glRotatef(this.pitch, 1, 0, 0);
		GL11.glRotatef(this.yaw, 0, 1, 0);
		GL11.glTranslatef(-this.position.x, -this.position.y+0.4f, -this.position.z);
	}
}

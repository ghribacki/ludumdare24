package net.ghribacki.ld24.entity;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Starship extends Entity {
	private float yaw = 135.0f;
	
	float movementSpeed = 6.0f; // Move 10 units per second.
	float dt = 0.0f; // Length of time.
	float dx = 0.0f;
	float dy = 0.0f;
	float lastTime = 0.0f; // When the last frame was.
	float time = 0.0f;
	float mouseSensibility = 0.1f;

	public Starship(float x, float y, float z) {
		super(x, y, z);
	}
	
	public void yaw(float amount) {
		this.yaw +=	amount;
	}
	
	private void walkForward(float distance) {
		this.position.x -= distance * (float)Math.cos(Math.toRadians(yaw));
		this.position.z += distance * (float)Math.sin(Math.toRadians(yaw));
	}
	
	private void walkBackwards(float distance) {
		this.position.x += distance * (float)Math.cos(Math.toRadians(yaw));
		this.position.z -= distance * (float)Math.sin(Math.toRadians(yaw));
	}
	
	private void rotate(float amount) {
		this.yaw(amount * this.mouseSensibility);
	}

	@Override
	public void update() {
		this.time = Sys.getTime();
		this.dt = (this.time - this.lastTime)/1000.0f;
		this.lastTime = this.time;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.rotate(10.0f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.rotate(-10.0f);
		}
		
		this.yaw(this.dx * this.mouseSensibility);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.walkForward(this.movementSpeed * this.dt);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.walkBackwards(this.movementSpeed * this.dt);
		}
		
		this.yaw = ((float) Math.toDegrees(Math.atan2(Mouse.getY()-195.0f, Mouse.getX()-408.0f))) + 45.0f;
		
		// 406, 195
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		
		GL11.glTranslatef(this.position.x, this.position.y, this.position.z);
		GL11.glRotatef(this.yaw+180.0f, 0.0f, 1.0f, 0.0f);
		
		GL11.glColor3f(0.1f, 0.25f, 1.0f);
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex3f(-0.25f, 0.0f, -0.25f);
			GL11.glVertex3f(-0.25f, 0.0f, 0.25f);
			GL11.glVertex3f(0.25f, 0.0f, 0.0f);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex3f(-0.35f, 0.0f, -0.15f);
			GL11.glVertex3f(-0.35f, 0.0f, 0.15f);
			GL11.glVertex3f(-0.2f, 0.0f, 0.15f);
			GL11.glVertex3f(-0.2f, 0.0f, -0.15f);
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}

}

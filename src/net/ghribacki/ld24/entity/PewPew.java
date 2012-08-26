package net.ghribacki.ld24.entity;

import org.lwjgl.opengl.GL11;

public class PewPew extends Entity {
	private float yaw;

	public PewPew(float yaw, float x, float y, float z) {
		super(x, y, z);
		this.yaw = yaw;
		this.lifeTime = 60;
	}
	
	private void walkForward(float distance) {
		this.position.x -= distance * (float)Math.cos(Math.toRadians(yaw));
		this.position.z += distance * (float)Math.sin(Math.toRadians(yaw));
	}

	@Override
	public void update() {
		if (this.lifeTime > 0) {
			this.walkForward(0.25f);
			this.lifeTime--;
		}
	}

	@Override
	public void render() {
		if (this.lifeTime > 0) {
			GL11.glPushMatrix();
			
			GL11.glTranslatef(this.position.x, this.position.y, this.position.z);
			GL11.glRotatef(this.yaw+180.0f, 0.0f, 1.0f, 0.0f);
			GL11.glScalef(0.75f, 1.0f, 0.75f);
	        
			GL11.glColor3f(1.0f, 1.0f, 0.0f);
			GL11.glBegin(GL11.GL_TRIANGLES);
				GL11.glVertex3f(-0.25f, 0.0f, -0.1f);
				GL11.glVertex3f(-0.25f, 0.0f, 0.1f);
				GL11.glVertex3f(0.25f, 0.0f, 0.0f);
			GL11.glEnd();
			
			GL11.glPopMatrix();
		}
	}

}

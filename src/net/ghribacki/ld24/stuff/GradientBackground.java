package net.ghribacki.ld24.stuff;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class GradientBackground {
	private Vector3f bottomColor;
	private Vector3f topColor;
	private Vector3f dynamicColor;
	private boolean up;
	
	public GradientBackground(float x0, float y0, float z0, float x1, float y1, float z1) {
		this.bottomColor = new Vector3f(x0, y0, z0);
		this.topColor = new Vector3f(x1, y1, z1);
		this.dynamicColor = new Vector3f(x0, y0, z0);
		this.up = true;
	}
	
	public void update() {
		if (this.up) {
			if ((this.dynamicColor.x > this.bottomColor.x) && (this.dynamicColor.y > this.bottomColor.y) && (this.dynamicColor.z > this.bottomColor.z)) {
				this.dynamicColor.x -= 0.001f;
				this.dynamicColor.y -= 0.001f;
				this.dynamicColor.z -= 0.001f;
			} else {
				this.up = false;
			}
		} else {
			if ((this.dynamicColor.x < 1) && (this.dynamicColor.y < 1) && (this.dynamicColor.z < 1)) {
				this.dynamicColor.x += 0.001f;
				this.dynamicColor.y += 0.001f;
				this.dynamicColor.z += 0.001f;
			} else {
				this.up = true;
			}
		}
	}
	
	public void render() {
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(this.dynamicColor.x, this.dynamicColor.y, this.dynamicColor.z);
			GL11.glVertex2f(-1.0f,-1.0f);
			GL11.glVertex2f(1.0f,-1.0f);

			GL11.glColor3f(this.topColor.x, this.topColor.y, this.topColor.z);
			GL11.glVertex2f(1.0f, 1.0f);
			GL11.glVertex2f(-1.0f, 1.0f);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_LIGHTING);
	}

}

package net.ghribacki.ld24.entity;

import net.ghribacki.ld24.world.Planet;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class PewPew extends Entity {
	private static int displayList;
	
	private float yaw;
	private Vector3f color;
	private boolean enemy;
	private float speed;

	public PewPew(Planet planet, float x, float y, float z, float yaw, boolean enemy) {
		super(planet, x, y, z);
		this.yaw = yaw;
		this.enemy = enemy;
		this.lifeTime = 20;
		
		if (enemy) {
			this.color = new Vector3f(1.0f, 0.0f, 0.0f);
			this.speed = 0.1f;
		} else {
			this.color = new Vector3f(1.0f, 1.0f, 0.0f);
			this.speed = 0.5f;
		}
	}
	
	private void walkForward(float distance) {
		this.position.x -= distance * (float)Math.cos(Math.toRadians(yaw));
		this.position.z += distance * (float)Math.sin(Math.toRadians(yaw));
	}

	@Override
	public void update() {
		if (this.lifeTime > 0) {
			this.walkForward(this.speed);
			
			if (this.position.x >= 32) {
				this.position.x = 0;
			} else if (this.position.x < 0) {
				this.position.x = 32;
			}
			if (this.position.z >= 32) {
				this.position.z = 0;
			} else if (this.position.z < 0) {
				this.position.z = 32;
			}
			
			this.lifeTime--;
		}
		
		if (!enemy) {
			if (this.planet.getCell((int)this.position.x, (int)this.position.z) > 0) {
				this.planet.setCell((int)this.position.x, (int)this.position.z, 0);
				this.lifeTime = 0;
				this.planet.refresh();
			}
		}
	}

	@Override
	public void render() {
		if (this.lifeTime > 0) {
			GL11.glPushMatrix();
			
			GL11.glTranslatef(this.position.x, this.position.y, this.position.z);
			GL11.glRotatef(this.yaw+180.0f, 0.0f, 1.0f, 0.0f);
			GL11.glScalef(0.75f, 1.0f, 0.75f);
			
			GL11.glColor3f(this.color.x, this.color.y, this.color.z);
			
			GL11.glCallList(PewPew.displayList);
			
			GL11.glPopMatrix();
		}
	}
	
	public static void compile() {
		PewPew.displayList = GL11.glGenLists(1);
		GL11.glNewList(PewPew.displayList, GL11.GL_COMPILE);

		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex3f(-0.25f, 0.0f, -0.1f);
			GL11.glVertex3f(-0.25f, 0.0f, 0.1f);
			GL11.glVertex3f(0.25f, 0.0f, 0.0f);
		GL11.glEnd();
		
		GL11.glEndList();
	}

}

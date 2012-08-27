package net.ghribacki.ld24.entity;

import org.lwjgl.opengl.GL11;

import net.ghribacki.ld24.world.Planet;

public class ShootingStar extends Entity {
	private static int displayList;
	
	private float yaw;
	private float pitch;

	public ShootingStar(Planet planet, float x, float y, float z) {
		super(planet, x, y, z);
		this.lifeTime = 1;
	}

	@Override
	public void update() {
		this.yaw += 5;
		this.pitch += 5;

		this.position.y -= 0.1f;
		
		if (this.position.y < 0) {
			int x = (int)this.position.x;
			int y = (int)this.position.z;
			this.planet.setCell(x, y, 1);
			this.planet.setCell((x+32-1)%32, y, 1);
			this.planet.setCell((x+1)%32, y, 1);
			this.planet.setCell(x, (y+32-1)%32, 1);
			this.planet.setCell(x, (y+1)%32, 1);
			this.planet.spawnTurret(x, y);
			this.lifeTime = 0;
		}
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		
		GL11.glTranslatef(this.position.x, this.position.y, this.position.z);
		GL11.glRotatef(this.pitch, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(this.yaw, 0.0f, 1.0f, 0.0f);
		GL11.glScalef(0.5f, 0.5f, 0.5f);

		GL11.glCallList(ShootingStar.displayList);
		
		GL11.glPopMatrix();
	}

	public static void compile() {
		ShootingStar.displayList = GL11.glGenLists(1);
		GL11.glNewList(ShootingStar.displayList, GL11.GL_COMPILE);
        
		GL11.glColor4f(1.0f, 1.0f, 0.0f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
			/*GL11.glVertex3f(-0.25f, 0.0f, -0.25f);
			GL11.glVertex3f(-0.25f, 0.0f, 0.25f);
			GL11.glVertex3f(0.25f, 0.0f, 0.25f);
			GL11.glVertex3f(0.25f, 0.0f, -0.25f);*/
			GL11.glVertex3f(-0.25f, -0.25f, -0.25f);
			GL11.glVertex3f(-0.25f, -0.25f, 0.25f);
			GL11.glVertex3f(0.25f, 0.25f, 0.25f);
			GL11.glVertex3f(0.25f, 0.25f, -0.25f);
			GL11.glVertex3f(-0.25f, 0.25f, -0.25f);
			GL11.glVertex3f(-0.25f, -0.25f, 0.25f);
			GL11.glVertex3f(0.25f, -0.25f, 0.25f);
			GL11.glVertex3f(0.25f, 0.25f, -0.25f);
			

			/*GL11.glVertex3f(-0.25f, 0.0f, -0.25f);
			GL11.glVertex3f(0.25f, 0.0f, -0.25f);
			GL11.glVertex3f(0.25f, 0.0f, 0.25f);
			GL11.glVertex3f(-0.25f, 0.0f, 0.25f);*/
			GL11.glVertex3f(-0.25f, -0.25f, -0.25f);
			GL11.glVertex3f(0.25f, 0.25f, -0.25f);
			GL11.glVertex3f(0.25f, 0.25f, 0.25f);
			GL11.glVertex3f(-0.25f, -0.25f, 0.25f);
			GL11.glVertex3f(-0.25f, 0.25f, -0.25f);
			GL11.glVertex3f(0.25f, 0.25f, -0.25f);
			GL11.glVertex3f(0.25f, -0.25f, 0.25f);
			GL11.glVertex3f(-0.25f, -0.25f, 0.25f);
		GL11.glEnd();
		
		GL11.glEndList();
	}
}

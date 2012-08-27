package net.ghribacki.ld24.entity;

import org.lwjgl.opengl.GL11;

import net.ghribacki.ld24.world.Planet;

public class Turret extends Entity {
	private static int displayList;
	
	private int shootTimer;
	private float yaw;

	public Turret(Planet planet, float x, float y, float z) {
		super(planet, x, y, z);
		this.lifeTime = 1;
	}

	@Override
	public void update() {
		this.shootTimer++;
		if (this.shootTimer == 180) {
			this.shootTimer = 0;
			this.planet.addEntity(new PewPew(this.planet, this.position.x, this.position.y-0.2f, this.position.z, this.yaw, true));
			this.planet.addEntity(new PewPew(this.planet, this.position.x, this.position.y-0.2f, this.position.z, this.yaw+90, true));
			this.planet.addEntity(new PewPew(this.planet, this.position.x, this.position.y-0.2f, this.position.z, this.yaw+180, true));
			this.planet.addEntity(new PewPew(this.planet, this.position.x, this.position.y-0.2f, this.position.z, this.yaw+270, true));
		}
		this.yaw += 1;
		if (this.planet.getCell((int)this.position.x, (int)this.position.z) == 0) {
			this.lifeTime = 0;
		}
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		
		GL11.glTranslatef(this.position.x, this.position.y, this.position.z);
		GL11.glRotatef(this.yaw, 0.0f, 1.0f, 0.0f);

		GL11.glCallList(Turret.displayList);
		
		GL11.glPopMatrix();
	}

	public static void compile() {
		Turret.displayList = GL11.glGenLists(1);
		GL11.glNewList(Turret.displayList, GL11.GL_COMPILE);
        
		GL11.glColor3f(0.5f, 0.1f, 0.1f);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex3f(-0.25f, 0.0f, -0.25f);
			GL11.glVertex3f(-0.25f, 0.0f, 0.25f);
			GL11.glVertex3f(0.25f, 0.0f, 0.25f);
			GL11.glVertex3f(0.25f, 0.0f, -0.25f);
		GL11.glEnd();
		
		GL11.glEndList();
	}
}

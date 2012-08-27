package net.ghribacki.ld24.entity;

import net.ghribacki.ld24.GameScene;
import net.ghribacki.ld24.world.Planet;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Starship extends Entity {
	private int controlMode = 1;
	
	private float yaw = 135.0f;
	
	private float movementSpeed = 0.0f; // Move 10 units per second.
	private float dt = 0.0f; // Length of time.
	//private float dx = 0.0f;
	//private float dy = 0.0f;
	private float lastTime = 0.0f; // When the last frame was.
	private float time = 0.0f;
	private float mouseSensibility = 0.1f;

	private float maxSpeed = 6.0f;
	private float acceleration = 0.1f;
	private float deacceleration = 0.1f;
	private int pewTimer;

	public Starship(Planet planet, float x, float y, float z) {
		super(planet, x, y, z);
	}
	
	public void yaw(float amount) {
		this.yaw +=	amount;
	}
	
	private void moveForward(float distance) {
		this.position.x -= distance * (float)Math.cos(Math.toRadians(yaw));
		this.position.z += distance * (float)Math.sin(Math.toRadians(yaw));
	}
	
	private void rotate(float amount) {
		this.yaw(amount * this.mouseSensibility);
	}

	@Override
	public void update() {
		this.time = Sys.getTime();
		this.dt = (this.time - this.lastTime)/1000.0f;
		this.lastTime = this.time;
		
		boolean forward = false;
		boolean backward = false;
		boolean shoot = false;
		
		if (this.controlMode == 0) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				forward = true;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				backward = true;
			}
			if (Mouse.isButtonDown(0)) {
				shoot = true;
			}
			
			this.yaw = ((float) Math.toDegrees(Math.atan2(Mouse.getY()-195.0f, Mouse.getX()-408.0f))) + 45.0f;
			// 406, 195 (screen 2d position of the ship).
		} else {
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				forward = true;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				backward = true;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				shoot = true;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				this.rotate(50.0f);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				this.rotate(-50.0f);
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
			this.controlMode = 1;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
			this.controlMode = 0;
		}
		
		
		if (forward) {
			if (this.movementSpeed < this.maxSpeed) {
				this.movementSpeed += this.acceleration;
			}
		} else if (backward) {
			if (this.movementSpeed > -this.maxSpeed) {
				this.movementSpeed -= this.acceleration;
			}
		} else {
			if (this.movementSpeed > 0) {
				this.movementSpeed -= this.deacceleration;
				if (this.movementSpeed < 0) {
					this.movementSpeed = 0;
				}
			} else if (this.movementSpeed < 0) {
				this.movementSpeed += this.deacceleration;
				if (this.movementSpeed > 0) {
					this.movementSpeed = 0;
				}
			}
		}
		
		this.moveForward(this.movementSpeed * this.dt);
		
		if (shoot && (this.pewTimer == 0)) {
			this.planet.addEntity(new PewPew(this.planet, this.position.x, this.position.y-0.05f, this.position.z, this.yaw, false));
			this.pewTimer = 15;
		}
		
		if (this.pewTimer > 0) {
			this.pewTimer--;
		}
		
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
		
		if (this.planet.getCell((int)this.position.x, (int)this.position.z) > 0) {
			// TODO GAME OVER
		}
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		
		GL11.glTranslatef(this.position.x, this.position.y, this.position.z);
		GL11.glRotatef(this.yaw+180.0f, 0.0f, 1.0f, 0.0f);
        
		GL11.glColor3f(0.0f, 0.0f, 0.8f);
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

        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, GameScene.asFloatBuffer(new float[] {5, 10f, 0, 1}));
		
		GL11.glPopMatrix();
	}

}

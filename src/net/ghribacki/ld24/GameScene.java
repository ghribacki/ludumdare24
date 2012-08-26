package net.ghribacki.ld24;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import net.ghribacki.ld24.entity.Entity;
import net.ghribacki.ld24.entity.Starship;
import net.ghribacki.ld24.world.Terrain;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GameScene extends Scene {
	private Shader shader;
	
	private Terrain terrain;
	private Camera cam;
	private Starship player;
	private List<Entity> entities;
	
	private int cleanTimer;

	public GameScene(Game game) {
		super(game);

		this.terrain = new Terrain(1);
		this.cam = new Camera();
		this.player = new Starship(16, 0.6f, 16);
		
		this.entities = new ArrayList<Entity>();
		this.player.setPewSpawn(this.entities);
	}

	@Override
	public void init() {
		//GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping
		GL11.glClearColor(0.1f, 0.1f, 0.2f, 1.0f);
		GL11.glClearDepth(1.0); // Depth Buffer Setup
        
        /*GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_NOTEQUAL, GL11.GL_ZERO);*/
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        // Really Nice Perspective Calculations.
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_FASTEST);
		
        // Light test!
        //GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);
        GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, asFloatBuffer(new float[] {0.3f, 0.3f, 0.3f, 1f}));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, asFloatBuffer(new float[] {0.7f, 0.7f, 0.7f, 1f}));
        //GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, asFloatBuffer(new float[] {0.7f, 0.7f, 0.7f, 1f}));
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE);
        
		// Load shaders...
		this.shader = new Shader();
	}
	
	public static FloatBuffer asFloatBuffer(float[] values) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}

	@Override
	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_F2)) {
			this.game.setScene(new GameScene(this.game));
		}
		
		this.terrain.update();
		this.player.update();
		this.cam.update(this.player);
		for (Entity entity : this.entities) {
			entity.update();
		}
		this.cleanTimer++;
		if (this.cleanTimer == 60) {
			this.cleanEntities();
			this.cleanTimer = 0;
		}
	}

	@Override
	public void render() {
		this.setup3D();
		
		GL11.glPushMatrix();
		
		this.shader.beginShaderProgram();
		
		this.cam.lookThrough();
		
		this.terrain.render(this.player.position.x, this.player.position.z);
		
		this.player.render();
		
		for (Entity entity : this.entities) {
			entity.render();
		}
		
		this.shader.endShaderProgram();
		
		GL11.glPopMatrix();
	}

	@Override
	public void destroy() {
		this.shader.clear();		
	}

	private void setup3D() {
		GL11.glViewport(0, 0, this.game.getWidth(), this.game.getHeight());
		
		GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
        GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix
        
        // Calculate The Aspect Ratio Of The Window.
        GLU.gluPerspective(
          30.0f,
          (float) this.game.getWidth() / (float) this.game.getHeight(),
          0.1f,
          64.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
        
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
	}

	public void cleanEntities() {
		List<Entity> entityTemp = new ArrayList<Entity>();
		entityTemp.addAll(this.entities);
		this.entities.clear();
		
		for (Entity entity : entityTemp) {
			if (entity.isAlive()) {
				this.entities.add(entity);
			}
		}
	}
}

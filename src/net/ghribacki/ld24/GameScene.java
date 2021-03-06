package net.ghribacki.ld24;

import java.nio.FloatBuffer;

import net.ghribacki.ld24.entity.PewPew;
import net.ghribacki.ld24.entity.ShootingStar;
import net.ghribacki.ld24.entity.Starship;
import net.ghribacki.ld24.entity.Turret;
import net.ghribacki.ld24.stuff.GradientBackground;
import net.ghribacki.ld24.world.Planet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GameScene extends Scene {
	private Shader shader;
	
	private Planet planet;
	private Camera cam;
	private Starship player;
	
	private GradientBackground bg;

	public GameScene(Game game) {
		super(game);

		this.planet = new Planet(1);
		this.cam = new Camera();
		this.player = new Starship(this.planet, 16, 0.5f, 16);
		
		this.bg = new GradientBackground(0.4f, 0.4f, 0.8f, 0.0f, 0.0f, 0.1f);
	}

	@Override
	public void init() {
		//GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping
		GL11.glClearColor(0.1f, 0.1f, 0.2f, 1.0f);
		GL11.glClearDepth(1.0); // Depth Buffer Setup
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_NOTEQUAL, GL11.GL_ZERO);
        
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
		
		this.planet.init();
		
		Turret.compile();
		ShootingStar.compile();
		PewPew.compile();
	}
	
	public static FloatBuffer asFloatBuffer(float[] values) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}

	@Override
	public void update() {
		this.planet.update();
		this.player.update();
		this.cam.update(this.player);
		this.bg.update();
	}

	@Override
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		this.bg.render();

		this.setup3D();
		
		GL11.glPushMatrix();
		
		this.shader.beginShaderProgram();
		
		this.cam.lookThrough();
		
		this.planet.render(this.player.position.x, this.player.position.z);
		
		this.player.render();
		
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
        
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
	}
}

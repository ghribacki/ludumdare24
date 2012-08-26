package net.ghribacki.ld24;

import net.ghribacki.ld24.world.Terrain;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GameScene extends Scene {
	private Terrain terrain;
	
	private double round = 0.0;
	private double round2 = 0.0;

	public GameScene(Game game) {
		super(game);

		this.terrain = new Terrain(1);
	}

	@Override
	public void init() {
		//GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping
		//GL11.glClearColor(0.53f, 0.8f, 1.0f, 0.0f); // Cyan Background
		GL11.glClearColor(0.1f, 0.1f, 0.2f, 1.0f); // Black Background
		GL11.glClearDepth(1.0); // Depth Buffer Setup
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_NOTEQUAL, GL11.GL_ZERO);
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        // Really Nice Perspective Calculations.
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_FASTEST);
	}

	@Override
	public void update() {
		this.terrain.update();
	}

	@Override
	public void render() {
		this.setup3D();
		
		GL11.glPushMatrix();

		GL11.glTranslated(0, 0, -10);
		GL11.glRotated(35.264, 1, 0, 0);
		//GL11.glRotated(45, 0, 1, 0);
		//GL11.glRotated(round, 1, 0, 0);
		GL11.glRotated(round2, 0, 1, 0);
		GL11.glTranslated(-10, 0, -10);
		this.terrain.render();
		
		if (round < 360.0) {
			round += 0.25;
		} else {
			round = 0.0;
		}
		
		if (round2 < 360.0) {
			round2 += 0.25;
		} else {
			round2 = 0.0;
		}
		
		GL11.glPopMatrix();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
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
          300.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
        
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
	}

}

package net.ghribacki.ld24;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final String TITLE = "Living Worlds";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int SCALE = 1;
	
	private boolean running;
	
	private Thread gameThread;
	
	private Scene scene;
	
	public Game() {
		Dimension size = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);
		this.setSize(size);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.setMinimumSize(size);
		this.setFocusable(true);
		this.requestFocus();
		this.setIgnoreRepaint(true);
	}

	@Override
	public void run() {
		try {
			Display.setParent(this);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		// TODO Sound stuff (load, etc).
		
		this.setScene(new GameScene(this));
		
		while (this.running) {
			// TODO Control.handleKeyboard();
			
			this.scene.update();
			
			// clear screen
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			
			this.scene.render();
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
		System.exit(0);
	}
	
	public void setScene(Scene scene) {
		if (this.scene != null) {
			this.scene.destroy();
		}
		this.scene = scene;
		this.scene.init();
	}

	public void start() {
		this.running = true;
		this.gameThread = new Thread(this);
		this.gameThread.start();
	}

	public void stop() {
		try {
			this.scene.destroy();
			this.gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.running = false;
	}
	
	public final void addNotify() {
		super.addNotify();
		this.start();
	}
	
	public final void removeNotify() {
		this.stop();
		super.removeNotify();
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		Dimension dimension = new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		game.setMinimumSize(dimension);
		game.setMaximumSize(dimension);
		game.setPreferredSize(dimension);
	
		JFrame frame = new JFrame(Game.TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

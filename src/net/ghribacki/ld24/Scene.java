package net.ghribacki.ld24;

public abstract class Scene {
	protected Game game;
	
	public Scene(Game game) {
		this.game = game;
	}
	
	public abstract void init();
	
	public abstract void update();
	
	public abstract void render();
	
	public abstract void destroy();
}

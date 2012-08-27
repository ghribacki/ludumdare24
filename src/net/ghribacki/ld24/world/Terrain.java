package net.ghribacki.ld24.world;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.ghribacki.ld24.entity.Entity;
import net.ghribacki.ld24.entity.Turret;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;


public class Terrain {
	private int size;
	private int cells[][];
	private int newCells[][];
	private boolean modified;
	
	private List<Entity> entities;
	private List<Entity> newEntities;
	private List<Turret> turrets;
	private List<Turret> newTurrets;
	private int cleanTimer;
	
	// Renderer.
	private int vertices;
	//private int textures;
	private int colors;
	private int count;
	
	private Tesselator tesselator;
	
	public Terrain(Tesselator tesselator, int size) {
		this.size = size;
		this.cells = new int[size][size];
		this.newCells = new int[size][size];
		
		this.tesselator = tesselator;
		
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int rand = random.nextInt(100);
				if (rand < 80) {
					this.setCell(i, j, 1);
				} else {
					this.setCell(i, j, 0);
				}
			}
		}
		this.refresh();
		
		this.entities = new ArrayList<Entity>();
		this.newEntities = new ArrayList<Entity>();
		this.turrets = new ArrayList<Turret>();
		this.newTurrets = new ArrayList<Turret>();
	}
	
	public void update() {
		for (Entity entity : this.entities) {
			entity.update();
		}
		
		this.entities.addAll(this.newEntities);
		this.newEntities.clear();
		
		this.cleanTimer++;
		if (this.cleanTimer == 10) {
			this.cleanEntities();
			this.cleanTimer = 0;
		}
	}
	
	public void render() {
		if (this.modified) {
			this.compile();
		}
		this.renderVbo();
		
		for (Entity entity : this.entities) {
			entity.render();
		}
	}
	
	private void renderVbo() {
		// TODO Bind the tileset!
		
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		//GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		
		GL11.glEnable(GL11.GL_BLEND);
		//GL11.glEnable(GL11.GL_CULL_FACE);
		
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.vertices);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		//ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.textures);
		//GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.colors);
		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0);
		
		GL11.glDrawArrays(GL11.GL_QUADS, 0, this.count/3);
		
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		//GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
	}
	
	private void compile() {		
		ArrayList<Float> verticesList = new ArrayList<Float>();
		//ArrayList<Float> texturesList = new ArrayList<Float>();
		ArrayList<Float> colorsList = new ArrayList<Float>();
		
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				int cellType = this.cells[i][j];
				verticesList.addAll(tesselator.getVertices(cellType, i, j));
				//texturesList.addAll(tesselator.getTextures(cellType));
				colorsList.addAll(tesselator.getColors(cellType));
			}
		}
		
		this.count = verticesList.size();
		float[] verts = new float[this.count];
		for (int i = 0; i < this.count; i++) {
			verts[i] = verticesList.get(i);
		}
		
		/*int countT = texturesList.size();
		float[] texts = new float[countT];
		for (int i = 0; i < countT; i++) {
			texts[i] = texturesList.get(i);
		}*/
		
		int countC = colorsList.size();
		float[] cols = new float[countC];
		for (int i = 0; i < countC; i++) {
			cols[i] = colorsList.get(i);
		}
		
		// The real thing.
		
		FloatBuffer verticesBuffer;
		//FloatBuffer texturesBuffer;
		FloatBuffer colorsBuffer;
		
		verticesBuffer = BufferUtils.createFloatBuffer(this.count);
		verticesBuffer.put(verts).flip();
		/*texturesBuffer = BufferUtils.createFloatBuffer(texturesList.size());
		texturesBuffer.put(texts).flip();*/
		colorsBuffer = BufferUtils.createFloatBuffer(colorsList.size());
		colorsBuffer.put(cols).flip();
		
		ARBVertexBufferObject.glDeleteBuffersARB(this.vertices);
		//ARBVertexBufferObject.glDeleteBuffersARB(this.textures);
		ARBVertexBufferObject.glDeleteBuffersARB(this.colors);
		
		this.vertices = ARBVertexBufferObject.glGenBuffersARB();
		//this.textures = ARBVertexBufferObject.glGenBuffersARB();
		this.colors = ARBVertexBufferObject.glGenBuffersARB();

		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.vertices);
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, verticesBuffer, ARBVertexBufferObject.GL_DYNAMIC_DRAW_ARB);
		//ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.textures);
		//ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, texturesBuffer, ARBVertexBufferObject.GL_DYNAMIC_DRAW_ARB);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.colors);
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, colorsBuffer, ARBVertexBufferObject.GL_DYNAMIC_DRAW_ARB);
		
		verticesBuffer.clear();
		//texturesBuffer.clear();
		colorsBuffer.clear();
		
		this.modified = false;
	}
	
	public void setCell(int x, int y, int type) {
		x = x % 32;
		y = y % 32;
		this.newCells[x][y] = type;
	}

	public int getCell(int x, int y) {
		return this.cells[x][y];
	}
	
	public void refresh() {
		synchronized (this) {
			this.cells = this.newCells;
			this.modified = true;
		}
	}

	public void addEntity(Entity entity) {
		this.newEntities.add(entity);
	}
	
	public void addTurret(Turret turret) {
		this.newTurrets.add(turret);
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

	public List<Turret> getTurrets() {
		this.cleanTurrets();
		return this.turrets;
	}

	public void cleanTurrets() {
		List<Turret> turretTemp = new ArrayList<Turret>();
		turretTemp.addAll(this.turrets);
		this.turrets.clear();
		
		for (Turret turret : turretTemp) {
			if (turret.isAlive()) {
				this.turrets.add(turret);
			}
		}
	}
	
	public void refreshTurrets() {
		this.turrets.addAll(this.newTurrets);
		this.newTurrets.clear();
	}
	
	public boolean isClean() {
		boolean clean = true;
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (this.cells[i][j] == 2) {
					clean = false;
					break;
				}
			}
		}
		return clean;
	}
	
	public void finish() {
		if (this.isClean()) {
			for (int i = 0; i < this.size; i++) {
				for (int j = 0; j < this.size; j++) {
					this.cells[i][j] = 1;
				}
			}
		} else {
			for (int i = 0; i < this.size; i++) {
				for (int j = 0; j < this.size; j++) {
					this.cells[i][j] = 2;
				}
			}
		}
		this.entities.clear();
		this.turrets.clear();
		this.refresh();
		this.refreshTurrets();
	}
}

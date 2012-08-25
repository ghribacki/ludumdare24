package net.ghribacki.ld24.world;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;


public class Chunk {
	private int size;
	private int cells[][];
	private boolean modified;
	
	// Renderer.
	private int vertices;
	private int textures;
	private int colors;
	private int count;
	
	public Chunk(int size) {
		this.size = size;
		this.cells = new int[size][size];
		this.modified = true;
	}
	
	public void update() {
		
	}
	
	public void render() {
		if (!this.modified) {
			this.renderVbo();
		} else {
			this.compile();
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
		Tesselator tesselator = new Tesselator();
		
		ArrayList<Float> verticesList = new ArrayList<Float>();
		ArrayList<Float> texturesList = new ArrayList<Float>();
		ArrayList<Float> colorsList = new ArrayList<Float>();
		
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				int cellType = this.cells[i][j];
				verticesList.addAll(tesselator.getVertices(cellType, i, j));
				texturesList.addAll(tesselator.getTextures(cellType));
				colorsList.addAll(tesselator.getColors(cellType));
			}
		}
		
		this.count = verticesList.size();
		float[] verts = new float[this.count];
		for (int i = 0; i < this.count; i++) {
			verts[i] = verticesList.get(i);
		}
		
		int countT = texturesList.size();
		float[] texts = new float[countT];
		for (int i = 0; i < countT; i++) {
			texts[i] = texturesList.get(i);
		}
		
		int countC = colorsList.size();
		float[] cols = new float[countC];
		for (int i = 0; i < countC; i++) {
			cols[i] = colorsList.get(i);
		}
		
		// The real thing.
		
		FloatBuffer verticesBuffer;
		FloatBuffer texturesBuffer;
		FloatBuffer colorsBuffer;
		
		verticesBuffer = BufferUtils.createFloatBuffer(this.count);
		verticesBuffer.put(verts).flip();
		texturesBuffer = BufferUtils.createFloatBuffer(texturesList.size());
		texturesBuffer.put(texts).flip();
		colorsBuffer = BufferUtils.createFloatBuffer(colorsList.size());
		colorsBuffer.put(cols).flip();
		
		ARBVertexBufferObject.glDeleteBuffersARB(this.vertices);
		ARBVertexBufferObject.glDeleteBuffersARB(this.textures);
		ARBVertexBufferObject.glDeleteBuffersARB(this.colors);
		
		this.vertices = ARBVertexBufferObject.glGenBuffersARB();
		this.textures = ARBVertexBufferObject.glGenBuffersARB();
		this.colors = ARBVertexBufferObject.glGenBuffersARB();

		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.vertices);
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, verticesBuffer, ARBVertexBufferObject.GL_DYNAMIC_DRAW_ARB);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.textures);
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, texturesBuffer, ARBVertexBufferObject.GL_DYNAMIC_DRAW_ARB);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.colors);
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, colorsBuffer, ARBVertexBufferObject.GL_DYNAMIC_DRAW_ARB);
		
		verticesBuffer.clear();
		texturesBuffer.clear();
		colorsBuffer.clear();
		
		this.modified = false;
	}
}

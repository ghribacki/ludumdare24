package net.ghribacki.ld24.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tesselator {
	
	/** Vertices **/
	
	public List<Float> getPoint(float x, float y, float z) {
		ArrayList<Float> point = new ArrayList<Float>();

		point.add(new Float(x)); // top right
		point.add(new Float(y));
		point.add(new Float(z));
		
		return point;
	}
	
	public List<Float> getPlain(float x0, float y0, float z0, float x1, float y1, float z1) {
		ArrayList<Float> quad = new ArrayList<Float>();

		quad.addAll(this.getPoint(x1, y0, z0));
		quad.addAll(this.getPoint(x0, y0, z0));
		quad.addAll(this.getPoint(x0, y1, z1));
		quad.addAll(this.getPoint(x1, y1, z1));
		
		return quad;
	}
	
	public List<Float> getNorthSlope(int x, int y, float z0, float z1) {
		ArrayList<Float> quad = new ArrayList<Float>();
		
		quad.addAll(this.getPoint(x, z0, y));
		quad.addAll(this.getPoint(x, z0, y+1.0f));
		quad.addAll(this.getPoint(x+0.1f, z1, y+0.9f));
		quad.addAll(this.getPoint(x+0.1f, z1, y+0.1f));
		
		return quad;
	}
	
	public List<Float> getEastSlope(int x, int y, float z0, float z1) {
		ArrayList<Float> quad = new ArrayList<Float>();

		quad.addAll(this.getPoint(x, z0, y+1.0f));
		quad.addAll(this.getPoint(x+1.0f, z0, y+1.0f));
		quad.addAll(this.getPoint(x+0.9f, z1, y+0.9f));
		quad.addAll(this.getPoint(x+0.1f, z1, y+0.9f));
		
		return quad;
	}
	
	public List<Float> getSouthSlope(int x, int y, float z0, float z1) {
		ArrayList<Float> quad = new ArrayList<Float>();

		quad.addAll(this.getPoint(x+1.0f, z0, y+1.0f));
		quad.addAll(this.getPoint(x+1.0f, z0, y));
		quad.addAll(this.getPoint(x+0.9f, z1, y+0.1f));
		quad.addAll(this.getPoint(x+0.9f, z1, y+0.9f));
		
		return quad;
	}
	
	public List<Float> getWestSlope(int x, int y, float z0, float z1) {
		ArrayList<Float> quad = new ArrayList<Float>();

		quad.addAll(this.getPoint(x+1.0f, z0, y));
		quad.addAll(this.getPoint(x, z0, y));
		quad.addAll(this.getPoint(x+0.1f, z1, y+0.1f));
		quad.addAll(this.getPoint(x+0.9f, z1, y+0.1f));
		
		return quad;
	}
	
	public List<Float> getVertices(int type, int x, int y) {
		switch (type) {
		case 0:
			return getVerticesPlain(x, y);
		case 1:
			return getVerticesMountain(x, y);
		case 2:
			return getVerticesWater(x, y);
		}
		return null;
	}
	
	private List<Float> getVerticesPlain(int x, int y) {
		ArrayList<Float> quad = new ArrayList<Float>();

		quad.addAll(this.getPlain(x, 0.0f, y, 1.0f + x, 0.0f, 1.0f + y));
		
		return quad;
	}
	
	private List<Float> getVerticesMountain(int x, int y) {
		ArrayList<Float> quad = new ArrayList<Float>();
		
		quad.addAll(this.getPlain(x+0.1f, 0.4f, y+0.1f, 0.9f + x, 0.4f, 0.9f + y));
		quad.addAll(this.getNorthSlope(x, y, 0.0f, 0.4f));
		quad.addAll(this.getEastSlope(x, y, 0.0f, 0.4f));
		quad.addAll(this.getSouthSlope(x, y, 0.0f, 0.4f));
		quad.addAll(this.getWestSlope(x, y, 0.0f, 0.4f));
		
		return quad;
	}
	
	private List<Float> getVerticesWater(int x, int y) {
		ArrayList<Float> quad = new ArrayList<Float>();

		quad.addAll(this.getPlain(x+0.1f, -0.2f, y+0.1f, 0.9f + x, -0.2f, 0.9f + y));
		quad.addAll(this.getNorthSlope(x, y, 0.0f, -0.2f));
		quad.addAll(this.getEastSlope(x, y, 0.0f, -0.2f));
		quad.addAll(this.getSouthSlope(x, y, 0.0f, -0.2f));
		quad.addAll(this.getWestSlope(x, y, 0.0f, -0.2f));
		
		return quad;
	}
	
	/** Textures **/

	/*public List<Float> getTextures(int type) {
		switch (type) {
		case 0:
			return getTexturesPlain();
		case 1:
			return getTexturesMountain();
		case 2:
			return getTexturesWater();
		}
		return null;
	}
	
	private List<Float> getTexturesPlain() {
		ArrayList<Float> textures = new ArrayList<Float>();
		
		// TODO Textura mapping...
		
		return textures;
	}
	
	private List<Float> getTexturesMountain() {
		ArrayList<Float> textures = new ArrayList<Float>();
		
		// TODO Textura mapping...
		
		return textures;
	}
	
	private List<Float> getTexturesWater() {
		ArrayList<Float> textures = new ArrayList<Float>();
		
		// TODO Textura mapping...
		
		return textures;
	}*/
	
	/** Colors **/
	
	public List<Float> getVertexColor(float red, float green, float blue) {
		ArrayList<Float> colors = new ArrayList<Float>();
		
		colors.add(red);
		colors.add(green);
		colors.add(blue);
		colors.add(red);
		colors.add(green);
		colors.add(blue);
		colors.add(red);
		colors.add(green);
		colors.add(blue);
		colors.add(red);
		colors.add(green);
		colors.add(blue);
		
		return colors;
	}

	public List<Float> getColors(int type) {
		switch (type) {
		case 0:
			return getColorsPlain();
		case 1:
			return getColorsMountain();
		case 2:
			return getColorsWater();
		}
		return null;
	}
	
	private List<Float> getColorsPlain() {
		ArrayList<Float> colors = new ArrayList<Float>();

		Random random = new Random();
		float rand = random.nextFloat() / 5.0f;
		//float rand = 2.0f / 10.0f;
		
		float red = rand + 0.4f;
		float green = rand;
		float blue = rand;
		
		colors.addAll(this.getVertexColor(red, green, blue));
		
		return colors;
	}
	
	private List<Float> getColorsMountain() {
		ArrayList<Float> colors = new ArrayList<Float>();
		
		Random random = new Random();
		float rand = random.nextFloat() / 10.0f;
		//float rand = 1.0f / 10.0f;
		
		float red = rand + 0.2f;
		float green = rand;
		float blue = rand;
		
		colors.addAll(this.getVertexColor(red+0.075f, green, blue));
		colors.addAll(this.getVertexColor(red+0.05f, green, blue));
		colors.addAll(this.getVertexColor(red+0.025f, green, blue));
		colors.addAll(this.getVertexColor(red+0.05f, green, blue));
		colors.addAll(this.getVertexColor(red+0.025f, green, blue));
		
		return colors;
	}
	
	private List<Float> getColorsWater() {
		ArrayList<Float> colors = new ArrayList<Float>();
		
		Random random = new Random();
		float rand = random.nextFloat() / 10.0f;
		
		float red = rand;
		float green = rand;
		float blue = rand + 0.4f;
		
		colors.addAll(this.getVertexColor(red+0.075f, green, blue));
		colors.addAll(this.getVertexColor(red+0.05f, green, blue));
		colors.addAll(this.getVertexColor(red+0.025f, green, blue));
		colors.addAll(this.getVertexColor(red+0.05f, green, blue));
		colors.addAll(this.getVertexColor(red+0.025f, green, blue));
		
		return colors;
	}
}
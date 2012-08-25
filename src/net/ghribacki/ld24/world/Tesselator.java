package net.ghribacki.ld24.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tesselator {
	
	/** Vertices **/
	
	public List<Float> getVertices(int type, int x, int y) {
		switch (type) {
		case 0:
			return getVerticesPlain(x, y);
		case 1:
			return getVerticesMountain();
		case 2:
			return getVerticesWater();
		}
		return null;
	}
	
	private List<Float> getVerticesPlain(int x, int y) {
		ArrayList<Float> quad = new ArrayList<Float>();

		quad.add(new Float(1.0f + x)); // top right
		quad.add(new Float(1.0f + 0.0f));
		quad.add(new Float(0.0f + y));
		quad.add(new Float(0.0f + x)); // top left
		quad.add(new Float(1.0f + 0.0f));
		quad.add(new Float(0.0f + y));
		quad.add(new Float(0.0f + x)); // bottom left
		quad.add(new Float(1.0f + 0.0f));
		quad.add(new Float(1.0f + y));
		quad.add(new Float(1.0f + x)); // bottom right
		quad.add(new Float(1.0f + 0.0f));
		quad.add(new Float(1.0f + y));
		
		return quad;
	}
	
	private List<Float> getVerticesMountain() {
		return null;
	}
	
	private List<Float> getVerticesWater() {
		return null;
	}
	
	/** Textures **/

	public List<Float> getTextures(int type) {
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
		return null;
	}
	
	private List<Float> getTexturesWater() {
		return null;
	}
	
	/** Colors **/

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
		
		float color = 1.0f - (random.nextFloat() / 2.0f);
		
		colors.add(color);
		colors.add(color);
		colors.add(color);
		colors.add(color);
		colors.add(color);
		colors.add(color);
		colors.add(color);
		colors.add(color);
		colors.add(color);
		colors.add(color);
		colors.add(color);
		colors.add(color);
		
		return colors;
	}
	
	private List<Float> getColorsMountain() {
		return null;
	}
	
	private List<Float> getColorsWater() {
		return null;
	}
}
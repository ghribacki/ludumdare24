package net.ghribacki.ld24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {
	private int shaderProgram;
	private int vertexShader;
	private int fragmentShader;
	
	public Shader() {
		this.shaderProgram = GL20.glCreateProgram();
		this.vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		this.fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		
		StringBuilder vertexShaderSource = new StringBuilder();
		StringBuilder fragmentShaderSource = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("res/shader.vert"));
			String line;
			while ((line = reader.readLine()) != null) {
				vertexShaderSource.append(line).append('\n');
			}
			reader.close();
			
			reader = new BufferedReader(new FileReader("res/shader.frag"));
			while ((line = reader.readLine()) != null) {
				fragmentShaderSource.append(line).append('\n');
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GL20.glShaderSource(this.vertexShader, vertexShaderSource);
		GL20.glCompileShader(this.vertexShader);
		if (GL20.glGetShader(this.vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Erro compiling vertex shader!");
		}
		GL20.glShaderSource(this.fragmentShader, fragmentShaderSource);
		GL20.glCompileShader(this.fragmentShader);
		if (GL20.glGetShader(this.fragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Erro compiling fragment shader!");
		}
		
		GL20.glAttachShader(this.shaderProgram, this.vertexShader);
		GL20.glAttachShader(this.shaderProgram, this.fragmentShader);
		GL20.glLinkProgram(this.shaderProgram);
		GL20.glValidateProgram(this.shaderProgram);
	}
	
	public void beginShaderProgram() {
		GL20.glUseProgram(this.shaderProgram);
	}
	
	public void endShaderProgram() {
		GL20.glUseProgram(0);
	}
	
	public void clear() {
		GL20.glDeleteProgram(this.shaderProgram);
		GL20.glDeleteShader(this.vertexShader);
		GL20.glDeleteShader(this.fragmentShader);
	}
}

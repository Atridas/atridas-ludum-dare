package cat.atridas87.ld26.render;

import java.io.InputStream;
import java.nio.FloatBuffer;

import javax.vecmath.Matrix4f;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import cat.atridas87.ld26.Utils;

public class ShaderManager {
	
	public static ShaderManager instance;
	
	private Program programs[];
	
	private Program currentProgram;

	public static final int POSITION_ATTRIBUTE = 0;
	public static final int COLOR_ATTRIBUTE = 1;
	public static final int TEX_COORD_ATTRIBUTE = 2;
	
	public ShaderManager() {
		int vertexShader = compileShader("resources/shaders/shader.vsh", GL20.GL_VERTEX_SHADER);
		int colorShader  = compileShader("resources/shaders/color.fsh", GL20.GL_FRAGMENT_SHADER);
		
		programs = new Program[ProgramType.values().length];
		
		programs[ProgramType.TEXTURED.ordinal()] = linkProgram(vertexShader, colorShader);
		
		

		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(colorShader);
	}
	
	
	private int compileShader(String shaderFile, int type) {
		
		String source = Utils.loadFileAsString(shaderFile);
		
		int s = GL20.glCreateShader(type);
		
		GL20.glShaderSource(s, source);
		
		GL20.glCompileShader(s);
		
		
		String info = GL20.glGetShaderInfoLog(s, 2048);
		System.out.println(info);

		
		int cs = GL20.glGetShaderi(s, GL20.GL_COMPILE_STATUS);
		
		if(cs == GL11.GL_FALSE) {
			System.err.println("error compiling shader " + shaderFile);
			return 0;
		}
		
		return s;
	}
	
	private Program linkProgram(int vs, int fs)
	{
		Program p = new Program();
		p.program = GL20.glCreateProgram();

		GL20.glAttachShader(p.program, vs);
		GL20.glAttachShader(p.program, fs);
		

		GL20.glBindAttribLocation(p.program, POSITION_ATTRIBUTE, "aPosition");
		GL20.glBindAttribLocation(p.program, COLOR_ATTRIBUTE, "aColor");
		GL20.glBindAttribLocation(p.program, TEX_COORD_ATTRIBUTE, "aTexcoord");
		
		
		GL20.glLinkProgram(p.program);
		
		String info = GL20.glGetProgramInfoLog(p.program, 2048);
		System.out.println(info);
		
		
		int ls = GL20.glGetProgrami(p.program, GL20.GL_LINK_STATUS);
		if(ls == GL11.GL_FALSE) {
			System.err.println("Error linking program");
			return null;
		}

		p.projectionUniform = GL20.glGetUniformLocation(p.program, "uProjection");
		p.viewUniform = GL20.glGetUniformLocation(p.program, "uView");
		p.modelUniform = GL20.glGetUniformLocation(p.program, "uModel");
		p.textureUniform = GL20.glGetUniformLocation(p.program, "uTexture");
		
		return p;
	}
	
	public void setCurrentProgram(ProgramType program) {
		currentProgram = programs[program.ordinal()];
		
		GL20.glUseProgram(currentProgram.program);
	}
	
	private FloatBuffer fbAux = BufferUtils.createFloatBuffer(16);
	public void setProjectionMatrix(Matrix4f m)
	{
		fbAux.put(m.m00);
		fbAux.put(m.m01);
		fbAux.put(m.m02);
		fbAux.put(m.m03);
		fbAux.put(m.m10);
		fbAux.put(m.m11);
		fbAux.put(m.m12);
		fbAux.put(m.m13);
		fbAux.put(m.m20);
		fbAux.put(m.m21);
		fbAux.put(m.m22);
		fbAux.put(m.m23);
		fbAux.put(m.m30);
		fbAux.put(m.m31);
		fbAux.put(m.m32);
		fbAux.put(m.m33);
		fbAux.flip();
		
		GL20.glUniformMatrix4(currentProgram.projectionUniform, true, fbAux);
		//fbAux.flip();
	}
	
	public void setViewMatrix(Matrix4f m)
	{
		fbAux.put(m.m00);
		fbAux.put(m.m01);
		fbAux.put(m.m02);
		fbAux.put(m.m03);
		fbAux.put(m.m10);
		fbAux.put(m.m11);
		fbAux.put(m.m12);
		fbAux.put(m.m13);
		fbAux.put(m.m20);
		fbAux.put(m.m21);
		fbAux.put(m.m22);
		fbAux.put(m.m23);
		fbAux.put(m.m30);
		fbAux.put(m.m31);
		fbAux.put(m.m32);
		fbAux.put(m.m33);
		fbAux.flip();
		
		GL20.glUniformMatrix4(currentProgram.viewUniform, true, fbAux);
		//fbAux.flip();
	}
	
	public void setModelMatrix(Matrix4f m)
	{
		fbAux.put(m.m00);
		fbAux.put(m.m01);
		fbAux.put(m.m02);
		fbAux.put(m.m03);
		fbAux.put(m.m10);
		fbAux.put(m.m11);
		fbAux.put(m.m12);
		fbAux.put(m.m13);
		fbAux.put(m.m20);
		fbAux.put(m.m21);
		fbAux.put(m.m22);
		fbAux.put(m.m23);
		fbAux.put(m.m30);
		fbAux.put(m.m31);
		fbAux.put(m.m32);
		fbAux.put(m.m33);
		fbAux.flip();
		
		GL20.glUniformMatrix4(currentProgram.modelUniform, true, fbAux);
		//fbAux.flip();
	}
	
	public void setTexturePosition(int position)
	{
		GL20.glUniform1i(currentProgram.textureUniform, position);
	}
	
	
	private static class Program {
		int program;

		int projectionUniform;
		int viewUniform;
		int modelUniform;
		int textureUniform;
	}
	
	
	public enum ProgramType {
		TEXTURED
	}
}

package cat.atridas87.ld26.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import cat.atridas87.ld27.Utils;

public class ShaderManager {
	
	public static ShaderManager instance;
	
	private Program programs[];
	
	private Program currentProgram;

	public static final int POSITION_ATTRIBUTE = 0;
	public static final int TEX_COORD_ATTRIBUTE = 1;
	
	public ShaderManager() {
		int vertexShader  = compileShader("resources/shaders/ortho.vsh", GL20.GL_VERTEX_SHADER);

		int fragmentShader  = compileShader("resources/shaders/textured.fsh", GL20.GL_FRAGMENT_SHADER);
		
		programs = new Program[ProgramType.values().length];

		programs[ProgramType.TEXTURED.ordinal()] = linkProgram(vertexShader, fragmentShader);
		
		
		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);
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
		GL20.glBindAttribLocation(p.program, TEX_COORD_ATTRIBUTE, "aTexcoord");
		
		
		GL20.glLinkProgram(p.program);
		
		String info = GL20.glGetProgramInfoLog(p.program, 2048);
		System.out.println(info);
		
		
		int ls = GL20.glGetProgrami(p.program, GL20.GL_LINK_STATUS);
		if(ls == GL11.GL_FALSE) {
			System.err.println("Error linking program");
			return null;
		}

		p.textureUniform = GL20.glGetUniformLocation(p.program, "uTexture");
		p.screenSize     = GL20.glGetUniformLocation(p.program, "uScreenSize");
		p.position       = GL20.glGetUniformLocation(p.program, "uPosition");
		p.texcoord       = GL20.glGetUniformLocation(p.program, "uTexcoord");
		
		return p;
	}
	
	public void setCurrentProgram(ProgramType program) {
		currentProgram = programs[program.ordinal()];
		
		GL20.glUseProgram(currentProgram.program);
	}
	
	
	public void setTexturePosition(int position)
	{
		GL20.glUniform1i(currentProgram.textureUniform, position);
	}
	
	public void setScreenSize(float width, float height)
	{
		GL20.glUniform2f(currentProgram.screenSize, width, height);
	}
	
	public void setPosition(float x, float y)
	{
		GL20.glUniform2f(currentProgram.position, x, y);
	}
	
	public void setTexcoords(float x, float y, float w, float h)
	{
		GL20.glUniform4f(currentProgram.texcoord, x, y, w, h);
	}
	
	
	private static class Program {
		int program;

		int textureUniform;
		int screenSize;
		int position;
		int texcoord;
	}
	
	
	public enum ProgramType {
		TEXTURED
	}
}

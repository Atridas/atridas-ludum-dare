package cat.atridas87.ld26.render;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class Model {
	private int vao;
	private int numIndexes;
	private int mode;
	
	public Model(float positions[], float colors[], short indexes[], int _mode) {
		mode = _mode;
		
		numIndexes = indexes.length;
		
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(positions.length * (3+4));
		
		for(int i = 0; i < positions.length / 3; i++) {
			float x = positions[i * 3 + 0];
			float y = positions[i * 3 + 1];
			float z = positions[i * 3 + 2];
			
			float r = colors[i * 4 + 0];
			float g = colors[i * 4 + 1];
			float b = colors[i * 4 + 2];
			float a = colors[i * 4 + 3];

			vertexBuffer.put(x);
			vertexBuffer.put(y);
			vertexBuffer.put(z);
			vertexBuffer.put(r);
			vertexBuffer.put(g);
			vertexBuffer.put(b);
			vertexBuffer.put(a);
		}
		
		ShortBuffer indexBuffer = BufferUtils.createShortBuffer(indexes.length);
		
		indexBuffer.put(indexes);

		vertexBuffer.flip();
		indexBuffer.flip();

		int vb = GL15.glGenBuffers();
		int ib = GL15.glGenBuffers();
		
		
		vao = ARBVertexArrayObject.glGenVertexArrays();
		ARBVertexArrayObject.glBindVertexArray(vao);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vb);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);

		
		GL20.glEnableVertexAttribArray(ShaderManager.POSITION_ATTRIBUTE);
		GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 3, GL11.GL_FLOAT, false, 7 * 4, 0);
		
		GL20.glEnableVertexAttribArray(ShaderManager.COLOR_ATTRIBUTE);
		GL20.glVertexAttribPointer(ShaderManager.COLOR_ATTRIBUTE, 4, GL11.GL_FLOAT, false, 7 * 4, 3 * 4);
		
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ib);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);

		ARBVertexArrayObject.glBindVertexArray(0);
		

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	
	public void draw() {
		ARBVertexArrayObject.glBindVertexArray(vao);
		
		GL11.glDrawElements(mode, numIndexes, GL11.GL_UNSIGNED_SHORT, 0);

		ARBVertexArrayObject.glBindVertexArray(0);
	}
	
}

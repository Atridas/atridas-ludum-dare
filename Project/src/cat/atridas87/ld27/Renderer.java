package cat.atridas87.ld27;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.FloatBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;
import org.newdawn.slick.util.ResourceLoader;

import cat.atridas87.ld26.render.ShaderManager;

import static cat.atridas87.ld27.LD27.*;

public class Renderer {

	private Texture caselles, graella;
	private ShaderManager shaderManager;

	private FloatBuffer casellaPositionBuffer;
	private FloatBuffer casellaTexCoordBuffer;

	Renderer() throws Exception {
		URL url = ResourceLoader.getResource("resources/quadre.png");
		BufferedImage bi = ImageIO.read(url);

		graella = BufferedImageUtil.getTexture("graella", bi);

		url = ResourceLoader.getResource("resources/Caselles.png");
		bi = ImageIO.read(url);

		caselles = BufferedImageUtil.getTexture("caselles", bi);
		
		shaderManager = new ShaderManager();
		shaderManager.setCurrentProgram(ShaderManager.ProgramType.TEXTURED);
		shaderManager.setTexturePosition(0);
		

		casellaPositionBuffer = BufferUtils.createFloatBuffer(4 * 2);
		casellaTexCoordBuffer = BufferUtils.createFloatBuffer(4 * 2);
		casellaPositionBuffer.put(new float[]{0,0, 170,0, 170,170, 0,170});
		casellaTexCoordBuffer.put(new float[]{0,1, 1,1, 1,0, 0,0});
		
		
		GL20.glEnableVertexAttribArray(ShaderManager.POSITION_ATTRIBUTE);
		GL20.glEnableVertexAttribArray(ShaderManager.TEX_COORD_ATTRIBUTE);
	}
	
	
	void render(float x, float y, float width, float height, TerrenyDeJoc terrenyDeJoc) {
		if(x + width > TAMANY_CASELLA * GRAELLA_TAMANY_X) {
			x = TAMANY_CASELLA * GRAELLA_TAMANY_X - width;
		}
		if(y + height > TAMANY_CASELLA * GRAELLA_TAMANY_Y) {
			y = TAMANY_CASELLA * GRAELLA_TAMANY_Y - height;
		}
		
		shaderManager.setScreenSize(width, height);

		int casellaX = (int)(x / TAMANY_CASELLA);
		int casellaY = (int)(y / TAMANY_CASELLA);

		int ultimaCasellesX = (int)(x + width / TAMANY_CASELLA) + 1;
		int ultimaCasellesY = (int)(y + height / TAMANY_CASELLA) + 1;

		casellaPositionBuffer.rewind();
		casellaTexCoordBuffer.rewind();
		GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false, 0, casellaPositionBuffer);
		GL20.glVertexAttribPointer(ShaderManager.TEX_COORD_ATTRIBUTE, 2, false, 0, casellaTexCoordBuffer);

		graella.bind();
		
		float cuadreX =  x - casellaX * TAMANY_CASELLA ;
		
		if(cuadreX > 0) {
			cuadreX = TAMANY_CASELLA - cuadreX;
		}
		
		for(int i = casellaX; i < ultimaCasellesX; i++) {

			float cuadreY =  y - casellaY * TAMANY_CASELLA ;
			
			if(cuadreY > 0) {
				cuadreY = TAMANY_CASELLA - cuadreY;
			}
			
			for(int j = casellaY; j < ultimaCasellesY; j++) {
				shaderManager.setPosition(cuadreX, cuadreY);
				
				GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
				
				cuadreY += TAMANY_CASELLA;
			}
			cuadreX += TAMANY_CASELLA;
		}
	}
	
}

package cat.atridas87.ld27;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.FloatBuffer;
import java.util.ArrayList;

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

	private Texture caselles, graella, recursos;
	private ShaderManager shaderManager;

	private FloatBuffer casellaPositionBuffer;
	private FloatBuffer recursosPositionBuffer;
	private FloatBuffer casellaTexCoordBuffer;

	Renderer() throws Exception {
		URL url = ResourceLoader.getResource("resources/quadre.png");
		BufferedImage bi = ImageIO.read(url);

		graella = BufferedImageUtil.getTexture("graella", bi);

		url = ResourceLoader.getResource("resources/Caselles.png");
		bi = ImageIO.read(url);

		caselles = BufferedImageUtil.getTexture("caselles", bi);

		url = ResourceLoader.getResource("resources/recursos.png");
		bi = ImageIO.read(url);

		recursos = BufferedImageUtil.getTexture("recursos", bi);

		shaderManager = new ShaderManager();
		shaderManager.setCurrentProgram(ShaderManager.ProgramType.TEXTURED);
		shaderManager.setTexturePosition(0);

		casellaPositionBuffer = BufferUtils.createFloatBuffer(4 * 2);
		recursosPositionBuffer = BufferUtils.createFloatBuffer(4 * 2);
		casellaTexCoordBuffer = BufferUtils.createFloatBuffer(4 * 2);
		casellaPositionBuffer
				.put(new float[] { 0, 0, 170, 0, 170, 170, 0, 170 });
		casellaTexCoordBuffer.put(new float[] { 0, 1, 1, 1, 1, 0, 0, 0 });
		recursosPositionBuffer
		.put(new float[] { 0, 0, 64, 0, 64, 64, 0, 64 });

		GL20.glEnableVertexAttribArray(ShaderManager.POSITION_ATTRIBUTE);
		GL20.glEnableVertexAttribArray(ShaderManager.TEX_COORD_ATTRIBUTE);
	}

	void render(float x, float y, float width, float height,
			TerrenyDeJoc terrenyDeJoc, float recursX, float recursY, Recurs recursTransportat) {
		if (x + width >= TAMANY_CASELLA * GRAELLA_TAMANY_X) {
			x = TAMANY_CASELLA * GRAELLA_TAMANY_X - width;
		}
		if (y + height >= TAMANY_CASELLA * GRAELLA_TAMANY_Y) {
			y = TAMANY_CASELLA * GRAELLA_TAMANY_Y - height;
		}

		shaderManager.setScreenSize(width, height);

		int casellaX = (int) (x / TAMANY_CASELLA);
		int casellaY = (int) (y / TAMANY_CASELLA);

		int ultimaCasellesX = (int) ((x + width) / TAMANY_CASELLA) + 1;
		int ultimaCasellesY = (int) ((y + height) / TAMANY_CASELLA) + 1;

		casellaPositionBuffer.rewind();
		casellaTexCoordBuffer.rewind();
		recursosPositionBuffer.rewind();
		GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false,
				0, casellaPositionBuffer);
		GL20.glVertexAttribPointer(ShaderManager.TEX_COORD_ATTRIBUTE, 2, false,
				0, casellaTexCoordBuffer);

		for (int pass = 0; pass < 3; pass++) {
			switch(pass) {
			case 0:
				// Cada un dels cuadrets
				caselles.bind();
				break;
			case 1:
				// Graella

				graella.bind();
				shaderManager.setTexcoords(0, 0, 1, 1);
				break;
				
			case 2:
				recursos.bind();
				GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false,
						0, recursosPositionBuffer);
				
				break;
			}
	
			float cuadreX = casellaX * TAMANY_CASELLA - x;
	
			for (int i = casellaX; i < ultimaCasellesX; i++) {
				
				if(i >= GRAELLA_TAMANY_X) {
					break;
				}
	
				float cuadreY = casellaY * TAMANY_CASELLA - y;
	
				for (int j = casellaY; j < ultimaCasellesY; j++) {
					
					if(j >= GRAELLA_TAMANY_Y) {
						break;
					}
					Casella casella;
					int indexX;
					int indexY;
					switch(pass) {
					case 0:
						shaderManager.setPosition(cuadreX, cuadreY);
						casella = terrenyDeJoc.caselles[i][j];
						indexX = casella.type.spriteX;
						indexY = casella.type.spriteY;
						shaderManager
								.setTexcoords((indexX * 170) / 1024.f,
										(indexY * 170) / 1024.f, 170.f / 1024.f,
										170.f / 1024.f);
		
						GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
						break;
					case 1:
						shaderManager.setPosition(cuadreX, cuadreY);

						GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
						break;
					case 2:
						casella = terrenyDeJoc.caselles[i][j];
						
						for(int recursPass = 0; recursPass < 3; recursPass++) {
							ArrayList<Recurs> recursos = null;
							float baseX = 0, baseY = 0;
							switch(recursPass) {
							case 0:
								recursos = casella.recursosEntrants;
								baseX = 90;
								baseY = 90;
								break;
							case 1:
								recursos = casella.recursosGenerats;
								baseX = 90;
								baseY = 10;
								break;
							case 2:
								recursos = casella.treballadors;
								baseX = 10;
								baseY = 50;
								break;
							}
							float dt = 0;
							if(recursos.size() > 1) {
								dt = 10.f / recursos.size() - 1;
							}
							
							for(int r = 0; r < recursos.size(); r++) {
								float px = baseX + r*dt;
								float py = baseY + r*dt;
								shaderManager.setPosition(cuadreX + px, cuadreY + py);
								
								Recurs recurs = recursos.get(r);
								indexX = recurs.spriteX;
								indexY = recurs.spriteY;
								shaderManager
										.setTexcoords((indexX * 64) / 512.f,
												(indexY * 64) / 512.f, 64.f / 512.f,
												64.f / 512.f);
								
								GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
							}
						}
						
						break;
					}
	
					cuadreY += TAMANY_CASELLA;
				}
				cuadreX += TAMANY_CASELLA;
			}
		}

		if(recursTransportat != null) {
			shaderManager.setPosition(recursX - 32, recursY - 32);
			int indexX = recursTransportat.spriteX;
			int indexY = recursTransportat.spriteY;
			shaderManager
					.setTexcoords((indexX * 64) / 512.f,
							(indexY * 64) / 512.f, 64.f / 512.f,
							64.f / 512.f);
			
			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
		}
	}

}

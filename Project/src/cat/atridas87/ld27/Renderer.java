package cat.atridas87.ld27;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;
import org.newdawn.slick.util.ResourceLoader;

import cat.atridas87.ld26.render.ShaderManager;

import static cat.atridas87.ld27.LD27.*;

public class Renderer {

	private Texture caselles, graella, recursos;
	private ShaderManager shaderManager;

	private FloatBuffer casellaPositionBuffer, recursosPositionBuffer,
			texCoordBuffer;
	private FloatBuffer casellaSmallPositionBuffer,
			recursosSmallPositionBuffer;

	private Font font, fontTimer;

	Renderer() throws Exception {

		font = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.PLAIN,
				20), true);
		fontTimer = new TrueTypeFont(new java.awt.Font("Courier New",
				java.awt.Font.PLAIN, 40), true);

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
		casellaSmallPositionBuffer = BufferUtils.createFloatBuffer(4 * 2);
		recursosSmallPositionBuffer = BufferUtils.createFloatBuffer(4 * 2);
		texCoordBuffer = BufferUtils.createFloatBuffer(4 * 2);
		casellaPositionBuffer
				.put(new float[] { 0, 0, 170, 0, 170, 170, 0, 170 });
		texCoordBuffer.put(new float[] { 0, 1, 1, 1, 1, 0, 0, 0 });
		recursosPositionBuffer.put(new float[] { 0, 0, 64, 0, 64, 64, 0, 64 });

		casellaSmallPositionBuffer
				.put(new float[] { 0, 0, 85, 0, 85, 85, 0, 85 });
		recursosSmallPositionBuffer.put(new float[] { 0, 0, 32, 0, 32, 32, 0, 32 });

		GL20.glEnableVertexAttribArray(ShaderManager.POSITION_ATTRIBUTE);
		GL20.glEnableVertexAttribArray(ShaderManager.TEX_COORD_ATTRIBUTE);

		texCoordBuffer.rewind();
		GL20.glVertexAttribPointer(ShaderManager.TEX_COORD_ATTRIBUTE, 2, false,
				0, texCoordBuffer);
	}

	void render(int x, int y, int width, int height, TerrenyDeJoc terrenyDeJoc,
			int recursX, int recursY, Recurs recursTransportat) {

		if (x + width >= TAMANY_CASELLA * GRAELLA_TAMANY_X) {
			x = TAMANY_CASELLA * GRAELLA_TAMANY_X - width;
		}
		if (y + height >= TAMANY_CASELLA * GRAELLA_TAMANY_Y) {
			y = TAMANY_CASELLA * GRAELLA_TAMANY_Y - height;
		}

		shaderManager.setCurrentProgram(ShaderManager.ProgramType.TEXTURED);

		shaderManager.setScreenSize(width, height);

		int casellaX = (int) (x / TAMANY_CASELLA);
		int casellaY = (int) (y / TAMANY_CASELLA);

		int ultimaCasellesX = (int) ((x + width) / TAMANY_CASELLA) + 1;
		int ultimaCasellesY = (int) ((y + height) / TAMANY_CASELLA) + 1;

		casellaPositionBuffer.rewind();
		recursosPositionBuffer.rewind();
		GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false,
				0, casellaPositionBuffer);

		for (int pass = 0; pass < 3; pass++) {
			switch (pass) {
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
				GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2,
						false, 0, recursosPositionBuffer);

				break;
			}

			float cuadreX = casellaX * TAMANY_CASELLA - x;

			for (int i = casellaX; i < ultimaCasellesX; i++) {

				if (i >= GRAELLA_TAMANY_X) {
					break;
				}

				float cuadreY = casellaY * TAMANY_CASELLA - y;

				for (int j = casellaY; j < ultimaCasellesY; j++) {

					if (j >= GRAELLA_TAMANY_Y) {
						break;
					}
					Casella casella;
					int indexX;
					int indexY;
					switch (pass) {
					case 0:
						shaderManager.setPosition(cuadreX, cuadreY);
						casella = terrenyDeJoc.caselles[i][j];
						indexX = casella.type.spriteX;
						indexY = casella.type.spriteY;
						shaderManager.setTexcoords((indexX * 170) / 1024.f,
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

						for (int recursPass = 0; recursPass < 3; recursPass++) {
							ArrayList<Recurs> recursos = null;
							float baseX = 0, baseY = 0;
							switch (recursPass) {
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
							if (recursos.size() > 1) {
								dt = 10.f / recursos.size() - 1;
							}

							for (int r = 0; r < recursos.size(); r++) {
								float px = baseX + r * dt;
								float py = baseY + r * dt;
								shaderManager.setPosition(cuadreX + px, cuadreY
										+ py);

								Recurs recurs = recursos.get(r);
								indexX = recurs.spriteX;
								indexY = recurs.spriteY;
								shaderManager.setTexcoords(
										(indexX * 64) / 512.f,
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

		if (recursTransportat != null) {
			shaderManager.setPosition(recursX - 32, recursY - 32);
			int indexX = recursTransportat.spriteX;
			int indexY = recursTransportat.spriteY;
			shaderManager.setTexcoords((indexX * 64) / 512.f,
					(indexY * 64) / 512.f, 64.f / 512.f, 64.f / 512.f);

			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
		}
	}

	void renderHUD(int w, int h, TerrenyDeJoc terrenyDeJoc) {

		shaderManager.setScreenSize(w, h);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, w, h, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		renderGameInfo(terrenyDeJoc);

		renderSelectedField(terrenyDeJoc);
	}

	private void renderGameInfo(TerrenyDeJoc terrenyDeJoc) {

		GL20.glUseProgram(0);

		int partEntera = (int) Math.ceil(terrenyDeJoc.timer);

		fontTimer.drawString(
				260 - fontTimer.getWidth(Integer.toString(partEntera)) / 2, 10,
				Integer.toString(partEntera));

		font.drawString(10, 10, "punts: " + terrenyDeJoc.pv + " / "
				+ POINTS_TO_WIN);
		font.drawString(10, 35, "ticks: " + terrenyDeJoc.ticks);

	}

	private void renderSelectedField(TerrenyDeJoc terrenyDeJoc) {
		if (terrenyDeJoc.casellaSeleccionada != null) {
			shaderManager.setCurrentProgram(ShaderManager.ProgramType.TEXTURED);

			casellaSmallPositionBuffer.rewind();
			recursosSmallPositionBuffer.rewind();
			GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false,
					0, casellaSmallPositionBuffer);

			caselles.bind();

			shaderManager.setPosition(25, 495);
			int indexX = terrenyDeJoc.casellaSeleccionada.type.spriteX;
			int indexY = terrenyDeJoc.casellaSeleccionada.type.spriteY;
			shaderManager.setTexcoords((indexX * 170) / 1024.f,
					(indexY * 170) / 1024.f, 170.f / 1024.f,
					170.f / 1024.f);

			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
			
			// -------------
			
			recursos.bind();
			GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false,
					0, recursosSmallPositionBuffer);
			
			for (int pass = 0; pass < 3; pass++) {
				ArrayList<Recurs> recursos = null;
				int y = 0;
				switch(pass) {
				case 0:
					recursos = terrenyDeJoc.casellaSeleccionada.treballadors;
					y = 571;
					break;
				case 1:
					recursos = terrenyDeJoc.casellaSeleccionada.recursosEntrants;
					y = 521;
					break;
				case 2:
					recursos = terrenyDeJoc.casellaSeleccionada.recursosGenerats;
					y = 471;
					break;
				}

				float dx = 150.f / (recursos.size() + 1);
				
				for(int r = 0; r < recursos.size(); r++) {
					Recurs recurs = recursos.get(r);
					int x = (int)(137.5f + dx * (r + 1) - 16);

					shaderManager.setPosition(x, y);

					indexX = recurs.spriteX;
					indexY = recurs.spriteY;
					shaderManager.setTexcoords(
							(indexX * 64) / 512.f,
							(indexY * 64) / 512.f, 64.f / 512.f,
							64.f / 512.f);

					GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
				}
			}
			
			//GL20.glUseProgram(0);
		}
	}
}

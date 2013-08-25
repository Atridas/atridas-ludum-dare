package cat.atridas87.ld27;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;
import org.newdawn.slick.util.ResourceLoader;

import cat.atridas87.ld26.render.ShaderManager;

import static cat.atridas87.ld27.LD27.*;

public class Renderer {

	private Texture caselles, graella, recursos, hud;
	private ShaderManager shaderManager;

	private FloatBuffer positionBuffer;

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

		url = ResourceLoader.getResource("resources/hud.png");
		bi = ImageIO.read(url);

		hud = BufferedImageUtil.getTexture("recursos", bi);

		shaderManager = new ShaderManager();
		shaderManager.setCurrentProgram(ShaderManager.ProgramType.TEXTURED);
		shaderManager.setTexturePosition(0);

		positionBuffer = BufferUtils.createFloatBuffer(4 * 2);
		positionBuffer.put(new float[] { 0, 0, 1, 0, 1, 1, 0, 1 });

		GL20.glEnableVertexAttribArray(ShaderManager.POSITION_ATTRIBUTE);

		positionBuffer.rewind();
		GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false,
				0, positionBuffer);
		shaderManager.setCurrentProgram(ShaderManager.ProgramType.TEXTURED);
	}

	void render(int x, int y, int width, int height, TerrenyDeJoc terrenyDeJoc) {

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
						shaderManager.setPosition(cuadreX, cuadreY, 170, 170);
						casella = terrenyDeJoc.caselles[i][j];
						indexX = casella.type.spriteX;
						indexY = casella.type.spriteY;
						shaderManager.setTexcoords((indexX * 170) / 1024.f,
								(indexY * 170) / 1024.f, 170.f / 1024.f,
								170.f / 1024.f);

						GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
						break;
					case 1:
						shaderManager.setPosition(cuadreX, cuadreY, 170, 170);

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
										+ py, 64, 64);

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

	}

	void renderHUD(int w, int h, TerrenyDeJoc terrenyDeJoc) {

		shaderManager.setScreenSize(w, h);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, w, h, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		for (int pass = 0; pass < 4; pass++) {
			switch (pass) {
			case 0:
				caselles.bind();
				break;
			case 1:
				hud.bind();
				shaderManager.setPosition(0, 800 - 350, 300, 350);
				shaderManager
						.setTexcoords(0, 0, 300.f / 1024.f, 350.f / 1024.f);
				GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
				break;
			case 2:
				recursos.bind();
				break;
			case 3:
				GL20.glUseProgram(0);
				break;
			}

			renderGameInfo(pass, terrenyDeJoc);

			renderSelectedField(pass, terrenyDeJoc);

			renderMagatzem(pass, terrenyDeJoc);
		}
	}

	private void renderGameInfo(int pass, TerrenyDeJoc terrenyDeJoc) {

		switch (pass) {
		case 1:

			float completed = (float) terrenyDeJoc.pv / (float) POINTS_TO_WIN;

			shaderManager.setPosition(20, 700, 148, 38);
			shaderManager.setTexcoords(41 / 1024.f, 401 / 1024.f,
					148.f / 1024.f, 38.f / 1024.f);
			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);

			shaderManager.setPosition(20, 700, 148 * completed, 38);
			shaderManager.setTexcoords(41 / 1024.f, 452 / 1024.f,
					148.f / 1024.f * completed, 38.f / 1024.f);
			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);

			break;
		case 3:
			int partEntera = (int) Math.ceil(terrenyDeJoc.timer);

			fontTimer.drawString(
					245 - fontTimer.getWidth(Integer.toString(partEntera)) / 2,
					60, Integer.toString(partEntera), Color.black);

			font.drawString(
					130 - fontTimer.getWidth(Integer.toString(terrenyDeJoc.pv)) / 2,
					35, Integer.toString(terrenyDeJoc.pv), Color.white);
			font.drawString(
					130 - fontTimer.getWidth(Integer.toString(terrenyDeJoc.ticks)) / 2,
					135, Integer.toString(terrenyDeJoc.ticks), Color.white);
			break;
		}
	}

	private void renderSelectedField(int pass, TerrenyDeJoc terrenyDeJoc) {
		switch (pass) {
		case 0:
			if (terrenyDeJoc.casellaSeleccionada != null) {

				int indexX = terrenyDeJoc.casellaSeleccionada.type.spriteX;
				int indexY = terrenyDeJoc.casellaSeleccionada.type.spriteY;
				shaderManager
						.setTexcoords((indexX * 170) / 1024.f,
								(indexY * 170) / 1024.f, 170.f / 1024.f,
								170.f / 1024.f);

			} else {
				shaderManager.setTexcoords(
						(Casella.Type.CAMP.spriteX * 170) / 1024.f,
						(Casella.Type.CAMP.spriteY * 170) / 1024.f,
						170.f / 1024.f, 170.f / 1024.f);
			}
			shaderManager.setPosition(25, 495, 85, 85);
			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
			break;
		case 2:
			if (terrenyDeJoc.casellaSeleccionada != null) {
				for (int linea = 0; linea < 3; linea++) {
					ArrayList<Recurs> recursos = null;
					int y = 0;
					switch (linea) {
					case 0:
						recursos = terrenyDeJoc.casellaSeleccionada.treballadors;
						y = 572;
						break;
					case 1:
						recursos = terrenyDeJoc.casellaSeleccionada.recursosEntrants;
						y = 522;
						break;
					case 2:
						recursos = terrenyDeJoc.casellaSeleccionada.recursosGenerats;
						y = 472;
						break;
					}

					float dx = 150.f / (recursos.size() + 1);

					for (int r = 0; r < recursos.size(); r++) {
						Recurs recurs = recursos.get(r);
						int x = (int) (137.5f + dx * (r + 1) - 16);

						shaderManager.setPosition(x, y, 32, 32);

						int indexX = recurs.spriteX;
						int indexY = recurs.spriteY;
						shaderManager.setTexcoords((indexX * 64) / 512.f,
								(indexY * 64) / 512.f, 64.f / 512.f,
								64.f / 512.f);

						GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
					}
				}
			}
			break;
		}
	}

	private void renderMagatzem(int pass, TerrenyDeJoc terrenyDeJoc) {

		float dx = 300.f / 6.f;
		float dy = 450.f / 14.f;

		int cont = 0;
		Iterator<Recurs> it = terrenyDeJoc.magatzem.keySet().iterator();
		while (it.hasNext()) {
			Recurs recurs = it.next();
			int number = terrenyDeJoc.magatzem.get(recurs);
			int columna = cont % 3;
			int fila = cont / 3;

			float baseX = dx * (columna * 2 + 1);
			float baseY = 450.f - dy * (fila * 2 + 1);

			switch (pass) {
			case 2:

				shaderManager.setPosition(baseX - 16 - 20, baseY - 16, 32, 32);

				if (number > 0 || terrenyDeJoc.knownRecursos.contains(recurs)) {
					int indexX = recurs.spriteX;
					int indexY = recurs.spriteY;
					shaderManager.setTexcoords((indexX * 64) / 512.f,
							(indexY * 64) / 512.f, 64.f / 512.f, 64.f / 512.f);
				} else {
					shaderManager.setTexcoords(
							(Recurs.unknownSpriteX * 64) / 512.f,
							(Recurs.unknownSpriteY * 64) / 512.f, 64.f / 512.f,
							64.f / 512.f);
				}
				GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);

				break;
			case 3:
				if (number > 0) {
					font.drawString(
							baseX + 35
									- font.getWidth(Integer.toString(number)),
							800 - baseY - 10, Integer.toString(number),
							Color.black);
				} else {
					font.drawString(baseX + 35 - font.getWidth("-"),
							800 - baseY - 10, "-", Color.black);
				}
				break;

			}

			cont++;
		}
	}

	void renderRecurs(int w, int h, int recursX, int recursY,
			Recurs recursTransportat) {

		shaderManager.setCurrentProgram(ShaderManager.ProgramType.TEXTURED);
		recursos.bind();

		shaderManager.setScreenSize(w, h);

		if (recursTransportat != null) {
			shaderManager.setPosition(recursX - 32, recursY - 32, 64, 64);
			int indexX = recursTransportat.spriteX;
			int indexY = recursTransportat.spriteY;
			shaderManager.setTexcoords((indexX * 64) / 512.f,
					(indexY * 64) / 512.f, 64.f / 512.f, 64.f / 512.f);

			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
		}
	}

}

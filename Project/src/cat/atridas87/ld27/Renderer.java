package cat.atridas87.ld27;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
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

import cat.atridas87.ld27.render.ShaderManager;

import static cat.atridas87.ld27.LD27.*;

public class Renderer {

	private final Texture caselles, graella, recursos, hud;
	private final HashMap<Popup.Type, Texture> popups = new HashMap<Popup.Type, Texture>();
	private final ShaderManager shaderManager;

	private final FloatBuffer positionBuffer;

	private final Font font, fontTimer;

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

		for (int i = 0; i < Popup.Type.values().length; i++) {
			url = ResourceLoader
					.getResource(Popup.Type.values()[i].textureName);
			bi = ImageIO.read(url);

			popups.put(Popup.Type.values()[i], BufferedImageUtil.getTexture(
					Popup.Type.values()[i].textureName, bi));
		}

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

	void render(int x, int y, int width, int height, LD27 ld27) {

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

		for (int pass = 0; pass < 4; pass++) {
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
				
			case 3:
				hud.bind();
				
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
						casella = ld27.terrenyDeJoc.caselles[i][j];
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
						casella = ld27.terrenyDeJoc.caselles[i][j];

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
					case 3:
						casella = ld27.terrenyDeJoc.caselles[i][j];
						renderPV(casella, (int)cuadreX, (int)cuadreY, ld27);
						break;
					}

					cuadreY += TAMANY_CASELLA;
				}
				cuadreX += TAMANY_CASELLA;
			}
		}

	}

	final private int vpS[], vpT[], vpW[], vpH[];
	
	{
		vpS = new int[8];
		vpT = new int[8];
		vpW = new int[8];
		vpH = new int[8];

		vpS[0] = 903;
		vpT[0] = 828;
		vpW[0] =  48;
		vpH[0] =  98;

		vpS[1] = 831;
		vpT[1] = 68;
		vpW[1] =  116;
		vpH[1] =  104;

		vpS[2] = 812;
		vpT[2] = 193;
		vpW[2] =  142;
		vpH[2] =  103;

		vpS[3] = 824;
		vpT[3] = 317;
		vpW[3] =  131;
		vpH[3] =  102;

		vpS[4] = 819;
		vpT[4] = 444;
		vpW[4] =  131;
		vpH[4] =  100;

		vpS[5] = 833;
		vpT[5] = 563;
		vpW[5] =  119;
		vpH[5] =  101;

		vpS[6] = 807;
		vpT[6] = 695;
		vpW[6] =  144;
		vpH[6] =  99;

		vpS[7] = 597;
		vpT[7] = 355;
		vpW[7] =  149;
		vpH[7] =  108;
	}
	
	private void renderPV(Casella casella, int baseX, int baseY, LD27 ld27) {
		float size = 0;
		
		if(ld27.terrenyDeJoc.timer > 9) {
			size = 10 - ld27.terrenyDeJoc.timer;
		} else {
			return;
		}
		
		
		boolean renderPV = false;
		int index = 0;
		switch(casella.pvGenerats) {
		case 1:
			index = 0;
			renderPV = true;
			break;
		case 2:
			index = 1;
			renderPV = true;
			break;
		case 25:
			index = 2;
			renderPV = true;
			break;
		case 50:
			index = 3;
			renderPV = true;
			break;
		case 75:
			index = 4;
			renderPV = true;
			break;
		case 100:
			index = 5;
			renderPV = true;
			break;
		case 200:
			index = 6;
			renderPV = true;
			break;
		case 300:
			index = 7;
			renderPV = true;
			break;
		}
		
		if(renderPV) {
			shaderManager.setTexcoords(vpS[index] / 1024.f, vpT[index] / 1024.f, vpW[index] / 1024.f, vpH[index] / 1024.f);

			float w = vpW[index] * size;
			float h = vpH[index] * size;
			
			shaderManager.setPosition(baseX + 85 - w / 2, baseY + 85 - h / 2, w, h);

			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
		}
		
	}

	void renderHUD(int w, int h, LD27 ld27) {

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
				shaderManager.setPosition(0, 0, 300, 800);
				shaderManager
						.setTexcoords(0, 0, 300.f / 1024.f, 800.f / 1024.f);
				GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
				break;
			case 2:
				recursos.bind();
				break;
			case 3:
				GL20.glUseProgram(0);
				break;
			}

			renderGameInfo(pass, ld27);

			renderSelectedField(pass, ld27);

			renderMagatzem(pass, ld27);
		}
	}

	private void renderGameInfo(int pass, LD27 ld27) {

		switch (pass) {
		case 1:

			float completed;
			if (ld27.terrenyDeJoc.timedMode) {
				completed = (float) ld27.terrenyDeJoc.ticks
						/ (float) TIMED_MODE_TICKS;
			} else {
				completed = (float) ld27.terrenyDeJoc.pv
						/ (float) POINTS_TO_WIN;
			}
			if (completed > 1) {
				completed = 1;
			}

			shaderManager.setPosition(73, 690, 92, 29);
			shaderManager.setTexcoords(712 / 1024.f, 911 / 1024.f,
					92.f / 1024.f, 29.f / 1024.f);
			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);

			shaderManager.setPosition(73, 690, 92 * completed, 29);
			shaderManager.setTexcoords(712 / 1024.f, 954 / 1024.f,
					92.f / 1024.f * completed, 29.f / 1024.f);
			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);

			// --- pause

			shaderManager.setPosition(PAUSE_X, PAUSE_Y, PAUSE_S, PAUSE_S);
			if (ld27.pausePressed && ld27.mouseOnPause) {
				shaderManager.setTexcoords(649 / 1024.f, 730 / 1024.f,
						64 / 1024.f, 64 / 1024.f);
			} else {
				shaderManager.setTexcoords(569 / 1024.f, 723 / 1024.f,
						64 / 1024.f, 64 / 1024.f);
			}
			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);

			break;
		case 3:
			int partEntera = (int) Math.ceil(ld27.terrenyDeJoc.timer);

			fontTimer.drawString(
					245 - fontTimer.getWidth(Integer.toString(partEntera)) / 2,
					60, Integer.toString(partEntera), Color.black);
			// TODO contador

			font.drawString(
					120 - font.getWidth(Integer.toString(ld27.terrenyDeJoc.pv)) / 2,
					27, Integer.toString(ld27.terrenyDeJoc.pv), Color.white);
			if (ld27.terrenyDeJoc.timedMode) {
				font.drawString(
						120 - font.getWidth(Integer.toString(TIMED_MODE_TICKS
								- ld27.terrenyDeJoc.ticks)) / 2,
						140,
						Integer.toString(TIMED_MODE_TICKS
								- ld27.terrenyDeJoc.ticks), Color.white);
			} else {
				font.drawString(120 - font.getWidth(Integer
						.toString(ld27.terrenyDeJoc.ticks)) / 2, 140, Integer
						.toString(ld27.terrenyDeJoc.ticks), Color.white);
			}
			break;
		}
	}

	private void renderSelectedField(int pass, LD27 ld27) {
		switch (pass) {
		case 0:
			if (ld27.terrenyDeJoc.casellaSeleccionada != null) {

				int indexX = ld27.terrenyDeJoc.casellaSeleccionada.type.spriteX;
				int indexY = ld27.terrenyDeJoc.casellaSeleccionada.type.spriteY;
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
			shaderManager.setPosition(25, 800 - 347, 85, 85);
			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
			break;
		case 2:
			if (ld27.terrenyDeJoc.casellaSeleccionada != null) {
				for (int linea = 0; linea < 3; linea++) {
					ArrayList<Recurs> recursos = null;
					int y = 0;
					switch (linea) {
					case 0:
						recursos = ld27.terrenyDeJoc.casellaSeleccionada.treballadors;
						y = 800 - 255;
						break;
					case 1:
						recursos = ld27.terrenyDeJoc.casellaSeleccionada.recursosEntrants;
						y = 800 - 310;
						break;
					case 2:
						recursos = ld27.terrenyDeJoc.casellaSeleccionada.recursosGenerats;
						y = 800 - 365;
						break;
					}

					float dx = 130.f / (recursos.size() + 1);

					for (int r = 0; r < recursos.size(); r++) {
						Recurs recurs = recursos.get(r);
						int x = (int) (147.5f + dx * (r + 1) - 16);

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

	private void renderMagatzem(int pass, LD27 ld27) {

		float dx = 280.f / 6.f;
		float dy = 380.f / 14.f;

		int cont = 0;
		Iterator<Recurs> it = ld27.terrenyDeJoc.magatzem.keySet().iterator();
		while (it.hasNext()) {
			Recurs recurs = it.next();
			int number = ld27.terrenyDeJoc.magatzem.get(recurs);
			int columna = cont % 3;
			int fila = cont / 3;

			float baseX = 10 + dx * (columna * 2 + 1);
			float baseY = 390.f - dy * (fila * 2 + 1);

			switch (pass) {
			case 2:

				shaderManager.setPosition(baseX - 16 - 20, baseY - 16, 32, 32);

				if (number > 0
						|| ld27.terrenyDeJoc.knownRecursos.contains(recurs)) {
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
							baseX + 20
									- font.getWidth(Integer.toString(number)),
							800 - baseY - 10, Integer.toString(number),
							Color.black);
				} else {
					font.drawString(baseX + 20 - font.getWidth("-"),
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

	void renderPopup(int w, int h, LD27 ld27) {

		shaderManager.setScreenSize(w, h);
		popups.get(ld27.popup.type).bind();

		int baseX = w / 2 - 350;
		int baseY = h / 2 - 350;

		shaderManager.setPosition(baseX, baseY, 700, 700);
		shaderManager.setTexcoords(0, 0, 700.f / 1024.f, 700.f / 1024.f);

		GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);

		for (int b = 0; b < ld27.popup.numButtons; b++) {
			int bx = baseX + ld27.popup.buttonX[b];
			int by = baseY + ld27.popup.buttonY[b];
			int bw = ld27.popup.buttonW[b];
			int bh = ld27.popup.buttonH[b];

			int bs = (ld27.popup.buttonPressed[b]) ? ld27.popup.buttonSP[b]
					: ld27.popup.buttonS[b];
			int bt = (ld27.popup.buttonPressed[b]) ? ld27.popup.buttonTP[b]
					: ld27.popup.buttonT[b];

			shaderManager.setPosition(bx, by, bw, bh);
			shaderManager.setTexcoords(bs / 1024.f, bt / 1024.f, bw / 1024.f,
					bh / 1024.f);

			GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
		}

		if (ld27.popup.type == Popup.Type.FINISH) {
			GL20.glUseProgram(0);

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, w, h, 0, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			String text1, text2;
			if (ld27.terrenyDeJoc.timedMode) {
				text1 = "You've finished a timed game with";
				text2 = ld27.terrenyDeJoc.pv + " points!";
			} else {
				text1 = "You've finished a game in";
				text2 = ld27.terrenyDeJoc.ticks + " steps!";
			}

			font.drawString(640 - (font.getWidth(text1) / 2), 370, text1,
					Color.black);

			fontTimer.drawString(640 - (fontTimer.getWidth(text2) / 2), 420,
					text2, Color.black);

			shaderManager.setCurrentProgram(ShaderManager.ProgramType.TEXTURED);
		}

	}

}

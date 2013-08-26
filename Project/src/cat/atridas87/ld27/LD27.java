package cat.atridas87.ld27;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import cat.atridas87.ld27.sounds.Sounds;

public class LD27 extends BaseGame {

	final static public int GRAELLA_TAMANY_X = 6;
	final static public int GRAELLA_TAMANY_Y = 5;

	final static public int TAMANY_CASELLA = 170;

	final static public int ZONA_JOC_W = 980;
	final static public int ZONA_JOC_H = 800;

	final static public int POINTS_TO_WIN = 10000;

	final static public int MAX_RECURSOS_ENTRANTS = 5;
	final static public int MAX_RECURSOS_GENERATS = 5;
	final static public int MAX_TREBALLADORS = 2;
	
	final static public int TIMED_MODE_TICKS = 180;

	final static public int PAUSE_S = 32;
	final static public int PAUSE_X = 300 - 10 - PAUSE_S;
	final static public int PAUSE_Y = 800 - 10 - PAUSE_S;

	Renderer renderer;
	TerrenyDeJoc terrenyDeJoc;

	float x, y;
	float mouseX, mouseY;
	boolean dragging;
	boolean pausePressed, mouseOnPause;

	Recurs recursTransportant = null;
	ArrayList<Recurs> llistaOriginal = null;

	Popup popup = new Popup(Popup.Type.START);
	int lastButtonPressed = -1;

	public LD27(int _width, int _height) {
		super(_width, _height);
	}

	@Override
	public String getWindowName() {
		return "LD27";
	}

	@Override
	public void init() throws Exception {

		GL11.glClearColor(0, .5f, .5f, 1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glEnable(GL11.GL_BLEND);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		renderer = new Renderer();
		terrenyDeJoc = new TerrenyDeJoc();
	}

	@Override
	public void render() {

		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);

		GL11.glViewport(0, 0, ZONA_JOC_W, ZONA_JOC_H);

		renderer.render((int) x, (int) y, ZONA_JOC_W, ZONA_JOC_H, this);

		GL11.glViewport(ZONA_JOC_W, 0, 1280 - ZONA_JOC_W, ZONA_JOC_H);

		renderer.renderHUD(1280 - ZONA_JOC_W, ZONA_JOC_H, this);

		GL11.glViewport(0, 0, 1280, 800);

		renderer.renderRecurs(1280, 800, (int) mouseX, (int) mouseY,
				recursTransportant);
		
		if(popup != null) {
			renderer.renderPopup(1280, 800, this);
		}
	}

	@Override
	public void update(float _dt) {
		if (popup == null) {
			terrenyDeJoc.update(this, _dt);
		} else {
			// --
		}
	}

	@Override
	public void cleanup() {
	}

	@Override
	public void mouseClick(float _x, float _y) {
		mouseX = _x;
		mouseY = _y;
		if (popup == null) {
			if (_x < ZONA_JOC_W) {
				int casellaX = (int) (x + _x) / TAMANY_CASELLA;
				int casellaY = (int) (y + _y) / TAMANY_CASELLA;

				terrenyDeJoc.casellaSeleccionada = terrenyDeJoc.caselles[casellaX][casellaY];
				float dx = (x + _x) - casellaX * TAMANY_CASELLA;
				float dy = (y + _y) - casellaY * TAMANY_CASELLA;

				if (dx > 10 && dx < 84 && dy > 50 && dy < 124) {
					llistaOriginal = terrenyDeJoc.casellaSeleccionada.treballadors;
				} else if (dx > 90 && dx < 164 && dy > 10 && dy < 84) {
					llistaOriginal = terrenyDeJoc.casellaSeleccionada.recursosGenerats;
				} else if (dx > 90 && dx < 164 && dy > 90 && dy < 164) {
					llistaOriginal = terrenyDeJoc.casellaSeleccionada.recursosEntrants;
				} else {
					llistaOriginal = null;
				}

				if (llistaOriginal != null && llistaOriginal.size() > 0) {
					recursTransportant = llistaOriginal.get(llistaOriginal
							.size() - 1);
					llistaOriginal.remove(llistaOriginal.size() - 1);
					Sounds.pickup.play();
				} else {
					dragging = true;
					llistaOriginal = null;
				}
			} else {
				float dx = 280.f / 3.f;
				float dy = 380.f / 7.f;

				int cont = 0;
				Iterator<Recurs> it = terrenyDeJoc.magatzem.keySet().iterator();
				while (it.hasNext()) {
					Recurs recurs = it.next();
					int columna = cont % 3;
					int fila = cont / 3;

					float left = ZONA_JOC_W + 10 + columna * dx;
					float right = left + dx;
					float top = ZONA_JOC_H - 410 - fila * dy;
					float bottom = top - dy;

					if (_x > left && _x < right && _y < top && _y > bottom) {
						int cuantitat = terrenyDeJoc.magatzem.get(recurs);
						if (cuantitat > 0) {
							recursTransportant = recurs;
							terrenyDeJoc.magatzem.put(recurs, cuantitat - 1);
							Sounds.pickup.play();
						}
						break;
					}
					cont++;
				}


				if(_x > ZONA_JOC_W + PAUSE_X && _x < ZONA_JOC_W + PAUSE_X + PAUSE_S && _y > PAUSE_Y && _y < PAUSE_Y + PAUSE_S) {
					pausePressed = true;
				}
				
			}
		} else {
			int baseX = 1280/2 - 350;
			int baseY = 800/2 - 350;
			lastButtonPressed = popup.coordsToButton((int)mouseX - baseX, (int)mouseY - baseY);
			if(lastButtonPressed >= 0) {
				popup.buttonPressed[lastButtonPressed] = true;
			}
		}

	}

	@Override
	public void mouseRelease(float _x, float _y) {

		if (popup == null) {
			if (recursTransportant != null) {
				ArrayList<Recurs> llista = llistaOriginal;

				if (mouseX < ZONA_JOC_W && mouseX > 0 && mouseY < ZONA_JOC_H
						&& mouseY > 0) {
					int casellaX = (int) (x + _x) / TAMANY_CASELLA;
					int casellaY = (int) (y + _y) / TAMANY_CASELLA;

					terrenyDeJoc.casellaSeleccionada = terrenyDeJoc.caselles[casellaX][casellaY];

					llista = terrenyDeJoc.casellaSeleccionada.recursosEntrants;
					if (recursTransportant.type == Recurs.Type.HABITANT) {
						llista = terrenyDeJoc.caselles[casellaX][casellaY].treballadors;
						if (llista.size() >= MAX_TREBALLADORS) {
							llista = llistaOriginal;
						}
					} else if (llista.size() >= MAX_RECURSOS_ENTRANTS) {
						llista = llistaOriginal;
					}
				}

				if (llista != null) {
					llista.add(recursTransportant);
					Sounds.pickdown.play();
				} else if (recursTransportant.type == Recurs.Type.OBJECTE) {
					terrenyDeJoc.magatzem.put(recursTransportant,
							terrenyDeJoc.magatzem.get(recursTransportant) + 1);
				} else {
					// TODO no hauria de passar mai aixos...
				}
				llistaOriginal = null;
				recursTransportant = null;
			} else if (mouseX < ZONA_JOC_W && mouseX > 0 && mouseY < ZONA_JOC_H
					&& mouseY > 0) {
				int casellaX = (int) (x + _x) / TAMANY_CASELLA;
				int casellaY = (int) (y + _y) / TAMANY_CASELLA;

				terrenyDeJoc.casellaSeleccionada = terrenyDeJoc.caselles[casellaX][casellaY];
			}

			if(mouseX > ZONA_JOC_W + PAUSE_X && mouseX < ZONA_JOC_W + PAUSE_X + PAUSE_S && mouseY > PAUSE_Y && mouseY < PAUSE_Y + PAUSE_S) {
				if(pausePressed) {
					popup = new Popup(Popup.Type.PAUSE);
					Sounds.click.play();
				}
			}
			
			pausePressed=false;
			dragging = false;
		} else {
			int baseX = 1280/2 - 350;
			int baseY = 800/2 - 350;
			int buttonPressed = popup.coordsToButton((int)mouseX - baseX, (int)mouseY - baseY);
			if(lastButtonPressed >= 0) {
				popup.buttonPressed[lastButtonPressed] = false;
				if( buttonPressed == lastButtonPressed ) {
					popup.executarBoto(this, buttonPressed);
				}
				lastButtonPressed = -1;
			}
		}
		mouseX = _x;
		mouseY = _y;
	}

	@Override
	public void mouseMove(float dx, float dy) {
		mouseX += dx;
		mouseY += dy;
		if (popup == null) {
			if (dragging) {
				x -= dx;
				y -= dy;

				if (x < 0) {
					x = 0;
				} else if (x > GRAELLA_TAMANY_X * TAMANY_CASELLA - ZONA_JOC_W) {
					x = GRAELLA_TAMANY_X * TAMANY_CASELLA - ZONA_JOC_W;
				}
				if (y < 0) {
					y = 0;
				} else if (y > GRAELLA_TAMANY_Y * TAMANY_CASELLA - ZONA_JOC_H) {
					y = GRAELLA_TAMANY_Y * TAMANY_CASELLA - ZONA_JOC_H;
				}
			}
			if(mouseX > ZONA_JOC_W + PAUSE_X && mouseX < ZONA_JOC_W + PAUSE_X + PAUSE_S && mouseY > PAUSE_Y && mouseY < PAUSE_Y + PAUSE_S) {
				mouseOnPause = true;
			} else {
				mouseOnPause = false;
			}
		} else {
			int baseX = 1280/2 - 350;
			int baseY = 800/2 - 350;
			int buttonPressed = popup.coordsToButton((int)mouseX - baseX, (int)mouseY - baseY);
			if(lastButtonPressed >= 0) {
				if( buttonPressed == lastButtonPressed ) {
					popup.buttonPressed[lastButtonPressed] = true;
				} else {
					popup.buttonPressed[lastButtonPressed] = false;
				}
			}
		}
	}

}

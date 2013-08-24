package cat.atridas87.ld27;

import org.lwjgl.opengl.GL11;

public class LD27 extends BaseGame {

	final static public int GRAELLA_TAMANY_X = 10;
	final static public int GRAELLA_TAMANY_Y = 10;
	
	final static public int TAMANY_CASELLA = 170;

	final static public int ZONA_JOC_W = 980;
	final static public int ZONA_JOC_H = 800;

	Renderer renderer;
	TerrenyDeJoc terrenyDeJoc;
	
	float x, y;
	boolean dragging;

	public LD27(int _width, int _height) {
		super(_width, _height);
		// TODO Auto-generated constructor stub
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

		renderer.render(x, y, ZONA_JOC_W, ZONA_JOC_H, terrenyDeJoc);
	}

	@Override
	public void update(float _dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClick(float _x, float _y) {
		dragging = true;
	}

	@Override
	public void mouseRelease(float _x, float _y) {
		dragging = false;

		
		
	}

	@Override
	public void mouseMove(float dx, float dy) {
		if(dragging) {
			x -= dx;
			y -= dy;

			if(x < 0) {
				x = 0;
			} else if(x > GRAELLA_TAMANY_X * TAMANY_CASELLA - ZONA_JOC_W) {
				x = GRAELLA_TAMANY_X * TAMANY_CASELLA - ZONA_JOC_W;
			}
			if(y < 0) {
				y = 0;
			} else if(y > GRAELLA_TAMANY_Y * TAMANY_CASELLA - ZONA_JOC_H) {
				y = GRAELLA_TAMANY_Y * TAMANY_CASELLA - ZONA_JOC_H;
			}
		}
	}

}

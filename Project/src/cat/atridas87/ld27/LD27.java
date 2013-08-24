package cat.atridas87.ld27;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class LD27 extends BaseGame {

	final static public int GRAELLA_TAMANY_X = 10;
	final static public int GRAELLA_TAMANY_Y = 10;
	
	final static public int TAMANY_CASELLA = 170;

	Renderer renderer;
	TerrenyDeJoc terrenyDeJoc;
	
	float x, y;

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
		
		GL11.glViewport(0, 0, 800, 800);

		renderer.render(x, y, 800, 800, terrenyDeJoc);
	}

	@Override
	public void update(float _dt) {
		// TODO Auto-generated method stub

		x += _dt * TAMANY_CASELLA / 2;
		y += _dt * TAMANY_CASELLA / 3;

		if( x > TAMANY_CASELLA * GRAELLA_TAMANY_X - 700 && y > TAMANY_CASELLA * GRAELLA_TAMANY_Y - 700) {
			x = 0;
			y = 0;
		}
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClick(float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseRelease(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMove(float dx, float dy) {
		// TODO Auto-generated method stub
		
	}

}

package cat.atridas87.ld27;

import org.lwjgl.opengl.GL11;

public class LD27 extends BaseGame {

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
		// TODO Auto-generated method stub

		GL11.glClearColor(0, .5f, .5f, 1);
	}

	@Override
	public void render() {

		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);

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
	public void mouseClick(float x, float y) {
		// TODO Auto-generated method stub
		
	}

}

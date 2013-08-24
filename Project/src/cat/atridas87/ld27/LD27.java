package cat.atridas87.ld27;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;
import org.newdawn.slick.util.ResourceLoader;

public class LD27 extends BaseGame {
	
	Texture texture;

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
		
		URL url = ResourceLoader.getResource("resources/Sin título.png");
		BufferedImage bi = ImageIO.read(url);
		
		texture = BufferedImageUtil.getTexture("Sin título", bi);

		System.out.println(url.toString());
		System.out.println(bi.getWidth() + " x " + bi.getHeight());
		
		GL11.glClearColor(0, .5f, .5f, 1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	@Override
	public void render() {

		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);

		texture.bind();
		
		GL11.glBegin(GL11.GL_QUADS); {

			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(-.5f, -.5f);
			
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(+.5f, -.5f);
			
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(+.5f, +.5f);
			
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(-.5f, +.5f);
		
		} GL11.glEnd();
		
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

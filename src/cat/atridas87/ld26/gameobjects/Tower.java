package cat.atridas87.ld26.gameobjects;

import org.lwjgl.opengl.GL11;

import cat.atridas87.ld26.render.Model;
import cat.atridas87.ld26.render.ShaderManager;

public class Tower {

	public static final int LIVES = 100;
	
	
	public final boolean player;
	public final float x, y;
	public int live;
	
	private static final Model model;
	
	static {
		float positions[] = {0,0,0};
		float colors[] = {0.5f,0.5f,0.5f,1 };
		
		short indexs[] = {0};
		
		model = new Model(positions, colors, indexs, GL11.GL_POINTS);
	}
	
	public Tower(boolean player, float x, float y) {
		this.player = player;
		this.x = x;
		this.y = y;
		this.live = LIVES;
	}
	
	public void render()
	{
		float r = player? 1 : 0;
		float g = player? 0 : 1;
		float b = player? 1 : 0;
		
		// TODO pintar vida
		
		ShaderManager.instance.setColor(r, g, b, 1);
		ShaderManager.instance.setPosition(x, y);
		
		model.draw();
	}
	
	
}

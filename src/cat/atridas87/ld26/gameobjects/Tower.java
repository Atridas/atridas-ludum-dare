package cat.atridas87.ld26.gameobjects;

import javax.vecmath.Vector2f;

import org.lwjgl.opengl.GL11;

import cat.atridas87.ld26.render.Model;
import cat.atridas87.ld26.render.ShaderManager;

public class Tower {

	public static final int LIVES = 200;
	
	
	public final boolean player;
	public final Vector2f position;
	public int live;
	
	private static final Model model;
	
	static {
		float positions[] = {0,0,0};
		
		short indexs[] = {0};
		
		model = new Model(positions, indexs, GL11.GL_POINTS);
	}
	
	public Tower(boolean player, float x, float y) {
		this.player = player;
		this.position = new Vector2f(x,y);
		this.live = LIVES;
	}
	
	public void render()
	{
		float r = player? 1 : 0;
		float g = player? 0 : 1;
		float b = player? 1 : 0;
		
		// TODO pintar vida
		
		ShaderManager.instance.setColor(r, g, b, 1);
		ShaderManager.instance.setPosition(position.x, position.y);
		
		model.draw();
	}
	
}

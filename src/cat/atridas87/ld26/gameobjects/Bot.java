package cat.atridas87.ld26.gameobjects;

import org.lwjgl.opengl.GL11;

import cat.atridas87.ld26.render.Model;
import cat.atridas87.ld26.render.ShaderManager;

public class Bot {

	
	private static final Model models[];
	
	private final boolean player;
	private float x,y;
	private final Type type;
	private int lives;
	
	public Bot(boolean _player, Type _type, float _x, float _y) {
		player = _player;
		type = _type;
		x = _x;
		y = _y;
		lives = type.lives;
	}
	
	public void draw() {

		float r = player? 1 : 0;
		float g = player? 0 : 1;
		float b = player? 1 : 0;
		
		// TODO pintar vida
		
		ShaderManager.instance.setColor(r, g, b, 1);
		ShaderManager.instance.setPosition(x, y);
		
		models[type.ordinal()].draw();
	}
	
	
	
	
	static {
		models = new Model[Type.values().length];

		{
			float positions[] = {0,0,0};
			short indexs[] = {0};
			
			models[Type.BASIC.ordinal()] = new Model(positions, indexs, GL11.GL_POINTS);
		}
		{
			float positions[] = {2.5f,2.5f,0, -2.5f,2.5f,0, -2.5f,-2.5f,0, 2.5f,-2.5f,0, };
			short indexs[] = {0,1, 1,2, 2,3, 3,0};
			
			models[Type.TANK.ordinal()] = new Model(positions, indexs, GL11.GL_LINES);
		}
		{
			float positions[] = {
					3,5,0,
					3.75f,3,0,
					5.5f,3,0,
					4,2,0,
					4.5f,0,0,
					3,1.25f,0,
					1.5f,0,0,
					2,2,0,
					0.5f,3,0,
					2.25f,3,0};
			for(int i = 0; i < 10; i++) {
				positions[i * 3 + 0] = (positions[i * 3 + 0] - 3.f) * 1.5f;
				positions[i * 3 + 1] = (positions[i * 3 + 1] - 2.5f)* 1.5f;
			}
			short indexs[] = {0,1, 1,2, 2,3, 3,4, 4,5, 5,6, 6,7, 7,8, 8,9, 9,0};
			
			models[Type.SUPER.ordinal()] = new Model(positions, indexs, GL11.GL_LINES);
		}
	}
	
	
	
	public static enum Type {
		BASIC(5, 25, 1.f),
		TANK(2, 50, 5.f),
		SUPER(15, 40, 0.75f);
		
		private final int lives, attack;
		private final float attTime;
		private Type(int _lives, int _attack, float _attTime) {
			lives = _lives;
			attack = _attack;
			attTime = _attTime;
		}
	}
}

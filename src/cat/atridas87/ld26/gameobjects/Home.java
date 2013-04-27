package cat.atridas87.ld26.gameobjects;

import javax.vecmath.Vector2f;

import org.lwjgl.opengl.GL11;

import cat.atridas87.ld26.render.Model;
import cat.atridas87.ld26.render.ShaderManager;

public class Home {

	public static final float HOME_WIDTH = 20;

	public static final int LIVES = 5000;

	public static final Vector2f PLAYER_HOME = new Vector2f(575, 25);
	public static final Vector2f AI_HOME = new Vector2f(25, 575);

	public final boolean player;
	public final float x, y;
	public int lives;

	private static final Model model;

	static {
		float positions[] = { -10, -15, 0, -10, 2.5f, 0, -15, -5, 0, 0, 15, 0,
				15, -5, 0, 10, 2.5f, 0, 10, -15, 0 };

		short indexs[] = { 0, 1, 2, 3, 3, 4, 5, 6 };

		model = new Model(positions, indexs, GL11.GL_LINES);
	}

	public Home(boolean player, float x, float y) {
		this.player = player;
		this.x = x;
		this.y = y;
		this.lives = LIVES;
	}

	public void render() {
		if (lives > 0) {
			float r = player ? 1 : 0;
			float g = player ? 0 : 1;
			float b = player ? 1 : 0;

			float vida = ((float) lives) / ((float) LIVES);

			r = r * vida + (1 - vida);
			g *= vida;
			b *= vida;

			ShaderManager.instance.setColor(r, g, b, 1);
		} else {
			ShaderManager.instance.setColor(.5f, .5f, .5f, 1);
		}
		ShaderManager.instance.setPosition(x, y);

		model.draw();
	}

}

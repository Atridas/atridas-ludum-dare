package cat.atridas87.ld26.gameobjects;

import java.util.Random;

import javax.vecmath.Vector2f;

import org.lwjgl.opengl.GL11;

import cat.atridas87.ld26.render.Model;
import cat.atridas87.ld26.render.ShaderManager;

public class Tower {

	public static final int LIVES = 200;
	public static final float TOWER_WIDTH = 15;
	public static final float COOL_DOWN = 2.f;

	public static final float RANGE = 50;
	public static final float ATTACK = 10;

	public final boolean player;
	public final Vector2f position;
	public int live;

	private static final Model model;
	
	private static final Random rnd = new Random();
	
	private float timeSinceLastShot = 0;

	static {
		float positions[] = { 0, 0, 0 };

		short indexs[] = { 0 };

		model = new Model(positions, indexs, GL11.GL_POINTS);
	}

	public Tower(boolean player, float x, float y) {
		this.player = player;
		this.position = new Vector2f(x, y);
		this.live = LIVES;
	}

	public void render() {
		if (live > 0) {
			float r = player ? 1 : 0;
			float g = player ? 0 : 1;
			float b = player ? 1 : 0;

			float vida = ((float) live) / ((float) LIVES);

			r = r * vida + (1 - vida);
			g *= vida;
			b *= vida;

			ShaderManager.instance.setColor(r, g, b, 1);
		} else {
			ShaderManager.instance.setColor(.5f, .5f, .5f, 1);
		}
		ShaderManager.instance.setPosition(position.x, position.y);

		model.draw();
	}

	public void update(float _dt) {
		if(live <= 0) return;
		
		if (timeSinceLastShot < COOL_DOWN) {
			timeSinceLastShot += _dt;
		} else {
			
			Bot[] closestBots = Battleground.instance.getClosestBots(position);

			int enemyBotsAtRange = 0;
			for (int i = 0; i < closestBots.length; i++) {
				if (closestBots[i].player != player) {

					Vector2f distToBot = new Vector2f();
					distToBot.x = closestBots[i].position.x - position.x;
					distToBot.y = closestBots[i].position.y - position.y;

					if (distToBot.lengthSquared() < RANGE * RANGE) {
						enemyBotsAtRange++;
					}
				}
			}

			if (enemyBotsAtRange > 0) {
				int shootAt = rnd.nextInt(enemyBotsAtRange);

				for (int i = 0; i < closestBots.length; i++) {
					if (closestBots[i].player != player) {

						Vector2f distToBot = new Vector2f();
						distToBot.x = closestBots[i].position.x - position.x;
						distToBot.y = closestBots[i].position.y - position.y;

						if (distToBot.lengthSquared() < RANGE * RANGE) {
							if (shootAt == 0) {
								Shot shot = new Shot(player, RANGE,
										ATTACK, position,
										closestBots[i].position);
								timeSinceLastShot = 0;
								Battleground.instance.addShot(shot);
								return;
							} else {
								shootAt--;
							}
						}
					}
				}

			}

		}
	}

}

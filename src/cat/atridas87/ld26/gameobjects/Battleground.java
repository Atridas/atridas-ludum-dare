package cat.atridas87.ld26.gameobjects;

import java.util.Vector;

import javax.vecmath.Vector2f;

import org.lwjgl.opengl.GL11;

import cat.atridas87.ld26.render.Model;
import cat.atridas87.ld26.render.ShaderManager;
import cat.atridas87.ld26.render.ShaderManager.ProgramType;

public class Battleground {

	public static Battleground instance;

	private Model model;

	private Tower towers[];

	private Vector<Bot> bots;

	private Home homePlayer, homeBot;

	private Vector<Shot> shots = new Vector<Shot>();

	public Battleground() {
		instance = this;

		float positions[] = { 0, 600, 0, 600, 600, 0, 600, 0, 0, 0, 0, 0, 550,
				550, 0, 50, 50, 0, 50, 400, 0, 100, 450, 0, 150, 500, 0, 200,
				550, 0, 400, 50, 0, 450, 100, 0, 500, 150, 0, 550, 200, 0 };
		float colors[] = { 0, 1, 0, 1, 1, 0.5f, 0, 1, 1, 0, 1, 1, 0, 0.5f, 1,
				0, 1, 0.5f, 0, 1, 0, 0.5f, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
				0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1,
				1 };

		short indexs[] = { 0, 1, 1, 2, 2, 3, 3, 0, 4, 13, 13, 12, 12, 8, 8, 9,
				9, 4, 10, 11, 11, 7, 7, 6, 6, 5, 5, 10 };

		model = new Model(positions, colors, indexs, GL11.GL_LINES);

		towers = new Tower[22];
		towers[0] = new Tower(true, 125, 25);
		towers[1] = new Tower(true, 325, 25);
		towers[2] = new Tower(false, 425, 25);
		towers[3] = new Tower(true, 500, 100);
		towers[4] = new Tower(true, 575, 175);
		towers[5] = new Tower(true, 575, 275);
		towers[6] = new Tower(true, 575, 475);
		towers[7] = new Tower(true, 425, 175);
		towers[8] = new Tower(true, 375, 225);
		towers[9] = new Tower(true, 525, 50);
		towers[10] = new Tower(true, 550, 75);

		towers[11] = new Tower(false, 600 - 125, 600 - 25);
		towers[12] = new Tower(false, 600 - 325, 600 - 25);
		towers[13] = new Tower(false, 600 - 425, 600 - 25);
		towers[14] = new Tower(true, 600 - 500, 600 - 100);
		towers[15] = new Tower(false, 600 - 575, 600 - 175);
		towers[16] = new Tower(false, 600 - 575, 600 - 275);
		towers[17] = new Tower(false, 600 - 575, 600 - 475);
		towers[18] = new Tower(false, 600 - 425, 600 - 175);
		towers[19] = new Tower(false, 600 - 375, 600 - 225);
		towers[20] = new Tower(false, 600 - 525, 600 - 50);
		towers[21] = new Tower(false, 600 - 550, 600 - 75);

		bots = new Vector<Bot>();

		bots.add(new Bot(true, Bot.Type.BASIC, Lane.UP));
		bots.add(new Bot(true, Bot.Type.BASIC, Lane.UP));
		bots.add(new Bot(true, Bot.Type.BASIC, Lane.UP));
		bots.add(new Bot(true, Bot.Type.BASIC, Lane.UP));

		bots.add(new Bot(true, Bot.Type.SUPER, Lane.BOT));
		bots.add(new Bot(true, Bot.Type.BASIC, Lane.BOT));
		bots.add(new Bot(true, Bot.Type.BASIC, Lane.BOT));

		bots.add(new Bot(true, Bot.Type.BASIC, Lane.MIDDLE));
		bots.add(new Bot(true, Bot.Type.BASIC, Lane.MIDDLE));
		bots.add(new Bot(true, Bot.Type.BASIC, Lane.MIDDLE));

		bots.add(new Bot(false, Bot.Type.TANK, Lane.MIDDLE));

		homePlayer = new Home(true, 575, 25);
		homeBot = new Home(false, 25, 575);
	}

	public Tower getClosestTower(Vector2f position) {
		Tower t = towers[0];
		Vector2f dist = new Vector2f();
		dist.x = position.x - t.position.x;
		dist.y = position.y - t.position.y;
		float dist2 = dist.lengthSquared();
		for (int i = 1; i < towers.length; i++) {
			Tower t2 = towers[i];
			dist.x = position.x - t2.position.x;
			dist.y = position.y - t2.position.y;
			float dist2New = dist.lengthSquared();

			if (dist2New < dist2) {
				t = t2;
				dist2 = dist2New;
			}
		}

		return t;
	}

	public Bot[] getClosestBots(Bot bot) {

		if (bots.size() == 0) {
			return new Bot[0];
		} else if (bots.size() < 6) {
			Bot[] targets = new Bot[bots.size() - 1];

			int aux = 0;
			for (int i = 0; i < bots.size(); i++) {
				Bot b = bots.get(i);
				if (b != bot) {
					targets[aux] = b;
					aux++;
				}
			}

			return targets;
		}

		Bot[] targets = new Bot[5];
		float dists[] = new float[5];
		for (int i = 0; i < bots.size(); i++) {
			Bot b = bots.get(i);

			Vector2f dist = new Vector2f();
			dist.x = bot.position.x - b.position.x;
			dist.y = bot.position.y - b.position.y;
			float dist2 = dist.lengthSquared();

			if (b != bot) {
				for (int j = 0; j < 5; j++) {
					if (targets[j] == null) {
						targets[j] = b;
						dists[j] = dist2;
					} else if (dist2 < dists[j]) {
						Bot oldTarget = targets[j];
						targets[j] = bot;
						bot = oldTarget;

						float oldDistTarget = dists[j];
						dists[j] = dist2;
						dist2 = oldDistTarget;

					}
				}
			}
		}

		return targets;
	}

	public void addShot(Shot shot) {
		shots.add(shot);
	}

	public void update(float _dt) {

		// TODO update towers

		for (int i = 0; i < bots.size(); i++) {
			bots.get(i).update(_dt);
		}

		for (int i = 0; i < shots.size(); i++) {
			shots.get(i).update(_dt);
			if (!shots.get(i).alive()) {
				shots.remove(i);
				i--;
			}
		}

		// TODO update homes
	}

	public void render() {
		GL11.glLineWidth(5);
		ShaderManager.instance.setCurrentProgram(ProgramType.COLORED_ORTHO);
		ShaderManager.instance.setScreenSize(600, 600);

		ShaderManager.instance.setPosition(0, 0);
		model.draw();

		ShaderManager.instance
				.setCurrentProgram(ProgramType.COLORED_FROM_UNIFORM_ORTHO);
		ShaderManager.instance.setScreenSize(600, 600);

		GL11.glPointSize(15);
		for (int i = 0; i < towers.length; i++) {
			towers[i].render();
		}

		GL11.glLineWidth(1);
		GL11.glPointSize(5);

		for (int i = 0; i < bots.size(); i++) {
			bots.get(i).render();
		}

		GL11.glPointSize(1);
		for (int i = 0; i < shots.size(); i++) {
			shots.get(i).render();
		}

		homePlayer.render();
		homeBot.render();
	}

}

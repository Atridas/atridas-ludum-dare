package cat.atridas87.ld26.gameobjects;

import java.util.Vector;

import javax.vecmath.Vector2f;

import org.lwjgl.opengl.GL11;

import cat.atridas87.ld26.GameInfo;
import cat.atridas87.ld26.HUD;
import cat.atridas87.ld26.ai.AI;
import cat.atridas87.ld26.render.Model;
import cat.atridas87.ld26.render.ShaderManager;
import cat.atridas87.ld26.render.ShaderManager.ProgramType;

import static cat.atridas87.ld26.GameParameters.*;

public class Battleground {

	public static Battleground instance;

	private final AI ai = new AI();

	private Model model;

	private Tower towers[];

	private Vector<Bot> bots;

	private Home homePlayer, homeAI;

	private Vector<Shot> shots = new Vector<Shot>();

	private Vector<Bot>[] botsInQueuePlayer;
	private Vector<Bot>[] botsInQueueAI;
	private float[] timeSinceLastBotPlayer;
	private float[] timeSinceLastBotAI;

	@SuppressWarnings("unchecked")
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
		towers[2] = new Tower(true, 425, 25);
		towers[3] = new Tower(true, 500, 100);
		towers[4] = new Tower(true, 575, 175);
		towers[5] = new Tower(true, 575, 275);
		towers[6] = new Tower(true, 575, 475);
		towers[7] = new Tower(true, 425, 175);
		towers[8] = new Tower(true, 375, 225);
		towers[9] = new Tower(true, 515, 40);
		towers[10] = new Tower(true, 560, 85);

		towers[11] = new Tower(false, 600 - 125, 600 - 25);
		towers[12] = new Tower(false, 600 - 325, 600 - 25);
		towers[13] = new Tower(false, 600 - 425, 600 - 25);
		towers[14] = new Tower(false, 600 - 500, 600 - 100);
		towers[15] = new Tower(false, 600 - 575, 600 - 175);
		towers[16] = new Tower(false, 600 - 575, 600 - 275);
		towers[17] = new Tower(false, 600 - 575, 600 - 475);
		towers[18] = new Tower(false, 600 - 425, 600 - 175);
		towers[19] = new Tower(false, 600 - 375, 600 - 225);
		towers[20] = new Tower(false, 600 - 500, 600 - 50);
		towers[21] = new Tower(false, 600 - 550, 600 - 100);

		bots = new Vector<Bot>();

		homePlayer = new Home(true, Home.PLAYER_HOME.x, Home.PLAYER_HOME.y);
		homeAI = new Home(false, Home.AI_HOME.x, Home.AI_HOME.y);

		botsInQueuePlayer = new Vector[Lane.values().length];
		botsInQueueAI = new Vector[Lane.values().length];
		timeSinceLastBotPlayer = new float[Lane.values().length];
		timeSinceLastBotAI = new float[Lane.values().length];

		for (int i = 0; i < Lane.values().length; i++) {
			botsInQueuePlayer[i] = new Vector<Bot>();
			botsInQueueAI[i] = new Vector<Bot>();
		}

		for (int i = 0; i < 5; i++) {
			addBot(new Bot(true, Bot.Type.BASIC, Lane.UP));
			addBot(new Bot(true, Bot.Type.BASIC, Lane.BOT));
			addBot(new Bot(true, Bot.Type.BASIC, Lane.MIDDLE));

			addBot(new Bot(false, Bot.Type.BASIC, Lane.UP));
			addBot(new Bot(false, Bot.Type.BASIC, Lane.BOT));
			addBot(new Bot(false, Bot.Type.BASIC, Lane.MIDDLE));
		}
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

	public Bot getClosestBot(Vector2f position) {
		if (bots.size() == 0) {
			return null;
		}
		Bot b = bots.get(0);
		Vector2f dist = new Vector2f();
		dist.x = position.x - b.position.x;
		dist.y = position.y - b.position.y;
		float dist2 = dist.lengthSquared();
		for (int i = 1; i < bots.size(); i++) {
			Bot b2 = bots.get(i);
			dist.x = position.x - b2.position.x;
			dist.y = position.y - b2.position.y;
			float dist2New = dist.lengthSquared();

			if (dist2New < dist2) {
				b = b2;
				dist2 = dist2New;
			}
		}

		return b;
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
						break;
					} else if (dist2 < dists[j]) {
						Bot oldTarget = targets[j];
						targets[j] = b;
						b = oldTarget;

						float oldDistTarget = dists[j];
						dists[j] = dist2;
						dist2 = oldDistTarget;

					}
				}
			}
		}

		return targets;
	}

	public Bot[] getClosestBots(Vector2f position) {

		if (bots.size() == 0) {
			return new Bot[0];
		} else if (bots.size() < 5) {
			Bot[] targets = new Bot[bots.size()];

			int aux = 0;
			for (int i = 0; i < bots.size(); i++) {
				Bot b = bots.get(i);
				targets[aux] = b;
				aux++;
			}

			return targets;
		}

		Bot[] targets = new Bot[5];
		float dists[] = new float[5];
		for (int i = 0; i < bots.size(); i++) {
			Bot b = bots.get(i);

			Vector2f dist = new Vector2f();
			dist.x = position.x - b.position.x;
			dist.y = position.y - b.position.y;
			float dist2 = dist.lengthSquared();

			for (int j = 0; j < 5; j++) {
				if (targets[j] == null) {
					targets[j] = b;
					dists[j] = dist2;
					break;
				} else if (dist2 < dists[j]) {
					Bot oldTarget = targets[j];
					targets[j] = b;
					b = oldTarget;

					float oldDistTarget = dists[j];
					dists[j] = dist2;
					dist2 = oldDistTarget;

				}
			}

		}

		return targets;
	}

	public Bot[] getClosestEnemyBotsInRange(Vector2f position, boolean player,
			float range) {

		Vector<Bot> enemyBots = new Vector<Bot>();

		for (int i = 0; i < bots.size(); i++) {
			Bot b = bots.get(i);

			if (player != b.player) {
				Vector2f dist = new Vector2f();
				dist.x = position.x - b.position.x;
				dist.y = position.y - b.position.y;

				if (dist.lengthSquared() < range * range) {
					enemyBots.add(b);

					if (enemyBots.size() >= 5) {
						break;
					}
				}
			}

		}

		return enemyBots.toArray(new Bot[enemyBots.size()]);
	}

	public Home getHome(boolean player) {
		return player ? homePlayer : homeAI;
	}

	public Vector2f vectorToField(Vector2f position) {
		if (position.x < 0) {
			return new Vector2f(1, 0);
		} else if (position.y < 0) {
			return new Vector2f(0, 1);
		} else if (position.x > 600) {
			return new Vector2f(-1, 0);
		} else if (position.y > 600) {
			return new Vector2f(0, -1);
		}

		if (position.x < 50 || position.y < 50 || position.x > 550
				|| position.y > 550) {
			return new Vector2f(0, 0);
		}

		return new Vector2f(0, 0); // TODO
	}

	public void addBot(Bot bot) {
		Vector<Bot>[] arrayBots = (bot.player) ? botsInQueuePlayer
				: botsInQueueAI;
		Vector<Bot> arrayBotsLane = arrayBots[bot.lane.ordinal()];
		arrayBotsLane.add(bot);
	}

	public void addShot(Shot shot) {
		shots.add(shot);
	}

	public void update(float _dt) {

		for (int i = 0; i < towers.length; i++) {
			towers[i].update(_dt);
		}

		for (int i = 0; i < bots.size(); i++) {
			bots.get(i).update(_dt);
			if (bots.get(i).lives <= 0) {
				Bot removedBot = bots.remove(i);
				i--;

				if (!removedBot.player) {
					HUD.instance.addCoins( removedBot.type.value );
					GameInfo.instance.addKill();
					GameInfo.instance.addPoints(removedBot.type.value);
				} else {
					GameInfo.instance.addLose();
				}

			}
		}

		for (int i = 0; i < shots.size(); i++) {
			shots.get(i).update(_dt);
			if (!shots.get(i).alive()) {
				shots.remove(i);
				i--;
			}
		}

		// new bots
		for (int i = 0; i < Lane.values().length; i++) {
			if (timeSinceLastBotPlayer[i] > TIME_BETWEEN_BOTS) {
				if (botsInQueuePlayer[i].size() > 0) {
					Bot b = botsInQueuePlayer[i].remove(0);
					bots.add(b);
					timeSinceLastBotPlayer[i] = 0;
				}
			} else {
				timeSinceLastBotPlayer[i] += _dt;
			}

			if (timeSinceLastBotAI[i] > TIME_BETWEEN_BOTS) {
				if (botsInQueueAI[i].size() > 0) {
					Bot b = botsInQueueAI[i].remove(0);
					bots.add(b);
					timeSinceLastBotAI[i] = 0;
				}
			} else {
				timeSinceLastBotAI[i] += _dt;
			}
		}

		ai.update(_dt);

		if(homePlayer.lives <= 0) {
			GameInfo.instance.lose();
		} else if(homeAI.lives <= 0) {
			GameInfo.instance.win();
		}
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
		homeAI.render();
	}

}

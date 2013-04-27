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

	private Tower playerTowers[];
	private Tower aiTowers[];

	private Vector<Bot> playerBots, aiBots;

	private Home homePlayer, homeBot;

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

		playerTowers = new Tower[11];
		playerTowers[0] = new Tower(true, 125, 25);
		playerTowers[1] = new Tower(true, 325, 25);
		playerTowers[2] = new Tower(true, 425, 25);
		playerTowers[3] = new Tower(true, 500, 100);
		playerTowers[4] = new Tower(true, 575, 175);
		playerTowers[5] = new Tower(true, 575, 275);
		playerTowers[6] = new Tower(true, 575, 475);
		playerTowers[7] = new Tower(true, 425, 175);
		playerTowers[8] = new Tower(true, 375, 225);
		playerTowers[9] = new Tower(true, 525, 50);
		playerTowers[10] = new Tower(true, 550, 75);

		aiTowers = new Tower[11];
		aiTowers[0] = new Tower(false, 600 - 125, 600 - 25);
		aiTowers[1] = new Tower(false, 600 - 325, 600 - 25);
		aiTowers[2] = new Tower(false, 600 - 425, 600 - 25);
		aiTowers[3] = new Tower(false, 600 - 500, 600 - 100);
		aiTowers[4] = new Tower(false, 600 - 575, 600 - 175);
		aiTowers[5] = new Tower(false, 600 - 575, 600 - 275);
		aiTowers[6] = new Tower(false, 600 - 575, 600 - 475);
		aiTowers[7] = new Tower(false, 600 - 425, 600 - 175);
		aiTowers[8] = new Tower(false, 600 - 375, 600 - 225);
		aiTowers[9] = new Tower(false, 600 - 525, 600 - 50);
		aiTowers[10] = new Tower(false, 600 - 550, 600 - 75);

		playerBots = new Vector<Bot>();
		aiBots = new Vector<Bot>();

		playerBots.add(new Bot(true, Bot.Type.BASIC, Lane.UP));
		playerBots.add(new Bot(true, Bot.Type.SUPER, Lane.BOT));

		aiBots.add(new Bot(false, Bot.Type.TANK, Lane.MIDDLE));

		homePlayer = new Home(true, 575, 25);
		homeBot = new Home(false, 25, 575);
	}

	public Tower getClosestTower(Vector2f position) {
		Tower t = aiTowers[0];
		Vector2f dist = new Vector2f();
		dist.x = position.x - t.position.x;
		dist.y = position.y - t.position.y;
		float dist2 = dist.lengthSquared();
		for (int i = 0; i < playerTowers.length; i++) {
			Tower t2 = playerTowers[i];
			dist.x = position.x - t2.position.x;
			dist.y = position.y - t2.position.y;
			float dist2New = dist.lengthSquared();

			if (dist2New < dist2) {
				t = t2;
				dist2 = dist2New;
			}

			t2 = aiTowers[i];
			dist.x = position.x - t2.position.x;
			dist.y = position.y - t2.position.y;
			dist2New = dist.lengthSquared();

			if (dist2New < dist2) {
				t = t2;
				dist2 = dist2New;
			}

		}
		
		return t;
	}

	public void update(float _dt) {

		// TODO update towers

		for (int i = 0; i < playerBots.size(); i++) {
			playerBots.get(i).update(_dt);
		}
		for (int i = 0; i < aiBots.size(); i++) {
			aiBots.get(i).update(_dt);
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
		for (int i = 0; i < playerTowers.length; i++) {
			playerTowers[i].render();
			aiTowers[i].render();
		}

		GL11.glLineWidth(1);
		GL11.glPointSize(5);

		for (int i = 0; i < playerBots.size(); i++) {
			playerBots.get(i).render();
		}
		for (int i = 0; i < aiBots.size(); i++) {
			aiBots.get(i).render();
		}

		homePlayer.render();
		homeBot.render();
	}

}

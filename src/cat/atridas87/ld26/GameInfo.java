package cat.atridas87.ld26;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import cat.atridas87.ld26.gameobjects.Bot;
import cat.atridas87.ld26.gameobjects.Lane;
import cat.atridas87.ld26.gameobjects.Tower;
import cat.atridas87.ld26.render.ShaderManager;
import cat.atridas87.ld26.render.ShaderManager.ProgramType;

public class GameInfo {

	public boolean won;
	public boolean lost;

	public int points;
	public int kills, loses;
	public int towerKills, towerLoses;
	
	private float milis;
	private int seconds, minutes, hours;

	public static GameInfo instance;

	private Bot botPlayerModel = new Bot(true, Bot.Type.BASIC, Lane.MIDDLE);
	private Bot botAIModel = new Bot(false, Bot.Type.BASIC, Lane.MIDDLE);
	private Tower towerPlayerModel = new Tower(true, 100, 132.5f);
	private Tower towerAIModel = new Tower(false, 100, 182.5f);

	{
		botPlayerModel.position.x = 100;
		botPlayerModel.position.y = 232.5f;
		botAIModel.position.x = 100;
		botAIModel.position.y = 282.5f;
		
		instance = this;
	}

	public void addPoints(int p) {
		if (!won && !lost) {
			points += p;
		}
	}

	public void addKill() {
		if (!won && !lost) {
			kills++;
		}
	}

	public void addLose() {
		if (!won && !lost) {
			loses++;
		}
	}

	public void addTowerKill() {
		if (!won && !lost) {
			towerKills++;
		}
	}

	public void addTowerLose() {
		if (!won && !lost) {
			towerLoses++;
		}
	}

	public void win() {
		if (!lost) {
			won = true;
		}
	}

	public void lose() {
		if (!won) {
			lost = true;
		}
	}

	public boolean gameFinished() {
		return won || lost;
	}
	
	public void update(float _dt) {
		milis += _dt;
		if(!gameFinished()) {
			while(milis > 1) {
				seconds++;
				milis -= 1;
			}
			while(seconds > 60) {
				minutes++;
				seconds -= 60;
			}
			while(minutes > 60) {
				hours++;
				minutes -= 60;
			}
		} else {
			while(milis > 1) {
				milis -= 1;
			}
		}
	}

	public void render() {
		ShaderManager.instance
				.setCurrentProgram(ProgramType.COLORED_FROM_UNIFORM_ORTHO);
		ShaderManager.instance.setScreenSize(600, 600);

		GL11.glLineWidth(1);
		GL11.glPointSize(5);
		botPlayerModel.render();
		botAIModel.render();

		GL11.glPointSize(15);
		towerPlayerModel.render();
		towerAIModel.render();

		String strNumKills = Integer.toString(kills);
		String strNumLoses = Integer.toString(loses);
		String strNumTowerKills = Integer.toString(towerKills);
		String strNumTowerLoses = Integer.toString(towerLoses);

		while (strNumKills.length() < strNumLoses.length()
				|| strNumKills.length() < strNumTowerKills.length()
				|| strNumKills.length() < strNumTowerLoses.length()) {
			strNumKills = '0' + strNumKills;
		}
		while (strNumLoses.length() < strNumKills.length()) {
			strNumLoses = '0' + strNumLoses;
		}
		while (strNumTowerKills.length() < strNumKills.length()) {
			strNumTowerKills = '0' + strNumTowerKills;
		}
		while (strNumTowerLoses.length() < strNumKills.length()) {
			strNumTowerLoses = '0' + strNumTowerLoses;
		}
		

		ShaderManager.instance.setColor(0, 0, 0, 1);
		HUD.renderText(strNumKills, 125, 275);
		HUD.renderText(strNumLoses, 125, 225);
		HUD.renderText(strNumTowerKills, 125, 175);
		HUD.renderText(strNumTowerLoses, 125, 125);
		
		HUD.renderText(Integer.toString(points), 325, 475);
		
		String strTime = Integer.toString(hours) + ':';
		if(minutes < 10) strTime += '0';
		strTime += Integer.toString(minutes) + ':';
		if(seconds < 10) strTime += '0';
		strTime += Integer.toString(seconds);
		
		HUD.renderText(strTime, 325, 450);
		
		
		if(gameFinished() && milis < 0.5f) {
			if(won) {
				ShaderManager.instance.setColor(1, 0, 1, 1);
			} else {
				ShaderManager.instance.setColor(0, 1, 0, 1);
			}
			renderCrown(375, 325);
		}
	}

	


	private static void renderCrown(float x, float y) {
		ShaderManager.instance.setPosition(x, y);

		float vvb[] = { 20,0, 0,80, 30,40, 40,80, 60,40, 80,80, 90,40, 120,80, 100,0 };

		FloatBuffer fb = BufferUtils.createFloatBuffer(vvb.length);

		for (int i = 0; i < vvb.length; i++) {
			fb.put(vvb[i]);
		}

		fb.flip();

		GL20.glEnableVertexAttribArray(ShaderManager.POSITION_ATTRIBUTE);
		GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false,
				2 * 4, fb);

		GL11.glDrawArrays(GL11.GL_LINE_LOOP, 0, vvb.length / 2);
	}
}

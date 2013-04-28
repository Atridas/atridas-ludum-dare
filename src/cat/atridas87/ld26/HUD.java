package cat.atridas87.ld26;

import java.nio.FloatBuffer;
import java.util.Vector;

import javax.vecmath.Vector2f;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import cat.atridas87.ld26.gameobjects.Battleground;
import cat.atridas87.ld26.gameobjects.Bot;
import cat.atridas87.ld26.gameobjects.Formation;
import cat.atridas87.ld26.gameobjects.Lane;
import cat.atridas87.ld26.render.ShaderManager;
import cat.atridas87.ld26.render.ShaderManager.ProgramType;

import static cat.atridas87.ld26.GameParameters.*;


public class HUD {

	private static final float LANE_BUTTONS_Y = 460;

	public static HUD instance;

	public int level = 0;
	private int numCoins;
	private float lastUpdateCoins;

	private Lane sendTo = Lane.MIDDLE;

	private final Formation formations[] = new Formation[8];

	{
		formations[0] = new Formation(NUM_BOTS[0], COST_FORMATION[0],
				new Vector2f(10, 320));
		formations[1] = new Formation(NUM_BOTS[1], COST_FORMATION[1],
				new Vector2f(110, 320));
		formations[2] = new Formation(NUM_BOTS[2], COST_FORMATION[2],
				new Vector2f(10, 220));
		formations[3] = new Formation(NUM_BOTS[3], COST_FORMATION[3],
				new Vector2f(110, 220));
		formations[4] = new Formation(NUM_BOTS[4], COST_FORMATION[4],
				new Vector2f(10, 120));
		formations[5] = new Formation(NUM_BOTS[5], COST_FORMATION[5],
				new Vector2f(110, 120));
		formations[6] = new Formation(NUM_BOTS[6], COST_FORMATION[6],
				new Vector2f(10, 20));
		formations[7] = new Formation(NUM_BOTS[7], COST_FORMATION[7],
				new Vector2f(110, 20));

		instance = this;
	}

	public void update(float _dt) {

		if (numCoins < MAX_COINS_PER_LEVEL[level]) {
			lastUpdateCoins += _dt;
			while (lastUpdateCoins > 1.f / COINS_PER_SECOND_PER_LEVEL[level]) {
				lastUpdateCoins -= 1.f / COINS_PER_SECOND_PER_LEVEL[level];
				numCoins++;
			}
			if (numCoins > MAX_COINS_PER_LEVEL[level]) {
				numCoins = MAX_COINS_PER_LEVEL[level];
			}
		}
	}
	
	public void addCoins(int n) {
		numCoins += n;
		if(numCoins > MAX_COINS_PER_LEVEL[level]) {
			numCoins = MAX_COINS_PER_LEVEL[level];
		}
	}

	public static void renderText(String text, float x, float y) {
		ShaderManager.instance.setPosition(x, y);

		Vector<Float> vvb = textToVertexs(text);

		FloatBuffer fb = BufferUtils.createFloatBuffer(vvb.size());

		for (int i = 0; i < vvb.size(); i++) {
			fb.put(vvb.get(i));
		}

		fb.flip();

		GL20.glEnableVertexAttribArray(ShaderManager.POSITION_ATTRIBUTE);
		GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false,
				2 * 4, fb);

		GL11.glDrawArrays(GL11.GL_LINES, 0, vvb.size() / 2);
	}

	private static void renderPlus(float x, float y) {
		ShaderManager.instance.setPosition(x, y);

		float vvb[] = { 5, 5, 5, 10, 2.5f, 7.5f, 7.5f, 7.5f, 1.75f, 3.25f,
				1.75f, 11.75f, 1.75f, 11.75f, 8.25f, 11.75f, 8.25f, 11.75f,
				8.25f, 3.35f, 8.25f, 3.35f, 1.75f, 3.35f, };

		FloatBuffer fb = BufferUtils.createFloatBuffer(vvb.length);

		for (int i = 0; i < vvb.length; i++) {
			fb.put(vvb[i]);
		}

		fb.flip();

		GL20.glEnableVertexAttribArray(ShaderManager.POSITION_ATTRIBUTE);
		GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false,
				2 * 4, fb);

		GL11.glDrawArrays(GL11.GL_LINES, 0, vvb.length / 2);
	}

	private void renderLaneButtons(float x, float y) {
		ShaderManager.instance.setPosition(x, y);

		float vvb1[] = { 25, 25, 50, 25, 25, 25, 31.25f, 31.25f, 25, 25,
				31.25f, 18.75f, 12.5f, 0, 62.5f, 0, 62.5f, 0, 62.5f, 50, 62.5f,
				50, 12.5f, 50, 12.5f, 50, 12.5f, 0 };

		float vvb2[] = { 87.5f, 37.5f, 112.5f, 12.5f, 87.5f, 37.5f, 87.5f, 25f,
				87.5f, 37.5f, 100f, 37.5f, 75f, 0, 125f, 0, 125f, 0, 125f, 50,
				125f, 50, 75f, 50, 75f, 50, 75f, 0 };

		float vvb3[] = { 162.5f, 37.5f, 162.5f, 12.5f, 162.5f, 37.5f, 157.5f,
				31.25f, 162.5f, 37.5f, 168.75f, 31.25f, 137.5f, 0, 187.5f, 0,
				187.5f, 0, 187.5f, 50, 187.5f, 50, 137.5f, 50, 137.5f, 50,
				137.5f, 0 };

		FloatBuffer fb1 = BufferUtils.createFloatBuffer(vvb1.length);
		FloatBuffer fb2 = BufferUtils.createFloatBuffer(vvb2.length);
		FloatBuffer fb3 = BufferUtils.createFloatBuffer(vvb3.length);

		for (int i = 0; i < vvb1.length; i++) {
			fb1.put(vvb1[i]);
		}
		for (int i = 0; i < vvb2.length; i++) {
			fb2.put(vvb2[i]);
		}
		for (int i = 0; i < vvb3.length; i++) {
			fb3.put(vvb3[i]);
		}

		fb1.flip();
		fb2.flip();
		fb3.flip();

		GL20.glEnableVertexAttribArray(ShaderManager.POSITION_ATTRIBUTE);
		GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false,
				2 * 4, fb1);

		if (sendTo == Lane.BOT)
			ShaderManager.instance.setColor(0, 1, 0, 1);
		else
			ShaderManager.instance.setColor(0, 0, 0, 1);
		GL11.glDrawArrays(GL11.GL_LINES, 0, vvb1.length / 2);

		// ----
		GL20.glEnableVertexAttribArray(ShaderManager.POSITION_ATTRIBUTE);
		GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false,
				2 * 4, fb2);

		if (sendTo == Lane.MIDDLE)
			ShaderManager.instance.setColor(0, 1, 0, 1);
		else
			ShaderManager.instance.setColor(0, 0, 0, 1);
		GL11.glDrawArrays(GL11.GL_LINES, 0, vvb2.length / 2);

		// ----
		GL20.glEnableVertexAttribArray(ShaderManager.POSITION_ATTRIBUTE);
		GL20.glVertexAttribPointer(ShaderManager.POSITION_ATTRIBUTE, 2, false,
				2 * 4, fb3);

		if (sendTo == Lane.UP)
			ShaderManager.instance.setColor(0, 1, 0, 1);
		else
			ShaderManager.instance.setColor(0, 0, 0, 1);
		GL11.glDrawArrays(GL11.GL_LINES, 0, vvb3.length / 2);
	}

	public void mouseClick(float x, float y) {

		// check level +
		if (x > 120 && y > 520 && x < 130 && y < 535) {
			if (numCoins >= COST_LEVEL[level] && level < NUM_LEVELS - 1) {
				numCoins -= COST_LEVEL[level];
				level++;
			}
		}

		if (y > LANE_BUTTONS_Y && y < LANE_BUTTONS_Y + 50) {
			if (x > 12.5f && x < 62.5f) {
				sendTo = Lane.BOT;
			} else if (x > 75f && x < 125f) {
				sendTo = Lane.MIDDLE;
			} else if (x > 137.5f && x < 187.5f) {
				sendTo = Lane.UP;
			}
		}

		for (int i = 0; i < formations.length; i++) {
			if (x > formations[i].position.x
					&& x < formations[i].position.x + 80
					&& y > formations[i].position.y
					&& y < formations[i].position.y + 80
					&& numCoins >= formations[i].price) {
				for (int t = 0; t < Bot.Type.values().length; t++) {
					for (int j = 0; j < formations[i].numBots[t]; j++) {
						Battleground.instance.addBot(new Bot(true, Bot.Type
								.values()[t], sendTo));
					}
				}

				numCoins -= formations[i].price;
			}
		}

	}

	public void render() {

		ShaderManager.instance
				.setCurrentProgram(ProgramType.COLORED_FROM_UNIFORM_ORTHO);
		ShaderManager.instance.setScreenSize(200, 600);
		ShaderManager.instance.setColor(0, 0, 0, 1);

		ARBVertexArrayObject.glBindVertexArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

		String strNumCoins = Integer.toString(numCoins);
		String strMaxCoins = Integer.toString(MAX_COINS_PER_LEVEL[level]);

		while (strNumCoins.length() < strMaxCoins.length()) {
			strNumCoins = '0' + strNumCoins;
		}

		renderText(strNumCoins, 10, 570);
		renderText(strMaxCoins, 140, 570);

		renderText("LEVEL", 10, 520);
		ShaderManager.instance.setColor(0.5f, 0.5f, 0.5f, 1);
		renderText(numToRoman(level + 1), 75, 520);
		if (level < NUM_LEVELS - 1) {
			if (numCoins >= COST_LEVEL[level]) {
				ShaderManager.instance.setColor(0, 1, 0, 1);
			} else {
				ShaderManager.instance.setColor(0, 0, 1, 1);
			}
			renderPlus(120, 520);
			ShaderManager.instance.setColor(0, 0, 0, 1);
			renderText(Integer.toString(COST_LEVEL[level]), 150, 520);
		} else {
			ShaderManager.instance.setColor(1, 0, 0, 1);
			renderPlus(120, 520);
		}

		renderLaneButtons(0, LANE_BUTTONS_Y);

		for (int i = 0; i < formations.length; i++) {
			formations[i].render(numCoins);
		}
	}

	public static Vector<Float> textToVertexs(String roman) {
		Vector<Float> vb = new Vector<Float>();
		int x = 0;
		for (int i = 0; i < roman.length(); i++) {
			char c = roman.charAt(i);
			switch (c) {
			case 'M':
				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(2.5f + x);
				vb.add(15.f);

				vb.add(2.5f + x);
				vb.add(15.f);
				vb.add(5f + x);
				vb.add(7.5f);

				vb.add(5f + x);
				vb.add(7.5f);
				vb.add(7.5f + x);
				vb.add(15f);

				vb.add(7.5f + x);
				vb.add(15f);
				vb.add(7.5f + x);
				vb.add(0f);

				x += 10;
				break;

			case 'V':
				vb.add(5f + x);
				vb.add(0.f);
				vb.add(2.5f + x);
				vb.add(15.f);

				vb.add(5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(15.f);

				x += 10;
				break;

			case 'C':
				vb.add(7.5f + x);
				vb.add(0.f);
				vb.add(2.5f + x);
				vb.add(5.f);

				vb.add(2.5f + x);
				vb.add(5.f);
				vb.add(2.5f + x);
				vb.add(10.f);

				vb.add(2.5f + x);
				vb.add(10.f);
				vb.add(7.5f + x);
				vb.add(15.f);

				x += 10;
				break;

			case 'D':
				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(2.5f + x);
				vb.add(15.f);

				vb.add(2.5f + x);
				vb.add(15.f);
				vb.add(7.5f + x);
				vb.add(10.f);

				vb.add(7.5f + x);
				vb.add(10.f);
				vb.add(7.5f + x);
				vb.add(5.f);

				vb.add(7.5f + x);
				vb.add(5.f);
				vb.add(2.5f + x);
				vb.add(0.f);

				x += 10;
				break;

			case 'X':
				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(15.f);

				vb.add(7.5f + x);
				vb.add(0.f);
				vb.add(2.5f + x);
				vb.add(15.f);

				x += 10;
				break;

			case 'L':
				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(0.f);

				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(2.5f + x);
				vb.add(15.f);

				x += 10;
				break;

			case 'I':
				vb.add(5f + x);
				vb.add(0.f);
				vb.add(5f + x);
				vb.add(15.f);

				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(0.f);

				vb.add(2.5f + x);
				vb.add(15.f);
				vb.add(7.5f + x);
				vb.add(15.f);

				x += 10;
				break;

			case 'E':
				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(2.5f + x);
				vb.add(15.f);

				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(0.f);

				vb.add(2.5f + x);
				vb.add(15.f);
				vb.add(7.5f + x);
				vb.add(15.f);

				vb.add(2.5f + x);
				vb.add(10.f);
				vb.add(5f + x);
				vb.add(10.f);

				x += 10;
				break;

			case '0':
				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(0.f);

				vb.add(7.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(15f);

				vb.add(2.5f + x);
				vb.add(0f);
				vb.add(2.5f + x);
				vb.add(15f);

				vb.add(2.5f + x);
				vb.add(15f);
				vb.add(7.5f + x);
				vb.add(15f);

				x += 10;
				break;

			case '1':
				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(0.f);

				vb.add(5f + x);
				vb.add(0.f);
				vb.add(5f + x);
				vb.add(15.f);

				vb.add(5f + x);
				vb.add(15.f);
				vb.add(2.5f + x);
				vb.add(10.f);

				x += 10;
				break;

			case '2':
				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(0.f);

				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(1.5f + x);
				vb.add(7.5f);

				vb.add(2.5f + x);
				vb.add(7.5f);
				vb.add(7.5f + x);
				vb.add(7.5f);

				vb.add(7.5f + x);
				vb.add(7.5f);
				vb.add(7.5f + x);
				vb.add(15f);

				vb.add(2.5f + x);
				vb.add(15f);
				vb.add(7.5f + x);
				vb.add(15f);

				x += 10;
				break;

			case '3':
				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(0.f);

				vb.add(7.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(15f);

				vb.add(5f + x);
				vb.add(7.5f);
				vb.add(7.5f + x);
				vb.add(7.5f);

				vb.add(2.5f + x);
				vb.add(15f);
				vb.add(7.5f + x);
				vb.add(15f);

				x += 10;
				break;

			case '4':
				vb.add(2.5f + x);
				vb.add(15.f);
				vb.add(2.5f + x);
				vb.add(7.5f);

				vb.add(2.5f + x);
				vb.add(7.5f);
				vb.add(7.5f + x);
				vb.add(7.5f);

				vb.add(7.5f + x);
				vb.add(12.5f);
				vb.add(7.5f + x);
				vb.add(0f);

				x += 10;
				break;

			case '5':
				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(0.f);

				vb.add(7.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(7.5f);

				vb.add(2.5f + x);
				vb.add(7.5f);
				vb.add(7.5f + x);
				vb.add(7.5f);

				vb.add(2.5f + x);
				vb.add(7.5f);
				vb.add(2.5f + x);
				vb.add(15f);

				vb.add(2.5f + x);
				vb.add(15f);
				vb.add(7.5f + x);
				vb.add(15f);

				x += 10;
				break;

			case '6':
				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(2.5f + x);
				vb.add(15.f);

				vb.add(2.5f + x);
				vb.add(15.f);
				vb.add(7.5f + x);
				vb.add(15f);

				vb.add(2.5f + x);
				vb.add(7.5f);
				vb.add(7.5f + x);
				vb.add(7.5f);

				vb.add(2.5f + x);
				vb.add(0f);
				vb.add(7.5f + x);
				vb.add(0f);

				vb.add(7.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(7.5f);

				x += 10;
				break;

			case '7':
				vb.add(2.5f + x);
				vb.add(15f);
				vb.add(7.5f + x);
				vb.add(15f);

				vb.add(7.5f + x);
				vb.add(15.f);
				vb.add(2.5f + x);
				vb.add(0f);

				vb.add(2.5f + x);
				vb.add(7.5f);
				vb.add(7.5f + x);
				vb.add(7.5f);

				x += 10;
				break;

			case '8':
				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(0.f);

				vb.add(7.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(15f);

				vb.add(2.5f + x);
				vb.add(7.5f);
				vb.add(7.5f + x);
				vb.add(7.5f);

				vb.add(2.5f + x);
				vb.add(0f);
				vb.add(2.5f + x);
				vb.add(15f);

				vb.add(2.5f + x);
				vb.add(15f);
				vb.add(7.5f + x);
				vb.add(15f);

				x += 10;
				break;

			case '9':
				vb.add(2.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(0.f);

				vb.add(7.5f + x);
				vb.add(0.f);
				vb.add(7.5f + x);
				vb.add(15f);

				vb.add(2.5f + x);
				vb.add(7.5f);
				vb.add(7.5f + x);
				vb.add(7.5f);

				vb.add(2.5f + x);
				vb.add(7.5f);
				vb.add(2.5f + x);
				vb.add(15f);

				vb.add(2.5f + x);
				vb.add(15f);
				vb.add(7.5f + x);
				vb.add(15f);

				x += 10;
				break;

			case ':':
				vb.add(4.5f + x);
				vb.add(5.f);
				vb.add(5.5f + x);
				vb.add(5.f);

				vb.add(4.5f + x);
				vb.add(10.f);
				vb.add(5.5f + x);
				vb.add(10.f);

				x += 10;
				break;
			}
		}
		return vb;
	}

	public static String numToRoman(int num) {
		String roman = "";
		while (num >= 1000) {
			roman += 'M';
			num -= 1000;
		}
		if (num >= 900) {
			roman += "CM";
			num -= 900;
		}
		if (num >= 500) {
			roman += 'D';
			num -= 500;
		}
		if (num >= 400) {
			roman += "CD";
			num -= 400;
		}

		while (num >= 100) {
			roman += 'C';
			num -= 100;
		}
		if (num >= 90) {
			roman += "XC";
			num -= 90;
		}
		if (num >= 50) {
			roman += 'L';
			num -= 50;
		}
		if (num >= 40) {
			roman += "XL";
			num -= 40;
		}

		while (num >= 10) {
			roman += 'X';
			num -= 10;
		}
		if (num == 9) {
			roman += "IX";
			num -= 9;
		}
		if (num >= 5) {
			roman += 'V';
			num -= 5;
		}
		if (num == 4) {
			roman += "IV";
			num -= 4;
		}

		while (num >= 1) {
			roman += 'I';
			num -= 1;
		}

		return roman;
	}

}

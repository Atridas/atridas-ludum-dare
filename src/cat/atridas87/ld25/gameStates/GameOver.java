package cat.atridas87.ld25.gameStates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld25.LD25;
import cat.atridas87.ld25.Resources;
import cat.atridas87.ld25.render.FontManager;
import cat.atridas87.ld25.render.ImageManager;

public class GameOver  extends BasicGameState {

	private Color filter;
	private LD25 ld25;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		filter = new Color(1, 1, 1, 0.25f);
		ld25 = (LD25)arg1;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		arg2.setBackground(Color.black);
		ImageManager im = ImageManager.getInstance();
		UnicodeFont font = FontManager.getInstance().getFont(25);

		int coins = ld25.getCurrentLevel().getCoins();
		int points = ld25.getCurrentLevel().getPoints();
		int souls = ld25.getCurrentLevel().soulCounter;
		int combos = ld25.getCurrentLevel().maxCombo;
		int lives = ld25.getCurrentLevel().getLives();
		
		int pCoins = coins / 100;
		int pSouls = souls * 10;
		int pCombos = combos * 50;
		float pLives = Resources.livesMult(lives);
		
		int totalPoints = (points + pCoins + pSouls + pCombos);
		totalPoints = (int)(totalPoints * pLives);
		
		im.gameoverBackground.draw(0, 0, filter);
		
		im.pointsLarge.draw(100, 70, 25, 25);
		font.drawString(360, 70, Integer.toString(points));

		im.coinLarge.draw(100, 120, 25, 25);
		font.drawString(150, 120, Integer.toString(coins));
		font.drawString(360, 120, Integer.toString(pCoins));
		
		im.soulLarge.draw(100, 170, 25, 25);
		font.drawString(150, 170, Integer.toString(souls));
		font.drawString(360, 170, Integer.toString(pSouls));
		
		im.soulCombo.draw(100, 220, 25, 25);
		font.drawString(150, 220, Integer.toString(combos));
		font.drawString(360, 220, Integer.toString(pCombos));
		
		im.livesLarge.draw(100, 270, 25, 25);
		font.drawString(150, 270, Integer.toString(lives));
		font.drawString(360, 270, "x " + Float.toString(pLives));
		
		
		im.pointsLarge.draw(250, 370, 25, 25);
		font.drawString(300, 370, Integer.toString(totalPoints));
		
		
		FontManager.getInstance().getFont(15).drawString(0, 500, "Double click to return to main screen");
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {

	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		
		if(clickCount == 2) {
			LD25.getInstance().enterState(Resources.State.TITLE_SCREEN.ordinal());
		}
	}

	@Override
	public int getID() {
		return Resources.State.GAME_OVER.ordinal();
	}

}

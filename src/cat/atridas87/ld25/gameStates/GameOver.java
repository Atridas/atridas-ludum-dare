package cat.atridas87.ld25.gameStates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld25.LD25;
import cat.atridas87.ld25.Resources;
import cat.atridas87.ld25.render.ImageManager;

public class GameOver  extends BasicGameState {

	private Color filter;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		filter = new Color(1, 1, 1, 0.25f);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		arg2.setBackground(Color.black);
		ImageManager im = ImageManager.getInstance();
		
		im.gameoverBackground.draw(0, 0, filter);
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

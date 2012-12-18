package cat.atridas87.ld25.gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld25.LD25;
import cat.atridas87.ld25.Resources;

public class Credits extends BasicGameState {
	
	private Image currentTutorialImage;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		currentTutorialImage = new Image("resources/images/credits_screen.png");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		
		currentTutorialImage.draw();
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {

	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		LD25.getInstance().enterState(Resources.State.TITLE_SCREEN.ordinal());
	}
	

	@Override
	public int getID() {
		return Resources.State.CREDITS.ordinal();
	}

}

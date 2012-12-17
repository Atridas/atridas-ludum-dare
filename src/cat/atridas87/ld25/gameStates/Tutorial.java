package cat.atridas87.ld25.gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld25.LD25;
import cat.atridas87.ld25.Resources;

public class Tutorial extends BasicGameState {
	
	public final int NUM_TUTORIALS = 3;
	
	private int currentTutorial;
	private Image currentTutorialImage;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		reset();
	}
	
	public void reset()
			throws SlickException {

		currentTutorial = 0;
		currentTutorialImage = new Image("resources/images/tutorial/tutorial_0.png");
		
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
		if(clickCount == 1) {
			currentTutorial++;
			if(currentTutorial >= NUM_TUTORIALS) {
				LD25.getInstance().enterState(Resources.State.TITLE_SCREEN.ordinal());
			} else {
				try {
					currentTutorialImage = new Image("resources/images/tutorial/tutorial_" + Integer.toString(currentTutorial) + ".png");
				} catch (SlickException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	

	@Override
	public int getID() {
		return Resources.State.TUTORIAL.ordinal();
	}
}

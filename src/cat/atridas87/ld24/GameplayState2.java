package cat.atridas87.ld24;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import static cat.atridas87.ld24.Resources.State.*;

public class GameplayState2 extends BasicGameState {

	private Image background, background2;
	
	private boolean pressed;
	private boolean changeState;

	@Override
	public int getID() {
		return GAMEPLAY_STATE_2.ordinal();
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		background  = new Image("resources/images/test3.png");
		background2 = new Image("resources/images/test4.png");
		
		changeState = false;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		if(!pressed)
		{
			background.draw(0, 0, container.getWidth(), container.getHeight());
		} else {
			background2.draw(0, 0, container.getWidth(), container.getHeight());
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if(changeState) {
			game.enterState(GAMEPLAY_STATE.ordinal());
			changeState = false;
		}
	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);

		if(button == 0)
		{
			pressed = !pressed;
		} else if(button == 1){
			changeState = true;
		}
	}
}

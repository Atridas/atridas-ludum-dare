package cat.atridas87.ld24;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import static cat.atridas87.ld24.Resources.*;
import static cat.atridas87.ld24.Resources.State.*;

public class LD24 extends StateBasedGame {

	public LD24() {
		super(APP_NAME);

		addState(new GameplayState());
		
		this.enterState(GAMEPLAY_STATE.ordinal());
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.getState(GAMEPLAY_STATE.ordinal()).init(container, this);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer container = new AppGameContainer(new LD24());
		
		container.setDisplayMode(800, 600, false);
		container.start();
	}
}

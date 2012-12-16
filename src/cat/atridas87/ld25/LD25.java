package cat.atridas87.ld25;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld25.gameStates.PlayerTurn;
import cat.atridas87.ld25.render.ImageManager;

public class LD25 extends StateBasedGame {

	public LD25() {
		super(Resources.APP_NAME);

		addState(new PlayerTurn());
		
		this.enterState(Resources.State.PLAYER_TURN.ordinal());
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		ImageManager.getInstance().init();

		this.getState(Resources.State.PLAYER_TURN.ordinal()).init(container, this);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer container = new AppGameContainer(new LD25());
		
		container.setDisplayMode(720, 540, false);
		container.start();
	}
	
	public void startNewGame() {
		
		//((NewGameState1)this.getState(Resources.State.NEW_GAME_STATE_1.ordinal())).reset(this);

		//this.enterState(Resources.State.NEW_GAME_STATE_1.ordinal());
	}

}

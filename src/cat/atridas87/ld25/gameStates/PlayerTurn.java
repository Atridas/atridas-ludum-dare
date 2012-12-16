package cat.atridas87.ld25.gameStates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld25.LD25;
import cat.atridas87.ld25.Resources;

public class PlayerTurn extends BasicGameState {
	
	@Override
	public void init(GameContainer _gameContainer,
			StateBasedGame _stateBasedGame) throws SlickException {

	}

	@Override
	public void render(GameContainer _gameContainer,
			StateBasedGame _stateBasedGame, Graphics _graphics)
			throws SlickException {

		_graphics.setBackground(Color.cyan);
		
		((LD25)_stateBasedGame).getCurrentLevel().draw(0, 0, 720, 540);
	}

	@Override
	public void update(GameContainer _gameContainer,
			StateBasedGame _stateBasedGame, int arg2) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return Resources.State.PLAYER_TURN.ordinal();
	}

}

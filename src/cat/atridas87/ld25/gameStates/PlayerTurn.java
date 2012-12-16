package cat.atridas87.ld25.gameStates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld25.Resources;
import cat.atridas87.ld25.modelData.Castle;

public class PlayerTurn extends BasicGameState {
	private Castle castleTest;

	@Override
	public void init(GameContainer _gameContainer,
			StateBasedGame _stateBasedGame) throws SlickException {
		castleTest = Resources.createLevel0Castle();
	}

	@Override
	public void render(GameContainer _gameContainer,
			StateBasedGame _stateBasedGame, Graphics _graphics)
			throws SlickException {

		_graphics.setBackground(Color.cyan);

		castleTest.drawCastle(0, 0, 540, 540);

		castleTest.drawConstructibleRooms(540, 100, 180, 440);
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

package cat.atridas87.ld25.gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld25.Resources;
import cat.atridas87.ld25.modelData.Sala;
import cat.atridas87.ld25.modelData.Soul;

public class PlayerTurn extends BasicGameState {

	Sala test1, test2, test3;
	
	@Override
	public void init(GameContainer _gameContainer, StateBasedGame _stateBasedGame)
			throws SlickException {
		test1 = new Sala(new Image("resources/images/rooms/aa.png"), Soul.A, Soul.A);
		test2 = new Sala(new Image("resources/images/rooms/ab.png"), Soul.A, Soul.B);
		test3 = new Sala(new Image("resources/images/rooms/aabbcc.png"), Soul.A, Soul.A, Soul.B, Soul.B, Soul.C, Soul.C);
	}

	@Override
	public void render(GameContainer _gameContainer, StateBasedGame _stateBasedGame, Graphics _graphics)
			throws SlickException {

		test1.draw(50, 50, 144, 81);
		test2.draw(300, 50, 144, 81);
		test3.draw(100, 200, 144, 81);

	}

	@Override
	public void update(GameContainer _gameContainer, StateBasedGame _stateBasedGame, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return Resources.State.PLAYER_TURN.ordinal();
	}

}

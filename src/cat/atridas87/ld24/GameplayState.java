package cat.atridas87.ld24;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld24.modelData.GameBoard;
import cat.atridas87.ld24.modelData.SkillCard.SkillColor;
import cat.atridas87.ld24.render.ImageManager;

import static cat.atridas87.ld24.Resources.State.*;

public class GameplayState extends BasicGameState {

	private Image background, background2;
	
	private GameBoard gameBoard;
	
	@Override
	public int getID() {
		return GAMEPLAY_STATE.ordinal();
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		ImageManager.getInstance().init();
		
		background  = new Image("resources/images/test.png");
		background2 = new Image("resources/images/test2.png");
		
		
		gameBoard = new GameBoard((new Random()).nextLong()); //TODO rnd
		
		gameBoard.initGame();
		
		gameBoard.initGraphics();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		background.draw(0, 0, container.getWidth(), container.getHeight());
		
		gameBoard.getCurrentEnvironment().draw(200, 225, 100, 150);

		gameBoard.getSkillDeck(SkillColor.RED).get(0).draw(500, 225, 100, 150);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		
		
	}
}

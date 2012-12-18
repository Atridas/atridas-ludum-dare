package cat.atridas87.ld25;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld25.gameStates.GameOver;
import cat.atridas87.ld25.gameStates.PlayerTurn;
import cat.atridas87.ld25.gameStates.TitleScreen;
import cat.atridas87.ld25.gameStates.Tutorial;
import cat.atridas87.ld25.modelData.Level;
import cat.atridas87.ld25.render.ImageManager;

public class LD25 extends StateBasedGame {
	
	private Level currentLevel;

	public LD25() {
		super(Resources.APP_NAME);
		
		instance = this;

		addState(new PlayerTurn());
		addState(new TitleScreen());
		addState(new Tutorial());
		addState(new GameOver());
		
		this.enterState(Resources.State.TITLE_SCREEN.ordinal());
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		ImageManager.getInstance().init();
		
		container.setShowFPS(false);

		//this.getState(Resources.State.PLAYER_TURN.ordinal()).init(container, this);
		startNewGame();
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer container = new AppGameContainer(new LD25());
		
		container.setDisplayMode(720, 540, false);
		container.start();
	}
	
	public Level getCurrentLevel() {
		return currentLevel;
	}
	
	public void startNewGame() throws SlickException {
		currentLevel = new Level(Resources.createLevel0Castle(), Resources.createLevel0Waves(), 100);
	}
	
	private static LD25 instance;
	public static LD25 getInstance() {
		return instance;
	}

}

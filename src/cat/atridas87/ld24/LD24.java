package cat.atridas87.ld24;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld24.modelData.GameBoard;
import cat.atridas87.ld24.modelData.PlayerBoard;
import cat.atridas87.ld24.render.ImageManager;

import cat.atridas87.ld24.Resources;

public class LD24 extends StateBasedGame {
	
	public GameBoard board;
	public PlayerBoard mainPlayer;
	public UnicodeFont popupFont;

	public LD24() {
		super(Resources.APP_NAME);

		addState(new EmptyState());
		addState(new NewGameState1());
		addState(new GamePhase1());
		
		this.enterState(Resources.State.EMPTY_STATE.ordinal());
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		ImageManager.getInstance().init();

		this.getState(Resources.State.EMPTY_STATE.ordinal()).init(container, this);
		this.getState(Resources.State.NEW_GAME_STATE_1.ordinal()).init(container, this);
		this.getState(Resources.State.GAME_PHASE_1.ordinal()).init(container, this);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer container = new AppGameContainer(new LD24());
		
		container.setDisplayMode(800, 600, false);
		container.start();
	}
	
	public void startNewGame() {
		board = new GameBoard((new Random()).nextLong());
		board.initGame();
		board.initGraphics();
		
		mainPlayer = board.getPlayers().get(0);
		
		((NewGameState1)this.getState(Resources.State.NEW_GAME_STATE_1.ordinal())).reset(this);

		this.enterState(Resources.State.NEW_GAME_STATE_1.ordinal());
	}
	
	@SuppressWarnings("unchecked")
	public void drawPopup(float x, float y, float w, float h, String text) {
		if(popupFont == null) {
			try {
				popupFont = new UnicodeFont("resources/Font/accid___.ttf", 20, false, false);//Create Instance
				popupFont.addAsciiGlyphs();   //Add Glyphs
				popupFont.addGlyphs(400, 600); //Add Glyphs
				popupFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); //Add Effects
				popupFont.loadGlyphs();  //Load Glyphs
			} catch (SlickException e) {
				throw new IllegalStateException(e);
			} 
		}
		
		ImageManager.getInstance().getPopupBackground().draw(x,y,w,h);
		
		popupFont.drawString(x + 0.06f * w, y + 0.06f * w, text);
	}
}

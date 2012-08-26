package cat.atridas87.ld24.gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld24.LD24;
import cat.atridas87.ld24.Resources;
import cat.atridas87.ld24.render.ImageManager;

public class FinalScreen extends BasicGameState {

	private LD24 game;
	private UnicodeFont font;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer container, StateBasedGame _game)
			throws SlickException {
		game = (LD24) _game;
		try {
			font = new UnicodeFont("resources/Font/accid___.ttf", 31,
					false, false);// Create Instance
			font.addAsciiGlyphs(); // Add Glyphs
			font.addGlyphs(400, 600); // Add Glyphs
			font.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); // Add
																		  // Effects
			font.loadGlyphs(); // Load Glyphs
		} catch (SlickException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame _game, Graphics g)
			throws SlickException {
		ImageManager im = ImageManager.getInstance();
		
		Image bk = im.getScoreBackground();

		int w = bk.getWidth();
		int h = bk.getHeight();

		int timesX = container.getWidth()  / w;
		int timesY = container.getHeight() / h;
		
		for(int x = 0; x <= timesX; x++) {
			for(int y = 0; y <= timesY; y++) {
				bk.draw(x * w, y * h);
			}
		}

		String str1 = "You got " + game.mainPlayer.getPoints() + " points";
		String str2 = "AI 1 got " + game.board.getPlayers().get(1).getPoints() + " points";
		String str3 = "AI 2 got " + game.board.getPlayers().get(2).getPoints() + " points";
		String str4 = "AI 3 got " + game.board.getPlayers().get(3).getPoints() + " points";

		font.drawString(200, 125, str1);
		font.drawString(200, 225, str2);
		font.drawString(200, 325, str3);
		font.drawString(200, 425, str4);
		
		im.getOk().draw(650, 50, 100, 100);
	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if(button == 0 && clickCount == 1) {
			if(x >= 650 && y >= 50 && x <= 750 && y <= 150) {
				game.enterState(EmptyState.ID);
			}
		}
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

	}

	@Override
	public int getID() {
		return ID;
	}

	public static final int ID = Resources.State.FINAL_SCREEN.ordinal();

}

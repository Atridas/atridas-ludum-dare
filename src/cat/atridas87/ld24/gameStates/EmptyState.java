package cat.atridas87.ld24.gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld24.LD24;
import cat.atridas87.ld24.Resources;
import cat.atridas87.ld24.render.ImageManager;

public class EmptyState extends BasicGameState {

	private LD24 game;
	private UnicodeFont font, font2;

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer container, StateBasedGame _game)
			throws SlickException {
		game = (LD24) _game;

		try {
			font = new UnicodeFont("resources/Font/accid___.ttf", 31, false,
					false);// Create Instance
			font.addAsciiGlyphs(); // Add Glyphs
			font.addGlyphs(400, 600); // Add Glyphs
			font.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); // Add
																			// Effects
			font.loadGlyphs(); // Load Glyphs

			font2 = new UnicodeFont("resources/Font/accid___.ttf", 20, false,
					false);// Create Instance
			font2.addAsciiGlyphs(); // Add Glyphs
			font2.addGlyphs(400, 600); // Add Glyphs
			font2.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); // Add
																			// Effects
			font2.loadGlyphs(); // Load Glyphs
		} catch (SlickException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		ImageManager im = ImageManager.getInstance();

		im.drawTiledBackground(container);

		font.drawString(100, 100, "Ludum Card Evolution\n"
				+ "is a game created by Isaac 'Atridas' Serrano Guash\n"
				+ "for Ludum Dare 24.\n"
				+ "This game was created in 48 hours, is distributed\n"
				+ "with the GPLv3 and contains art not created by me.\n\n"
				+ "Developed using the Slick and jorbis libraries.\n"
				+ "Sound effects created with sfxr.");

		String credits;
		if (LD24.FULL_GAME) {
			credits = "Music: http://www.toucanmusic.co.uk\n"
					+ "Background: http://andrearusky.deviantart.com/art/Vintage-Background-4-portaits-29219790\n"
					+ "Menu Background was developed from: http://www.squidfingers.com/patterns/";
		} else {
			credits = "Menu Background was developed from: http://www.squidfingers.com/patterns/";
		}

		font2.drawString(100, 440, credits);

		im.getOk().draw(650, 50, 100, 100);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0 && clickCount == 1) {
			if (x >= 650 && y >= 50 && x <= 750 && y <= 150) {
				game.startNewGame();
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	public static final int ID = Resources.State.EMPTY_STATE.ordinal();

}

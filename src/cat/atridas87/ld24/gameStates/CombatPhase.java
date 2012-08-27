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
import cat.atridas87.ld24.ai.EnemyAI;
import cat.atridas87.ld24.modelData.Creature;
import cat.atridas87.ld24.modelData.PlayerBoard;
import cat.atridas87.ld24.render.ImageManager;

public class CombatPhase extends BasicGameState {

	private float w, h;
	private LD24 game;

	private UnicodeFont font;

	private PopupState popupState;
	
	private Creature creatures[] = new Creature[4];

	@Override
	@SuppressWarnings("unchecked")
	public void init(GameContainer container, StateBasedGame _game)
			throws SlickException {
		game = (LD24) _game;
		try {
			font = new UnicodeFont("resources/Font/accid___.ttf", 25, false,
					false);// Create Instance
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

		w = container.getWidth();
		h = container.getHeight();

		float hUnit = w / 16;
		float vUnit = h / 12;

		im.getBackground().draw(0, 0, w, h);

		font.drawString(hUnit * 6, vUnit * .5f, "Combat Phase");

		game.board.draw(0, 0, w, h);

		game.mainPlayer.drawCreatures(8 * hUnit, 4 * vUnit, 8 * hUnit,
				8 * vUnit);

		game.mainPlayer.drawHand(0, 8 * vUnit, 8 * hUnit, 4 * vUnit);

		// popup
		if (popupState != PopupState.DISMISSED) {

			String text;
			if (popupState == PopupState.FIRST) {

				text = "Now you have to choose a creature to combat.\n"
						+ "Your creature will be compared to those of the\n"
						+ "other players and the winner will award 3 times it's\n"
						+ "star values. The looser will award it's stars too,\n"
						+ "so keep that in mind!" + "\n\n"
						+ "Click here to show next.";
			} else {
				text = "Remember that you can see the other player's\n"
						+ "creatures at any time clicking on their names\n"
						+ "on the upper right corner." + "\n\n"
						+ "Click here to dismiss.";
			}

			game.drawPopup(8 * hUnit, vUnit, 7 * hUnit, (7.f * 11.f / 20.f)
					* hUnit, text);

		} else {
			im.getInfo().draw(14.75f * hUnit, 0.25f * vUnit, hUnit, hUnit);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0 && clickCount == 1) {

			float hUnit = w / 16;
			float vUnit = h / 12;

			if (popupState != PopupState.DISMISSED && x >= 8 * hUnit
					&& y >= vUnit && x <= 15 * hUnit
					&& y <= (1 + 7.f * 11.f / 20.f) * hUnit) {
				switch (popupState) {
				case FIRST:
					popupState = PopupState.SECOND;
				case SECOND:
					popupState = PopupState.DISMISSED;
					break;
				default:
					break;
				}
				Resources.next.play(1, 0.25f);
			} else if (popupState == PopupState.DISMISSED
					&& x >= 14.75f * hUnit && y >= 0.25f * vUnit
					&& x <= 15.75f * hUnit && y <= 1.25f * vUnit) {
				popupState = PopupState.FIRST;
			} else {

				// creatures
				float cardSizeW = 8 * vUnit
						/ (game.mainPlayer.getCreatures().size() + 1);

				float interCardW = cardSizeW
						/ (game.mainPlayer.getCreatures().size() + 1);

				float posX = 8 * vUnit + interCardW;
				for (Creature creature : game.mainPlayer.getCreatures()) {

					float posY = 7 * vUnit - cardSizeW;

					if (x >= posX && y >= posY && x <= posX + cardSizeW && y <= posY + cardSizeW) {

						// TODO
						creatures[0] = creature;

						doIA();

						if(game.board.getNextEnvironment() != null)
						{
							((RegenerationPhase)game.getState(RegenerationPhase.ID)).enterPhase();
							game.enterState(RegenerationPhase.ID);
						} else {
							//((FinalScreen)game.getState(FinalScreen.ID)).enterPhase();
							game.enterState(FinalScreen.ID);
						}
					}
					// im.getCreatureImage(creature).draw(posX - cardSizeW *
					// 0.125f,
					// posY - cardSizeW * 0.125f, cardSizeW * 1.25f,
					// cardSizeW * 1.25f);
					
					// next position
					posX += interCardW + cardSizeW;
				}

			}
		}
	}

	private void doIA() {
		for (int i = 0; i < 3; i++) {
			EnemyAI ai = game.ai[i];
			PlayerBoard playerBoard = game.board.getPlayers().get(i + 1);

			creatures[i + 1] = ai.combatPhase(game.board, playerBoard);
		}
	}

	public void setFirstTime() {
		popupState = PopupState.FIRST;
		enterPhase();
	}

	public void enterPhase() {
		for(int i = 0; i < creatures.length; i++) {
			creatures[i] = null;
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	public static final int ID = Resources.State.COMBAT_PHASE.ordinal();

	private static enum PopupState {
		FIRST, SECOND, DISMISSED;
	}
}

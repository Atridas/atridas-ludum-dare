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
import cat.atridas87.ld24.ai.EnemyAI;
import cat.atridas87.ld24.ai.EnemyAI.DiscardAndReplace;
import cat.atridas87.ld24.modelData.Creature;
import cat.atridas87.ld24.modelData.PlayerBoard;
import cat.atridas87.ld24.modelData.PlayerBoard.CreatureAndCard;
import cat.atridas87.ld24.modelData.SkillCard;
import cat.atridas87.ld24.render.ImageManager;

public class EvolutionPhase extends BasicGameState {

	private float w, h;
	private LD24 game;

	private UnicodeFont font, helpFont;

	private PopupState popupState;
	private ActionState actionState;

	private SkillCard addedCards[] = new SkillCard[2];
	private Creature creatureToAddCard;
	
	private PlayerBoard showBoard;

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer container, StateBasedGame _game)
			throws SlickException {
		game = (LD24) _game;
		try {
			font = new UnicodeFont("resources/Font/accid___.ttf", 25,
					false, false);// Create Instance
			font.addAsciiGlyphs(); // Add Glyphs
			font.addGlyphs(400, 600); // Add Glyphs
			font.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); // Add
																			// Effects
			font.loadGlyphs(); // Load Glyphs
			
			helpFont = new UnicodeFont("resources/Font/accid___.ttf", 15,
					false, false);// Create Instance
			helpFont.addAsciiGlyphs(); // Add Glyphs
			helpFont.addGlyphs(400, 600); // Add Glyphs
			helpFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); // Add
																			// Effects
			helpFont.loadGlyphs(); // Load Glyphs
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

		font.drawString(hUnit * 6, vUnit * .5f, "Evolution Phase");

		game.board.draw(0, 0, w, h);

		game.mainPlayer.drawCreatures(8 * hUnit, 4 * vUnit, 8 * hUnit,
				8 * vUnit);

		game.mainPlayer.drawHand(0, 8 * vUnit, 8 * hUnit, 4 * vUnit);
		
		
		String text;
		if(addedCards[0] == null) {
			text = "You have to change 3 skill cards from your creatures";
		} else if(addedCards[1] == null) {
			text = "You have to change 2 skill cards from your creatures";
		} else {
			text = "You have to change one skill card from your creatures";
		}
		helpFont.drawString(0.75f * hUnit, 8.5f * vUnit, text);

		// popup
		if (popupState != PopupState.DISMISSED) {

			if (popupState == PopupState.FIRST) {
				text = "Here you have the chance to change 3 cards from\n"
						+ "all of your creatures skill cards." + "\n\n"
						+ "Click here to show next.";
			} else if (popupState == PopupState.SECOND) {
				text = "To do so, you must firs click at a card you want\n"
						+ "to dismiss and then a card on your hand to add."
						+ "\n\n" + "Click here to show next.";
			} else {
				text = "Remember that the weakest creature in the\n"
						+ "current ambient will perish!. The stars are not\n"
						+ "attributes, but victory points, so make sure\n"
						+ "surviving creatures will have many!" + "\n\n" +
						// "Click here to show next.";
						"Click here to dismiss.";
			}

			game.drawPopup(8 * hUnit, vUnit, 7 * hUnit, (7.f * 11.f / 20.f)
					* hUnit, text);

		} else {
			im.getInfo().draw(14.75f * hUnit, 0.25f * vUnit, hUnit, hUnit);
		}
		
		if(showBoard != null) {
			Image popupImage = im.getPopupBackground();
			popupImage.draw(4 * hUnit, 1 * vUnit, 8 * hUnit, 10 * hUnit);
			showBoard.drawCreatures(4 * hUnit, 1.25f * vUnit, 8 * hUnit, 8 * hUnit);
			showBoard.drawBackHand(4 * hUnit, 9 * vUnit, 8 * hUnit, 2 * hUnit);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0 && clickCount == 1) {

			if(showBoard != null) {
				showBoard = null;
				return;
			}
			
			float hUnit = w / 16;
			float vUnit = h / 12;

			{
				showBoard = game.board.clickBoard(hUnit, vUnit, x, y);
				if(showBoard != null) {
					return;
				}
			}
			
			if (popupState != PopupState.DISMISSED && x >= 8 * hUnit
					&& y >= vUnit && x <= 15 * hUnit
					&& y <= (1 + 7.f * 11.f / 20.f) * hUnit) {
				switch (popupState) {
				case FIRST:
					popupState = PopupState.SECOND;
					break;
				case SECOND:
					popupState = PopupState.THIRD;
					break;
				case THIRD:
					popupState = PopupState.DISMISSED;
					break;
				default:
					break;
				}
				Resources.next.play(1, 0.25f);
			} else if (popupState == PopupState.DISMISSED && x >= 14.75f * hUnit && y >= 0.25f * vUnit
					&& x <= 15.75f * hUnit && y <= 1.25f * vUnit) {
				popupState = PopupState.FIRST;
			} else if (actionState == ActionState.DISCARD_CARD) {
				CreatureAndCard cac = game.mainPlayer.discardCardFromCreature(
						8 * hUnit, 4 * vUnit, hUnit, vUnit, x, y);
				if (cac != null) {
					if (cac.card == addedCards[0] || cac.card == addedCards[1]) {
						game.mainPlayer.addCardToCreature(cac.creature,
								cac.card);
						// TODO error message
					} else {
						creatureToAddCard = cac.creature;
						game.board.discardCard(cac.card);
						actionState = ActionState.SELECT_NEW_CARD;
						Resources.select.play(1, 0.25f);
					}
				}
			} else if (actionState == ActionState.SELECT_NEW_CARD) {
				SkillCard card = game.mainPlayer.useCardFromHand(0, 8 * vUnit,
						hUnit, vUnit, x, y);
				if (card != null) {
					game.mainPlayer.addCardToCreature(creatureToAddCard, card);
					actionState = ActionState.DISCARD_CARD;
					Resources.select.play(1, 0.25f);
					if (addedCards[0] == null) {
						addedCards[0] = card;
					} else if (addedCards[1] == null) {
						addedCards[1] = card;
					} else {
						doIA();
						((EnvironmentPhase) game.getState(EnvironmentPhase.ID))
								.enterPhase();
						game.enterState(EnvironmentPhase.ID);
					}
				}
			}
		}
	}

	private void doIA() {
		for (int i = 0; i < 3; i++) {
			EnemyAI ai = game.ai[i];
			PlayerBoard playerBoard = game.board.getPlayers().get(i + 1);

			DiscardAndReplace[] actions = new DiscardAndReplace[3];
			ai.evolutionPhase(game.board, playerBoard, actions);

			for (DiscardAndReplace action : actions) {
				playerBoard.removeCardFromCreature(action.creature,
						action.discard);
				playerBoard.removeCardFromHand(action.replace);
				playerBoard.addCardToCreature(action.creature, action.replace);
				game.board.discardCard(action.discard);
			}
		}
	}

	public void setFirstTime() {
		popupState = PopupState.FIRST;
		enterPhase();
	}

	public void enterPhase() {
		actionState = ActionState.DISCARD_CARD;
		addedCards[0] = null;
		addedCards[1] = null;
		showBoard = null;
	}

	@Override
	public int getID() {
		return ID;
	}

	public static final int ID = Resources.State.EVOLUTION_PHASE.ordinal();

	private static enum PopupState {
		FIRST, SECOND, THIRD, DISMISSED;
	}

	private static enum ActionState {
		DISCARD_CARD, SELECT_NEW_CARD;
	}
}

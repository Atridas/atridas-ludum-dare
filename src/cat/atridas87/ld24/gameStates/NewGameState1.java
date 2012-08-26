package cat.atridas87.ld24.gameStates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import cat.atridas87.ld24.modelData.Creature;
import cat.atridas87.ld24.modelData.PlayerBoard;
import cat.atridas87.ld24.modelData.SkillCard;
import cat.atridas87.ld24.modelData.SkillCard.SkillColor;
import cat.atridas87.ld24.render.ImageManager;

public class NewGameState1 extends BasicGameState {

	private ArrayList<SkillCard> skillsToPlace;
	private HashMap<Creature, ArrayList<SkillCard>> skillsPlaced;

	private UnicodeFont font;

	private float w, h;
	private LD24 game;
	private PopupState popupState;
	
	private PlayerBoard showBoard;

	@Override
	public void init(GameContainer container, StateBasedGame _game)
			throws SlickException {
		game = (LD24) _game;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void render(GameContainer container, StateBasedGame _game, Graphics g)
			throws SlickException {
		if (font == null) {
			try {
				font = new UnicodeFont("resources/Font/accid___.ttf", 25,
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
		ImageManager im = ImageManager.getInstance();

		w = container.getWidth();
		h = container.getHeight();

		float hUnit = w / 16;
		float vUnit = h / 12;

		im.getBackground().draw(0, 0, w, h);

		font.drawString(hUnit * 6, vUnit * 0.5f, "Game Preparation");

		game.board.draw(0, 0, w, h);

		game.mainPlayer.drawCreatures(8 * hUnit, 4 * vUnit, 8 * hUnit,
				8 * vUnit);

		// skills to be placed
		float posY = 9 * hUnit;

		for (SkillCard card : skillsToPlace) {

			im.getCardBackSide(card.getSkillColor()).draw(5 * hUnit, posY,
					2 * hUnit, 3 * hUnit);

			posY += 0.5f * hUnit;
		}

		// skills placed
		float cardSizeW = 8 * hUnit / (4 + 1);
		float cardSizeH = cardSizeW * 3 / 2;

		float interCardW = cardSizeW / (4 + 1);

		float posX = 8 * hUnit + interCardW;
		for (Creature creature : game.mainPlayer.getCreatures()) {
			float interCardH = 2.5f * vUnit / 5;

			posY = 5 * vUnit + 2.5f * vUnit;

			for (SkillCard card : skillsPlaced.get(creature)) {
				// card.draw(posX, posY, cardSizeW, cardSizeH);
				im.getCardBackSide(card.getSkillColor()).draw(posX, posY,
						cardSizeW, cardSizeH);
				posY += interCardH;
			}

			posX += interCardW + cardSizeW;
		}

		// popup
		if (popupState != PopupState.DISMISSED) {

			String text;
			if (popupState == PopupState.FIRST) {
				text = "You now have 5 cards of each color, and you must\n"
						+ "assign them to your creatures. Each card will\n"
						+ "improve your creatures' skills, and each color\n"
						+ "has a diferent skill distribution." + "\n\n"
						+ "Click here to show next.";
			} else if (popupState == PopupState.SECOND) {
				text = "You can click on any creature icon (right bellow\n"
						+ "this popup) and you will assign a card to that\n"
						+ "creature." + "\n\n" + "Click here to show next.";
			} else if (popupState == PopupState.THIRD) {
				text = "Remember that the weakest creature in the\n"
						+ "current ambient will perish and that there will\n"
						+ "be a combat." + "\n\n" +
						// "Click here to show next.";
						"Click here to dismiss.";
			} else {
				text = "\n" + "\n" + "\n" + "\n\n" + "Click here to dismiss.";
			}

			game.drawPopup(8 * hUnit, vUnit, 7 * hUnit, (7.f * 11.f / 20.f)
					* hUnit, text);

			if (popupState == PopupState.FOURTH) {
				// TODO
			}
		} else {
			im.getInfo().draw(14.75f * hUnit, 0.25f * vUnit, hUnit, hUnit);
		}
		
		if(showBoard != null) {
			Image popupImage = im.getPopupBackground();
			popupImage.draw(4 * hUnit, 1 * vUnit, 8 * hUnit, 10 * hUnit);
			showBoard.drawCreatures(4 * hUnit, 1 * vUnit, 8 * hUnit, 8 * hUnit);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame _game, int delta)
			throws SlickException {

		if (skillsToPlace.size() == 0) {
			{ // player
				// we have added all skills
				for (Entry<Creature, ArrayList<SkillCard>> entry : skillsPlaced
						.entrySet()) {
					Creature creature = entry.getKey();
					for (SkillCard card : entry.getValue()) {
						game.mainPlayer.addCardToCreature(creature, card);
					}
				}

				// we give the cards
				for (SkillColor color : SkillColor.values()) {
					game.mainPlayer.addCardToHand(game.board.drawSkill(color));
					game.mainPlayer.addCardToHand(game.board.drawSkill(color));
				}
			}

			for (int i = 0; i < 3; i++) {
				EnemyAI ai = game.ai[i];
				PlayerBoard playerBoard = game.board.getPlayers().get(i + 1);

				Map<Creature, ArrayList<SkillColor>> cardColors = ai
						.distributeInitialCards(game.board, playerBoard);

				HashMap<Creature, ArrayList<SkillCard>> cards = new HashMap<>();
				for (Entry<Creature, ArrayList<SkillColor>> entry : cardColors
						.entrySet()) {
					ArrayList<SkillCard> deck = new ArrayList<>();
					for (SkillColor color : entry.getValue()) {
						deck.add(game.board.drawSkill(color));
					}
					cards.put(entry.getKey(), deck);
				}

				// we have added all skills
				for (Entry<Creature, ArrayList<SkillCard>> entry : cards
						.entrySet()) {
					Creature creature = entry.getKey();
					for (SkillCard card : entry.getValue()) {
						playerBoard.addCardToCreature(creature, card);
					}
				}

				// we give the cards
				for (SkillColor color : SkillColor.values()) {
					playerBoard.addCardToHand(game.board.drawSkill(color));
					playerBoard.addCardToHand(game.board.drawSkill(color));
				}
			}

			((EvolutionPhase) game.getState(EvolutionPhase.ID)).setFirstTime();
			((EnvironmentPhase) game.getState(EnvironmentPhase.ID)).setFirstTime();
			((RegenerationPhase) game.getState(RegenerationPhase.ID)).setFirstTime();
			game.enterState(EvolutionPhase.ID);
		}

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
					// popupState = PopupState.FOURTH;
					popupState = PopupState.DISMISSED;
					break;
				case FOURTH:
					popupState = PopupState.DISMISSED;
					break;
				default:
					break;
				}
				Resources.next.play(1, 0.25f);
			} else if (popupState == PopupState.DISMISSED && x >= 14.75f * hUnit && y >= 0.25f * vUnit
					&& x <= 15.75f * hUnit && y <= 1.25f * vUnit) {
				popupState = PopupState.FIRST;
			} else if (skillsToPlace.size() > 0) {

				Creature creature = game.mainPlayer.creatureHitTest(8 * hUnit,
						5 * vUnit, 8 * hUnit, 7 * vUnit, (float) x, (float) y);

				if (creature != null) {
					ArrayList<SkillCard> skills = skillsPlaced.get(creature);
					if (skills.size() < 5) {
						skills.add(skillsToPlace.remove(0));
						Resources.select.play(1, 0.25f);
					}
				}
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	public static final int ID = Resources.State.NEW_GAME_STATE_1.ordinal();

	public void reset(LD24 game) {
		skillsToPlace = new ArrayList<>(4 * 5);
		skillsPlaced = new HashMap<>();
		for (Creature creature : game.mainPlayer.getCreatures()) {
			skillsPlaced.put(creature, new ArrayList<SkillCard>(5));
		}

		for (SkillColor color : SkillColor.values()) {

			skillsToPlace.add(game.board.drawSkill(color));
			skillsToPlace.add(game.board.drawSkill(color));
			skillsToPlace.add(game.board.drawSkill(color));
			skillsToPlace.add(game.board.drawSkill(color));
			skillsToPlace.add(game.board.drawSkill(color));

		}

		popupState = PopupState.FIRST;
		showBoard = null;
	}

	private static enum PopupState {
		FIRST, SECOND, THIRD, FOURTH, DISMISSED;
	}
}

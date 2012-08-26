package cat.atridas87.ld24.gameStates;

import java.util.HashSet;

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
import cat.atridas87.ld24.modelData.Attribute;
import cat.atridas87.ld24.modelData.Creature;
import cat.atridas87.ld24.modelData.GameBoard;
import cat.atridas87.ld24.modelData.PlayerBoard;
import cat.atridas87.ld24.modelData.SkillCard;
import cat.atridas87.ld24.modelData.SkillCard.SkillColor;
import cat.atridas87.ld24.render.ImageManager;

public class RegenerationPhase extends BasicGameState {

	private float w, h;
	private LD24 game;

	private UnicodeFont font;

	private PopupState popupState;
	private ActionState actionState;

	private Creature regeneratedCreature;

	private HashSet<SkillColor> possibleDecks;

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

		font.drawString(hUnit * 6, vUnit * .5f, "Regeneration Phase");

		game.board.draw(0, 0, w, h);

		game.mainPlayer.drawCreatures(8 * hUnit, 4 * vUnit, 8 * hUnit,
				8 * vUnit);

		game.mainPlayer.drawHand(0, 8 * vUnit, 8 * hUnit, 4 * vUnit);

		int cardsAdded;
		switch (actionState) {
		case ADD_CARDS_1:
			cardsAdded = game.mainPlayer.getCreatureSkills(regeneratedCreature)
					.size();
			font.drawString(2 * hUnit, 7.75f * vUnit,
					addCardsText(3 - cardsAdded));
			break;
		case ADD_CARDS_2:
			cardsAdded = game.mainPlayer.getCreatureSkills(regeneratedCreature)
					.size();
			font.drawString(2 * hUnit, 7.75f * vUnit,
					addCardsText(5 - cardsAdded));
			break;
		case CHOOSE_CARDS:
			cardsAdded = game.mainPlayer.getHandSize();
			font.drawString(9 * hUnit, 0.75f * vUnit, drawCardsText(cardsAdded));

			for (SkillColor color : possibleDecks) {
				float sDeckX;
				float sDeckY = vUnit * 2;
				switch (color) {
				case RED:
					sDeckX = hUnit * 8;
					break;
				case GREEN:
					sDeckX = hUnit * 10;
					break;
				case BLUE:
					sDeckX = hUnit * 12;
					break;
				case YELLOW:
					sDeckX = hUnit * 14;
					break;
				default:
					throw new IllegalStateException();
				}

				sDeckX += hUnit * 0.25f;
				sDeckY -= hUnit * 0.75f;

				im.getStar().draw(sDeckX, sDeckY, hUnit * 0.5f, hUnit * 0.5f);
			}
		}

		// popup
		if (popupState != PopupState.DISMISSED) {
			String text;
			if (popupState == PopupState.FIRST) {
				text = "Here you will regenerate your creature.\n"
						+ "First you will add 3 skill cards, then you will\n"
						+ "draw cards based on your surviving creatures'\n"
						+ "skills and finally you will add the last 2 cards."
						+ "\n\n" + "Click here to show next.";
			} else {
				text = "You will choose 4 cards from the deck related to\n"
						+ "your stronger creature (that with the most stars),\n"
						+ "2 from the deck related to your 2nd creature and\n"
						+ "2 from any deck." + "\n\n"
						+ "Click here to dismiss.";
			}

			game.drawPopup(8 * hUnit, vUnit, 7 * hUnit, (7.f * 11.f / 20.f)
					* hUnit, text);

		} else {
			im.getInfo().draw(14.75f * hUnit, 0.25f * vUnit, hUnit, hUnit);
		}
	}

	private String addCardsText(int cards) {
		if (cards == 1) {
			return "Add one card to your creature";
		} else {
			return "Add " + cards + " cards to your creature.";
		}
	}

	private String drawCardsText(int cards) {
		if (cards < 6) {
			cards = 6 - cards;
		} else if (cards < 8) {
			cards = 8 - cards;
		} else {
			cards = 10 - cards;
		}
		if (cards == 1) {
			return "Draw one card from possible decks";
		} else {
			return "Draw " + cards + " cards from possible decks.";
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame _game, int delta)
			throws SlickException {
		if (possibleDecks != null) {

			checkDecks(possibleDecks, game.board);
		}
	}

	private static void checkDecks(HashSet<SkillColor> possibleDecks,
			GameBoard board) {
		EXTERN_LOOP: do {
			for (SkillColor color : possibleDecks) {
				if (board.isSkillDeckEmpty(color)) {
					possibleDecks.remove(color);
					continue EXTERN_LOOP;
				}
			}
		} while (false);

		if (possibleDecks.size() == 0) {
			for (SkillColor color : SkillColor.values()) {
				if (!board.isSkillDeckEmpty(color)) {
					possibleDecks.add(color);
				}
			}
		}

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
					break;
				case SECOND:
					popupState = PopupState.DISMISSED;
					break;
				default:
					break;
				}
				Resources.next.play(1, 0.25f);
			} else if (popupState == PopupState.DISMISSED && x >= 14.75f * hUnit && y >= 0.25f * vUnit
					&& x <= 15.75f * hUnit && y <= 1.25f * vUnit) {
				popupState = PopupState.FIRST;
			} else if (actionState == ActionState.ADD_CARDS_1) {
				SkillCard card = game.mainPlayer.useCardFromHand(0, 8 * vUnit,
						hUnit, vUnit, x, y);
				if (card != null) {
					game.mainPlayer
							.addCardToCreature(regeneratedCreature, card);
					Resources.select.play(1, 0.25f);
					if (game.mainPlayer.getCreatureSkills(regeneratedCreature)
							.size() == 3) {
						actionState = ActionState.CHOOSE_CARDS;
						HashSet<Creature> creatures = strongestCreatures(
								game.mainPlayer, regeneratedCreature);

						possibleDecks = possibleDecks(game.mainPlayer,
								creatures);
					}

				}
			} else if (actionState == ActionState.CHOOSE_CARDS) {

				SkillCard card = game.board.drawSkill(0, 0, w, h, x, y,
						possibleDecks);
				if (card != null) {
					game.mainPlayer.addCardToHand(card);
					Resources.select.play(1, 0.25f);

					if (game.mainPlayer.getHandSize() == 6) {

						HashSet<Creature> creatures = secondStrongestCreatures(
								game.mainPlayer, regeneratedCreature);
						if (creatures.size() == 0) {
							for (SkillColor color : SkillColor.values()) {
								possibleDecks.add(color);
							}
						} else {
							possibleDecks = possibleDecks(game.mainPlayer,
									creatures);
						}

					} else if (game.mainPlayer.getHandSize() == 8) {
						for (SkillColor color : SkillColor.values()) {
							possibleDecks.add(color);
						}
					}
					if (game.mainPlayer.getHandSize() == 10) {
						actionState = ActionState.ADD_CARDS_2;
					}
				}

			} else if (actionState == ActionState.ADD_CARDS_2) {
				SkillCard card = game.mainPlayer.useCardFromHand(0, 8 * vUnit,
						hUnit, vUnit, x, y);
				if (card != null) {
					game.mainPlayer
							.addCardToCreature(regeneratedCreature, card);
					Resources.select.play(1, 0.25f);
					if (game.mainPlayer.getCreatureSkills(regeneratedCreature)
							.size() == 5) {
						doIA();
						
						game.board.doEnviromentalChangePhase();
						

						((EvolutionPhase)game.getState(EvolutionPhase.ID)).enterPhase();
						game.enterState(EvolutionPhase.ID);
					}
				}
			}
		}
	}

	private static HashSet<Creature> strongestCreatures(PlayerBoard player,
			Creature regeneratedCreature) {
		int maxStars = 0;
		for (Creature creature : player.getCreatures()) {
			if (creature != regeneratedCreature) {
				int creatureStars = player.getStarCount(creature);
				if (creatureStars > maxStars) {
					maxStars = creatureStars;
				}
			}
		}

		HashSet<Creature> creatures = new HashSet<>();

		for (Creature creature : player.getCreatures()) {
			if (creature != regeneratedCreature
					&& player.getStarCount(creature) == maxStars) {
				creatures.add(creature);
			}
		}

		return creatures;
	}

	private static HashSet<Creature> secondStrongestCreatures(
			PlayerBoard player, Creature regeneratedCreature) {
		int maxStars = 0;
		for (Creature creature : player.getCreatures()) {
			if (creature != regeneratedCreature) {
				int creatureStars = player.getStarCount(creature);
				if (creatureStars > maxStars) {
					maxStars = creatureStars;
				}
			}
		}

		int maxStars2 = 0;
		for (Creature creature : player.getCreatures()) {
			if (creature != regeneratedCreature) {
				int creatureStars = player.getStarCount(creature);
				if (creatureStars > maxStars2 && creatureStars < maxStars) { // worse
																				// than
																				// the
																				// best
					maxStars2 = creatureStars;
				}
			}
		}

		HashSet<Creature> creatures = new HashSet<>();

		for (Creature creature : player.getCreatures()) {
			if (creature != regeneratedCreature
					&& player.getStarCount(creature) == maxStars2) {
				creatures.add(creature);
			}
		}

		return creatures;
	}

	private static HashSet<SkillColor> possibleDecks(PlayerBoard player,
			HashSet<Creature> creatures) {
		HashSet<SkillColor> decks = new HashSet<>();

		int maxStrength = 0;
		for (Creature creature : creatures) {

			for (Attribute attr : Attribute.values()) {
				int str = player.getAttributeCount(creature, attr);
				if (str > maxStrength) {
					maxStrength = str;
				}
			}
		}

		for (Creature creature : creatures) {
			for (Attribute attr : Attribute.values()) {
				int str = player.getAttributeCount(creature, attr);
				if (str == maxStrength) {
					decks.add(attr.mainColor());
				}
			}
		}
		return decks;
	}

	private void doIA() {
		for (int i = 0; i < 3; i++) {
			EnemyAI ai = game.ai[i];
			PlayerBoard playerBoard = game.board.getPlayers().get(i + 1);

			Creature aiRegeneratedCreature = regeneratedCreature(playerBoard);

			HashSet<Creature> creatures = strongestCreatures(playerBoard,
					aiRegeneratedCreature);

			HashSet<SkillColor> aiPossibleDecks1 = possibleDecks(playerBoard,
					creatures);
			creatures = secondStrongestCreatures(playerBoard,
					aiRegeneratedCreature);
			HashSet<SkillColor> aiPossibleDecks2;
			if (creatures.size() == 0) {
				aiPossibleDecks2 = new HashSet<>();
				for (SkillColor color : SkillColor.values()) {
					aiPossibleDecks2.add(color);
				}
			} else {
				aiPossibleDecks2 = possibleDecks(playerBoard, creatures);
			}

			SkillCard[] cardsToAdd = new SkillCard[3];

			ai.regenerationPhaseAddCards1(game.board, playerBoard,
					aiRegeneratedCreature, cardsToAdd);

			for (SkillCard card : cardsToAdd) {
				playerBoard.addCardToCreature(aiRegeneratedCreature, card);
				playerBoard.removeCardFromHand(card);
			}

			// --

			SkillColor[] cardsToDraw = new SkillColor[4];

			checkDecks(aiPossibleDecks1, game.board);
			ai.regenerationPhaseDrawCards1(game.board, playerBoard,
					aiRegeneratedCreature, aiPossibleDecks1, aiPossibleDecks2,
					cardsToDraw);

			for (SkillColor color : cardsToDraw) {
				SkillCard card = game.board.drawSkill(color);
				playerBoard.addCardToHand(card);
			}

			cardsToDraw = new SkillColor[2];

			checkDecks(aiPossibleDecks1, game.board); // TODO això caldria fer-ho d'una altra manera
			//pero pal extrem
			ai.regenerationPhaseDrawCards2(game.board, playerBoard,
					aiRegeneratedCreature, aiPossibleDecks2, cardsToDraw);

			for (SkillColor color : cardsToDraw) {
				SkillCard card = game.board.drawSkill(color);
				playerBoard.addCardToHand(card);
			}

			cardsToDraw = new SkillColor[4];

			checkDecks(aiPossibleDecks1, game.board);
			ai.regenerationPhaseDrawCards3(game.board, playerBoard,
					aiRegeneratedCreature, cardsToDraw);

			for (SkillColor color : cardsToDraw) {
				SkillCard card = game.board.drawSkill(color);
				playerBoard.addCardToHand(card);
			}

			cardsToAdd = new SkillCard[2];

			ai.regenerationPhaseAddCards2(game.board, playerBoard,
					aiRegeneratedCreature, cardsToAdd);

			for (SkillCard card : cardsToAdd) {
				playerBoard.addCardToCreature(aiRegeneratedCreature, card);
				playerBoard.removeCardFromHand(card);
			}

			// --
			/*
			 * 
			 * public void regenerationPhaseAddCards1(GameBoard board,
			 * PlayerBoard myBoard, Creature resurrectedCreature, SkillCard[]
			 * out); public void regenerationPhaseDrawCards1(GameBoard board,
			 * PlayerBoard myBoard, Creature resurrectedCreature,
			 * Set<SkillColor> possibles1, Set<SkillColor> possibles2,
			 * SkillColor[] out); public void
			 * regenerationPhaseDrawCards2(GameBoard board, PlayerBoard myBoard,
			 * Creature resurrectedCreature, Set<SkillColor> possibles2,
			 * SkillColor[] out); public void
			 * regenerationPhaseDrawCards3(GameBoard board, PlayerBoard myBoard,
			 * Creature resurrectedCreature, SkillColor[] out); public void
			 * regenerationPhaseAddCards2(GameBoard board, PlayerBoard myBoard,
			 * Creature resurrectedCreature, SkillCard[] out);
			 */
		}
	}

	public void setFirstTime() {
		popupState = PopupState.FIRST;
		enterPhase();
	}

	public void enterPhase() {
		regeneratedCreature = null;

		regeneratedCreature = regeneratedCreature(game.mainPlayer);

		actionState = ActionState.ADD_CARDS_1;
	}

	private static Creature regeneratedCreature(PlayerBoard playerBoard) {
		for (Creature creature : playerBoard.getCreatures()) {
			if (playerBoard.getCreatureSkills(creature).size() == 0) {
				return creature;
			}
		}
		return null;
	}

	@Override
	public int getID() {
		return ID;
	}

	public static final int ID = Resources.State.REGENERATION_PHASE.ordinal();

	private static enum PopupState {
		FIRST, SECOND, DISMISSED;
	}

	private static enum ActionState {
		ADD_CARDS_1, CHOOSE_CARDS, ADD_CARDS_2
	}
}

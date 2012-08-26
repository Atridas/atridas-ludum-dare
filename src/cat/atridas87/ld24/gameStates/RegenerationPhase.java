package cat.atridas87.ld24.gameStates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

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
import cat.atridas87.ld24.modelData.EnvironmentCard;
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

		font.drawString(hUnit * 6, vUnit * .5f, "Regeneration Phase");

		game.board.draw(0, 0, w, h);

		game.mainPlayer.drawCreatures(8 * hUnit, 4 * vUnit, 8 * hUnit,
				8 * vUnit);

		game.mainPlayer.drawHand(0, 8 * vUnit, 8 * hUnit, 4 * vUnit);

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
				text = "You will choose 4 cards from the deck related to\n" +
						"your stronger creature (that with the most stars),\n" +
						"2 from the deck related to your 2nd creature and\n" +
						"2 from any deck."
				+ "\n\n" + "Click here to dismiss.";
			}

			game.drawPopup(8 * hUnit, vUnit, 7 * hUnit, (7.f * 11.f / 20.f)
					* hUnit, text);

		}

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
			font.drawString(9 * hUnit, 0.75f * vUnit,
					drawCardsText(cardsAdded));
			
			for(SkillColor color : possibleDecks) {
				float sDeckX;
				float sDeckY = vUnit * 2;
				switch(color) {
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
	}

	private String addCardsText(int cards) {
		if (cards == 1) {
			return "Add one card to your creature";
		} else {
			return "Add " + cards + " cards to your creature.";
		}
	}

	private String drawCardsText(int cards) {
		if(cards < 6) {
			cards = 6 - cards;
		} else if(cards < 8) {
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
					break;
				case SECOND:
					popupState = PopupState.DISMISSED;
					break;
				default:
					break;
				}
			} else if (actionState == ActionState.ADD_CARDS_1) {
				SkillCard card = game.mainPlayer.useCardFromHand(0, 8 * vUnit,
						hUnit, vUnit, x, y);
				if (card != null) {
					game.mainPlayer
							.addCardToCreature(regeneratedCreature, card);
					if (game.mainPlayer.getCreatureSkills(regeneratedCreature)
							.size() == 3) {
						actionState = ActionState.CHOOSE_CARDS;
					}
					
					HashSet<Creature> creatures = strongerCreatures(game.mainPlayer, regeneratedCreature);
					
					possibleDecks = possibleDecks(game.mainPlayer, creatures);
				}
			} else if (actionState == ActionState.CHOOSE_CARDS) {

				// TODO

			} else if (actionState == ActionState.ADD_CARDS_2) {
				SkillCard card = game.mainPlayer.useCardFromHand(0, 8 * vUnit,
						hUnit, vUnit, x, y);
				if (card != null) {
					game.mainPlayer
							.addCardToCreature(regeneratedCreature, card);
					if (game.mainPlayer.getCreatureSkills(regeneratedCreature)
							.size() == 5) {

						// TODO next state

					}
				}
			}
		}
	}
	
	private static HashSet<Creature> strongerCreatures(PlayerBoard player, Creature regeneratedCreature) {
		int maxStars = 0;
		for(Creature creature : player.getCreatures()) {
			if(creature != regeneratedCreature) {
				int creatureStars = player.getStarCount(creature);
				if(creatureStars > maxStars) {
					maxStars = creatureStars;
				}
			}
		}
		
		HashSet<Creature> creatures = new HashSet<>();

		for(Creature creature : player.getCreatures()) {
			if(creature != regeneratedCreature && player.getStarCount(creature) == maxStars) {
				creatures.add(creature);
			}
		}
		
		return creatures;
	}
	
	private static HashSet<SkillColor> possibleDecks(PlayerBoard player, HashSet<Creature> creatures) {
		HashSet<SkillColor> decks = new HashSet<>();
		
		for(Creature creature : creatures) {
			int maxStrength = 0;
			
			for(Attribute attr : Attribute.values()) {
				int str = player.getAttributeCount(creature, attr);
				if(str > maxStrength) {
					maxStrength = str;
				}
			}

			for(Attribute attr : Attribute.values()) {
				int str = player.getAttributeCount(creature, attr);
				if(str == maxStrength) {
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

			// --
		}
	}

	public void setFirstTime() {
		popupState = PopupState.FIRST;
		enterPhase();
	}

	public void enterPhase() {
		regeneratedCreature = null;

		for (Creature creature : game.mainPlayer.getCreatures()) {
			if (game.mainPlayer.getCreatureSkills(creature).size() == 0) {
				regeneratedCreature = creature;
				break;
			}
		}

		actionState = ActionState.ADD_CARDS_1;
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

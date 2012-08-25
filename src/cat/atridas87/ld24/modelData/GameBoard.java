package cat.atridas87.ld24.modelData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cat.atridas87.ld24.modelData.SkillCard.SkillColor;

public final class GameBoard {

	public static final int ENVIRONMENT_DECK_PERIOD_1_SIZE = 4;
	public static final int ENVIRONMENT_DECK_PERIOD_2_SIZE = 4;

	public static final int SKILL_DECK_MAIN_SKILL_CARD_COUNT = 5;
	public static final int SKILL_DECK_MAIN_X2_SKILL_CARD_COUNT = 10;
	public static final int SKILL_DECK_MAIN_X3_SKILL_CARD_COUNT = 3;
	public static final int SKILL_DECK_OTHER_SKILL_CARD_COUNT = 3;
	public static final int SKILL_DECK_MIXED_SKILL_CARD_COUNT = 3;
	public static final int SKILL_DECK_MIXED_X2_SKILL_CARD_COUNT = 1;
	public static final int SKILL_DECK_STAR_CARD_COUNT = 10;
	
	
	public static final int NUMBER_OF_PLAYERS = 4;
	
	public static final int INITIAL_NUMBER_OF_CREATURES = 4;
	
	
	private final Random rnd;
	
	private EnvironmentCard currentEnvironment, nextEnvironment;
	private EnvironmentCard combatCard;

	private ArrayList<EnvironmentCard> environmentDeck = new ArrayList<>();
	private ArrayList<EnvironmentCard> combatDeck = new ArrayList<>();
	private ArrayList<EnvironmentCard> environmentDiscardDeck = new ArrayList<>();
	private ArrayList<EnvironmentCard> combatDiscardDeck = new ArrayList<>();

	private HashMap<SkillColor, ArrayList<SkillCard>> skillDecks = new HashMap<>();
	private HashMap<SkillColor, ArrayList<SkillCard>> skillDiscardDecks = new HashMap<>();
	
	private ArrayList<PlayerBoard> players = new ArrayList<>();
	
	public GameBoard(long randomSeed) {
		rnd = new Random(randomSeed);
	}
	
	public EnvironmentCard getCurrentEnvironment() {
		return currentEnvironment;
	}
	
	public EnvironmentCard getNextEnvironment() {
		return nextEnvironment;
	}
	
	public EnvironmentCard getCombatCard() {
		return combatCard;
	}
	
	public List<EnvironmentCard> getEnvironmentDeck() {
		return Collections.unmodifiableList( environmentDeck );
	}
	
	public List<EnvironmentCard> getCombatDeck() {
		return Collections.unmodifiableList( combatDeck );
	}
	
	public List<EnvironmentCard> getEnvironmentDiscardDeck() {
		return Collections.unmodifiableList( environmentDiscardDeck );
	}
	
	public List<EnvironmentCard> getCombatDiscardDeck() {
		return Collections.unmodifiableList( combatDiscardDeck );
	}
	
	public List<SkillCard> getSkillDeck(SkillColor skillColor) {
		return Collections.unmodifiableList( skillDecks.get(skillColor) );
	}
	
	public boolean isSkillDeckEmpty(SkillColor skillColor) {
		return skillDecks.get(skillColor).size() == 0;
	}
	
	
	public void drawEnvironment() {
		environmentDiscardDeck.add(currentEnvironment);
		currentEnvironment = nextEnvironment;
		if(environmentDeck.size() > 0) {
			nextEnvironment = environmentDeck.remove(0);
		} else {
			nextEnvironment = null;
		}
	}
	
	public void drawCombat() {
		combatDiscardDeck.add(combatCard);
		if(combatDeck.size() > 0) {
			combatCard = combatDeck.remove(0);
		} else {
			combatCard = null;
		}
	}
	
	public SkillCard drawSkill(SkillColor color) {
		ArrayList<SkillCard> skillDeck = skillDecks.get(color);
		SkillCard skillCard = skillDeck.remove(0);

		if(skillDeck.size() == 0) {
			ArrayList<SkillCard> shuffle = skillDiscardDecks.get(color);
			Collections.shuffle(shuffle, rnd);
			skillDecks.put(color, shuffle);
			skillDiscardDecks.put(color, skillDeck);
		}
		
		return skillCard;
	}
	
	public void initGame() {
		HashSet<Creature> chosenCreatures = new HashSet<>();
		for(int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			PlayerBoard player = new PlayerBoard();
			
			for(int j = 0; j < INITIAL_NUMBER_OF_CREATURES; j++) {
				Creature choosenCreature = chooseCreatureAtRandom(chosenCreatures, rnd);
				player.addCreature(choosenCreature);
			}
			
			players.add(player);
		}
		
		createEnvironmentAndCombatDecks();
		
		currentEnvironment = environmentDeck.remove(0);
		nextEnvironment = environmentDeck.remove(0);
		
		combatCard = combatDeck.remove(0);
		
		for(SkillColor color : SkillColor.values()) {
			ArrayList<SkillCard> deck = createSkillDeck(color);
			Collections.shuffle(deck, rnd);
			skillDecks.put(color, deck);
			skillDiscardDecks.put(color, new ArrayList<SkillCard>());
		}
	}
	
	static Creature chooseCreatureAtRandom(Set<Creature> chosenCreatures, Random rnd) {
		int index = rnd.nextInt(Creature.values().length - chosenCreatures.size());
		for(Creature creature : Creature.values()) {
			if(!chosenCreatures.contains(creature)) {
				if(index == 0) {
					chosenCreatures.add(creature);
					return creature;
				} else {
					index--;
				}
			}
		}
		return null;
	}
	
	static ArrayList<EnvironmentCard> createPeriod1Cards() {
		ArrayList<EnvironmentCard> period1 = new ArrayList<>();
		
		for(Attribute attribute : Attribute.values()) {
			period1.add(new EnvironmentCard(attribute));
			period1.add(new EnvironmentCard(attribute));
		}
		period1.add(new EnvironmentCard(true));
		
		return period1;
	}
	
	static ArrayList<EnvironmentCard> createPeriod2Cards() {
		ArrayList<EnvironmentCard> period2 = new ArrayList<>();
		
		for(int i = 0; i < Attribute.values().length; i++) {
			Attribute attribute1 = Attribute.values()[i];
			for(int j = i + 1; j < Attribute.values().length; j++) {
				Attribute attribute2 = Attribute.values()[j];

				period2.add(new EnvironmentCard(attribute1, attribute2));
			}
		}
		period2.add(new EnvironmentCard(false));
		
		return period2;
	}
	
	private void createEnvironmentAndCombatDecks() {		
		ArrayList<EnvironmentCard> period1 = createPeriod1Cards();
		ArrayList<EnvironmentCard> period2 = createPeriod2Cards();

		Collections.shuffle(period1, rnd);
		Collections.shuffle(period2, rnd);
		
		environmentDeck = new ArrayList<>();
		environmentDiscardDeck = new ArrayList<>();
		
		for(int i = 0; i < ENVIRONMENT_DECK_PERIOD_1_SIZE; i++) {
			environmentDeck.add(period1.remove(0));
		}
		
		environmentDeck.add(new EnvironmentCard());

		for(int i = 0; i < ENVIRONMENT_DECK_PERIOD_2_SIZE; i++) {
			environmentDeck.add(period2.remove(0));
		}
		
		combatDeck = new ArrayList<>();
		combatDeck.addAll(period1);
		combatDeck.addAll(period2);
		
		Collections.shuffle(combatDeck, rnd);
	}
	/*
	 * 
	public static final int SKILL_DECK_MAIN_SKILL_CARD_COUNT = 5;
	public static final int SKILL_DECK_MAIN_X2_SKILL_CARD_COUNT = 10;
	public static final int SKILL_DECK_MAIN_X3_SKILL_CARD_COUNT = 3;
	public static final int SKILL_DECK_OTHER_SKILL_CARD_COUNT = 3;
	public static final int SKILL_DECK_MIXED_SKILL_CARD_COUNT = 3;
	public static final int SKILL_DECK_MIXED_X2_SKILL_CARD_COUNT = 1;
	public static final int SKILL_DECK_STAR_CARD_COUNT = 10;
	 */
	static ArrayList<SkillCard> createSkillDeck(SkillColor color) {
		ArrayList<SkillCard> deck = new ArrayList<>();
		
		for(int i = 0; i < SKILL_DECK_MAIN_SKILL_CARD_COUNT; i++) {
			deck.add(new SkillCard(color.mainAttribute, color));
		}
		
		for(int i = 0; i < SKILL_DECK_MAIN_X2_SKILL_CARD_COUNT; i++) {
			deck.add(new SkillCard(color.mainAttribute, color.mainAttribute, color));
		}
		
		for(int i = 0; i < SKILL_DECK_MAIN_X3_SKILL_CARD_COUNT; i++) {
			deck.add(new SkillCard(color.mainAttribute, color.mainAttribute, color.mainAttribute, color));
		}
		
		for(int i = 0; i < SKILL_DECK_OTHER_SKILL_CARD_COUNT; i++) {
			for(Attribute attribute : Attribute.values()) {
				if(attribute != color.mainAttribute) {
					deck.add(new SkillCard(attribute, color));
				}
			}
		}
		
		for(int i = 0; i < SKILL_DECK_MIXED_SKILL_CARD_COUNT; i++) {
			for(Attribute attribute : Attribute.values()) {
				if(attribute != color.mainAttribute) {
					deck.add(new SkillCard(color.mainAttribute, attribute, color));
				}
			}
		}
		
		for(int i = 0; i < SKILL_DECK_MIXED_X2_SKILL_CARD_COUNT; i++) {
			for(Attribute attribute : Attribute.values()) {
				if(attribute != color.mainAttribute) {
					deck.add(new SkillCard(color.mainAttribute, color.mainAttribute, attribute, color));
				}
			}
		}
		
		for(int i = 0; i < SKILL_DECK_STAR_CARD_COUNT; i++) {
			deck.add(new SkillCard(color));
		}
		
		return deck;
	}
	
	public void initGraphics() {
		currentEnvironment.initGraphics();
		nextEnvironment.initGraphics();
		combatCard.initGraphics();
		for(EnvironmentCard card : environmentDeck) {
			card.initGraphics();
		}
		for(EnvironmentCard card : combatDeck) {
			card.initGraphics();
		}
		
		// TODO
	}
}

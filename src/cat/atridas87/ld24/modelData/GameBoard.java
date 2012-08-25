package cat.atridas87.ld24.modelData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cat.atridas87.ld24.modelData.SkillCard.SkillColor;

public final class GameBoard {
	
	private final Random rnd;
	
	private EnvironmentCard currentEnvironment, nextEnvironment;
	private EnvironmentCard combatCard;

	private ArrayList<EnvironmentCard> environmentDeck = new ArrayList<>();
	private ArrayList<EnvironmentCard> combatDeck = new ArrayList<>();
	private ArrayList<EnvironmentCard> environmentDiscardDeck = new ArrayList<>();
	private ArrayList<EnvironmentCard> combatDiscardDeck = new ArrayList<>();

	private HashMap<SkillColor, ArrayList<SkillCard>> skillDecks = new HashMap<>();
	private HashMap<SkillColor, ArrayList<SkillCard>> skillDiscardDecks = new HashMap<>();
	
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
		if(skillDeck.size() == 0) {
			ArrayList<SkillCard> shuffle = skillDiscardDecks.get(color);
			Collections.shuffle(shuffle, rnd);
		}
		
		return skillDecks.get(color).remove(0);
	}
}

package cat.atridas87.ld24.modelData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class PlayerBoard {

	private final HashSet<Creature> creatures = new HashSet<>();
	private final HashMap<Creature, ArrayList<SkillCard>> creatureSkills = new HashMap<>();
	
	private final ArrayList<SkillCard> hand = new ArrayList<>();
	
	public Set<Creature> getCreatures() {
		return Collections.unmodifiableSet(creatures);
	}
	
	public List<SkillCard> getHand() {
		return Collections.unmodifiableList(hand);
	}
	
	public int getHandSize() {
		return hand.size();
	}
	
	public List<SkillCard> getCreatureSkills(Creature creature) {
		return Collections.unmodifiableList(creatureSkills.get(creature));
	}
	
	
	public void addCreature(Creature creature) {
		creatures.add(creature);
		creatureSkills.put(creature, new ArrayList<SkillCard>());
	}
	
	public void removeCardFromHand(SkillCard card) {
		hand.remove(card);
	}
	
	public void addCardFromHand(SkillCard card) {
		hand.add(card);
	}
	
	public void removeCardFromCreature(Creature creature, SkillCard card) {
		creatureSkills.get(creature).remove(card);
	}
	
	public void addCardFromCreature(Creature creature, SkillCard card) {
		creatureSkills.get(creature).add(card);
	}
	
	
}

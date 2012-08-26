package cat.atridas87.ld24;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import cat.atridas87.ld24.modelData.Creature;
import cat.atridas87.ld24.modelData.EnvironmentCard;
import cat.atridas87.ld24.modelData.SkillCard.SkillColor;

public class RandomAI implements EnemyAI {
	
	private final Random rnd;
	
	public RandomAI(long seed) {
		rnd = new Random(seed);
	}

	public Map<Creature, ArrayList<SkillColor>> distributeInitialCards(
			Set<Creature> creatures,
			EnvironmentCard ambient, EnvironmentCard nextAmbient,
			EnvironmentCard combat) {
		ArrayList<SkillColor> hand = new ArrayList<>();
		
		for(SkillColor color : SkillColor.values()) {
			hand.add(color);
			hand.add(color);
			hand.add(color);
			hand.add(color);
			hand.add(color);
		}
		
		Collections.shuffle(hand, rnd);
		HashMap<Creature, ArrayList<SkillColor>> out = new HashMap<>();
		
		for(Creature creature : creatures) {
			ArrayList<SkillColor> skills = new ArrayList<>();
			skills.add(hand.remove(0));
			skills.add(hand.remove(0));
			skills.add(hand.remove(0));
			skills.add(hand.remove(0));
			skills.add(hand.remove(0));
			
			out.put(creature, skills);
		}
		
		return out;
	}

}

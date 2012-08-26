package cat.atridas87.ld24.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import cat.atridas87.ld24.modelData.Creature;
import cat.atridas87.ld24.modelData.GameBoard;
import cat.atridas87.ld24.modelData.PlayerBoard;
import cat.atridas87.ld24.modelData.SkillCard;
import cat.atridas87.ld24.modelData.SkillCard.SkillColor;

public class RandomAI implements EnemyAI {

	private final Random rnd;

	public RandomAI(long seed) {
		rnd = new Random(seed);
	}

	public Map<Creature, ArrayList<SkillColor>> distributeInitialCards(
			GameBoard board, PlayerBoard myBoard) {
		ArrayList<SkillColor> hand = new ArrayList<>();

		for (SkillColor color : SkillColor.values()) {
			hand.add(color);
			hand.add(color);
			hand.add(color);
			hand.add(color);
			hand.add(color);
		}

		Collections.shuffle(hand, rnd);
		HashMap<Creature, ArrayList<SkillColor>> out = new HashMap<>();

		for (Creature creature : myBoard.getCreatures()) {
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

	@Override
	public void evolutionPhase(GameBoard board, PlayerBoard myBoard,
			DiscardAndReplace[] out) {
		
		ArrayList<SkillCard> hand = new ArrayList<>(myBoard.getHand());
		Collections.shuffle(hand, rnd);
		
		ArrayList<Creature> creatures = new ArrayList<>(myBoard.getCreatures());
		Collections.shuffle(creatures, rnd);
		
		for(int i = 0; i < out.length; i++) {
			ArrayList<SkillCard> skills = new ArrayList<>(myBoard.getCreatureSkills(creatures.get(i)));
			SkillCard discart = skills.get(rnd.nextInt(skills.size()));
			
			out[i] = new DiscardAndReplace(creatures.get(i), discart, hand.get(i));
			if(discart == null) {
				throw new IllegalStateException();
			}
		}
	}

	public Creature environmentPhase(GameBoard board, PlayerBoard myBoard,
			Set<Creature> lessAdaptedCreatures) {
		int index = rnd.nextInt(lessAdaptedCreatures.size());
		for (Creature creature : lessAdaptedCreatures) {
			if (index == 0)
				return creature;
			else
				index--;
		}
		return null;
	}

	@Override
	public void regenerationPhaseAddCards1(GameBoard board,
			PlayerBoard myBoard, Creature resurrectedCreature, SkillCard[] out) {
		for (int i = 0; i < out.length; i++) {
			int index = rnd.nextInt(myBoard.getHandSize());

			out[i] = myBoard.getHand().get(index);
			for (int j = 0; j < i; j++) {
				if (out[i] == out[j]) {
					i--;
					break;
				}
			}
		}
	}

	@Override
	public void regenerationPhaseAddCards2(GameBoard board,
			PlayerBoard myBoard, Creature resurrectedCreature, SkillCard[] out) {
		for (int i = 0; i < out.length; i++) {
			int index = rnd.nextInt( myBoard.getHandSize() );
			
			out[i] = myBoard.getHand().get(index);
			for (int j = 0; j < i; j++) {
				if (out[i] == out[j]) {
					i--;
					break;
				}
			}
		}
	}

	@Override
	public void regenerationPhaseDrawCards1(GameBoard board,
			PlayerBoard myBoard, Creature resurrectedCreature,
			Set<SkillColor> possibles1, Set<SkillColor> possibles2,
			SkillColor[] out) {
		for (int i = 0; i < out.length; i++) {

			int index = rnd.nextInt(possibles1.size());
			for (SkillColor color : possibles1) {
				if (index == 0) {
					out[i] = color;
					break;
				} else {
					index--;
				}
			}
		}
	}

	@Override
	public void regenerationPhaseDrawCards2(GameBoard board,
			PlayerBoard myBoard, Creature resurrectedCreature,
			Set<SkillColor> possibles2, SkillColor[] out) {

		for (int i = 0; i < out.length; i++) {

			int index = rnd.nextInt(possibles2.size());
			for (SkillColor color : possibles2) {
				if (index == 0) {
					out[i] = color;
					break;
				} else {
					index--;
				}
			}
		}
	}

	@Override
	public void regenerationPhaseDrawCards3(GameBoard board,
			PlayerBoard myBoard, Creature resurrectedCreature, SkillColor[] out) {
		for (int i = 0; i < out.length; i++) {

			int index = rnd.nextInt(SkillColor.values().length);
			for (SkillColor color : SkillColor.values()) {
				if (index == 0) {
					out[i] = color;
					break;
				} else {
					index--;
				}
			}
		}
	}
}

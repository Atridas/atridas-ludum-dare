package cat.atridas87.ld24.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
		for (int i = 0; i < out.length; i++) {
			int c = rnd.nextInt(myBoard.getCreatures().size());
			for (Creature creature : myBoard.getCreatures()) {
				if (c == 0) {
					int cardIndex;
					SkillCard discard;
					boolean repeated;
					do {
						repeated = false;
						cardIndex = rnd.nextInt(myBoard.getCreatureSkills(
								creature).size());
						discard = myBoard.getCreatureSkills(creature).get(
								cardIndex);
						for (int j = 0; j < i; j++) {
							if (out[j].creature == creature
									&& out[j].discard == discard) {
								repeated = true;
							}
						}
					} while (repeated);

					SkillCard replace;
					do {
						repeated = false;
						cardIndex = rnd.nextInt(myBoard.getHandSize());
						replace = myBoard.getHand().get(cardIndex);
						for (int j = 0; j < i; j++) {
							if (out[j].replace == replace) {
								repeated = true;
							}
						}
					} while (repeated);

					out[i] = new DiscardAndReplace(creature, discard, replace);

					break;
				} else {
					c--;
				}
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

			SkillCard card = myBoard.getHand().get(index);
			for (int j = 0; j < i; j++) {
				if (card == out[j]) {
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
			SkillCard card = myBoard.getHand().get(index);
			for (int j = 0; j < i; j++) {
				if (card == out[j]) {
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

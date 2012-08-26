package cat.atridas87.ld24.ai;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import cat.atridas87.ld24.modelData.Creature;
import cat.atridas87.ld24.modelData.GameBoard;
import cat.atridas87.ld24.modelData.PlayerBoard;
import cat.atridas87.ld24.modelData.SkillCard;
import cat.atridas87.ld24.modelData.SkillCard.SkillColor;

public interface EnemyAI {
	public Map<Creature, ArrayList<SkillColor>> distributeInitialCards(
			GameBoard board, PlayerBoard myBoard);

	public DiscardAndReplace[] evolutionPhase(GameBoard board,
			PlayerBoard myBoard);

	public Creature environmentPhase(GameBoard board,
			PlayerBoard myBoard, Set<Creature> lessAdaptedCreatures);

	public static class DiscardAndReplace {
		public final Creature creature;
		public final SkillCard discard, replace;
		
		public DiscardAndReplace(Creature creature, SkillCard discard, SkillCard replace) {
			this.creature = creature;
			this.discard = discard;
			this.replace = replace;
		}
	}
}

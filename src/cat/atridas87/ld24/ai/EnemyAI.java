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

	public void evolutionPhase(GameBoard board,
			PlayerBoard myBoard, DiscardAndReplace[] out);

	public Creature environmentPhase(GameBoard board,
			PlayerBoard myBoard, Set<Creature> lessAdaptedCreatures);
	
	public Creature combatPhase(GameBoard board,
			PlayerBoard myBoard);

	public void regenerationPhaseAddCards1(GameBoard board,
			PlayerBoard myBoard, Creature resurrectedCreature, SkillCard[] out);
	public void regenerationPhaseAddCards2(GameBoard board,
			PlayerBoard myBoard, Creature resurrectedCreature, SkillCard[] out);
	public void regenerationPhaseDrawCards1(GameBoard board,
			PlayerBoard myBoard, Creature resurrectedCreature, Set<SkillColor> possibles1, Set<SkillColor> possibles2, SkillColor[] out);
	public void regenerationPhaseDrawCards2(GameBoard board,
			PlayerBoard myBoard, Creature resurrectedCreature, Set<SkillColor> possibles2, SkillColor[] out);
	public void regenerationPhaseDrawCards3(GameBoard board,
			PlayerBoard myBoard, Creature resurrectedCreature, SkillColor[] out);
	

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

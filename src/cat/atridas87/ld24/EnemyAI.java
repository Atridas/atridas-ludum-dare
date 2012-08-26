package cat.atridas87.ld24;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import cat.atridas87.ld24.modelData.Creature;
import cat.atridas87.ld24.modelData.EnvironmentCard;
import cat.atridas87.ld24.modelData.SkillCard.SkillColor;

public interface EnemyAI {
	public Map<Creature, ArrayList<SkillColor>> distributeInitialCards(
			Set<Creature> creatures,
			EnvironmentCard ambient, EnvironmentCard nextAmbient,
			EnvironmentCard combat);
}

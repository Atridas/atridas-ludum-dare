package cat.atridas87.ld24.modelData;

import cat.atridas87.ld24.modelData.SkillCard.SkillColor;

public enum Attribute {
	STRENGTH,
	SPEED,
	INTELLIGENCE,
	CAMOUFLAGE;
	
	public final SkillColor mainColor() {
		switch(this) {
		case STRENGTH:
			return SkillColor.RED;
		case SPEED:
			return SkillColor.BLUE;
		case INTELLIGENCE:
			return SkillColor.YELLOW;
		case CAMOUFLAGE:
			return SkillColor.GREEN;
		}
		return null;
	}
}

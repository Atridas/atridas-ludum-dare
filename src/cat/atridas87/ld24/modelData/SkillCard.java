package cat.atridas87.ld24.modelData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class SkillCard {
	
	private final Type type;
	private final SkillColor skillColor;
	private final List<Attribute> attributes;
	
	public SkillCard(SkillColor color) {
		type = Type.STAR;
		skillColor = color;
		attributes = Collections.emptyList();
	}
	
	public SkillCard(Attribute attribute, SkillColor color) {
		type = Type.SKILL;
		skillColor = color;
		
		ArrayList<Attribute> atts = new ArrayList<>(1);
		atts.add(attribute);
		
		attributes = Collections.unmodifiableList(atts);
	}
	
	public SkillCard(Attribute attribute, Attribute attribute2, SkillColor color) {
		type = Type.SKILL;
		skillColor = color;
		
		ArrayList<Attribute> atts = new ArrayList<>(2);
		atts.add(attribute);
		atts.add(attribute2);
		
		attributes = Collections.unmodifiableList(atts);
	}
	
	public SkillCard(Attribute attribute, Attribute attribute2, Attribute attribute3, SkillColor color) {
		type = Type.SKILL;
		skillColor = color;
		
		ArrayList<Attribute> atts = new ArrayList<>(3);
		atts.add(attribute);
		atts.add(attribute2);
		atts.add(attribute3);
		
		attributes = Collections.unmodifiableList(atts);
	}

	public Type getType() {
		return type;
	}

	public SkillColor getSkillColor() {
		return skillColor;
	}
	
	public List<Attribute> attributes() {
		return attributes;
	}
	
	public static enum Type {
		STAR,
		SKILL
	}
	
	public static enum SkillColor {
		RED(Attribute.STRENGTH),
		BLUE(Attribute.SPEED),
		GREEN(Attribute.CAMOUFLAGE),
		YELLOW(Attribute.INTELLIGENCE);
		
		public final Attribute mainAttribute;
		
		private SkillColor(Attribute _relatedAttribute) {
			mainAttribute = _relatedAttribute;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((skillColor == null) ? 0 : skillColor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SkillCard other = (SkillCard) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (type != other.type)
			return false;
		if (skillColor != other.skillColor)
			return false;
		return true;
	}
}

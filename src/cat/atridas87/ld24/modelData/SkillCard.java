package cat.atridas87.ld24.modelData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.Image;

import cat.atridas87.ld24.render.ImageManager;


public final class SkillCard {
	
	private final SkillCardType type;
	private final SkillColor skillColor;
	private final List<Attribute> attributes;

	private Image backgroundImage;
	private Image[] iconImages;
	
	public SkillCard(SkillColor color) {
		type = SkillCardType.STAR;
		skillColor = color;
		attributes = Collections.emptyList();
	}
	
	public SkillCard(Attribute attribute, SkillColor color) {
		type = SkillCardType.SKILL;
		skillColor = color;
		
		ArrayList<Attribute> atts = new ArrayList<>(1);
		atts.add(attribute);
		
		attributes = Collections.unmodifiableList(atts);
	}
	
	public SkillCard(Attribute attribute, Attribute attribute2, SkillColor color) {
		type = SkillCardType.SKILL;
		skillColor = color;
		
		ArrayList<Attribute> atts = new ArrayList<>(2);
		atts.add(attribute);
		atts.add(attribute2);
		
		attributes = Collections.unmodifiableList(atts);
	}
	
	public SkillCard(Attribute attribute, Attribute attribute2, Attribute attribute3, SkillColor color) {
		type = SkillCardType.SKILL;
		skillColor = color;
		
		ArrayList<Attribute> atts = new ArrayList<>(3);
		atts.add(attribute);
		atts.add(attribute2);
		atts.add(attribute3);
		
		attributes = Collections.unmodifiableList(atts);
	}
	
	public void initGraphics() {
		ImageManager im = ImageManager.getInstance();
		backgroundImage = im.getCardBackground(skillColor);
		
		switch(type)
		{
		case SKILL:
			iconImages = new Image[attributes.size()];
			int i = 0;
			for(Attribute attribute : attributes) {
				iconImages[i] = im.getAttributeIcon(attribute);
				
				i++;
			}
			break;
		case STAR:
			iconImages = new Image[1];
			iconImages[0] = im.getStar();
			break;
		default:
			throw new IllegalStateException();
		}
		
	}

	public SkillCardType getType() {
		return type;
	}

	public SkillColor getSkillColor() {
		return skillColor;
	}
	
	public List<Attribute> attributes() {
		return attributes;
	}
	

	
	public void draw(float x, float y, float w, float h) {
		backgroundImage.draw(x, y, w, h);
		
		float hUnit = w / 4;
		float vUnit = h / 6;
		
		switch(iconImages.length) {
		case 0:
			break;
		case 1:
			iconImages[0].draw(
					x + hUnit / 2,
					y + vUnit * 3 /2,
					hUnit * 3,
					hUnit * 3);
			break;
		case 2:
			iconImages[0].draw(
					x + hUnit,
					y + vUnit * 2 / 3,
					hUnit * 2,
					hUnit * 2);
			iconImages[1].draw(
					x + hUnit,
					y + vUnit * 10 / 3,
					hUnit * 2,
					hUnit * 2);
			break;
		case 3:
			iconImages[0].draw(
					x + hUnit * 5 / 4,
					y + vUnit * 3 / 8,
					hUnit * 3 / 2,
					hUnit * 3 / 2);
			iconImages[1].draw(
					x + hUnit * 5 / 4,
					y + vUnit * 9 / 4,
					hUnit * 3 / 2,
					hUnit * 3 / 2);
			iconImages[2].draw(
					x + hUnit * 5 / 4,
					y + vUnit * 33 / 8,
					hUnit * 3 / 2,
					hUnit * 3 / 2);
			break;
		case 4:
			iconImages[0].draw(
					x + hUnit / 2,
					y + vUnit * 3 / 2,
					hUnit * 5 / 4,
					hUnit * 5 / 4);
			iconImages[1].draw(
					x + hUnit * 9 / 4,
					y + vUnit * 3 / 2,
					hUnit * 5 / 4,
					hUnit * 5 / 4);
			iconImages[2].draw(
					x + hUnit / 2,
					y + vUnit * 13 / 4,
					hUnit * 5 / 4,
					hUnit * 5 / 4);
			iconImages[3].draw(
					x + hUnit * 9 / 4,
					y + vUnit * 13 / 4,
					hUnit * 5 / 4,
					hUnit * 5 / 4);
			break;
		default:
			throw new IllegalStateException();
		}
		
	}
	
	public static enum SkillCardType {
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

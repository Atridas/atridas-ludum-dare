package cat.atridas87.ld24.modelData;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.Image;

import cat.atridas87.ld24.render.ImageManager;

public final class EnvironmentCard {
	
	private final EnvironmentType type;
	private final Set<Attribute> attributes;

	private Image backgroundImage;
	private Image[] iconImages;
	
	public EnvironmentCard() {
		type = EnvironmentType.PERIOD_CHANGE;
		attributes = Collections.emptySet();
	}
	
	public EnvironmentCard(Attribute attribute) {
		type = EnvironmentType.PERIOD_1;
		HashSet<Attribute> atts = new HashSet<>();
		atts.add(attribute);
		attributes = Collections.unmodifiableSet(atts);
	}
	
	public EnvironmentCard(Attribute attribute, Attribute attribute2) {
		type = EnvironmentType.PERIOD_2;
		HashSet<Attribute> atts = new HashSet<>(2);
		atts.add(attribute);
		atts.add(attribute2);
		attributes = Collections.unmodifiableSet(atts);
	}
	
	public EnvironmentCard(boolean period1) {
		if(period1) {
			type = EnvironmentType.PERIOD_1;
		} else {
			type = EnvironmentType.PERIOD_2;
		}
		HashSet<Attribute> atts = new HashSet<>(Attribute.values().length);
		for(Attribute attribute : Attribute.values()) {
			atts.add(attribute);
		}
		attributes = Collections.unmodifiableSet(atts);
	}
	
	public void initGraphics() {
		ImageManager im = ImageManager.getInstance();
		backgroundImage = im.getEnvironmentBackground(type);
		
		iconImages = new Image[attributes.size()];
		int i = 0;
		for(Attribute attribute : attributes) {
			iconImages[i] = im.getAttributeIcon(attribute);
			
			i++;
		}
		
	}
	
	public EnvironmentType getType() {
		return type;
	}
	
	public Set<Attribute> getAttributes() {
		return attributes;
	}
	
	public boolean containsAttribute(Attribute attribute) {
		return attributes.contains(attribute);
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
					x + hUnit,
					y + 3 * vUnit,
					2 * hUnit,
					2 * hUnit);
			break;
		case 2:
			iconImages[0].draw(
					x + hUnit / 2,
					y + vUnit * 7 / 2,
					hUnit * 5 / 4,
					hUnit * 5 / 4);
			iconImages[1].draw(
					x + hUnit * 9 / 4,
					y + vUnit * 7 / 2,
					hUnit * 5 / 4,
					hUnit * 5 / 4);
			break;
		case 4:
			iconImages[0].draw(
					x + hUnit / 2,
					y + vUnit * 5 / 2,
					hUnit * 5 / 4,
					hUnit * 5 / 4);
			iconImages[1].draw(
					x + hUnit * 9 / 4,
					y + vUnit * 5 / 2,
					hUnit * 5 / 4,
					hUnit * 5 / 4);
			iconImages[2].draw(
					x + hUnit / 2,
					y + vUnit * 17 / 4,
					hUnit * 5 / 4,
					hUnit * 5 / 4);
			iconImages[3].draw(
					x + hUnit * 9 / 4,
					y + vUnit * 17 / 4,
					hUnit * 5 / 4,
					hUnit * 5 / 4);
			break;
		default:
			throw new IllegalStateException();
		}
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		EnvironmentCard other = (EnvironmentCard) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder aux = new StringBuilder();
		aux.append(type.getShortString());
		for(Attribute att : attributes) {
			aux.append(' ');
			aux.append(att.getShortString());
		}
		return aux.toString();
	}

	public static enum EnvironmentType {
		PERIOD_1,
		PERIOD_2,
		PERIOD_CHANGE;
		
		public final String getShortString() {
			switch(this) {
			case PERIOD_1:
				return "1";
			case PERIOD_2:
				return "2";
			case PERIOD_CHANGE:
				return "C";
			}
			return null;
		}
	}
}

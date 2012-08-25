package cat.atridas87.ld24.modelData;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class EnvironmentCard {

	
	private final Type type;
	private final Set<Attribute> attributes;

	
	public EnvironmentCard() {
		type = Type.PERIOD_CHANGE;
		attributes = Collections.emptySet();
	}
	
	public EnvironmentCard(Attribute attribute) {
		type = Type.PERIOD_1;
		HashSet<Attribute> atts = new HashSet<>();
		atts.add(attribute);
		attributes = Collections.unmodifiableSet(atts);
	}
	
	public EnvironmentCard(Attribute attribute, Attribute attribute2) {
		type = Type.PERIOD_2;
		HashSet<Attribute> atts = new HashSet<>(2);
		atts.add(attribute);
		atts.add(attribute2);
		attributes = Collections.unmodifiableSet(atts);
	}
	
	public EnvironmentCard(boolean period1) {
		if(period1) {
			type = Type.PERIOD_1;
		} else {
			type = Type.PERIOD_2;
		}
		HashSet<Attribute> atts = new HashSet<>(Attribute.values().length);
		for(Attribute attribute : Attribute.values()) {
			atts.add(attribute);
		}
		attributes = Collections.unmodifiableSet(atts);
	}
	
	public Type getType() {
		return type;
	}
	
	public Set<Attribute> getAttributes() {
		return attributes;
	}
	
	public boolean containsAttribute(Attribute attribute) {
		return attributes.contains(attribute);
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
	

	public static enum Type {
		PERIOD_1,
		PERIOD_2,
		PERIOD_CHANGE
	}
}

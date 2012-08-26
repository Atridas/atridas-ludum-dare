package cat.atridas87.ld24.modelData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cat.atridas87.ld24.modelData.SkillCard.SkillCardType;
import cat.atridas87.ld24.render.ImageManager;

public final class PlayerBoard {

	private final TreeSet<Creature> creatures = new TreeSet<>();
	private final HashMap<Creature, ArrayList<SkillCard>> creatureSkills = new HashMap<>();

	private final ArrayList<SkillCard> hand = new ArrayList<>();

	public Set<Creature> getCreatures() {
		return Collections.unmodifiableSet(creatures);
	}

	public List<SkillCard> getHand() {
		return Collections.unmodifiableList(hand);
	}

	public int getHandSize() {
		return hand.size();
	}

	public List<SkillCard> getCreatureSkills(Creature creature) {
		return Collections.unmodifiableList(creatureSkills.get(creature));
	}

	public int getAttributeCount(Creature creature, Attribute attribute) {
		ArrayList<SkillCard> cards = creatureSkills.get(creature);

		int count = 0;

		for (SkillCard card : cards) {
			count += card.getAttributeCount(attribute);
		}
		return count;
	}

	public int getStarCount(Creature creature) {
		ArrayList<SkillCard> cards = creatureSkills.get(creature);

		int count = 0;

		for (SkillCard card : cards) {
			if (card.getType() == SkillCardType.STAR) {
				count++;
			}
		}
		return count;
	}

	public void addCreature(Creature creature) {
		creatures.add(creature);
		creatureSkills.put(creature, new ArrayList<SkillCard>());
	}

	public void removeCardFromHand(SkillCard card) {
		hand.remove(card);
	}

	public void addCardToHand(SkillCard card) {
		hand.add(card);
	}

	public void removeCardFromCreature(Creature creature, SkillCard card) {
		creatureSkills.get(creature).remove(card);
	}

	public void addCardToCreature(Creature creature, SkillCard card) {
		creatureSkills.get(creature).add(card);
	}


	
	public Creature discardCardFromCreature(float x, float y, float hUnit, float vUnit, float mouseX, float mouseY, SkillCard forbiddenCard1, SkillCard forbiddenCard2) {
		
		Creature selectedCreature = null;
		SkillCard selectedSkillCard = null;

		float cardSizeW = 8 * hUnit / (creatures.size() + 1);
		float cardSizeH = cardSizeW * 3 / 2;
		float interCardW = cardSizeW / (creatures.size() + 1);
		float posX = x + interCardW;
		for(Creature creature : creatures) {
			
			float interCardH;
			List<SkillCard> skills = creatureSkills.get(creature);
			int numSkills = skills.size();
			if(numSkills > 0) {
				interCardH = 2.5f * vUnit / numSkills; 
			} else {
				interCardH = 0;
			}
			
			// creature skill cards
			float posY = y + 3.5f * vUnit;
			for(SkillCard card : skills) {
				
				//card.draw(posX, posY, cardSizeW, cardSizeH);
				if(
						card != forbiddenCard1 && // can not discard the now-placed
						card != forbiddenCard2 && // cards
						mouseX >= posX &&
						mouseY >= posY &&
						mouseX <= posX + cardSizeW &&
						mouseY <= posY + cardSizeH) {
					selectedCreature = creature;
					selectedSkillCard = card;
				}
				posY += interCardH;
			}
			if(selectedSkillCard != null) {
				break;
			}
			
			// next position
			posX += interCardW + cardSizeW;
		}
		
		if(selectedSkillCard != null) {
			removeCardFromCreature(selectedCreature, selectedSkillCard);
			return selectedCreature;
		} else {
			return null;
		}
	}
	
	public SkillCard useCardFromHand(float x, float y, float hUnit, float vUnit, float mouseX, float mouseY) {
		float posX = x + 0.5f * hUnit;
		float posY = y + vUnit;

		float cardSizeW = 2 * hUnit;
		float cardSizeH = 3 * hUnit;
		
		SkillCard selectedCard = null;

		for (SkillCard card : hand) {
			if(
					mouseX >= posX &&
					mouseY >= posY &&
					mouseX <= posX + cardSizeW &&
					mouseY <= posY + cardSizeH) {
				selectedCard = card;
			}
			//card.draw(posX, posY, cardSizeW, cardSizeH);
			posX += 0.5f * hUnit;
		}
		
		if(selectedCard != null) {
			removeCardFromHand(selectedCard);
		}
		
		return selectedCard;
	}
	
	
	
	
	
	public void drawCreatures(float x, float y, float w, float h) {
		ImageManager im = ImageManager.getInstance();

		// float hUnit = w / 8;
		float vUnit = h / 8;

		float cardSizeW = w / (creatures.size() + 1);
		float cardSizeH = cardSizeW * 3 / 2;

		float interCardW = cardSizeW / (creatures.size() + 1);

		float posX = x + interCardW;
		for (Creature creature : creatures) {

			// creature icon
			float posY = y + 3 * vUnit - cardSizeW;

			im.getCreatureImage(creature)
					.draw(posX, posY, cardSizeW, cardSizeW);

			float interCardH;
			ArrayList<SkillCard> skills = creatureSkills.get(creature);
			int numSkills = skills.size();
			if (numSkills > 0) {
				interCardH = 2.5f * vUnit / numSkills;
			} else {
				interCardH = 0;
			}

			// creature skill cards
			posY = y + 3.5f * vUnit;

			for (SkillCard card : skills) {
				card.draw(posX, posY, cardSizeW, cardSizeH);
				posY += interCardH;
			}

			// creature skills
			float size = (3 * vUnit - cardSizeW)
					/ (Attribute.values().length + 2);
			float whiteSpace = size / (Attribute.values().length + 2);
			posY = y + whiteSpace;
			for (Attribute att : Attribute.values()) {
				int value = getAttributeCount(creature, att);
				float iconX = posX;
				for (int i = 0; i < value; i++) {
					im.getAttributeIcon(att).draw(iconX, posY, size, size);
					iconX += cardSizeW / 13.f;
				}
				posY += size + whiteSpace;
			}
			int value = getStarCount(creature);
			float iconX = posX;
			for (int i = 0; i < value; i++) {
				im.getStar().draw(iconX, posY, size, size);
				iconX += cardSizeW / 13.f;
			}

			// next position
			posX += interCardW + cardSizeW;
		}
	}

	public void drawHand(float x, float y, float w, float h) {
		float hUnit = w / 8;
		float vUnit = h / 4;

		float posX = x + 0.5f * hUnit;
		float posY = y + vUnit;

		float cardSizeW = 2 * hUnit;
		float cardSizeH = 3 * hUnit;

		for (SkillCard card : hand) {
			card.draw(posX, posY, cardSizeW, cardSizeH);
			posX += 0.5f * hUnit;
		}
	}

	public Creature creatureHitTest(float x, float y, float w, float h,
			float mouseX, float mouseY) {
		// float hUnit = w / 8;
		float vUnit = h / 7;

		float cardSizeW = w / (creatures.size() + 1);
		// float cardSizeH = cardSizeW * 3 / 2;

		float interCardW = cardSizeW / (creatures.size() + 1);

		float posX = x + interCardW;
		for (Creature creature : creatures) {
			float posY = y + 2 * vUnit - cardSizeW;

			if (mouseX >= posX && mouseY >= posY) {
				if (mouseX <= posX + cardSizeW && mouseY <= posY + cardSizeW) {
					return creature;
				}
			}
			posX += interCardW + cardSizeW;
		}
		return null;
	}

}

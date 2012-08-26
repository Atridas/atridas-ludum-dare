package cat.atridas87.ld24.modelData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import cat.atridas87.ld24.modelData.SkillCard.SkillCardType;
import cat.atridas87.ld24.render.ImageManager;

public final class PlayerBoard {

	private UnicodeFont font;

	private final TreeSet<Creature> creatures = new TreeSet<>();
	private final HashMap<Creature, ArrayList<SkillCard>> creatureSkills = new HashMap<>();

	private final ArrayList<SkillCard> hand = new ArrayList<>();

	private int points;

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

	public int getStrength(Creature creature, EnvironmentCard environment) {
		int strength = 0;
		for (Attribute att : environment.getAttributes()) {
			strength += getAttributeCount(creature, att);
		}
		return strength;
	}

	public int getPoints() {
		return points;
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

	public void discardAllCardsFromCreature(Creature creature, GameBoard board) {
		try {
			for (SkillCard card : creatureSkills.get(creature)) {
				board.discardCard(card);
			}
			creatureSkills.get(creature).clear();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void addCardToCreature(Creature creature, SkillCard card) {
		try {
			creatureSkills.get(creature).add(card);
		} catch (Exception e) {
			throw e;
		}
	}

	public void addPoints(int n) {
		points += n;
	}

	public void addSurvivingCreaturePoints() {
		for (Creature creature : creatures) {
			points += getStarCount(creature);
		}
	}

	public CreatureAndCard discardCardFromCreature(float x, float y,
			float hUnit, float vUnit, float mouseX, float mouseY) {

		Creature selectedCreature = null;
		SkillCard selectedSkillCard = null;

		float cardSizeW = 8 * hUnit / (creatures.size() + 1);
		float cardSizeH = cardSizeW * 3 / 2;
		float interCardW = cardSizeW / (creatures.size() + 1);
		float posX = x + interCardW;
		for (Creature creature : creatures) {

			float interCardH;
			List<SkillCard> skills = creatureSkills.get(creature);
			int numSkills = skills.size();
			if (numSkills > 0) {
				interCardH = 2.5f * vUnit / numSkills;
			} else {
				interCardH = 0;
			}

			// creature skill cards
			float posY = y + 3.5f * vUnit;
			for (SkillCard card : skills) {

				// card.draw(posX, posY, cardSizeW, cardSizeH);
				if (mouseX >= posX && mouseY >= posY
						&& mouseX <= posX + cardSizeW
						&& mouseY <= posY + cardSizeH) {
					selectedCreature = creature;
					selectedSkillCard = card;
				}
				posY += interCardH;
			}
			if (selectedSkillCard != null) {
				break;
			}

			// next position
			posX += interCardW + cardSizeW;
		}

		if (selectedSkillCard != null) {
			removeCardFromCreature(selectedCreature, selectedSkillCard);
			return new CreatureAndCard(selectedCreature, selectedSkillCard);
		} else {
			return null;
		}
	}

	public static final class CreatureAndCard {
		public final Creature creature;
		public final SkillCard card;

		CreatureAndCard(Creature creature, SkillCard card) {
			this.creature = creature;
			this.card = card;
		}
	}

	public SkillCard useCardFromHand(float x, float y, float hUnit,
			float vUnit, float mouseX, float mouseY) {
		float posX = x + 0.5f * hUnit;
		float posY = y + vUnit;

		float cardSizeW = 2 * hUnit;
		float cardSizeH = 3 * hUnit;

		SkillCard selectedCard = null;

		for (SkillCard card : hand) {
			if (mouseX >= posX && mouseY >= posY && mouseX <= posX + cardSizeW
					&& mouseY <= posY + cardSizeH) {
				selectedCard = card;
			}
			// card.draw(posX, posY, cardSizeW, cardSizeH);
			posX += 0.5f * hUnit;
		}

		if (selectedCard != null) {
			removeCardFromHand(selectedCard);
		}

		return selectedCard;
	}

	@SuppressWarnings("unchecked")
	public void drawCreatures(float x, float y, float w, float h) {
		if (font == null) {
			try {
				font = new UnicodeFont("resources/Font/accid___.ttf", 14,
						false, false);// Create Instance
				font.addAsciiGlyphs(); // Add Glyphs
				font.addGlyphs(400, 600); // Add Glyphs
				font.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); // Add
																				// Effects
				font.loadGlyphs(); // Load Glyphs
			} catch (SlickException e) {
				throw new IllegalStateException(e);
			}
		}
		ImageManager im = ImageManager.getInstance();

		//float hUnit = w / 8;
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
		/*
		float posY = y + 3 * vUnit - cardSizeW * 0.5f - hUnit * 0.125f;

		im.getStar().draw(x - hUnit * 0.5f, posY, hUnit * 0.25f, hUnit * 0.25f);
		font.drawString(x - hUnit * 0.125f,
				posY + hUnit * 0.25f - font.getAscent(), "" + points);
		*/
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

	public void drawBackHand(float x, float y, float w, float h) {
		ImageManager im = ImageManager.getInstance();
		float hUnit = w / 8;
		//float vUnit = h / 2;

		float posX = x;
		float posY = y;
		
		float dX = hUnit * 6.f / 10.f;

		float cardSizeW = 1.33f * hUnit;
		float cardSizeH = 2 * hUnit;

		for (SkillCard card : hand) {
			im.getCardBackSide(card.getSkillColor()).draw(posX, posY, cardSizeW, cardSizeH);
			posX += dX;
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

	@Override
	public String toString() {
		StringBuilder aux = new StringBuilder();
		aux.append("HAND: [");

		for (SkillCard card : hand) {
			aux.append("\n  (");
			aux.append(card.toString());
			aux.append(')');
		}

		aux.append("]\nCREATURES: [");

		for (Creature creature : creatures) {
			aux.append("\n  ");
			aux.append(creature.toString());
			aux.append(" {");

			for (SkillCard card : creatureSkills.get(creature)) {
				aux.append("\n    (");
				aux.append(card.toString());
				aux.append(')');
			}

			aux.append("}");
		}
		
		return aux.toString();
	}
}

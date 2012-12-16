package cat.atridas87.ld24.modelData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cat.atridas87.ld24.render.ImageManager;

public final class PlayerBoard {

	private final HashSet<Creature> creatures = new HashSet<Creature>();
	private final HashMap<Creature, ArrayList<SkillCard>> creatureSkills = new HashMap<Creature, ArrayList<SkillCard>>();
	
	private final ArrayList<SkillCard> hand = new ArrayList<SkillCard>();
	
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
	
	
	public void addCreature(Creature creature) {
		creatures.add(creature);
		creatureSkills.put(creature, new ArrayList<SkillCard>());
	}
	
	public void removeCardFromHand(SkillCard card) {
		hand.remove(card);
	}
	
	public void addCardFromHand(SkillCard card) {
		hand.add(card);
	}
	
	public void removeCardFromCreature(Creature creature, SkillCard card) {
		creatureSkills.get(creature).remove(card);
	}
	
	public void addCardToCreature(Creature creature, SkillCard card) {
		creatureSkills.get(creature).add(card);
	}
	
	public void drawCreatures(float x, float y, float w, float h) {
		ImageManager im = ImageManager.getInstance();
		
		//float hUnit = w / 8;
		float vUnit = h / 7;
		
		float cardSizeW = w / (creatures.size() + 1);
		float cardSizeH = cardSizeW * 3 / 2;
		
		float interCardW = cardSizeW / (creatures.size() + 1);
		
		float posX = x + interCardW;
		for(Creature creature : creatures) {
			float posY = y + 2 * vUnit - cardSizeW;
			
			im.getCreatureImage(creature).draw(posX, posY, cardSizeW, cardSizeW);
			
			float interCardH;
			ArrayList<SkillCard> skills = creatureSkills.get(creature);
			int numSkills = skills.size();
			if(numSkills > 0) {
				interCardH = 2.5f * vUnit / numSkills; 
			} else {
				interCardH = 0;
			}
			
			posY = y + 2.5f * vUnit;
			
			for(SkillCard card : skills) {
				card.draw(posX, posY, cardSizeW, cardSizeH);
				posY += interCardH;
			}
			
			posX += interCardW + cardSizeW;
		}
		
	}
	
	public Creature creatureHitTest(float x, float y, float w, float h, float mouseX, float mouseY) {
		//float hUnit = w / 8;
		float vUnit = h / 7;
		
		float cardSizeW = w / (creatures.size() + 1);
		//float cardSizeH = cardSizeW * 3 / 2;
		
		float interCardW = cardSizeW / (creatures.size() + 1);
		
		float posX = x + interCardW;
		for(Creature creature : creatures) {
			float posY = y + 2 * vUnit - cardSizeW;
			
			if(mouseX >= posX && mouseY >= posY) {
				if(mouseX <= posX + cardSizeW && mouseY <= posY + cardSizeW) {
					return creature;
				}
			}
			posX += interCardW + cardSizeW;
		}
		return null;
	}
	
}

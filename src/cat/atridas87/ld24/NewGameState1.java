package cat.atridas87.ld24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld24.modelData.Creature;
import cat.atridas87.ld24.modelData.SkillCard;
import cat.atridas87.ld24.modelData.SkillCard.SkillColor;
import cat.atridas87.ld24.render.ImageManager;

public class NewGameState1 extends BasicGameState {

	private ArrayList<SkillCard> skillsToPlace;
	private HashMap<Creature, ArrayList<SkillCard>> skillsPlaced;

	private float w, h;
	private LD24 game;

	@Override
	public void init(GameContainer container, StateBasedGame _game)
			throws SlickException {
		game = (LD24) _game;
	}

	@Override
	public void render(GameContainer container, StateBasedGame _game, Graphics g)
			throws SlickException {
		ImageManager im = ImageManager.getInstance();

		w = container.getWidth();
		h = container.getHeight();

		float hUnit = w / 16;
		float vUnit = h / 12;

		im.getBackground().draw(0, 0, w, h);

		game.board.draw(0, 0, w, h);

		game.mainPlayer.drawCreatures(8 * hUnit, 5 * vUnit, 8 * hUnit,
				7 * vUnit);

		// skills to be placed
		float posY = 9 * hUnit;

		for (SkillCard card : skillsToPlace) {

			im.getCardBackSide(card.getSkillColor()).draw(5 * hUnit, posY,
					2 * hUnit, 3 * hUnit);

			posY += 0.5f * hUnit;
		}

		// skills placed
		float cardSizeW = 8 * hUnit / (4 + 1);
		float cardSizeH = cardSizeW * 3 / 2;
		
		float interCardW = cardSizeW / (4 + 1);
		
		float posX = 8 * hUnit + interCardW;
		for(Creature creature : game.mainPlayer.getCreatures()) {
			float interCardH = 2.5f * vUnit / 5; 
			
			
			posY = 5 * vUnit + 2.5f * vUnit;
			
			for(SkillCard card : skillsPlaced.get(creature)) {
				//card.draw(posX, posY, cardSizeW, cardSizeH);
				im.getCardBackSide(card.getSkillColor()).draw(posX, posY, cardSizeW, cardSizeH);
				posY += interCardH;
			}
			
			posX += interCardW + cardSizeW;
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame _game, int delta)
			throws SlickException {

		if(skillsToPlace.size() == 0) {
			// we have added all skills
			for(Entry<Creature, ArrayList<SkillCard>> entry : skillsPlaced.entrySet()) {
				Creature creature = entry.getKey();
				for(SkillCard card : entry.getValue()) {
					game.mainPlayer.addCardToCreature(creature, card);
				}
			}
			
			game.enterState(Resources.State.GAME_PHASE_1.ordinal());
		}

	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0 && clickCount == 1 && skillsToPlace.size() > 0) {

			float hUnit = w / 16;
			float vUnit = h / 12;

			Creature creature = game.mainPlayer.creatureHitTest(8 * hUnit,
					5 * vUnit, 8 * hUnit, 7 * vUnit, (float) x, (float) y);
			
			if(creature != null) {
				ArrayList<SkillCard> skills = skillsPlaced.get(creature);
				if(skills.size() < 5) {
					skills.add(skillsToPlace.remove(0));
				}
			}
			
		}
	}

	@Override
	public int getID() {
		return Resources.State.NEW_GAME_STATE_1.ordinal();
	}

	public void reset(LD24 game) {
		skillsToPlace = new ArrayList<SkillCard>(4 * 5);
		skillsPlaced = new HashMap<Creature, ArrayList<SkillCard>>();
		for (Creature creature : game.mainPlayer.getCreatures()) {
			skillsPlaced.put(creature, new ArrayList<SkillCard>(5));
		}

		for (SkillColor color : SkillColor.values()) {

			skillsToPlace.add(game.board.drawSkill(color));
			skillsToPlace.add(game.board.drawSkill(color));
			skillsToPlace.add(game.board.drawSkill(color));
			skillsToPlace.add(game.board.drawSkill(color));
			skillsToPlace.add(game.board.drawSkill(color));

		}
	}
}

package cat.atridas87.ld24;

import java.util.ArrayList;
import java.util.HashMap;

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

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, StateBasedGame _game, Graphics g)
			throws SlickException {
		ImageManager im = ImageManager.getInstance();
		LD24 l_game = (LD24)_game;

		float w = container.getWidth();
		float h = container.getHeight();
		
		float hUnit = w / 16;
		float vUnit = h / 12;
		
		im.getBackground().draw(0, 0, w, h);
		
		l_game.board.draw(0, 0, w, h);
		
		l_game.mainPlayer.drawCreatures(8 * hUnit, 5 * vUnit, 8 * hUnit, 7 * vUnit);
		
		float posY = 9 * hUnit;
		
		for(SkillCard card : skillsToPlace) {
			
			im.getCardBackground(card.getSkillColor()).draw(5 * hUnit, posY, 2 * hUnit, 3 * hUnit);
			
			posY += 0.5f * hUnit;
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return Resources.State.NEW_GAME_STATE_1.ordinal();
	}

	public void reset(LD24 game) {
		skillsToPlace = new ArrayList<>( 4 * 5 );
		skillsPlaced = new HashMap<>();
		for(Creature creature : game.mainPlayer.getCreatures()) {
			skillsPlaced.put(creature, new ArrayList<SkillCard>(5));
		}
		
		for(SkillColor color : SkillColor.values()) {
			
			skillsToPlace.add(game.board.drawSkill(color));
			skillsToPlace.add(game.board.drawSkill(color));
			skillsToPlace.add(game.board.drawSkill(color));
			skillsToPlace.add(game.board.drawSkill(color));
			skillsToPlace.add(game.board.drawSkill(color));
			
		}
	}
}

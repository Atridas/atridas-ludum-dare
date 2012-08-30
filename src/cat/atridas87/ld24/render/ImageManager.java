package cat.atridas87.ld24.render;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import cat.atridas87.ld24.modelData.Attribute;
import cat.atridas87.ld24.modelData.Creature;
import cat.atridas87.ld24.modelData.EnvironmentCard.EnvironmentType;
import cat.atridas87.ld24.modelData.SkillCard.SkillColor;

public final class ImageManager {

	private Image strength, speed, intelligence, camouflage;
	private Image star;
	private Image red, green, blue, yellow;
	private Image redBackSide, greenBackSide, blueBackSide, yellowBackSide;
	
	private Image period1Background, period2Background, periodChangeBackground, environmentBackSide;
	
	private Image background, popup, scoreBackground;
	
	private Image info, ok;
	
	private Image[] creatures;
	
	public void init() throws SlickException {
		strength = new Image("resources/images/skill icons/strength skill.png");
		speed = new Image("resources/images/skill icons/speed skill.png");
		intelligence = new Image("resources/images/skill icons/intelligence skill.png");
		camouflage = new Image("resources/images/skill icons/camouflage skill.png");
		
		star = new Image("resources/images/skill icons/star.png");
		
		red = new Image("resources/images/card backgrounds/red card.png");
		green = new Image("resources/images/card backgrounds/green card.png");
		blue = new Image("resources/images/card backgrounds/blue card.png");
		yellow = new Image("resources/images/card backgrounds/yellow card.png");
		
		redBackSide = new Image("resources/images/card backgrounds/red card back side.png");
		greenBackSide = new Image("resources/images/card backgrounds/green card back side.png");
		blueBackSide = new Image("resources/images/card backgrounds/blue card back side.png");
		yellowBackSide = new Image("resources/images/card backgrounds/yellow card back side.png");
		

		period1Background = new Image("resources/images/card backgrounds/period1 card.png");
		period2Background = new Image("resources/images/card backgrounds/period2 card.png");
		periodChangeBackground = new Image("resources/images/card backgrounds/change to phase 2 card.png");
		environmentBackSide = new Image("resources/images/card backgrounds/environment card back side.png");
	
		background = new Image("resources/images/Vintage_Background_For_Portraits.png");
		scoreBackground = new Image("resources/images/pattern_145.png");
		popup = new Image("resources/images/popup background.png");

		info = new Image("resources/images/Info.png");
		ok = new Image("resources/images/ok button.png");
		
		creatures = new Image[Creature.values().length];
		for(int i = 0; i < Creature.values().length; i++) {
			creatures[i] = new Image("resources/images/creatures/creature " + i + ".png");
		}
	}
	
	public Image getCardBackground(SkillColor color) {
		switch(color) {
		case RED:
			return red;
		case GREEN:
			return green;
		case BLUE:
			return blue;
		case YELLOW:
			return yellow;
		default:
			return null;
		}
	}
	
	public Image getCardBackSide(SkillColor color) {
		switch(color) {
		case RED:
			return redBackSide;
		case GREEN:
			return greenBackSide;
		case BLUE:
			return blueBackSide;
		case YELLOW:
			return yellowBackSide;
		default:
			return null;
		}
	}
	
	public Image getAttributeIcon(Attribute attribute) {
		switch(attribute) {
		case STRENGTH:
			return strength;
		case SPEED:
			return speed;
		case INTELLIGENCE:
			return intelligence;
		case CAMOUFLAGE:
			return camouflage;
		default:
			return null;
		}
	}
	
	public Image getEnvironmentBackground(EnvironmentType type) {
		switch(type) {
		case PERIOD_1:
			return period1Background;
		case PERIOD_2:
			return period2Background;
		case PERIOD_CHANGE:
			return periodChangeBackground;
		default:
			return null;
		}
	}
	
	public Image getCreatureImage(Creature creature) {
		return creatures[creature.ordinal()];
	}
	
	public Image getEnvironmentBackSide() {
		return environmentBackSide;
	}
	
	public Image getStar() {
		return star;
	}
	
	public Image getBackground() {
		return background;
	}
	
	public Image getScoreBackground() {
		return scoreBackground;
	}
	
	public Image getPopupBackground() {
		return popup;
	}
	
	public Image getInfo() {
		return info;
	}
	
	public Image getOk() {
		return ok;
	}
	
	private static ImageManager im = new ImageManager();
	
	public static ImageManager getInstance() {
		return im;
	}
	
	public void drawTiledBackground(GameContainer container) {
		int w = scoreBackground.getWidth();
		int h = scoreBackground.getHeight();

		int timesX = container.getWidth() / w;
		int timesY = container.getHeight() / h;

		for (int x = 0; x <= timesX; x++) {
			for (int y = 0; y <= timesY; y++) {
				scoreBackground.draw(x * w, y * h);
			}
		}
	}
}

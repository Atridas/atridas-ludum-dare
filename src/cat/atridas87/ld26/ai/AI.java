package cat.atridas87.ld26.ai;

import java.util.Random;

import cat.atridas87.ld26.gameobjects.Battleground;
import cat.atridas87.ld26.gameobjects.Bot;
import cat.atridas87.ld26.gameobjects.Lane;

public class AI {

	public static final float AI_TIME_BTWN_LEVELS[] =  {  30,   45,   60,   75,   90,  105,  120};
	public static final float AI_LEVEL_TIME_TO_ADD[] = {   5,    4,    3, 2.5f,    2, 1.5f,    1, 0.75f};

	private int currentLevel;
	
	private float timeSinceLastLevelChange;
	private float timeSinceLastBot;
	
	private static final Random rnd = new Random();
	
	public void update(float _dt) {
		timeSinceLastBot += _dt;
		
		
		if(timeSinceLastBot > AI_LEVEL_TIME_TO_ADD[currentLevel]) {
			Lane lane = Lane.values()[rnd.nextInt(Lane.values().length)];
			float totalWeight = 0;
			for(int i = 0; i < Bot.Type.values().length; i++) {
				totalWeight += Bot.Type.values()[i].aiWeight;
			}
			
			Bot.Type type = Bot.Type.BASIC;
			
			float w = rnd.nextFloat() * totalWeight;
			for(int i = 0; i < Bot.Type.values().length; i++) {
				if(w < Bot.Type.values()[i].aiWeight) {
					type = Bot.Type.values()[i];
				} else {
					w -= Bot.Type.values()[i].aiWeight;
				}
			}
			
			Battleground.instance.addBot(new Bot(false, type, lane));
			
			timeSinceLastBot = 0;
		}
		
		
		if(currentLevel < AI_TIME_BTWN_LEVELS.length) {
			timeSinceLastLevelChange += _dt;
			if(timeSinceLastLevelChange > AI_TIME_BTWN_LEVELS[currentLevel]) {
				currentLevel++;
				timeSinceLastLevelChange = 0;
			}
		}
	}
	
}

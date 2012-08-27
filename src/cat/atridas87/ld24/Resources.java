package cat.atridas87.ld24;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public abstract class Resources {

	public static final String APP_NAME = "LUDUM CARD EVOLUTION";
	

	public static enum State
	{
		EMPTY_STATE,
		NEW_GAME_STATE_1,
		EVOLUTION_PHASE,
		COMBAT_PHASE,
		ENVIRONMENT_PHASE,
		REGENERATION_PHASE,
		FINAL_SCREEN
	}
	
	public static Sound hit, next, select;

	public static void init() throws SlickException {
		hit = new Sound("resources/FX/fx hit.wav");
		next = new Sound("resources/FX/fx next.wav");
		select = new Sound("resources/FX/fx select.wav");
	}
}

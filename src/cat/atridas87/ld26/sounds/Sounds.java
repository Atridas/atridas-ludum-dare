package cat.atridas87.ld26.sounds;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds {


	public static Sound shot = null;
	public static Sound botDestroyed = null;
	public static Sound newBot = null;
	//public static Sound touch = null;
	public static Sound towerDestroyed = null;
	
	public static Music music;
	
	static {
		try {
			shot = new Sound("resources/sounds/shot.wav");
			botDestroyed = new Sound("resources/sounds/botDestroyed.wav");
			newBot = new Sound("resources/sounds/newBot.wav");
			//touch = new Sound("resources/sounds/touch.wav");
			towerDestroyed = new Sound("resources/sounds/towerDestroyed.wav");
			
			music = new Music("resources/sounds/song.wav");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}

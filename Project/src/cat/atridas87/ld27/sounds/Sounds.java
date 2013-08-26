package cat.atridas87.ld27.sounds;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds {


	public static Sound click = null;
	public static Sound pickup = null;
	public static Sound pickdown = null;
	public static Sound tick = null;
	
	public static Music music;
	
	static {
		try {
			//URL is = Utils.class.getClassLoader().getResource("resources/sounds/shot.wav");
			
			//System.out.println(is.toString());
			
			click = new Sound("resources/fx/click.wav");
			pickup = new Sound("resources/fx/pickup.wav");
			pickdown = new Sound("resources/fx/pickdown.wav");
			tick = new Sound("resources/fx/tick.wav");
			
			music = new Music("resources/intent fallit.ogg");
			music.loop(1, .25f);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}

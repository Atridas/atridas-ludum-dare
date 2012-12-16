package cat.atridas87.ld25.render;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import cat.atridas87.ld25.modelData.Soul;

public class ImageManager {
	private final HashMap<Soul, Image> soulImages = new HashMap<Soul, Image>();
	
	private Image coin, points;
	
	private Image fullCircle, emptyCircle, ocupiedCircle;
	
	private Image roomBase;
	
	private ImageManager() {};
	
	public void init() throws SlickException {
		for(Soul soul : Soul.values()) {
			Image i = new Image(soul.imageString);
			soulImages.put(soul, i);
		}

		coin = new Image("resources/images/icons/coin.png");
		points = new Image("resources/images/icons/points.png");
		fullCircle = new Image("resources/images/icons/Full_Circle.png");
		emptyCircle = new Image("resources/images/icons/Empty_Circle.png");
		ocupiedCircle = new Image("resources/images/icons/Ocupied_circle.png");
		roomBase = new Image("resources/images/room_base.png");
	}
	
	public Image getSoulImage(Soul soul) {
		return soulImages.get(soul);
	}
	
	public Image getCoinImage() {
		return coin;
	}
	
	public Image getPointsImage() {
		return points;
	}
	
	public Image getFullCircleImage() {
		return fullCircle;
	}
	
	public Image getEmptyCircleImage() {
		return emptyCircle;
	}
	
	public Image getOcupiedCircleImage() {
		return ocupiedCircle;
	}
	
	public Image getRoomBase() {
		return roomBase;
	}
	
	private static ImageManager im = new ImageManager();
	
	public static ImageManager getInstance() {
		return im;
	}
}

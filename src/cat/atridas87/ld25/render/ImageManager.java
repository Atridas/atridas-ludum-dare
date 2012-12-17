package cat.atridas87.ld25.render;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import cat.atridas87.ld25.modelData.Soul;

public class ImageManager {
	private final HashMap<Soul, Image> soulImages = new HashMap<Soul, Image>();
	private final HashMap<Soul, Image> soulImages_2 = new HashMap<Soul, Image>();
	private final HashMap<Soul, Image> soulImages_3 = new HashMap<Soul, Image>();
	private final HashMap<Soul, Image> soulImages_4 = new HashMap<Soul, Image>();
	private final HashMap<Soul, Image> soulImages_5 = new HashMap<Soul, Image>();

	public Image coin, points, lives;

	public Image fullCircle, emptyCircle, ocupiedCircle;

	public Image roomBase, roomHighlight;

	public Image fons;
	public Image titleScreenBackground;
	public Image gameoverBackground;

	public Image newGameButtonPressed, newGameButtonNormal;
	public Image tutorialButtonPressed, tutorialButtonNormal;

	private ImageManager() {
	};

	public void init() throws SlickException {
		for (Soul soul : Soul.values()) {
			Image i = new Image(soul.imageString + ".png");
			Image i2 = new Image(soul.imageString + "_2.png");
			Image i3 = new Image(soul.imageString + "_3.png");
			Image i4 = new Image(soul.imageString + "_4.png");
			Image i5 = new Image(soul.imageString + "_5.png");
			soulImages.put(soul, i);
			soulImages_2.put(soul, i2);
			soulImages_3.put(soul, i3);
			soulImages_4.put(soul, i4);
			soulImages_5.put(soul, i5);
		}

		coin = new Image("resources/images/icons/coin.png");
		points = new Image("resources/images/icons/points.png");
		lives = new Image("resources/images/icons/lives.png");
		fullCircle = new Image("resources/images/icons/Full_circle.png");
		emptyCircle = new Image("resources/images/icons/Empty_circle.png");
		ocupiedCircle = new Image("resources/images/icons/Ocupied_circle.png");
		roomBase = new Image("resources/images/room_base.png");
		roomHighlight = new Image("resources/images/room_highlight.png");

		fons = new Image("resources/images/fons.png");
		titleScreenBackground = new Image("resources/images/title_screen.png");
		gameoverBackground = titleScreenBackground;

		newGameButtonPressed = new Image(
				"resources/images/buttons/new_game_pressed.png");
		newGameButtonNormal = new Image(
				"resources/images/buttons/new_game_normal.png");
		tutorialButtonPressed = new Image(
				"resources/images/buttons/tutorial_pressed.png");
		tutorialButtonNormal = new Image(
				"resources/images/buttons/tutorial_normal.png");
	}

	public Image getSoulImage(Soul soul) {
		return soulImages.get(soul);
	}

	public Image getSoulImage(Soul soul, int souls) {
		switch (souls) {
		case 1:
			return soulImages.get(soul);
		case 2:
			return soulImages_2.get(soul);
		case 3:
			return soulImages_3.get(soul);
		case 4:
			return soulImages_4.get(soul);
		case 5:
			return soulImages_5.get(soul);
		default:
			throw new RuntimeException();
		}
	}

	private static ImageManager im = new ImageManager();

	public static ImageManager getInstance() {
		return im;
	}
}

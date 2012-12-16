package cat.atridas87.ld25.modelData;

import org.newdawn.slick.UnicodeFont;

import cat.atridas87.ld25.render.FontManager;
import cat.atridas87.ld25.render.ImageManager;

public class Level {
	private Castle castle;
	
	private int coins, points;
	
	public Level(Castle _castle, int initialCoins) {
		castle = _castle;
		coins = initialCoins;
		points = 0;
	}
	
	public void draw(float x, float y, float w, float h) {
		ImageManager im = ImageManager.getInstance();

		castle.drawCastle(x, y, 540 * w / 720, h);

		
		drawRightUI(im, 
				540 * w / 720,
				0,
				180 * w / 720,
				h);
		
	}
	
	private void drawRightUI(ImageManager im, float x, float y, float w, float h) {

		float coinsDx = 10.f * w / 180;
		float pointsDx = 100.f * w / 180;
		float uiDy = 5.f * w / 180;
		float uiSize = 17.f * w / 180;

		float stringCoinsDx = 30.f * w / 180;
		float stringPointsDx = 120.f * w / 180;
		float stringDy = 5.f * w / 180;
		UnicodeFont font = FontManager.getInstance().getFont((int)uiSize);

		im.getCoinImage().draw(x + coinsDx, y + uiDy, uiSize, uiSize);
		im.getPointsImage().draw(x + pointsDx, y + uiDy, uiSize, uiSize);

		font.drawString(x + stringCoinsDx, y + stringDy, Integer.toString(coins));
		font.drawString(x + stringPointsDx, y + stringDy, Integer.toString(points));
				
		castle.drawConstructibleRooms(
				540 * w / 180,
				100 * h / 540,
				w,
				440 * h / 540);
	}
}

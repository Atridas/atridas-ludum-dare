package cat.atridas87.ld25.modelData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.UnicodeFont;

import cat.atridas87.ld25.render.FontManager;
import cat.atridas87.ld25.render.ImageManager;

public final class Level {
	private Castle castle;
	
	private HashMap<Soul, Integer> reserve = new HashMap<Soul, Integer>();
	private LinkedList<Wave> waves;
	
	private int coins, points;
	
	public Level(Castle _castle, List<Wave> _waves, int initialCoins) {
		castle = _castle;
		coins = initialCoins;
		points = 0;
		waves = new LinkedList<Wave>(_waves);
		
		for(Soul soul : Soul.values()) {
			reserve.put(soul, 0);
		}
	}
	
	public int getReserve(Soul soul) {
		return reserve.get(soul);
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
				
		// -------

		float aDy = 32.f * w / 180;
		float bDy = 62.f * w / 180;
		float cDy = 92.f * w / 180;
		
		float iconDx = 10.f * w / 180;
		float stringDx = 30.f * w / 180;

		im.getSoulImage(Soul.A).draw(x + iconDx, aDy, uiSize, uiSize);
		im.getSoulImage(Soul.B).draw(x + iconDx, bDy, uiSize, uiSize);
		im.getSoulImage(Soul.C).draw(x + iconDx, cDy, uiSize, uiSize);
		
		font.drawString(x + stringDx, aDy, Integer.toString(getReserve(Soul.A)) + " / 10" );
		font.drawString(x + stringDx, bDy, Integer.toString(getReserve(Soul.B)) + " / 10" );
		font.drawString(x + stringDx, cDy, Integer.toString(getReserve(Soul.C)) + " / 10" );
		
		float waveDx = 80.f * w / 180;
		float nextWaveDx = 30.f * w / 180;
		
		for(int i = 0; i < 4; i++) {
			if(waves.size() <= i) break;
			int a = waves.get(i).getSouls(Soul.A);
			int b = waves.get(i).getSouls(Soul.B);
			int c = waves.get(i).getSouls(Soul.C);
			if(a > 0) {
				im.getSoulImage(Soul.A, a).draw(x + waveDx, aDy, uiSize, uiSize);
			}
			if(b > 0) {
				im.getSoulImage(Soul.B, b).draw(x + waveDx, bDy, uiSize, uiSize);
			}
			if(c > 0) {
				im.getSoulImage(Soul.C, c).draw(x + waveDx, cDy, uiSize, uiSize);
			}
			
			waveDx += nextWaveDx;
		}
		
		// -------
		
		castle.drawConstructibleRooms(
				540 * w / 180,
				130 * h / 540,
				w,
				410 * h / 540);
	}
}

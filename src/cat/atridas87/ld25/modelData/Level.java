package cat.atridas87.ld25.modelData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.UnicodeFont;

import cat.atridas87.ld25.LD25;
import cat.atridas87.ld25.Resources;
import cat.atridas87.ld25.modelData.Castle.RoomSocket;
import cat.atridas87.ld25.render.FontManager;
import cat.atridas87.ld25.render.ImageManager;

public final class Level {
	private Castle castle;
	
	private HashMap<Soul, Integer> reserve = new HashMap<Soul, Integer>();
	private LinkedList<Wave> waves;

	private float deltaToNextWave = 0;
	private float deltaToNextSoul = 0;
	private Soul lastEnteringSoul = Soul.C;
	
	private int lives = 10;
	private int soulsCombo = 0;
	private int coins, points;
	
	public int maxCombo = 0;
	public int soulCounter = 0;
	
	//private boolean holdingMouse = false;
	
	public Level(Castle _castle, List<Wave> _waves, int initialCoins) {
		castle = _castle;
		coins = initialCoins;
		points = 0;
		waves = new LinkedList<Wave>(_waves);
		
		for(Soul soul : Soul.values()) {
			reserve.put(soul, 0);
		}
	}

	public void update(float ds) {
		float waveDelta = ds / Resources.WAVE_TIME;
		
		deltaToNextWave += waveDelta;
		
		while(deltaToNextWave >= 1) {
			deltaToNextWave -= 1;
			
			Wave wave = getNextWave();
			
			if(wave != null) {
				addSoulsToReserve(Soul.A, wave.getSouls(Soul.A));
			addSoulsToReserve(Soul.B, wave.getSouls(Soul.B));
			addSoulsToReserve(Soul.C, wave.getSouls(Soul.C));
		}}
		
		float soulDelta = ds / Resources.ENTER_TIME;
		
		deltaToNextSoul += soulDelta;
		while(deltaToNextSoul > 1) {
			deltaToNextSoul -= 1;
			
			int cont = 0;
			do {
				lastEnteringSoul = lastEnteringSoul.next();
				if(getReserve(lastEnteringSoul) > 0) {
					getSoulsFromReserve(lastEnteringSoul, 1);
					castle.addWalingSoul(lastEnteringSoul);
					break;
				}
				cont++;
			} while(cont < Soul.values().length);
		}
		
		castle.update(ds);
		
		if(checkLevelComplete()) {
			LD25.getInstance().enterState(Resources.State.GAME_OVER.ordinal());
		}
	}
	
	
	private boolean checkLevelComplete() {
		if(waves.size() != 0) {
			return false;
		}
		for(Soul soul : Soul.values()) {
			if(castle.getWalkingSouls(soul) != 0) {
				return false;
			}
		}
		return true;
	}
	
	public Castle getCastle() {
		return castle;
	}
	
	public int getReserve(Soul soul) {
		return reserve.get(soul);
	}
	
	public void getSoulsFromReserve(Soul soul, int num) {
		assert(num >= 0);
		int res = reserve.get(soul);
		res -= num;
		assert(res >= 0);
		reserve.put(soul, res);
	}
	
	public void addSoulsToReserve(Soul soul, int num) {
		assert(num >= 0);
		int res = reserve.get(soul);
		res += num;
		assert(res >= 0);
		reserve.put(soul, res);
	}
	
	public void breakCombo() {
		soulsCombo = 0;
	}
	
	public void finishProcessingSoul() {
		soulsCombo++;
		soulCounter++;
		addCoins(Resources.coinCombo(soulsCombo));
		addPoints(Resources.pointCombo(soulsCombo));
		
		if(soulsCombo % Resources.SOULS_TO_LIVE == 0) {
			lives++;
		}
		
		if(maxCombo < soulsCombo) {
			maxCombo = soulsCombo;
		}
	}
	
	public void dropSoul(Soul soul) {
		//addSoulsToReserve(soul, 1);
		breakCombo();
		lives--;
		
		if(lives <= 0) {
			LD25.getInstance().enterState(Resources.State.GAME_OVER.ordinal());
		}
	}
	
	public Wave getNextWave() {
		if(waves.size() > 0) {
			return waves.removeFirst();
		} else {
			return null;
		}
	}

	public void scroll(float dy) {
		castle.scroll(dy);
	}

	public boolean canScroll() {
		return castle.getFreeRooms().size() > 2;
	}
	
	/*
	public void holdButton(boolean hold) {
		holdingMouse = hold;
	}
	*/

	public RoomSocket isSocket(float x, float y) {
		return castle.isSocket(x, y);
	}

	public void setRoom(RoomSocket socket, Sala room) {
		castle.setRoom(socket, room);
	}
	
	public void takeCoins(int _coins) {
		assert(_coins <= coins);
		assert(_coins >= 0);
		coins -= _coins;
	}
	
	public void addCoins(int _coins) {
		assert(_coins >= 0);
		coins += _coins;
	}
	
	public int getCoins() {
		return coins;
	}

	public void addPoints(int _points) {
		assert(_points >= 0);
		points += _points;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getLives() {
		return lives;
	}
	
	public Sala canGrabRoom(float x, float y) {
		return castle.canGrabRoom(x, y);
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
		
		// ------

		
		castle.drawConstructibleRooms(
				x,
				y + 160 * h / 540,
				w,
				380 * h / 540);
		
		// ------
		
		im.menuTop.draw(x, y);
		
		// ------

		float coinsDx = 10.f * w / 180;
		float pointsDx = 100.f * w / 180;
		float uiDy = 5.f * w / 180;
		float uiSize = 17.f * w / 180;

		float stringCoinsDx = 30.f * w / 180;
		float stringPointsDx = 120.f * w / 180;
		float stringDy = 5.f * w / 180;
		UnicodeFont font = FontManager.getInstance().getFont((int)uiSize);

		im.coin.draw(x + coinsDx, y + uiDy, uiSize, uiSize);
		im.points.draw(x + pointsDx, y + uiDy, uiSize, uiSize);

		font.drawString(x + stringCoinsDx, y + stringDy, Integer.toString(coins));
		font.drawString(x + stringPointsDx, y + stringDy, Integer.toString(points));
		
		// ------
		
		float livesDy = 32.f * w / 180;
		
		im.lives.draw(x + coinsDx, y + livesDy, uiSize, uiSize);
		im.points.draw(x + pointsDx, y + livesDy, uiSize, uiSize);

		font.drawString(x + stringCoinsDx, y + livesDy, Integer.toString(lives));
		font.drawString(x + stringPointsDx, y + livesDy, "x " + Integer.toString(Resources.pointCombo(soulsCombo)) + " (" + Integer.toString(soulsCombo) + ")");
		
		// -------

		float aDy = 62.f * w / 180;
		float bDy = 92.f * w / 180;
		float cDy = 122.f * w / 180;
		
		float iconDx = 10.f * w / 180;
		float stringDx = 30.f * w / 180;

		im.getSoulImage(Soul.A).draw(x + iconDx, aDy, uiSize, uiSize);
		im.getSoulImage(Soul.B).draw(x + iconDx, bDy, uiSize, uiSize);
		im.getSoulImage(Soul.C).draw(x + iconDx, cDy, uiSize, uiSize);
		
		font.drawString(x + stringDx, aDy, Integer.toString(getReserve(Soul.A) + castle.getWalkingSouls(Soul.A)) );
		font.drawString(x + stringDx, bDy, Integer.toString(getReserve(Soul.B) + castle.getWalkingSouls(Soul.B)) );
		font.drawString(x + stringDx, cDy, Integer.toString(getReserve(Soul.C) + castle.getWalkingSouls(Soul.C)) );
		
		float waveDx = 80.f * w / 180;
		float nextWaveDx = 30.f * w / 180;
		
		waveDx += nextWaveDx * (1 - deltaToNextWave);
		
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
	}
}

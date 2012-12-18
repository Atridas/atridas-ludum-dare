package cat.atridas87.ld25.modelData;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

import cat.atridas87.ld25.LD25;
import cat.atridas87.ld25.Resources;
import cat.atridas87.ld25.modelData.Castle.RoomSocket;
import cat.atridas87.ld25.render.ImageManager;

public class Sala implements Comparable<Sala> {

	private final ArrayList<EstatSala> estatEspais = new ArrayList<EstatSala>();
	private final ArrayList<Soul> espais;
	private final Image backgroundImage;

	private final int price;

	public RoomSocket currentSocket;
	
	private static Sound entryRoomSound;

	public Sala(Image _backgroundImage, int _price, Soul... _souls) {
		backgroundImage = _backgroundImage;
		price = _price;
		espais = new ArrayList<Soul>(_souls.length);
		for (Soul soul : _souls) {
			espais.add(soul);
			estatEspais.add(new EstatSala());
		}

		if (entryRoomSound == null) {
			try {
				entryRoomSound = new Sound("resources/music/enter_room.wav");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public int getPrice() {
		return price;
	}

	public void reset() {
		for (int i = 0; i < estatEspais.size(); i++) {
			estatEspais.get(i).reset();
		}
		currentSocket = null;
	}

	public boolean processingSouls() {
		for (int i = 0; i < espais.size(); i++) {
			if (estatEspais.get(i).ocupat) {
				return true;
			}
		}
		return false;
	}

	public int availableSoulSpaces(Soul soul) {
		int spaces = 0;
		for (int i = 0; i < espais.size(); i++) {
			if (espais.get(i) == soul && !estatEspais.get(i).ocupat) {
				spaces++;
			}
		}
		return spaces;
	}

	public void update(float ds) {
		float addDelta = ds / Resources.TIME_CONSUMPTION;
		for (EstatSala estat : estatEspais) {
			if (estat.ocupat) {
				estat.processant += addDelta;
				if (estat.processant >= 1) {
					estat.ocupat = false;
					estat.processant = 0;
					LD25.getInstance().getCurrentLevel().finishProcessingSoul(currentSocket);
				}
			}
		}
	}

	/*
	 * public int process() { int processed = 0; for (int i = 0; i <
	 * estatEspais.size(); i++) { switch (estatEspais.get(i)) { case OCUPAT:
	 * processed++; estatEspais.set(i, EstatSala.LLIURE); break; case ENTRANT:
	 * estatEspais.set(i, EstatSala.OCUPAT); break; default: // -- } } return
	 * processed; }
	 */

	public void putSoul(Soul soul) {
		if (Resources.soundsActivated) {
			entryRoomSound.play(1, Resources.FX_VOLUME * .25f);
		}
		for (int i = 0; i < espais.size(); i++) {
			if (espais.get(i) == soul && !estatEspais.get(i).ocupat) {
				estatEspais.get(i).ocupar();
				return;
			}
		}
	}

	public void draw(ImageManager im, float x, float y, float w, float h) {
		draw(im, x, y, w, h, Color.white);
	}

	public void draw(ImageManager im, float x, float y, float w, float h,
			Color filter) {

		im.roomBase.draw(x, y, w, h, filter);

		float backgroundWidth = w * 108 / 144;
		backgroundImage.draw(x, y, backgroundWidth, h, filter);

		float soulSize = Math.min(w * 17 / 144, h * 17 / 81);
		float centerDelta = w * 18 / 144;
		float aCenter = w * 117 / 144;
		float bCenter = aCenter;
		float cCenter = aCenter;

		for (int i = 0; i < espais.size(); i++) {
			Soul soul = espais.get(i);
			EstatSala estat = estatEspais.get(i);
			float centre;
			float soulY = y - soulSize / 2;
			switch (soul) {
			case A:
				soulY += h / 6;
				centre = aCenter;
				aCenter += centerDelta;
				break;
			case B:
				soulY += h / 2; // * 3 / 6;
				centre = bCenter;
				bCenter += centerDelta;
				break;
			case C:
				soulY += h * 5 / 6;
				centre = cCenter;
				cCenter += centerDelta;
				break;
			default:
				throw new RuntimeException();
			}

			float soulX = x + centre - (soulSize / 2);

			// estat.getImage(im).draw(soulX, soulY, soulSize, soulSize,
			// filter);

			if (estat.ocupat) {

				float enteringThreshold = Resources.TIME_PREPROCESS
						/ Resources.TIME_CONSUMPTION;

				if (estat.processant < enteringThreshold) {
					im.ocupiedCircle.draw(soulX, soulY, soulSize, soulSize,
							filter);
				} else {
					float d = (estat.processant - enteringThreshold)
							/ (1 - enteringThreshold);
					drawPercentage(im, d, soulX, soulY, soulSize, soulSize,
							filter);
				}
			} else {
				im.emptyCircle.draw(soulX, soulY, soulSize, soulSize, filter);
			}
			im.getSoulImage(soul)
					.draw(soulX, soulY, soulSize, soulSize, filter);
		}
	}

	public class EstatSala {
		public boolean ocupat = false;
		public float processant = 0;

		private void ocupar() {
			assert (!ocupat);
			ocupat = true;
			processant = 0;
		}

		private void reset() {
			ocupat = false;
			processant = 0;
		}
	}

	private static void drawPercentage(ImageManager im, float percent, float x,
			float y, float w, float h, Color filter) {

		Image lliure = im.ocupiedCircle;
		Image ocupat = im.fullCircle;

		int baseHeight = lliure.getHeight();

		int topHeight = (int) (baseHeight * (1 - percent));
		int botHeight = baseHeight - topHeight;

		// Image topImage = lliure.getSubImage(0, 0, lliure.getWidth(),
		// topHeight);
		Image botImage = ocupat.getSubImage(0, topHeight, ocupat.getWidth(),
				botHeight);

		lliure.draw(x, y, w, h, filter);
		// topImage.draw(x, y, w, h * (1 - percent), filter);
		botImage.draw(x, y + h * (1 - percent), w, h * percent, filter);
	}

	@Override
	public int compareTo(Sala o) {
		int result = price - o.price;
		if (result == 0) {
			result = espais.size() - o.espais.size();
			if (result == 0) {
				for (int i = 0; i < espais.size(); i++) {
					Soul mine = espais.get(i);
					Soul its = o.espais.get(i);
					result = mine.ordinal() - its.ordinal();
					if (result != 0) {
						break;
					}
				}
			}
		}

		return result;
	}
}

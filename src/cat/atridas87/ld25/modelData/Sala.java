package cat.atridas87.ld25.modelData;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import cat.atridas87.ld25.render.ImageManager;

public class Sala implements Comparable<Sala> {

	private final ArrayList<EstatSala> estatEspais = new ArrayList<EstatSala>();
	private final ArrayList<Soul> espais;
	private final Image backgroundImage;
	
	private final int price;
	
	public Sala(Image _backgroundImage, int _price, Soul... _souls) {
		backgroundImage = _backgroundImage;
		price = _price;
		espais = new ArrayList<Soul>(_souls.length);
		for(Soul soul : _souls) {
			espais.add(soul);
			estatEspais.add(EstatSala.LLIURE);
		}
	}
	
	public int getPrice() {
		return price;
	}

	public int availableSoulSpaces(Soul soul) {
		int spaces = 0;
		for(int i = 0; i < espais.size(); i++) {
			if(espais.get(i) == soul && estatEspais.get(i) == EstatSala.LLIURE) {
				spaces++;
			}
		}
		return spaces;
	}

	public int process() {
		int processed = 0;
		for(int i = 0; i < estatEspais.size(); i++) {
			switch(estatEspais.get(i)) {
			case OCUPAT:
				processed++;
				estatEspais.set(i, EstatSala.LLIURE);
				break;
			case ENTRANT:
				estatEspais.set(i, EstatSala.OCUPAT);
				break;
			default:
				// --
			}
		}
		return processed;
	}
	
	public void putSoul(Soul soul) {
		for(int i = 0; i < espais.size(); i++) {
			if(espais.get(i) == soul && estatEspais.get(i) == EstatSala.LLIURE) {
				estatEspais.set(i, EstatSala.ENTRANT);
				return;
			}
		}
	}
	
	public void draw(ImageManager im, float x, float y, float w, float h) {
		draw(im, x, y, w, h, Color.white);
	}
		
	public void draw(ImageManager im, float x, float y, float w, float h, Color filter) {
		
		im.getRoomBase().draw(x, y, w, h, filter);
		
		float backgroundWidth = w * 108 / 144;
		backgroundImage.draw(x, y, backgroundWidth, h, filter);
		
		float soulSize = Math.min(w * 17 / 144, h * 17 / 81);
		float centerDelta = w * 18 / 144;
		float aCenter = w * 117 / 144;
		float bCenter = aCenter;
		float cCenter = aCenter;
		
		for(int i = 0; i < espais.size(); i++) {
			Soul soul = espais.get(i);
			EstatSala estat = estatEspais.get(i);
			float centre;
			float soulY = y - soulSize / 2;
			switch(soul) {
			case A:
				soulY += h / 6;
				centre = aCenter;
				aCenter += centerDelta;
				break;
			case B:
				soulY += h / 2; //* 3 / 6;
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
			
			estat.getImage(im).draw(soulX, soulY, soulSize, soulSize, filter);
			im.getSoulImage(soul).draw(soulX, soulY, soulSize, soulSize, filter);
		}
	}
	
	public static enum EstatSala {
		LLIURE,
		OCUPAT,
		ENTRANT;
		
		public Image getImage(ImageManager im) {
			switch(this) {
			case LLIURE:
				return im.getEmptyCircleImage();
			case OCUPAT:
				return im.getFullCircleImage();
			case ENTRANT:
				return im.getOcupiedCircleImage();
			default:
				throw new RuntimeException();
			}
		}
	}

	@Override
	public int compareTo(Sala o) {
		int result = price - o.price;
		if(result == 0) {
			result = espais.size() - o.espais.size();
			if(result == 0) {
				for(int i = 0; i < espais.size(); i++) {
					Soul mine = espais.get(i);
					Soul its = o.espais.get(i);
					result = mine.ordinal() - its.ordinal();
					if(result != 0) {
						break;
					}
				}
			}
		}
		
		return result;
	}
}

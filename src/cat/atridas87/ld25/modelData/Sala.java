package cat.atridas87.ld25.modelData;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Image;

import cat.atridas87.ld25.render.ImageManager;

public class Sala {

	private final HashMap<Integer, EstatSala> estatEspais = new HashMap<Integer, EstatSala>();
	private final ArrayList<Soul> espais;
	private final Image backgroundImage;
	
	
	public Sala(Image _backgroundImage, Soul... _souls) {
		backgroundImage = _backgroundImage;
		espais = new ArrayList<Soul>(_souls.length);
		int i = 0;
		for(Soul soul : _souls) {
			espais.add(soul);
			estatEspais.put(i, EstatSala.LLIURE);
			i++;
		}
	}
	
	public void draw(float x, float y, float w, float h) {
		ImageManager im = ImageManager.getInstance();
		
		im.getRoomBase().draw(x, y, w, h);
		
		float backgroundWidth = w * 108 / 144;
		backgroundImage.draw(x, y, backgroundWidth, h);
		
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
			
			estat.getImage(im).draw(soulX, soulY, soulSize, soulSize);
			im.getSoulImage(soul).draw(soulX, soulY, soulSize, soulSize);
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
}

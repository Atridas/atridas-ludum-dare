package cat.atridas87.ld25.modelData;

import org.newdawn.slick.Image;
import org.newdawn.slick.UnicodeFont;

import cat.atridas87.ld25.Resources;
import cat.atridas87.ld25.render.ImageManager;

public class Panel {
	
	private final Image image;
	private final String message;
	
	private final float x, y;
	
	private float delta;
	
	public Panel(Image _image, String _message, float _x, float _y) {
		image = _image;
		message = _message;
		delta = 0;
		x = _x;
		y = _y;
	}
	
	public boolean update(float ds) {
		delta += ds / Resources.TIME_PANEL;
		if(delta >= 1) {
			return false;
		}
		return true;
	}

	public void render(ImageManager im, UnicodeFont font14) {
		float dy = y - 11 + Resources.PANEL_TOTAL_Y * delta;
		float dx = x - 22;
		
		im.panel.draw(dx, dy);
		
		font14.drawString(dx + 4, dy + 4, message);
		
		image.draw(dx + 26, dy + 4, 14, 14);
	}
}

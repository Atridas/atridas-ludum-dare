package cat.atridas87.ld25.render;

import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class FontManager {

	private final HashMap<Integer, UnicodeFont> ingameFonts = new HashMap<Integer, UnicodeFont>();

	private FontManager() {
	};

	@SuppressWarnings("unchecked")
	public UnicodeFont getFont(int height) {
		UnicodeFont font = ingameFonts.get(height);

		if (font == null) {
			try {
				font = new UnicodeFont("resources/fonts/accid___.ttf", height,
						false, false);
				// Create
				// Instance
				font.addAsciiGlyphs(); // Add Glyphs
				font.addGlyphs(400, 600); // Add Glyphs
				font.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); // Add
				// Effects
				font.loadGlyphs(); // Load Glyphs
				
				ingameFonts.put(height, font);
			} catch (SlickException e) {
				throw new RuntimeException();
			}
		}
		
		return font;
	}

	private static FontManager instance = new FontManager();

	public static FontManager getInstance() {
		return instance;
	}

}

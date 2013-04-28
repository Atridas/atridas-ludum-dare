package cat.atridas87.ld26;

import org.lwjgl.opengl.GL11;

import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;

import cat.atridas87.ld26.gameobjects.Battleground;
import cat.atridas87.ld26.render.ShaderManager;
import cat.atridas87.ld26.sounds.Sounds;

public class LD26 extends BaseGame {

	/** Game title */
	public static final String GAME_TITLE = "GeoBattle";

	private Battleground battleground;
	private HUD hud;
	private GameInfo gameInfo;

	/**
	 * No constructor needed - this class is static
	 */
	public LD26(int width, int height) {
		super(width, height);
	}

	public void init() throws Exception {

		// Start up the sound system
		//AL.create();
		
		Sounds.music.loop(1, 0.5f);

		// TODO: Load in your textures etc here
		ShaderManager.instance = new ShaderManager();

		battleground = new Battleground();
		hud = new HUD();
		gameInfo = new GameInfo();

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
	}

	/**
	 * Do any game-specific cleanup
	 */
	public void cleanup() {
		// TODO: save anything you want to disk here

		// Stop the sound
		AL.destroy();

		// Close the window
		Display.destroy();
	}

	/**
	 * Do all calculations, handle input, etc.
	 */
	public void update(float _dt) {
		// Example input handler: we'll check for the ESC key and finish the
		// game instantly when it's pressed
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			finished = true;
		}

		if (!GameInfo.instance.gameFinished()) {
			battleground.update(_dt);

			hud.update(_dt);
		}

		gameInfo.update(_dt);
	}

	/**
	 * Render the current frame
	 */
	public void render() {
		GL11.glClearColor(0.3f, 0.3f, 0.3f, 1);
		GL11.glViewport(0, 0, width, height);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL11.glViewport(0, 0, height, height);

		battleground.render();

		gameInfo.render();

		GL11.glViewport(height, 0, width - height, height);

		// battleground.render();

		hud.render();
	}

	@Override
	public String getWindowName() {
		return GAME_TITLE;
	}

	@Override
	public void mouseClick(float x, float y) {
		if (!gameInfo.gameFinished()) {
			hud.mouseClick(x - 600, y);
		}
	}

}

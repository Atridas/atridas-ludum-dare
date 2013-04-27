package cat.atridas87.ld26;

import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;

import cat.atridas87.ld26.gameobjects.Battleground;
import cat.atridas87.ld26.render.Model;
import cat.atridas87.ld26.render.ShaderManager;
import cat.atridas87.ld26.render.ShaderManager.ProgramType;

public class LD26 extends BaseGame {

	/** Game title */
	public static final String GAME_TITLE = "GeoBattle";

	private Battleground battleground;

	/**
	 * No constructor needed - this class is static
	 */
	public LD26(int width, int height) {
		super(width, height);
	}

	public void init() throws Exception {

		// Start up the sound system
		AL.create();

		// TODO: Load in your textures etc here
		ShaderManager.instance = new ShaderManager();

		battleground = new Battleground();
		
		GL11.glClearColor(1, 1, 1, 1);
		GL11.glLineWidth(5);
		GL11.glPointSize(5);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
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
	public void update() {
		// Example input handler: we'll check for the ESC key and finish the
		// game instantly when it's pressed
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			finished = true;
		}
	}

	/**
	 * Render the current frame
	 */
	public void render() {
		GL11.glViewport(0, 0, width, height);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		ShaderManager.instance.setCurrentProgram(ProgramType.COLORED_ORTHO);

		ShaderManager.instance.setScreenSize(800, 600);
		
		battleground.render();
	}

	@Override
	public String getWindowName() {
		// TODO Auto-generated method stub
		return null;
	}

}

package cat.atridas87.ld26;

import org.lwjgl.opengl.GL11;

import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;

import cat.atridas87.ld26.gameobjects.Battleground;
import cat.atridas87.ld26.gameobjects.Bot;
import cat.atridas87.ld26.gameobjects.Tower;
import cat.atridas87.ld26.render.ShaderManager;
import cat.atridas87.ld26.render.ShaderManager.ProgramType;

public class LD26 extends BaseGame {

	/** Game title */
	public static final String GAME_TITLE = "GeoBattle";

	private Battleground battleground;

	private Tower playerTowers[];
	private Tower aiTowers[];
	
	private Bot testBot1, testBot2, testBot3;

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

		playerTowers = new Tower[11];
		playerTowers[0] = new Tower(true, 125, 25);
		playerTowers[1] = new Tower(true, 325, 25);
		playerTowers[2] = new Tower(true, 425, 25);
		playerTowers[3] = new Tower(true, 500, 100);
		playerTowers[4] = new Tower(true, 575, 175);
		playerTowers[5] = new Tower(true, 575, 275);
		playerTowers[6] = new Tower(true, 575, 475);
		playerTowers[7] = new Tower(true, 425, 175);
		playerTowers[8] = new Tower(true, 375, 225);
		playerTowers[9] = new Tower(true, 525, 50);
		playerTowers[10] = new Tower(true, 550, 75);

		aiTowers = new Tower[11];
		aiTowers[0] = new Tower(false, 600 - 125, 600 - 25);
		aiTowers[1] = new Tower(false, 600 - 325, 600 - 25);
		aiTowers[2] = new Tower(false, 600 - 425, 600 - 25);
		aiTowers[3] = new Tower(false, 600 - 500, 600 - 100);
		aiTowers[4] = new Tower(false, 600 - 575, 600 - 175);
		aiTowers[5] = new Tower(false, 600 - 575, 600 - 275);
		aiTowers[6] = new Tower(false, 600 - 575, 600 - 475);
		aiTowers[7] = new Tower(false, 600 - 425, 600 - 175);
		aiTowers[8] = new Tower(false, 600 - 375, 600 - 225);
		aiTowers[9] = new Tower(false, 600 - 525, 600 - 50);
		aiTowers[10] = new Tower(false, 600 - 550, 600 - 75);

		

		testBot1 = new Bot(true, Bot.Type.BASIC, 550, 50);
		testBot2 = new Bot(false, Bot.Type.TANK, 50, 550);
		testBot3 = new Bot(true, Bot.Type.SUPER, 300, 300);
		
		
		GL11.glClearColor(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glHint( GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST );
		GL11.glHint( GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST );
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

		GL11.glViewport(0, 0, height, height);

		ShaderManager.instance.setCurrentProgram(ProgramType.COLORED_ORTHO);
		ShaderManager.instance.setScreenSize(600, 600);

		GL11.glLineWidth(5);
		battleground.render();

		ShaderManager.instance
				.setCurrentProgram(ProgramType.COLORED_FROM_UNIFORM_ORTHO);
		ShaderManager.instance.setScreenSize(600, 600);

		GL11.glPointSize(15);
		for (int i = 0; i < playerTowers.length; i++) {
			playerTowers[i].render();
			aiTowers[i].render();
		}
		

		GL11.glLineWidth(1);
		GL11.glPointSize(5);

		testBot1.draw();
		testBot2.draw();
		testBot3.draw();
	}

	@Override
	public String getWindowName() {
		// TODO Auto-generated method stub
		return null;
	}

}

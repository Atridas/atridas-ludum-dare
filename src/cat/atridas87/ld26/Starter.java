package cat.atridas87.ld26;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Starter {

	/** Desired frame time */
	private static final int FRAMERATE = 60;

	private static BaseGame createGame(int w, int h) {
		return new LD26(w, h);
	}

	/**
	 * Application init
	 * 
	 * @param args
	 *            Commandline args
	 */
	public static void main(String[] args) {
		String windowName = "";
		try {
			BaseGame g = initDisplay();
			windowName = g.getWindowName();
			try {
				g.init();
				runDisplay(g);
			} catch (Exception e) {
				e.printStackTrace(System.err);
				Sys.alert(windowName,
						"An error occured and the game will exit.");
			} finally {
				g.cleanup();
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
			Sys.alert(windowName, "An error occured and the game will exit.");
		}

		System.exit(0);
	}

	/**
	 * Runs the game (the "main loop")
	 */
	private static void runDisplay(BaseGame game) {
		long currentTick = Sys.getTime();
		long lastTick = currentTick - Sys.getTimerResolution() / 60;
		while (!game.isFinished()) {
			// Always call Window.update(), all the time
			Display.update();
			
			currentTick = Sys.getTime();
			double ticks = currentTick - lastTick;
			ticks /= Sys.getTimerResolution();
			
			if(ticks > 1.f/30.f) {
				ticks =  1.f/30.f;
			}

			if (Display.isCloseRequested()) {
				// Check for O/S close requests
				game.setFinished(true);
			} else if (Display.isActive()) {
				// The window is in the foreground, so we should play the game
				
				while(Mouse.next()) {
					if(Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
						game.mouseClick(Mouse.getX(), Mouse.getY());
					}
				}
				
				game.update((float)ticks);
				game.render();
				Display.sync(FRAMERATE);
			} else {
				// The window is not in the foreground, so we can allow other
				// stuff to run and
				// infrequently update
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				game.update((float)ticks);
				if (Display.isVisible() || Display.isDirty()) {
					// Only bother rendering if the window is visible or dirty
					game.render();
				}
			}
			
			lastTick = currentTick;
		}
	}

	/**
	 * Initialise the game
	 * 
	 * @throws Exception
	 *             if init fails
	 */
	private static BaseGame initDisplay() throws Exception {

		BaseGame g = createGame(800, 600);

		// Create a fullscreen window with 1:1 orthographic 2D projection, and
		// with
		// mouse, keyboard, and gamepad inputs.
		Display.setTitle(g.getWindowName());
		Display.setFullscreen(false);

		DisplayMode dm = new DisplayMode(800, 600);

		Display.setDisplayMode(dm);

		// Enable vsync if we can
		Display.setVSyncEnabled(true);

		Display.create();

		return g;
	}
}

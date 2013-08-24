package cat.atridas87.ld27;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

@SuppressWarnings("serial")
public class Starter extends Applet {

	/** Desired frame time */
	private static final int FRAMERATE = 60;

	private static BaseGame createGame(int w, int h) {
		return new LD27(w, h);
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
				runDesktop(g);
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
	private static void runDesktop(BaseGame game) {
		long currentTick = Sys.getTime();
		long lastTick = currentTick - Sys.getTimerResolution() / 60;
		while (!game.isFinished()) {
			// Always call Window.update(), all the time
			Display.update();

			currentTick = Sys.getTime();
			double ticks = currentTick - lastTick;
			ticks /= Sys.getTimerResolution();

			if (ticks > 1.f / 30.f) {
				ticks = 1.f / 30.f;
			}

			if (Display.isCloseRequested()) {
				// Check for O/S close requests
				game.setFinished(true);
			} else if (Display.isActive()) {
				// The window is in the foreground, so we should play the game

				while (Mouse.next()) {
					if (Mouse.getEventButton() == 0
							&& Mouse.getEventButtonState()) {
						game.mouseClick(Mouse.getX(), Mouse.getY());
					}
				}

				game.update((float) ticks);
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
				game.update((float) ticks);
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

		BaseGame g = createGame(1280, 800);

		// Create a fullscreen window with 1:1 orthographic 2D projection, and
		// with
		// mouse, keyboard, and gamepad inputs.
		Display.setTitle(g.getWindowName());
		Display.setFullscreen(false);

		DisplayMode dm = new DisplayMode(1280, 800);

		Display.setDisplayMode(dm);

		// Enable vsync if we can
		Display.setVSyncEnabled(true);

		Display.create();

		return g;
	}

	// Applet stuff

	/** The Canvas where the LWJGL Display is added */
	Canvas display_parent;

	/** Thread which runs the main game loop */
	Thread gameThread;

	/** is the game loop running */
	// boolean running;

	BaseGame game;

	/**
	 * Once the Canvas is created its add notify method will call this method to
	 * start the LWJGL Display and game loop in another thread.
	 */
	public void startLWJGL() {
		game = createGame(1280, 800);
		gameThread = new Thread() {
			public void run() {
				// running = true;
				try {
					Display.setParent(display_parent);
					// Display.setVSyncEnabled(true);
					Display.create();
					game.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
				// gameLoop();
				runApplet(game);
			}
		};
		gameThread.start();
	}

	/**
	 * Tell game loop to stop running, after which the LWJGL Display will be
	 * destoryed. The main thread will wait for the Display.destroy() to
	 * complete
	 */
	private void stopLWJGL() {
		// running = false;
		game.setFinished(true);
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void start() {

	}

	public void stop() {

	}

	/**
	 * Applet Destroy method will remove the canvas, before canvas is destroyed
	 * it will notify stopLWJGL() to stop main game loop and to destroy the
	 * Display
	 */
	public void destroy() {
		remove(display_parent);
		super.destroy();
		System.out.println("Clear up");
	}

	/**
	 * initialise applet by adding a canvas to it, this canvas will start the
	 * LWJGL Display and game loop in another thread. It will also stop the game
	 * loop and destroy the display on canvas removal when applet is destroyed.
	 */
	public void init() {
		setLayout(new BorderLayout());
		try {
			display_parent = new Canvas() {
				public void addNotify() {
					super.addNotify();
					startLWJGL();
				}

				public void removeNotify() {
					stopLWJGL();
					super.removeNotify();
				}
			};
			display_parent.setSize(getWidth(), getHeight());
			add(display_parent);
			display_parent.setFocusable(true);
			display_parent.requestFocus();
			display_parent.setIgnoreRepaint(true);
			// setResizable(true);
			setVisible(true);
		} catch (Exception e) {
			System.err.println(e);
			throw new RuntimeException("Unable to create display");
		}
	}

	private static void runApplet(BaseGame game) {
		long currentTick = Sys.getTime();
		long lastTick = currentTick - Sys.getTimerResolution() / 60;
		while (!game.isFinished()) {
			// Always call Window.update(), all the time
			Display.update();

			currentTick = Sys.getTime();
			double ticks = currentTick - lastTick;
			ticks /= Sys.getTimerResolution();

			if (ticks > 1.f / 30.f) {
				ticks = 1.f / 30.f;
			}

			while (Mouse.next()) {
				if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
					game.mouseClick(Mouse.getX(), Mouse.getY());
				}
			}

			game.update((float) ticks);
			game.render();
			Display.sync(FRAMERATE);

			lastTick = currentTick;
		}

		Display.destroy();
	}
}

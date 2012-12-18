package cat.atridas87.ld25.gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld25.LD25;
import cat.atridas87.ld25.Resources;
import cat.atridas87.ld25.render.ImageManager;

public class TitleScreen extends BasicGameState {

	private boolean pressingNewGame = false;
	private boolean pressingTutorial = false;
	private boolean pressingCredits = false;

	private boolean hoveringNewGame = false;
	private boolean hoveringTutorial = false;
	private boolean hoveringCredits = false;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		pressingNewGame = false;
		pressingTutorial = false;

		hoveringNewGame = false;
		hoveringTutorial = false;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		ImageManager im = ImageManager.getInstance();

		im.titleScreenBackground.draw();

		Image newGameButton = hoveringNewGame ? im.newGameButtonPressed
				: im.newGameButtonNormal;
		Image tutorialButton = hoveringTutorial ? im.tutorialButtonPressed
				: im.tutorialButtonNormal;
		Image creditsButton = hoveringCredits ? im.creditsButtonPressed
				: im.creditsButtonNormal;

		im.title.draw(210, 70);
		
		newGameButton.draw(260, 270, 200, 37);
		tutorialButton.draw(260, 340, 200, 37);
		creditsButton.draw(260, 410, 200, 37);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {

	}

	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);

		if (x > 210 && x < 510) {
			if (y > 270 && y < 340) {
				hoveringNewGame = pressingNewGame = true;
			}
			if (y > 340 && y < 410) {
				hoveringTutorial = pressingTutorial = true;
			}
			if (y > 410 && y < 480) {
				hoveringCredits = pressingCredits = true;
			}
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		try {
			super.mouseReleased(button, x, y);

			if (x > 210 && x < 510) {
				if (pressingNewGame && y > 240 && y < 340) {
					LD25.getInstance().enterState(
							Resources.State.PLAYER_TURN.ordinal());

					LD25.getInstance().startNewGame();

					((PlayerTurn) LD25.getInstance().getState(
							Resources.State.PLAYER_TURN.ordinal())).reset();
				}
				if (pressingTutorial && y > 340 && y < 410) {
					LD25.getInstance().enterState(
							Resources.State.TUTORIAL.ordinal());
					((Tutorial) LD25.getInstance().getState(
							Resources.State.TUTORIAL.ordinal())).reset();

				}
				if (pressingCredits && y > 410 && y < 480) {
					LD25.getInstance().enterState(
							Resources.State.CREDITS.ordinal());
				}
			}

			pressingNewGame = false;
			pressingTutorial = false;
			pressingCredits = false;

			hoveringNewGame = false;
			hoveringTutorial = false;
			hoveringCredits = false;
		} catch (SlickException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		super.mouseDragged(oldx, oldy, newx, newy);

		if (newx > 210 && newx < 510) {
			if (pressingNewGame && newy > 240 && newy < 340) {
				hoveringNewGame = true;
			} else {
				hoveringNewGame = false;
			}
			if (pressingTutorial && newy > 340 && newy < 410) {
				hoveringTutorial = true;
			} else {
				hoveringTutorial = false;
			}
			if (pressingCredits && newy > 410 && newy < 480) {
				hoveringCredits = true;
			} else {
				hoveringCredits = false;
			}
		} else {
			hoveringNewGame = false;
			hoveringTutorial = false;
			hoveringCredits = false;
		}

	}

	@Override
	public int getID() {
		return Resources.State.TITLE_SCREEN.ordinal();
	}

}

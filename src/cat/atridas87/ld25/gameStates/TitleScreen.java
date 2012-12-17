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

	private boolean hoveringNewGame = false;
	private boolean hoveringTutorial = false; 

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

		Image newGameButton = hoveringNewGame ? im.newGameButtonPressed : im.newGameButtonNormal;
		Image tutorialButton = hoveringTutorial ? im.tutorialButtonPressed : im.tutorialButtonNormal;

		newGameButton.draw(210, 270);
		tutorialButton.draw(210, 420);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {

	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);

		if(x > 210 && x < 510) {
			if(y > 240 && y < 340) {
				hoveringNewGame = pressingNewGame = true;
			}
			if(y > 420 && y < 520) {
				hoveringTutorial = pressingTutorial = true;
			}
		}
		
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
	
		if(x > 210 && x < 510) {
			if(pressingNewGame && y > 240 && y < 340) {
				LD25.getInstance().enterState(Resources.State.PLAYER_TURN.ordinal());
			}
			if(pressingTutorial && y > 420 && y < 520) {
				// TODO tutorial
			}
		}

		pressingNewGame = false;
		pressingTutorial = false;

		hoveringNewGame = false;
		hoveringTutorial = false; 
	}
	
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		super.mouseDragged(oldx, oldy, newx, newy);

		if(newx > 210 && newx < 510) {
			if(pressingNewGame && newy > 240 && newy < 340) {
				hoveringNewGame = true;
			} else {
				hoveringNewGame = false;
			}
			if(pressingTutorial && newy > 420 && newy < 520) {
				hoveringTutorial = true;
			} else {
				hoveringTutorial = false;
			}
		}
		
	}

	@Override
	public int getID() {
		return Resources.State.TITLE_SCREEN.ordinal();
	}

}

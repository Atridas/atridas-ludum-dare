package cat.atridas87.ld25.gameStates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld25.LD25;
import cat.atridas87.ld25.Resources;
import cat.atridas87.ld25.modelData.Castle.RoomSocket;
import cat.atridas87.ld25.modelData.Level;
import cat.atridas87.ld25.modelData.Sala;
import cat.atridas87.ld25.render.ImageManager;

public class PlayerTurn extends BasicGameState {

	private int mouseFirstX, mouseFirstY;
	private int mouseX, mouseY;
	private boolean scrolling = false;
	private boolean hasTriedToGrabRoom = false;
	private boolean canDropRoomHere = false;
	private Sala grabedRoom;

	private Level level;

	private Color dragFilter = new Color(1, 1, 1, 0.75f);
	private Color validDragFilter = new Color(0.75f, 1, 0.75f, 0.75f);
	private Color invalidDragFilter = new Color(1, 0.75f, 0.75f, 0.75f);

	@Override
	public void init(GameContainer _gameContainer,
			StateBasedGame _stateBasedGame) throws SlickException {

		scrolling = false;
		level = ((LD25) _stateBasedGame).getCurrentLevel();
		grabedRoom = null;
		hasTriedToGrabRoom = false;
	}

	@Override
	public void render(GameContainer _gameContainer,
			StateBasedGame _stateBasedGame, Graphics _graphics)
			throws SlickException {
		ImageManager im = ImageManager.getInstance();
		
		_graphics.setBackground(Color.cyan);

		level.draw(0, 0, 720, 540);
		
		if(grabedRoom != null) {
			Color filter = dragFilter;
			if(level.getCoins() < grabedRoom.getPrice()) {
				filter = invalidDragFilter;
			} else if(canDropRoomHere) {
				filter = validDragFilter;
			}
			grabedRoom.draw(im, mouseX - 72, mouseY - 40, 144, 81, filter);
			_graphics.setColor(Color.white);
		}
	}

	@Override
	public void update(GameContainer _gameContainer,
			StateBasedGame _stateBasedGame, int ms) throws SlickException {

		canDropRoomHere = false;
		
		if(grabedRoom != null) {
			if(level.getCoins() >= grabedRoom.getPrice() && level.isSocket(mouseX, mouseY) != null) {
				canDropRoomHere = true;
			}
		}
	}

	@Override
	public int getID() {
		return Resources.State.PLAYER_TURN.ordinal();
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);

		mouseFirstX = x;
		mouseFirstY = y;

		grabedRoom = null;
		hasTriedToGrabRoom = false;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);

		scrolling = false;

		if(grabedRoom != null && level.getCoins() >= grabedRoom.getPrice()) {
			RoomSocket socket = level.isSocket(x, y);

			if(socket != null) {
				level.setRoom(socket, grabedRoom);
				level.takeCoins(grabedRoom.getPrice());
			}
		}
		
		grabedRoom = null;
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		
		mouseX = newx;
		mouseY = newy;
	}
	
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		super.mouseDragged(oldx, oldy, newx, newy);
		
		mouseX = newx;
		mouseY = newy;

		if (!scrolling && grabedRoom == null) {
			float dy = Math.abs(newy - mouseFirstY);
			if (dy > Resources.DRAG_THRESHOLD
					&& pointIsInScrollableZone(mouseFirstX, mouseFirstY)) {
				scrolling = true;

				level.scroll(mouseFirstY - newy); // la dy es passa invertida

				return;
			}

			if (!hasTriedToGrabRoom) {
				float dx = Math.abs(newx - mouseFirstX);
				if (dx > Resources.DRAG_THRESHOLD
						&& pointIsInScrollableZone(mouseFirstX, mouseFirstY)) {
					hasTriedToGrabRoom = true;

					grabedRoom = level.canGrabRoom(newx, newy);
				}
			}
		}

		if (scrolling) {
			level.scroll(oldy - newy); // la dy es passa invertida
		}
	}
	
	@Override
	public void mouseWheelMoved(int newValue) {
		super.mouseWheelMoved(newValue);
		
		level.scroll(newValue / -5);
	}

	private boolean pointIsInScrollableZone(int x, int y) {
		return x > 540 && y > 130 && y < 490;
	}
}

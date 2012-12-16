package cat.atridas87.ld25.gameStates;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cat.atridas87.ld25.LD25;
import cat.atridas87.ld25.Resources;
import cat.atridas87.ld25.modelData.Castle;
import cat.atridas87.ld25.modelData.Castle.RoomResult;
import cat.atridas87.ld25.modelData.Castle.RoomSocket;
import cat.atridas87.ld25.modelData.Level;
import cat.atridas87.ld25.modelData.Sala;
import cat.atridas87.ld25.modelData.Soul;
import cat.atridas87.ld25.modelData.Wave;
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
	
	private void next() {
		
		Castle castle = level.getCastle();
		
		// 1 - compute how many souls we can consume
		int soulsToConsumeA = level.getReserve(Soul.A);
		int soulsToConsumeB = level.getReserve(Soul.B);
		int soulsToConsumeC = level.getReserve(Soul.C);

		int availableConsumptionsA = castle.availableSoulSpaces(Soul.A);
		int availableConsumptionsB = castle.availableSoulSpaces(Soul.B);
		int availableConsumptionsC = castle.availableSoulSpaces(Soul.C);

		soulsToConsumeA = (soulsToConsumeA > availableConsumptionsA) ? availableConsumptionsA : soulsToConsumeA;
		soulsToConsumeB = (soulsToConsumeB > availableConsumptionsB) ? availableConsumptionsB : soulsToConsumeB;
		soulsToConsumeC = (soulsToConsumeC > availableConsumptionsC) ? availableConsumptionsC : soulsToConsumeC;

		// 2 - Take those souls from the reserve
		
		level.getSoulsFromReserve(Soul.A, soulsToConsumeA);
		level.getSoulsFromReserve(Soul.B, soulsToConsumeB);
		level.getSoulsFromReserve(Soul.C, soulsToConsumeC);
		
		// 3 - Find the place for those souls
		ArrayList<Integer> roomsForSoulsA = new ArrayList<Integer>();
		ArrayList<Integer> roomsForSoulsB = new ArrayList<Integer>();
		ArrayList<Integer> roomsForSoulsC = new ArrayList<Integer>();

		for(int i = 0; i < soulsToConsumeA; i++) {
			roomsForSoulsA.add(castle.putSoulInRoom(Soul.A));
		}
		for(int i = 0; i < soulsToConsumeB; i++) {
			roomsForSoulsB.add(castle.putSoulInRoom(Soul.B));
		}
		for(int i = 0; i < soulsToConsumeC; i++) {
			roomsForSoulsC.add(castle.putSoulInRoom(Soul.C));
		}
		
		// 4 - Process All the souls, and add points / coins
		ArrayList<RoomResult> results = castle.processRooms();
		
		for(RoomResult result : results) {
			level.addCoins(result.coins);
			level.addPoints(result.points);
		}
		
		// 5 - Move the threadmill
		
		Wave wave = level.getNextWave();
		
		if(wave == null) {
			levelCompelete();
			return;
		}

		level.addSoulsToReserve(Soul.A, wave.getSouls(Soul.A));
		level.addSoulsToReserve(Soul.B, wave.getSouls(Soul.B));
		level.addSoulsToReserve(Soul.C, wave.getSouls(Soul.C));
	}

	public void levelCompelete() {
		// TODO
	}
	
	/*
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		
		if(x > 589 && x < 671 && y > 499 && y < 532) {
			next();
		}
	}
	*/
	
	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);

		mouseFirstX = x;
		mouseFirstY = y;

		grabedRoom = null;
		hasTriedToGrabRoom = false;
		

		if(x > 589 && x < 671 && y > 499 && y < 532) {
			level.holdButton(true);
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);

		scrolling = false;

		if(mouseFirstX > 589 && mouseFirstX < 671 && mouseFirstY > 499 && mouseFirstY < 532) {
			if(x > 589 && x < 671 && y > 499 && y < 532) {
				next();
			}
		}
		level.holdButton(false);

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

		if(mouseFirstX > 589 && mouseFirstX < 671 && mouseFirstY > 499 && mouseFirstY < 532) {
			if(newx > 589 && newx < 671 && newy > 499 && newy < 532) {
				level.holdButton(true);
			} else {
				level.holdButton(false);
			}
		}

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

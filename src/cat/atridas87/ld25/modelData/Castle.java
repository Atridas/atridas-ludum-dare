package cat.atridas87.ld25.modelData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.newdawn.slick.Image;
import org.newdawn.slick.UnicodeFont;

import cat.atridas87.ld25.Resources;
import cat.atridas87.ld25.render.FontManager;
import cat.atridas87.ld25.render.ImageManager;

public class Castle {

	private final float width, height;
	private final Image background;
	private final TreeSet<Sala> sales;
	private final ArrayList<Sala> salesConstruides;
	private final ArrayList<RoomSocket> sockets;
	private final ArrayList<ArrayList<Point>> waypoints;
	
	private final ArrayList<WalkingSoul> walkingSouls = new ArrayList<Castle.WalkingSoul>();

	private float scroll = 0;

	public Castle(float _width, float _height, Image _background,
			Set<Sala> _sales, List<RoomSocket> _sockets,
			List<Sala> _salesConstruides, ArrayList<ArrayList<Point>> _waypoints) {
		width = _width;
		height = _height;
		background = _background;
		sales = new TreeSet<Sala>(_sales);
		assert (_salesConstruides.size() == _sockets.size());
		salesConstruides = new ArrayList<Sala>(_salesConstruides);
		sockets = new ArrayList<RoomSocket>(_sockets);
		waypoints = new ArrayList<ArrayList<Point>>(_waypoints.size());
		for(ArrayList<Point> waypointList : _waypoints) {
			waypoints.add(new ArrayList<Castle.Point>(waypointList));
		}
	}
	
	public void AddWalingSoul(Soul soul) {
		walkingSouls.add(new WalkingSoul(soul));
	}

	public ArrayList<Sala> getFreeRooms() {
		ArrayList<Sala> freeRooms = new ArrayList<Sala>();
		for (Sala sala : sales) {
			if (!salesConstruides.contains(sala)) {
				freeRooms.add(sala);
			}
		}
		return freeRooms;
	}

	public int availableSoulSpaces(Soul soul) {
		int spaces = 0;
		for (Sala sala : salesConstruides) {
			if (sala != null) {
				spaces += sala.availableSoulSpaces(soul);
			}
		}
		return spaces;
	}

	public int putSoulInRoom(Soul soul) {
		int i = 0;
		for (Sala sala : salesConstruides) {
			if (sala.availableSoulSpaces(soul) > 0) {

				sala.putSoul(soul);

				return i;
			}
			i++;
		}
		throw new RuntimeException();
	}

	public ArrayList<RoomResult> processRooms() {
		ArrayList<RoomResult> results = new ArrayList<Castle.RoomResult>();
		for (int i = 0; i < sockets.size(); i++) {
			Sala sala = salesConstruides.get(i);
			if (sala != null) {
				int processed = sala.process();
				if (processed > 0) {
					RoomResult result = new RoomResult(sockets.get(i),
							Resources.pointCombo(processed),
							Resources.coinCombo(processed));
					results.add(result);
				}
			}
		}
		return results;
	}

	public void scroll(float dy) {
		scroll += dy;
		if (scroll < 0)
			scroll = 0;
	}

	public Sala canGrabRoom(float x, float y) {
		float roomDx = 550;
		float roomDy = 30;
		float roomW = 144;
		float roomH = 81;

		float itemDy = roomW;

		float currentDY = 130;

		ArrayList<Sala> freeRooms = getFreeRooms();

		for (Sala sala : freeRooms) {
			float currentY = currentDY - scroll;

			if (x > roomDx && x < roomDx + roomW && y > currentY + roomDy
					&& y < currentY + roomDy + roomH) {
				return sala;
			}

			currentDY += itemDy;
		}
		return null;
	}

	public RoomSocket isSocket(float x, float y) {
		for (RoomSocket socket : sockets) {
			if (x > socket.x && x < socket.x + socket.w && y > socket.y
					&& y < socket.y + socket.h) {
				return socket;
			}
		}
		return null;
	}

	public void setRoom(RoomSocket socket, Sala room) {
		assert (!salesConstruides.contains(room));
		assert (sales.contains(room));
		assert (sockets.contains(socket));

		for (int i = 0; i < sockets.size(); i++) {
			if (sockets.get(i) == socket) {
				salesConstruides.set(i, room);
				return;
			}
		}
		assert (false);
	}
	
	public void update(float ds) {
		float addDelta = ds / Resources.TIME_BETWEN_SOCKETS;
		
		LinkedList<WalkingSoul> removableSouls = new LinkedList<Castle.WalkingSoul>();
		
		for(WalkingSoul walkingSoul : walkingSouls) {
			walkingSoul.delta += addDelta;
			while(walkingSoul.delta > 1) {
				walkingSoul.delta -= 1;
				walkingSoul.goingToSoket++;
				// TODO
				
				if(walkingSoul.goingToSoket >= sockets.size()) {
					removableSouls.add(walkingSoul);
				}
			}
		}
		
		walkingSouls.removeAll(removableSouls);
	}

	public void drawCastle(float x, float y, float w, float h) {
		ImageManager im = ImageManager.getInstance();

		for (int i = 0; i < sockets.size(); i++) {
			Sala sala = salesConstruides.get(i);
			if (sala == null)
				break;
			RoomSocket socket = sockets.get(i);

			float salaX = x + (socket.x * w / width);
			float salaY = y + (socket.y * h / height);
			float salaW = socket.w * w / width;
			float salaH = socket.h * h / height;

			sala.draw(im, salaX, salaY, salaW, salaH);
		}

		background.draw(x, y, w, h);
		
		float soulSize = w * 17.f / 540;
		for(WalkingSoul walkingSoul : walkingSouls) {
			Point p = walkingSoul.getPoint();

			float px = p.x * w / 540;
			float py = p.y * h / 540;

			im.getSoulImage(walkingSoul.kind).draw(px - soulSize / 2, py - soulSize / 2, soulSize, soulSize);
		}
	}

	public void drawConstructibleRooms(float x, float y, float w, float h) {
		ImageManager im = ImageManager.getInstance();

		float coinDx = 10.f * w / 180;
		float coinDy = 5.f * w / 180;
		float coinSize = 17.f * w / 180;

		float stringDx = 30.f * w / 180;
		float stringDy = 5.f * w / 180;
		UnicodeFont font = FontManager.getInstance().getFont((int) coinSize);

		float roomDx = coinDx;
		float roomDy = 30.f * w / 180;
		float roomW = 144.f * w / 180;
		float roomH = 81.f * w / 180;

		float itemDy = roomW;

		float currentDY = 0;

		ArrayList<Sala> freeRooms = getFreeRooms();

		float totalHeight = freeRooms.size() * itemDy;
		if (scroll + h > totalHeight)
			scroll = totalHeight - h;
		if (scroll < 0)
			scroll = 0;

		for (Sala sala : freeRooms) {
			float currentY = y + currentDY - scroll;
			im.getCoinImage().draw(x + coinDx, currentY + coinDy, coinSize,
					coinSize);

			sala.draw(im, x + roomDx, currentY + roomDy, roomW, roomH);

			font.drawString(x + stringDx, currentY + stringDy,
					Integer.toString(sala.getPrice()));

			currentDY += itemDy;
			// if(currentDY >= h) break;
		}
	}

	public static class RoomSocket {
		public final float x, y, w, h;

		public RoomSocket(float _x, float _y, float _w, float _h) {
			x = _x;
			y = _y;
			w = _w;
			h = _h;
		}
	}

	public static class RoomResult {
		public final RoomSocket socket;
		public final int points, coins;

		private RoomResult(RoomSocket _socket, int _points, int _coins) {
			socket = _socket;
			points = _points;
			coins = _coins;
		}
	}
	
	public static class Point {
		public float x, y;
		
		public Point(float _x, float _y) {
			x = _x;
			y = _y;
		}
	}
	
	private class WalkingSoul {
		Soul kind;
		int goingToSoket;
		float delta;
		
		WalkingSoul(Soul soul) {
			kind = soul;
			goingToSoket = 0;
			delta = 0;
		}
		
		Point getPoint() {
			ArrayList<Point> path = waypoints.get(goingToSoket);
			float length = 0;
			for(int i = 1; i < path.size(); i++) {
				Point p0 = path.get(i-1);
				Point p1 = path.get(i);
				
				float l = (p0.x - p1.x) * (p0.x - p1.x) + (p0.y - p1.y) * (p0.y - p1.y);
				length += (float)Math.sqrt(l);
			}
			float currentLength = 0;

			for(int i = 1; i < path.size(); i++) {
				Point p0 = path.get(i-1);
				Point p1 = path.get(i);
				
				float l = (p0.x - p1.x) * (p0.x - p1.x) + (p0.y - p1.y) * (p0.y - p1.y);
				
				l = (float)Math.sqrt(l);
				
				if((currentLength + l) / length > delta) {
					
					float currentDelta = (delta - currentLength / length) / (l / length);

					float x = p0.x * (1 - currentDelta) + p1.x * currentDelta;
					float y = p0.y * (1 - currentDelta) + p1.y * currentDelta;
					
					return new Point(x, y);
				}
				
				currentLength += l;
			}
			
			return path.get(path.size() - 1);
		}
	}
}

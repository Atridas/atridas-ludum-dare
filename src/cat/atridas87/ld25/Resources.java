package cat.atridas87.ld25;

import java.util.ArrayList;
import java.util.TreeSet;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import cat.atridas87.ld25.modelData.Castle;
import cat.atridas87.ld25.modelData.Sala;
import cat.atridas87.ld25.modelData.Soul;
import cat.atridas87.ld25.modelData.Castle.RoomSocket;
import cat.atridas87.ld25.modelData.Castle.Point;
import cat.atridas87.ld25.modelData.Wave;

public abstract class Resources {

	public static final String APP_NAME = "LD25";

	public static final float DRAG_THRESHOLD = 10;
	public static final float TIME_BETWEN_SOCKETS = 3;
	public static final float TIME_ENTER = 2f;
	public static final float TIME_CONSUMPTION = 10f;
	public static final float WAVE_TIME = 4f;
	public static final float ENTER_TIME = 0.5f;
	
	public static final float SPEED = 1;
	
	public static final float SOULS_TO_LIVE = 50;
	

	public static Castle createLevel0Castle() throws SlickException {

		TreeSet<Sala> sales = new TreeSet<Sala>();
		ArrayList<RoomSocket> sockets = new ArrayList<Castle.RoomSocket>();
		ArrayList<Sala> salesInicials = new ArrayList<Sala>();

		Sala aa = new Sala(new Image("resources/images/rooms/electricitat-petit.png"), 50,
				Soul.A, Soul.A);
		Sala bb = new Sala(new Image("resources/images/rooms/infectades-petit.png"), 50,
				Soul.B, Soul.B);
		Sala cc = new Sala(new Image("resources/images/rooms/parrilla-petit.png"), 50,
				Soul.C, Soul.C);
		Sala ab = new Sala(new Image("resources/images/rooms/ab.png"), 75,
				Soul.A, Soul.B);
		Sala ac = new Sala(new Image("resources/images/rooms/ac.png"), 75,
				Soul.A, Soul.C);
		Sala bc = new Sala(new Image("resources/images/rooms/bc.png"), 75,
				Soul.B, Soul.C);
		Sala abc = new Sala(new Image("resources/images/rooms/vampireses-petit.png"), 100,
				Soul.A, Soul.B, Soul.C);
		Sala aabbcc = new Sala(new Image("resources/images/rooms/gel-petit.png"),
				250, Soul.A, Soul.A, Soul.B, Soul.B, Soul.C, Soul.C);

		sales.add(aa);
		sales.add(bb);
		sales.add(cc);
		sales.add(ab);
		sales.add(ac);
		sales.add(bc);
		sales.add(abc);
		sales.add(aabbcc);

		salesInicials.add(aa);
		sockets.add(new RoomSocket(42, 403, 144, 81));
		salesInicials.add(bb);
		sockets.add(new RoomSocket(221, 404, 144, 81));
		salesInicials.add(cc);
		sockets.add(new RoomSocket(378, 403, 144, 81));

		salesInicials.add(null);
		sockets.add(new RoomSocket(302, 257, 144, 81));
		salesInicials.add(null);
		sockets.add(new RoomSocket(107, 255, 144, 81));

		// salesInicials.add(null);sockets.add(new RoomSocket(209, 132, 144,
		// 81));

		// -----

		Point w0 = new Point(0, 500);
		Point w1 = new Point(75, 500);
		Point ew0 = new Point(75, 450);
		Point w2 = new Point(275, 500);
		Point ew1 = new Point(275, 450);
		Point w3 = new Point(440, 500);
		Point ew2 = new Point(440, 450);
		Point w4 = new Point(510, 500);

		Point w5 = new Point(510, 370);
		Point w6 = new Point(360, 370);
		Point ew3 = new Point(360, 320);
		Point w7 = new Point(175, 370);
		Point ew4 = new Point(175, 320);
		Point dw = new Point(50, 370);

		ArrayList<ArrayList<Point>> waypoints = new ArrayList<ArrayList<Point>>();

		ArrayList<Point> path0 = new ArrayList<Castle.Point>();
		path0.add(w0);
		path0.add(w1);

		ArrayList<Point> path1 = new ArrayList<Castle.Point>();
		path1.add(w1);
		path1.add(w2);

		ArrayList<Point> path2 = new ArrayList<Castle.Point>();
		path2.add(w2);
		path2.add(w3);

		ArrayList<Point> path3 = new ArrayList<Castle.Point>();
		path3.add(w3);
		path3.add(w4);
		path3.add(w5);
		path3.add(w6);

		ArrayList<Point> path4 = new ArrayList<Castle.Point>();
		path4.add(w6);
		path4.add(w7);

		waypoints.add(path0);
		waypoints.add(path1);
		waypoints.add(path2);
		waypoints.add(path3);
		waypoints.add(path4);
		
		// -----

		ArrayList<ArrayList<Point>> entryWaypoints = new ArrayList<ArrayList<Point>>();

		ArrayList<Point> entryPath0 = new ArrayList<Castle.Point>();
		entryPath0.add(w1);
		entryPath0.add(ew0);

		ArrayList<Point> entryPath1 = new ArrayList<Castle.Point>();
		entryPath1.add(w2);
		entryPath1.add(ew1);

		ArrayList<Point> entryPath2 = new ArrayList<Castle.Point>();
		entryPath2.add(w3);
		entryPath2.add(ew2);

		ArrayList<Point> entryPath3 = new ArrayList<Castle.Point>();
		entryPath3.add(w6);
		entryPath3.add(ew3);

		ArrayList<Point> entryPath4 = new ArrayList<Castle.Point>();
		entryPath4.add(w7);
		entryPath4.add(ew4);

		entryWaypoints.add(entryPath0);
		entryWaypoints.add(entryPath1);
		entryWaypoints.add(entryPath2);
		entryWaypoints.add(entryPath3);
		entryWaypoints.add(entryPath4);

		// -----

		ArrayList<Point> diePath = new ArrayList<Castle.Point>();
		diePath.add(w7);
		diePath.add(dw);

		// -----

		return new Castle(540, 540, new Image("resources/images/level_0.png"),
				sales, sockets, salesInicials, waypoints, entryWaypoints, diePath);
	}

	public static ArrayList<Wave> createLevel0Waves() {
		ArrayList<Wave> waves = new ArrayList<Wave>();

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(1, 0, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 1, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 1));

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(1, 1, 1));

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(1, 0, 0));
		waves.add(new Wave(0, 1, 0));
		waves.add(new Wave(0, 0, 1));

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(2, 0, 0));
		waves.add(new Wave(0, 2, 0));
		waves.add(new Wave(0, 0, 2));

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(1, 1, 0));
		waves.add(new Wave(0, 1, 1));
		waves.add(new Wave(1, 0, 1));

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(1, 1, 2));
		waves.add(new Wave(2, 1, 1));
		waves.add(new Wave(1, 2, 1));

		// ----------------------

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 0));

		waves.add(new Wave(3, 1, 2));
		waves.add(new Wave(2, 3, 1));
		waves.add(new Wave(1, 2, 3));
		
		waves.add(new Wave(0, 0, 0));

		waves.add(new Wave(1, 3, 2));
		waves.add(new Wave(2, 1, 3));
		waves.add(new Wave(3, 2, 1));
		
		waves.add(new Wave(0, 0, 0));

		waves.add(new Wave(4, 3, 2));
		waves.add(new Wave(2, 4, 3));
		waves.add(new Wave(3, 4, 3));
		// ----------------------

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(2, 2, 2));
		waves.add(new Wave(4, 1, 1));
		waves.add(new Wave(1, 1, 4));
		waves.add(new Wave(0, 0, 0));

		// ----------------------

		waves.add(new Wave(3, 1, 0));
		waves.add(new Wave(1, 1, 5));
		waves.add(new Wave(0, 3, 1));
		waves.add(new Wave(5, 1, 1));
		waves.add(new Wave(1, 0, 3));
		waves.add(new Wave(1, 5, 1));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 0));

		// ----------------------

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(5, 1, 5));
		waves.add(new Wave(1, 5, 5));
		waves.add(new Wave(5, 5, 1));
		waves.add(new Wave(2, 2, 2));
		waves.add(new Wave(5, 5, 5));
		waves.add(new Wave(1, 1, 1));
		waves.add(new Wave(5, 1, 5));
		waves.add(new Wave(1, 5, 5));
		waves.add(new Wave(5, 5, 1));
		waves.add(new Wave(2, 2, 2));
		waves.add(new Wave(5, 5, 5));
		waves.add(new Wave(1, 1, 1));
		waves.add(new Wave(5, 1, 5));
		waves.add(new Wave(1, 5, 5));
		waves.add(new Wave(5, 5, 1));
		waves.add(new Wave(2, 2, 2));
		waves.add(new Wave(5, 5, 5));
		waves.add(new Wave(1, 1, 1));
		waves.add(new Wave(5, 1, 5));
		waves.add(new Wave(1, 5, 5));
		waves.add(new Wave(5, 5, 1));
		waves.add(new Wave(2, 2, 2));
		waves.add(new Wave(5, 5, 5));
		waves.add(new Wave(1, 1, 1));
		waves.add(new Wave(0, 0, 0));

		return waves;
	}

	public static int pointCombo(int numSouls) {
		if(numSouls < 10) {
			return 10;
		} else if(numSouls < 20) {
			return 15;
		} else if(numSouls < 30) {
			return 25;
		} else {
			return 40;
		}
	}

	public static int coinCombo(int numSouls) {
		return 10;
	}

	public static enum State {
		PLAYER_TURN
	}
}

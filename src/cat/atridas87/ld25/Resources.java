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
	public static final float TIME_BETWEN_SOCKETS = 4;
	

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
		Point w2 = new Point(275, 500);
		Point w3 = new Point(440, 500);
		Point w4 = new Point(510, 500);

		Point w5 = new Point(510, 370);
		Point w6 = new Point(360, 370);
		Point w7 = new Point(175, 370);

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

		return new Castle(540, 540, new Image("resources/images/level_0.png"),
				sales, sockets, salesInicials, waypoints);
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
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(2, 0, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 2));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 2, 0));

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(2, 1, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(1, 2, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 1, 2));

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(2, 1, 2));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(1, 2, 2));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(2, 1, 2));

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(2, 2, 2));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(1, 2, 2));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(2, 1, 2));

		// ----------------------

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(1, 0, 0));
		waves.add(new Wave(0, 1, 0));
		waves.add(new Wave(0, 0, 1));

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(1, 1, 1));

		waves.add(new Wave(2, 0, 0));
		waves.add(new Wave(0, 0, 2));
		waves.add(new Wave(0, 2, 0));

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(2, 1, 0));
		waves.add(new Wave(1, 2, 0));
		waves.add(new Wave(0, 1, 2));

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(2, 1, 2));
		waves.add(new Wave(1, 2, 2));
		waves.add(new Wave(2, 1, 2));

		waves.add(new Wave(2, 2, 2));
		waves.add(new Wave(1, 2, 2));
		waves.add(new Wave(2, 1, 2));
		waves.add(new Wave(2, 2, 1));

		waves.add(new Wave(2, 2, 2));
		waves.add(new Wave(2, 2, 2));

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

		return waves;
	}

	public static int pointCombo(int numSouls) {
		switch (numSouls) {
		case 1:
			return 10;
		case 2:
			return 25;
		case 3:
			return 40;
		case 4:
			return 55;
		case 5:
			return 70;
		case 6:
			return 100;
		}
		throw new RuntimeException();
	}

	public static int coinCombo(int numSouls) {
		return numSouls * 50;
	}

	public static enum State {
		PLAYER_TURN
	}
}

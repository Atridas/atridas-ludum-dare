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
	public static final float TIME_ENTER = 10;
	public static final float TIME_BETWEN_SOCKETS = 4;
	public static final float TIME_PREPROCESS = 2f;
	public static final float TIME_CONSUMPTION = 7f;
	public static final float WAVE_TIME = 8f;
	public static final float ENTER_TIME = 0.5f;
	
	public static final float SPEED = 1;
	
	public static final float SOULS_TO_LIVE = 50;
	

	public static Castle createLevel0Castle() throws SlickException {

		TreeSet<Sala> sales = new TreeSet<Sala>();
		ArrayList<RoomSocket> sockets = new ArrayList<Castle.RoomSocket>();
		ArrayList<Sala> salesInicials = new ArrayList<Sala>();

		Sala aa = new Sala(new Image("resources/images/rooms/electricitat-petit.png"), 75,
				Soul.A, Soul.A);
		Sala bb = new Sala(new Image("resources/images/rooms/infectades-petit.png"), 75,
				Soul.B, Soul.B);
		Sala cc = new Sala(new Image("resources/images/rooms/parrilla-petit.png"), 75,
				Soul.C, Soul.C);
		Sala ab = new Sala(new Image("resources/images/rooms/aa.png"), 70,
				Soul.A, Soul.A);
		Sala ac = new Sala(new Image("resources/images/rooms/bb.png"), 70,
				Soul.B, Soul.B);
		Sala bc = new Sala(new Image("resources/images/rooms/vampireses-petit.png"), 70,
				Soul.C, Soul.C);
		//Sala abc = new Sala(new Image("resources/images/rooms/vampireses-petit.png"), 100,
		//		Soul.A, Soul.B, Soul.C);
		Sala aabbcc = new Sala(new Image("resources/images/rooms/gel-petit.png"),
				500, Soul.A, Soul.B, Soul.C);

		sales.add(aa);
		sales.add(bb);
		sales.add(cc);
		sales.add(ab);
		sales.add(ac);
		sales.add(bc);
		//sales.add(abc);
		sales.add(aabbcc);

		salesInicials.add(aa);
		sockets.add(new RoomSocket(32, 400, 134, 88));
		salesInicials.add(bb);
		sockets.add(new RoomSocket(196, 401, 139, 88));
		salesInicials.add(cc);
		sockets.add(new RoomSocket(366, 402, 134, 89));

		salesInicials.add(null);
		sockets.add(new RoomSocket(297, 279, 144, 87));
		salesInicials.add(null);
		sockets.add(new RoomSocket(123, 288, 144, 81));

		// salesInicials.add(null);sockets.add(new RoomSocket(209, 132, 144,
		// 81));

		// -----

		Point e0 = new Point(10, 0);

		Point w0 = new Point(10, 516);
		Point w1 = new Point(146, 516);
		Point ew0 = new Point(146, 423);
		Point w2 = new Point(320, 516);
		Point ew1 = new Point(320, 423);
		Point w3 = new Point(483, 516);
		Point ew2 = new Point(483, 423);
		Point w4 = new Point(514, 516);

		Point w5 = new Point(514, 386);
		Point w6 = new Point(422, 386);
		Point ew3 = new Point(422, 320);
		Point w7 = new Point(284, 386);
		Point w8 = new Point(284, 324);
		Point ew4 = new Point(250, 324);
		Point dw = new Point(284, 280);

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
		path4.add(w8);

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
		entryPath4.add(w8);
		entryPath4.add(ew4);

		entryWaypoints.add(entryPath0);
		entryWaypoints.add(entryPath1);
		entryWaypoints.add(entryPath2);
		entryWaypoints.add(entryPath3);
		entryWaypoints.add(entryPath4);

		// -----

		ArrayList<Point> enterPath = new ArrayList<Castle.Point>();
		enterPath.add(e0);
		enterPath.add(w0);

		// -----

		ArrayList<Point> diePath = new ArrayList<Castle.Point>();
		diePath.add(w8);
		diePath.add(dw);

		// -----

		return new Castle(540, 540, new Image("resources/images/level_0.png"),
				sales, sockets, salesInicials, waypoints, entryWaypoints, diePath, enterPath);
	}

	public static ArrayList<Wave> createLevel0Waves() {
		ArrayList<Wave> waves = new ArrayList<Wave>();

		waves.add(new Wave(1, 0, 0));
		waves.add(new Wave(0, 1, 0));
		waves.add(new Wave(0, 0, 1));

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(1, 1, 1));

		waves.add(new Wave(2, 0, 0));
		waves.add(new Wave(0, 2, 0));
		waves.add(new Wave(0, 0, 2));

		waves.add(new Wave(1, 1, 0));
		waves.add(new Wave(0, 1, 1));
		waves.add(new Wave(1, 0, 1));

		waves.add(new Wave(1, 1, 2));
		waves.add(new Wave(2, 1, 1));
		waves.add(new Wave(1, 2, 1));

		// ----------------------

		waves.add(new Wave(3, 1, 2));
		waves.add(new Wave(2, 3, 1));
		waves.add(new Wave(1, 2, 3));

		waves.add(new Wave(1, 3, 2));
		waves.add(new Wave(2, 1, 3));
		waves.add(new Wave(3, 2, 1));
		
		waves.add(new Wave(0, 0, 0));

		waves.add(new Wave(4, 3, 2));
		waves.add(new Wave(2, 4, 3));
		waves.add(new Wave(3, 4, 3));
		// ----------------------

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

		// ----------------------



		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(5, 0, 5));
		waves.add(new Wave(5, 0, 5));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 5, 5));
		waves.add(new Wave(0, 5, 5));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(5, 5, 0));
		waves.add(new Wave(5, 5, 0));

		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(5, 0, 5));
		waves.add(new Wave(5, 5, 0));
		waves.add(new Wave(0, 5, 5));


		// ----------------------
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(4, 4, 2));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(2, 4, 4));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(4, 2, 4));
		
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(4, 4, 0));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(0, 4, 4));
		waves.add(new Wave(0, 0, 0));
		waves.add(new Wave(4, 0, 4));
		
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

	public static float livesMult(int numLives) {
		if(numLives <= 0) {
			return 0.5f;
		}
		switch(numLives) {
		case 1:
			return 2;
		case 2:
			return 2.5f;
		case 3:
			return 3;
		case 4:
			return 3.5f;
		case 5:
			return 4;
		case 6:
			return 4.5f;
		case 7:
			return 5;
		case 8:
			return 6;
		case 9:
			return 8;
		case 10:
			return 10;
		default:
			return numLives * 3 - 20;
		}
	}

	public static enum State {
		PLAYER_TURN,
		TITLE_SCREEN,
		GAME_OVER,
		TUTORIAL
	}
}

package cat.atridas87.ld24;

import java.util.Random;

import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;

public class GameMusic extends Thread implements MusicListener {

	public static Music songs[];

	@Override
	public void run() {
		songs = new Music[19];

		try {
			
			int firstSong = (new Random()).nextInt(2);
			
			songs[firstSong] = new Music("resources/Music/0" + (firstSong + 1) + ".ogg");

			songs[firstSong].play(1, 0.25f);

			songs[firstSong].addListener(this);

			synchronized (this) {
				songs[0] = new Music("resources/Music/01.ogg");
				songs[0].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[1] = new Music("resources/Music/02.ogg");
				songs[1].addListener(this);
				wait(1);
			}
			/*
			synchronized (this) {
				songs[2] = new Music("resources/Music/03.ogg");
				songs[2].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[3] = new Music("resources/Music/04.ogg");
				songs[3].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[4] = new Music("resources/Music/05.ogg");
				songs[4].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[5] = new Music("resources/Music/06.ogg");
				songs[5].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[6] = new Music("resources/Music/07.ogg");
				songs[6].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[7] = new Music("resources/Music/08.ogg");
				songs[7].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[8] = new Music("resources/Music/09.ogg");
				songs[8].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[9] = new Music("resources/Music/10.ogg");
				songs[9].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[10] = new Music("resources/Music/11.ogg");
				songs[10].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[11] = new Music("resources/Music/12.ogg");
				songs[11].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[12] = new Music("resources/Music/13.ogg");
				songs[12].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[13] = new Music("resources/Music/14.ogg");
				songs[13].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[14] = new Music("resources/Music/15.ogg");
				songs[14].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[15] = new Music("resources/Music/16.ogg");
				songs[15].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[16] = new Music("resources/Music/17.ogg");
				songs[16].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[17] = new Music("resources/Music/18.ogg");
				songs[17].addListener(this);
				wait(1);
			}
			synchronized (this) {
				songs[18] = new Music("resources/Music/19.ogg");
				songs[18].addListener(this);
				wait(1);
			}
			*/
		} catch (SlickException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
		}

	}

	@Override
	public synchronized void musicEnded(Music music) {
		int musicID = 0;
		for (int i = 0; i < songs.length; i++) {
			if (music == songs[i]) {
				musicID = i;
				break;
			}
		}
		musicID++;
		musicID %= songs.length;
		if(songs[musicID] == null)
			musicID = 0;
		songs[musicID].play(1, 0.25f);
	}

	@Override
	public void musicSwapped(Music music, Music newMusic) {
		// TODO Auto-generated method stub

	}
}

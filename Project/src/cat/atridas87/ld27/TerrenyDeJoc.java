package cat.atridas87.ld27;

import static cat.atridas87.ld27.LD27.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

public class TerrenyDeJoc {

	final Casella caselles[][] = new Casella[GRAELLA_TAMANY_X][GRAELLA_TAMANY_Y];
	Casella casellaSeleccionada = null;

	final Recepta receptes[];
	float timer;
	int pv;
	int ticks;

	final private Random rnd = new Random();
	
	final TreeMap<Recurs, Integer> magatzem = new TreeMap<Recurs, Integer>();
	final HashSet<Recurs> knownRecursos = new HashSet<Recurs>();

	TerrenyDeJoc() {
		for (int i = 0; i < GRAELLA_TAMANY_X; i++) {
			for (int j = 0; j < GRAELLA_TAMANY_Y; j++) {
				caselles[i][j] = new Casella();
				caselles[i][j].type = Casella.Type.CAMP;
				/*
				caselles[i][j].type = Casella.Type.values()[(i + j
						* GRAELLA_TAMANY_X)
						% Casella.Type.values().length];
				*/
			}
		}

		caselles[GRAELLA_TAMANY_X - 1][GRAELLA_TAMANY_Y - 1].type = Casella.Type.AIGUA;
		caselles[GRAELLA_TAMANY_X - 1][GRAELLA_TAMANY_Y - 2].type = Casella.Type.AIGUA;
		caselles[GRAELLA_TAMANY_X - 2][GRAELLA_TAMANY_Y - 1].type = Casella.Type.AIGUA;
		caselles[GRAELLA_TAMANY_X - 2][GRAELLA_TAMANY_Y - 2].type = Casella.Type.AIGUA;
		caselles[GRAELLA_TAMANY_X - 1][GRAELLA_TAMANY_Y - 3].type = Casella.Type.AIGUA;
		caselles[GRAELLA_TAMANY_X - 2][GRAELLA_TAMANY_Y - 3].type = Casella.Type.AIGUA;

		caselles[GRAELLA_TAMANY_X - 3][GRAELLA_TAMANY_Y - 2].type = Casella.Type.BOSC;
		caselles[GRAELLA_TAMANY_X - 3][GRAELLA_TAMANY_Y - 3].type = Casella.Type.BOSC;
		caselles[GRAELLA_TAMANY_X - 4][GRAELLA_TAMANY_Y - 2].type = Casella.Type.BOSC;
		caselles[GRAELLA_TAMANY_X - 4][GRAELLA_TAMANY_Y - 3].type = Casella.Type.BOSC;

		caselles[GRAELLA_TAMANY_X - 3][GRAELLA_TAMANY_Y - 1].type = Casella.Type.CANTERA;
		caselles[GRAELLA_TAMANY_X - 4][GRAELLA_TAMANY_Y - 1].type = Casella.Type.CANTERA;
		
		
		caselles[1][1].type = Casella.Type.MAGATZEM;

		caselles[1][2].treballadors.add(Recurs.HABITANT);
		caselles[1][0].treballadors.add(Recurs.HABITANT);

		receptes = GeneradorDeReceptes.receptes();

		timer = 10;
		
		for(int i = 0; i < Recurs.values().length; i++) {
			Recurs recurs = Recurs.values()[i];
			if(recurs.type == Recurs.Type.OBJECTE) {
				magatzem.put(recurs, 0);
			}
		}
	}

	public void update(float _dt) {
		timer -= _dt;
		if (timer <= 0) {
			timer = 10;

			checkReceptes();
			ticks++;
		}

		rnd.nextInt(); // randomize a saco
	}

	private void checkReceptes() {
		LinkedList<Recepta> receptesPossibles = new LinkedList<Recepta>();

		for (int i = 0; i < GRAELLA_TAMANY_X; i++) {
			for (int j = 0; j < GRAELLA_TAMANY_Y; j++) {
				receptesPossibles.clear();
				Casella casella = caselles[i][j];
				for (int r = 0; r < receptes.length; r++) {
					if (receptaPossible(receptes[r], casella)) {
						receptesPossibles.add(receptes[r]);
					}
				}
				Recepta millorRecepta = triarMillorRecepta(receptesPossibles,
						casella);

				if (!executarRecepta(millorRecepta, casella)) {
					executarEdificiEspecial(casella);
				}

			}
		}
	}

	private boolean receptaPossible(Recepta recepta, Casella casella) {
		if (casella.treballadors.isEmpty() || recepta.casella != casella.type
				|| casella.recursosGenerats.size() >= MAX_RECURSOS_GENERATS) {
			return false;
		}

		if (recepta.treballador != null
				&& recepta.treballador != casella.treballadors
						.get(casella.treballadors.size() - 1)) {
			return false;
		}

		HashMap<Recurs, Integer> recursosTrobats = new HashMap<Recurs, Integer>();

		for (int m = 0; m < recepta.materials.length; m++) {
			Recurs material = recepta.materials[m];
			Integer anteriors0 = recursosTrobats.get(material);
			int anteriors = 0;
			if (anteriors0 != null) {
				anteriors = anteriors0.intValue();
			}
			int buscant = 0;

			for (int i = 0; i < casella.recursosEntrants.size(); i++) {
				Recurs recuts = casella.recursosEntrants.get(i);
				if (recuts == material) {
					buscant++;
					if (buscant > anteriors) {
						break;
					}
				}
			}

			if (buscant > anteriors) {
				recursosTrobats.put(material, buscant);
			} else {
				return false;
			}
		}

		return true;
	}

	private Recepta triarMillorRecepta(LinkedList<Recepta> receptesPossibles,
			Casella casella) {
		return receptesPossibles.peekLast(); // TODO
	}

	private boolean executarRecepta(Recepta recepta, Casella casella) {
		if (recepta == null) {
			return false;
		}
		for (int m = 0; m < recepta.materials.length; m++) {
			Recurs material = recepta.materials[m];

			casella.recursosEntrants.remove(material);
		}

		int possibilitats = 0;
		for (int m = 0; m < recepta.possibilitatsResultats.length; m++) {
			possibilitats += recepta.possibilitatsResultats[m];
		}

		if (possibilitats > 0) {
			possibilitats = rnd.nextInt(possibilitats);
		}

		boolean haGenerat = false;

		for (int m = 0; m < recepta.resultats.length; m++) {
			if (recepta.possibilitatsResultats[m] == 0
					|| (possibilitats >= 0 && possibilitats < recepta.possibilitatsResultats[m])) {

				Recurs material = recepta.resultats[m];
				if (material != null) {
					casella.recursosGenerats.add(material);
					knownRecursos.add(material);
					haGenerat = true;
				}

			}
			possibilitats -= recepta.possibilitatsResultats[m];
		}

		if (haGenerat) {
			if (recepta.consumeixTreballador) {
				casella.treballadors.remove(casella.treballadors.size() - 1);
			}

			pv += recepta.pv;
		}

		return haGenerat;
	}

	private void executarEdificiEspecial(Casella casella) {
		switch (casella.type) {
		case CASA:
			if(casella.treballadors.size() == 2 && casella.recursosGenerats.size() == 0) {
				float possibilitats = .1f;
				if(casella.recursosEntrants.contains(Recurs.PA)) {
					possibilitats = .5f;
					casella.recursosEntrants.remove(Recurs.PA);
					pv += 500;
				} else if(casella.recursosEntrants.contains(Recurs.PEIX)) {
					possibilitats = .25f;
					casella.recursosEntrants.remove(Recurs.PEIX);
					pv += 50;
				} else if(casella.recursosEntrants.contains(Recurs.FRUITA)) {
					possibilitats = .25f;
					casella.recursosEntrants.remove(Recurs.FRUITA);
					pv += 50;
				}
				
				if(rnd.nextFloat() < possibilitats) {
					casella.recursosGenerats.add(Recurs.HABITANT);
				}
			}
			break;

		case MAGATZEM:
			if(casella.treballadors.size() > 0) {
				for(int i = 0; i < casella.recursosEntrants.size(); i++) {
					Recurs recurs = casella.recursosEntrants.get(i);
					if(recurs.type == Recurs.Type.OBJECTE) {
						magatzem.put(recurs, magatzem.get(recurs) + 1);
						casella.recursosEntrants.remove(recurs);
						i--;
					}
				}
			}
			break;
		default:
			break;
		}
	}
}

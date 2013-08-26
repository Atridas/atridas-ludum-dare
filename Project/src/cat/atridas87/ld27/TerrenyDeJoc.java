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
				 * caselles[i][j].type = Casella.Type.values()[(i + j
				 * GRAELLA_TAMANY_X) % Casella.Type.values().length];
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

		caselles[2][1].type = Casella.Type.MAGATZEM;

		caselles[1][2].recursosEntrants.add(Recurs.CASA);

		caselles[3][1].type = Casella.Type.CONSTRUCTORA;
		caselles[3][1].recursosEntrants.add(Recurs.PEDRA);
		caselles[3][1].recursosEntrants.add(Recurs.FUSTA);
		caselles[3][1].recursosEntrants.add(Recurs.ARGILA);

		caselles[1][2].treballadors.add(Recurs.HABITANT);
		caselles[1][0].treballadors.add(Recurs.HABITANT);

		receptes = GeneradorDeReceptes.receptes();

		timer = 10;

		for (int i = 0; i < Recurs.values().length; i++) {
			Recurs recurs = Recurs.values()[i];
			if (recurs.type == Recurs.Type.OBJECTE) {
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
				Casella casella = caselles[i][j];
				if (!executarEdificiEspecial(casella)) {
					receptesPossibles.clear();
					for (int r = 0; r < receptes.length; r++) {
						if (receptaPossible(receptes[r], casella)) {
							receptesPossibles.add(receptes[r]);
						}
					}
					Recepta millorRecepta = triarMillorRecepta(
							receptesPossibles, casella);

					executarRecepta(millorRecepta, casella);
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
		if (receptesPossibles.size() == 0) {
			return null;
		} else if (receptesPossibles.size() == 1) {
			return receptesPossibles.get(0);
		}

		{
			boolean hasSpecialist = false;
			for (int i = 0; i < receptesPossibles.size(); i++) {
				if (receptesPossibles.get(i).treballador != null) {
					hasSpecialist = true;
					break;
				}
			}

			if (hasSpecialist) {
				for (int i = 0; i < receptesPossibles.size(); i++) {
					if (receptesPossibles.get(i).treballador == null) {
						receptesPossibles.remove(i);
						i--;
					}
				}
			}

			if (receptesPossibles.size() == 1) {
				return receptesPossibles.get(0);
			}
		}
		{
			int maxPunts = 0;
			for (int i = 0; i < receptesPossibles.size(); i++) {
				int punts = receptesPossibles.get(i).pv;
				if (punts > maxPunts) {
					maxPunts = punts;
				}
			}

			for (int i = 0; i < receptesPossibles.size(); i++) {
				if (receptesPossibles.get(i).pv != maxPunts) {
					receptesPossibles.remove(i);
					i--;
				}
			}

			if (receptesPossibles.size() == 1) {
				return receptesPossibles.get(0);
			}
		}
		{
			int maxGeneracio = 0;
			for (int i = 0; i < receptesPossibles.size(); i++) {
				int generacio = receptesPossibles.get(i).possibilitatsResultats.length;
				if (generacio > maxGeneracio) {
					maxGeneracio = generacio;
				}
			}

			for (int i = 0; i < receptesPossibles.size(); i++) {
				if (receptesPossibles.get(i).possibilitatsResultats.length != maxGeneracio) {
					receptesPossibles.remove(i);
					i--;
				}
			}

			if (receptesPossibles.size() == 1) {
				return receptesPossibles.get(0);
			}
		}
		{
			int maxRecursos = 0;
			for (int i = 0; i < receptesPossibles.size(); i++) {
				int recursos = receptesPossibles.get(i).materials.length;
				if (recursos > maxRecursos) {
					maxRecursos = recursos;
				}
			}

			for (int i = 0; i < receptesPossibles.size(); i++) {
				if (receptesPossibles.get(i).materials.length != maxRecursos) {
					receptesPossibles.remove(i);
					i--;
				}
			}

			if (receptesPossibles.size() == 1) {
				return receptesPossibles.get(0);
			}
		}

		return receptesPossibles.get(0);
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

	private boolean executarEdificiEspecial(Casella casella) {
		switch (casella.type) {
		case CASA:
			if (casella.treballadors.size() == 2
					&& casella.recursosGenerats.size() == 0) {
				float possibilitats = .1f;
				if (casella.recursosEntrants.contains(Recurs.PA)) {
					possibilitats = .5f;
					casella.recursosEntrants.remove(Recurs.PA);
					pv += 500;
				} else if (casella.recursosEntrants.contains(Recurs.PEIX)) {
					possibilitats = .25f;
					casella.recursosEntrants.remove(Recurs.PEIX);
					pv += 50;
				} else if (casella.recursosEntrants.contains(Recurs.FRUITA)) {
					possibilitats = .25f;
					casella.recursosEntrants.remove(Recurs.FRUITA);
					pv += 50;
				}

				if (rnd.nextFloat() < possibilitats) {
					casella.recursosGenerats.add(Recurs.HABITANT);
					return true;
				}
			}
			break;
		case MAGATZEM:
			boolean guardat = false;
			for (int i = 0; i < casella.recursosEntrants.size(); i++) {
				Recurs recurs = casella.recursosEntrants.get(i);
				if (recurs.type == Recurs.Type.OBJECTE) {
					magatzem.put(recurs, magatzem.get(recurs) + 1);
					casella.recursosEntrants.remove(recurs);
					i--;
					guardat = true;
				}
			}
			if (guardat) {
				return true;
			}
			break;

		case CAMP:

			if (casella.treballadors.contains(Recurs.CONSTRUCTOR)
					&& casella.type != Casella.Type.MAGATZEM
					&& casella.type != Casella.Type.CONSTRUCTORA) {
				for (int i = 0; i < Casella.Type.values().length; i++) {
					Casella.Type tipusCasella = Casella.Type.values()[i];
					Recurs generador = tipusCasella.generador;
					if (generador != null
							&& casella.recursosEntrants.contains(generador)) {
						casella.type = tipusCasella;
						casella.recursosEntrants.remove(generador);
						return true;
					}
				}
			}

			break;

		case MERCAT:

			if (casella.treballadors.contains(Recurs.MERCADER)) {
				boolean venut = false;
				for (int i = 0; i < casella.recursosEntrants.size(); i++) {
					Recurs material = casella.recursosEntrants.get(i);
					if (material.valor > 0) {
						casella.recursosEntrants.remove(material);
						for(int j = 0; j < material.valor; j++) {
							casella.recursosGenerats.add(Recurs.MONEDES);
						}
						venut = true;
					}
				}
				if(venut) {
					return true;
				}
			}

			break;
			
		case BANC:
			
			if (casella.recursosEntrants.contains(Recurs.MONEDES)) {
				while (casella.recursosEntrants.contains(Recurs.MONEDES)) {
					casella.recursosEntrants.remove(Recurs.MONEDES);
					pv += 100;
				}
			}
			
			
		default:
			break;
		}

		if (casella.type.generador != null && casella.treballadors.size() == 1
				&& casella.treballadors.contains(Recurs.CONSTRUCTOR)
				&& casella.recursosEntrants.size() == 0
				&& casella.recursosGenerats.size() == 0) {

			casella.recursosGenerats.add(casella.type.generador);
			casella.type = Casella.Type.CAMP;
			return true;
		}

		return false;
	}
}

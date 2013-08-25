package cat.atridas87.ld27;

import static cat.atridas87.ld27.LD27.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class TerrenyDeJoc {
	
	final Casella caselles[][] = new Casella[GRAELLA_TAMANY_X][GRAELLA_TAMANY_Y];
	Casella casellaSeleccionada = null;

	final Recepta receptes[];
	float timer;
	int pv;
	int ticks;
	
	final private Random rnd = new Random();

	TerrenyDeJoc() {
		for (int i = 0; i < GRAELLA_TAMANY_X; i++) {
			for (int j = 0; j < GRAELLA_TAMANY_Y; j++) {
				caselles[i][j] = new Casella();
				caselles[i][j].type = Casella.Type.CAMP;

				caselles[i][j].type = Casella.Type.values()[(i + j
						* GRAELLA_TAMANY_X)
						% Casella.Type.values().length];
			}
		}

		caselles[1][2].recursosEntrants.add(Recurs.FUSTA);
		caselles[1][2].treballadors.add(Recurs.HABITANT);

		caselles[2][1].recursosEntrants.add(Recurs.FUSTA);
		caselles[2][1].recursosEntrants.add(Recurs.PEDRA);

		caselles[2][1].recursosGenerats.add(Recurs.FUSTA);
		caselles[2][1].recursosGenerats.add(Recurs.FUSTA);

		receptes = new Recepta[] { new Recepta(Casella.Type.BOSC, null,
				new Recurs[] {}, new Recurs[] { Recurs.FUSTA, Recurs.FRUITA,
						Recurs.AIGUA }, new int[] { 10, 1, 1 }, false, 1) };

		timer = 10;
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
				
				
				executarRecepta(millorRecepta, casella);
				
				// TODO receptes especials
			}
		}
	}

	private boolean receptaPossible(Recepta recepta, Casella casella) {
		if(casella.treballadors.isEmpty() || recepta.casella != casella.type || casella.recursosGenerats.size() >= MAX_RECURSOS_GENERATS) {
			return false;
		}
		
		if(recepta.treballador != null && 
				recepta.treballador != casella.treballadors.get(casella.treballadors.size() - 1))
		{
			return false;
		}
		
		HashMap<Recurs, Integer> recursosTrobats = new HashMap<Recurs, Integer>();
		
		for(int m = 0; m < recepta.materials.length; m++) {
			Recurs material = recepta.materials[m];
			//Integer anteriors0 = recursosTrobats.get(material);
			int anteriors = recursosTrobats.get(material);
			//if(anteriors0 != null) {
			//	anteriors = anteriors0.intValue();
			//}
			int buscant = 0;
			
			for(int i = 0; i < casella.recursosEntrants.size(); i++) {
				Recurs recuts = casella.recursosEntrants.get(i);
				if(recuts == material) {
					buscant++;
					if(buscant > anteriors) {
						break;
					}
				}
			}
			
			if(buscant > anteriors) {
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
	
	private void executarRecepta(Recepta recepta, Casella casella) {
		if(recepta == null) {
			return;
		}
		for(int m = 0; m < recepta.materials.length; m++) {
			Recurs material = recepta.materials[m];
			
			casella.recursosEntrants.remove(material);
		}
		
		int possibilitats = 0;
		for(int m = 0; m < recepta.possibilitatsResultats.length; m++) {
			possibilitats += recepta.possibilitatsResultats[m];
		}
		
		if(possibilitats > 0) {
			possibilitats = rnd.nextInt(possibilitats);
		}
		
		for(int m = 0; m < recepta.resultats.length; m++) {
			if(recepta.possibilitatsResultats[m] == 0 || (possibilitats >= 0 && possibilitats < recepta.possibilitatsResultats[m])) {
				
				Recurs material = recepta.resultats[m];
				if(material != null) {
					casella.recursosGenerats.add(material);
				}
				
			}
			possibilitats -= recepta.possibilitatsResultats[m];
		}
		
		pv += recepta.pv;
	}
}

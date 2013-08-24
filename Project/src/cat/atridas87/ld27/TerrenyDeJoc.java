package cat.atridas87.ld27;

import static cat.atridas87.ld27.LD27.*;

public class TerrenyDeJoc {
	final Casella caselles[][] = new Casella[GRAELLA_TAMANY_X][GRAELLA_TAMANY_Y];
	
	
	TerrenyDeJoc() {
		for(int i = 0; i < GRAELLA_TAMANY_X; i++) {
			for(int j = 0; j < GRAELLA_TAMANY_Y; j++) {
				caselles[i][j] = new Casella();
				caselles[i][j].type = Casella.Type.CAMP;
				
				caselles[i][j].type = Casella.Type.values()[(i + j * GRAELLA_TAMANY_X) % Casella.Type.values().length];
			}
		}
		
		

		caselles[1][2].recursosEntrants.add(Recurs.FUSTA);
		caselles[1][2].treballadors.add(Recurs.HABITANT);


		caselles[2][1].recursosEntrants.add(Recurs.FUSTA);
		caselles[2][1].recursosEntrants.add(Recurs.PEDRA);

		caselles[2][1].recursosGenerats.add(Recurs.FUSTA);
		caselles[2][1].recursosGenerats.add(Recurs.FUSTA);
	}
	
}

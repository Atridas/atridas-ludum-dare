package cat.atridas87.ld27;

import static cat.atridas87.ld27.LD27.*;

public class TerrenyDeJoc {
	final Casella caselles[][] = new Casella[GRAELLA_TAMANY_X][GRAELLA_TAMANY_Y];
	
	
	TerrenyDeJoc() {
		for(int i = 0; i < GRAELLA_TAMANY_X; i++) {
			for(int j = 0; j < GRAELLA_TAMANY_Y; j++) {
				caselles[i][j] = Casella.CAMP;
			}
		}

		caselles[1][1] = Casella.BOSC;
		caselles[2][3] = Casella.AIGUA;
		caselles[3][2] = Casella.CANTERA;
	}
	
}

package cat.atridas87.ld27;

import java.util.ArrayList;

public class Casella {
	public enum Type {
		BOSC(0,0),
		CAMP(1,0),
		CANTERA(2,0),
		AIGUA(3,0),
		HORT(4,0),
		GRANJA(5,0),
		MOLI_DE_VENT(0,1),
		FLECA(1,1),
		MAGATZEM(2,1),
		TEMPLE(3,1),
		CABANA_BRUIXA(4,1),
		TEATRE(5,1),
		CONSTRUCTORA(0,2),
		BANC(1,2),
		INSTITUT(2,2),
		BAR(3,2),
		MERCAT(4,2),
		CASA(4,3);
		
		public final int spriteX, spriteY;
		
		Type(int x, int y) {
			spriteX = x;
			spriteY = y;
		}
	}
	public Type type;
	public final ArrayList<Recurs> recursosEntrants, recursosGenerats, treballadors;
	
	{
		recursosEntrants = new ArrayList<Recurs>();
		recursosGenerats = new ArrayList<Recurs>();
		treballadors = new ArrayList<Recurs>();
	}
}

package cat.atridas87.ld27;

import java.util.LinkedList;

public class Casella {
	public enum Type {
		CAMP(0,0),
		BOSC(1,0),
		CANTERA(2,0),
		AIGUA(3,0),
		HORT(4,0),
		GRANJA(5,0),
		MAGATZEM(0,1),
		TEMPLE(1,1),
		CASA(2,1),
		CABANA_BRUIXA(3,1),
		TEATRE(4,1),
		BANC(5,1),
		INSTITUT(0,2),
		BAR(1,2),
		MOLI_DE_VENT(2,2),
		FLECA(3,2),
		MERCAT(4,2);
		
		public final int spriteX, spriteY;
		
		Type(int x, int y) {
			spriteX = x;
			spriteY = y;
		}
	}
	public Type type;
	public final LinkedList<Recurs> recursosEntrants, recursosGenerats;
	
	{
		recursosEntrants = new LinkedList<Recurs>();
		recursosGenerats = new LinkedList<Recurs>();
	}
}

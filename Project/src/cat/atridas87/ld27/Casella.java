package cat.atridas87.ld27;

import java.util.ArrayList;

public class Casella {
	public enum Type {
		BOSC(0,0,null),
		CAMP(1,0,null),
		CANTERA(2,0,null),
		AIGUA(3,0,null),
		HORT(4,0,Recurs.HORT),
		GRANJA(5,0,Recurs.GRANJA),
		MOLI_DE_VENT(0,1,Recurs.MOLI_DE_VENT),
		FLECA(1,1,Recurs.FLECA),
		MAGATZEM(2,1,Recurs.MAGATZEM),
		TEMPLE(3,1,Recurs.TEMPLE),
		CABANA_BRUIXA(4,1,Recurs.CABANA_BRUIXA),
		TEATRE(5,1,Recurs.TEATRE),
		CONSTRUCTORA(0,2,Recurs.CONSTRUCTORA),
		BANC(1,2,Recurs.BANC),
		INSTITUT(2,2,Recurs.INSTITUT),
		BAR(3,2,Recurs.BAR),
		MERCAT(4,2,Recurs.MERCAT),
		CASA(5,2,Recurs.CASA);
		
		public final int spriteX, spriteY;
		public final Recurs generador;
		
		Type(int x, int y, Recurs _generador) {
			spriteX = x;
			spriteY = y;
			generador = _generador;
		}
	}
	public Type type;
	public final ArrayList<Recurs> recursosEntrants, recursosGenerats, treballadors;
	public int pvGenerats;
	
	{
		recursosEntrants = new ArrayList<Recurs>();
		recursosGenerats = new ArrayList<Recurs>();
		treballadors = new ArrayList<Recurs>();
	}
}

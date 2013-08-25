package cat.atridas87.ld27;

public enum Recurs {
	
	FUSTA(0,0, Type.OBJECTE),
	PEDRA(1,0, Type.OBJECTE),
	ARGILA(2,0, Type.OBJECTE),
	PEDRES_PRECIOSES(3,0, Type.OBJECTE),
	OR(4,0, Type.OBJECTE),
	BLAT(5,0, Type.OBJECTE),
	FARINA(6,0, Type.OBJECTE),
	PA(7,0, Type.OBJECTE),
	PEIX(0,1, Type.OBJECTE),
	POLLASTRE(1,1, Type.OBJECTE),
	OUS(2,1, Type.OBJECTE),
	PASTIS(3,1, Type.OBJECTE),
	FALC(4,1, Type.OBJECTE),
	PIC(5,1, Type.OBJECTE),
	SERRA(6,1, Type.OBJECTE),
	MONEDES(7,1, Type.OBJECTE),
	AIGUA(0,2, Type.OBJECTE),
	FRUITA(1,2, Type.OBJECTE),
	RAIM(2,2, Type.OBJECTE),
	VI(3,2, Type.OBJECTE),

	HORT(4,2, Type.EDIFICI),
	GRANJA(5,2, Type.EDIFICI),
	MAGATZEM(5,2, Type.EDIFICI),
	TEMPLE(6,2, Type.EDIFICI),
	CASA(7,2, Type.EDIFICI),
	CABANA_BRUIXA(0,3, Type.EDIFICI),
	TEATRE(1,3, Type.EDIFICI),
	BANC(2,3, Type.EDIFICI),
	INSTITUT(3,3, Type.EDIFICI),
	BAR(4,3, Type.EDIFICI),
	MOLI_DE_VENT(5,4, Type.EDIFICI),
	FLECA(6,4, Type.EDIFICI),
	MERCAT(7,4, Type.EDIFICI),
	
	HABITANT(0,5, Type.HABITANT), //
	BRUIXA(1,5, Type.HABITANT),
	SACERDOTESSA(2,5, Type.HABITANT),
	GRANGER(3,5, Type.HABITANT),
	CONSTRUCTOR(4,5, Type.HABITANT),
	MINER(5,5, Type.HABITANT),
	JOGLAR(6,5, Type.HABITANT),
	GUARDABOSC(7,5, Type.HABITANT),
	MERCADER(0,6, Type.HABITANT);
	
	public final int spriteX, spriteY;
	public final Type type;
	
	Recurs(int x, int y, Type _type) {
		spriteX = x;
		spriteY = y;
		type = _type;
	}
	
	public static enum Type {
		OBJECTE, EDIFICI, HABITANT
	}
}

package cat.atridas87.ld27;

public enum Recurs {
	
	FUSTA(7,2, Type.OBJECTE),
	PEDRA(0,3, Type.OBJECTE),
	ARGILA(1,3, Type.OBJECTE),
	PEDRES_PRECIOSES(2,3, Type.OBJECTE),
	OR(3,3, Type.OBJECTE),
	BLAT(4,3, Type.OBJECTE),
	FARINA(5,3, Type.OBJECTE),
	PA(6,3, Type.OBJECTE),
	PEIX(7,3, Type.OBJECTE),
	POLLASTRE(0,4, Type.OBJECTE),
	OUS(1,4, Type.OBJECTE),
	PASTIS(2,4, Type.OBJECTE),
	FALC(3,4, Type.OBJECTE),
	PIC(4,4, Type.OBJECTE),
	SERRA(5,4, Type.OBJECTE),
	MONEDES(6,4, Type.OBJECTE),
	AIGUA(7,4, Type.OBJECTE),
	FRUITA(0,5, Type.OBJECTE),
	RAIM(1,5, Type.OBJECTE),
	VI(2,5, Type.OBJECTE),

	HORT(1,1, Type.EDIFICI),
	GRANJA(2,1, Type.EDIFICI),
	MOLI_DE_VENT(3,1, Type.EDIFICI),
	FLECA(4,1, Type.EDIFICI),
	MAGATZEM(5,1, Type.EDIFICI),
	TEMPLE(6,1, Type.EDIFICI),
	CABANA_BRUIXA(7,1, Type.EDIFICI),
	TEATRE(0,2, Type.EDIFICI),
	CONSTRUCTORA(1,2, Type.EDIFICI),
	BANC(2,2, Type.EDIFICI),
	INSTITUT(3,2, Type.EDIFICI),
	BAR(4,2, Type.EDIFICI),
	MERCAT(5,2, Type.EDIFICI),
	CASA(6,2, Type.EDIFICI),
	
	BRUIXA(0,0, Type.HABITANT),
	SACERDOTESSA(1,0, Type.HABITANT),
	GRANGER(2,0, Type.HABITANT),
	CONSTRUCTOR(3,0, Type.HABITANT),
	MINER(4,0, Type.HABITANT),
	JOGLAR(5,0, Type.HABITANT),
	HABITANT(6,0, Type.HABITANT), //
	GUARDABOSC(7,0, Type.HABITANT),
	MERCADER(0,1, Type.HABITANT);
	
	public final int spriteX, spriteY;
	public final Type type;

	public final static int unknownSpriteX = 3;
	public final static int unknownSpriteY = 5;
	
	Recurs(int x, int y, Type _type) {
		spriteX = x;
		spriteY = y;
		type = _type;
	}
	
	public static enum Type {
		OBJECTE, EDIFICI, HABITANT
	}
}

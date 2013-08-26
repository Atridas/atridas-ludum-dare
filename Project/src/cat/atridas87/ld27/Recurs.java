package cat.atridas87.ld27;

public enum Recurs {
	
	FUSTA(7,2, Type.OBJECTE, 1),
	PEDRA(0,3, Type.OBJECTE, 1),
	ARGILA(1,3, Type.OBJECTE , 1),
	PEDRES_PRECIOSES(2,3, Type.OBJECTE, 3),
	OR(3,3, Type.OBJECTE, 5),
	BLAT(4,3, Type.OBJECTE, 1),
	FARINA(5,3, Type.OBJECTE, 1),
	PA(6,3, Type.OBJECTE, 2),
	PEIX(7,3, Type.OBJECTE, 1),
	POLLASTRE(0,4, Type.OBJECTE, 1),
	OUS(1,4, Type.OBJECTE, 2),
	PASTIS(2,4, Type.OBJECTE, 4),
	FALC(3,4, Type.OBJECTE, 1),
	PIC(4,4, Type.OBJECTE, 1),
	SERRA(5,4, Type.OBJECTE, 1),
	MONEDES(6,4, Type.OBJECTE, 0),
	AIGUA(7,4, Type.OBJECTE, 1),
	FRUITA(0,5, Type.OBJECTE, 1),
	RAIM(1,5, Type.OBJECTE, 1),
	VI(2,5, Type.OBJECTE, 2),

	HORT(1,1, Type.EDIFICI, 1),
	GRANJA(2,1, Type.EDIFICI, 1),
	MOLI_DE_VENT(3,1, Type.EDIFICI, 1),
	FLECA(4,1, Type.EDIFICI, 1),
	MAGATZEM(5,1, Type.EDIFICI, 1),
	TEMPLE(6,1, Type.EDIFICI, 1),
	CABANA_BRUIXA(7,1, Type.EDIFICI, 1),
	TEATRE(0,2, Type.EDIFICI, 1),
	CONSTRUCTORA(1,2, Type.EDIFICI, 1),
	BANC(2,2, Type.EDIFICI, 1),
	INSTITUT(3,2, Type.EDIFICI, 1),
	BAR(4,2, Type.EDIFICI, 1),
	MERCAT(5,2, Type.EDIFICI, 1),
	CASA(6,2, Type.EDIFICI, 1),
	
	BRUIXA(0,0, Type.HABITANT, 0),
	SACERDOTESSA(1,0, Type.HABITANT, 0),
	GRANGER(2,0, Type.HABITANT, 0),
	CONSTRUCTOR(3,0, Type.HABITANT, 0),
	MINER(4,0, Type.HABITANT, 0),
	JOGLAR(5,0, Type.HABITANT, 0),
	HABITANT(6,0, Type.HABITANT, 0), //
	GUARDABOSC(7,0, Type.HABITANT, 0),
	MERCADER(0,1, Type.HABITANT, 0);
	
	public final int spriteX, spriteY, valor;
	public final Type type;

	public final static int unknownSpriteX = 3;
	public final static int unknownSpriteY = 5;
	
	Recurs(int x, int y, Type _type, int _valor) {
		spriteX = x;
		spriteY = y;
		type = _type;
		valor = _valor;
	}
	
	public static enum Type {
		OBJECTE, EDIFICI, HABITANT
	}
}

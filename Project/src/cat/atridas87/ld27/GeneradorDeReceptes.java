package cat.atridas87.ld27;

public abstract class GeneradorDeReceptes {

	public static Recepta[] receptes() {
		
		return new Recepta[] { 
				new Recepta(
						Casella.Type.BOSC,
						null,
						new Recurs[] {},
						new Recurs[] { Recurs.FUSTA, Recurs.FRUITA, Recurs.POLLASTRE },
						new int[] { 6, 3, 1 },
						false,
						1),
				new Recepta(
						Casella.Type.AIGUA,
						null,
						new Recurs[] { },
						new Recurs[] { Recurs.AIGUA, Recurs.PEIX },
						new int[] { 2, 1 },
						false,
						1),
				new Recepta(
						Casella.Type.BOSC,
						Recurs.GUARDABOSC,
						new Recurs[] {},
						new Recurs[] { Recurs.FUSTA, Recurs.FUSTA, Recurs.FUSTA },
						new int[] { 0,0,0 },
						false,
						1),
				new Recepta(
						Casella.Type.CANTERA,
						null,
						new Recurs[] {},
						new Recurs[] { Recurs.PEDRA },
						new int[] { 0 },
						false,
						1),
				new Recepta(
						Casella.Type.CANTERA,
						Recurs.MINER,
						new Recurs[] {},
						new Recurs[] { Recurs.PEDRA, Recurs.PEDRA, Recurs.PEDRA },
						new int[] { 0, 0, 0 },
						false,
						1),
				new Recepta(
						Casella.Type.CANTERA,
						Recurs.MINER,
						new Recurs[] { Recurs.AIGUA, Recurs.AIGUA, Recurs.AIGUA },
						new Recurs[] { Recurs.ARGILA },
						new int[] { 0 },
						false,
						1),
				new Recepta(
						Casella.Type.CANTERA,
						Recurs.BRUIXA,
						new Recurs[] {  },
						new Recurs[] { Recurs.PEDRES_PRECIOSES },
						new int[] { 0 },
						false,
						1),
				new Recepta(
						Casella.Type.CANTERA,
						Recurs.BRUIXA,
						new Recurs[] { Recurs.PIC },
						new Recurs[] { Recurs.OR },
						new int[] { 0 },
						false,
						1),
				new Recepta(
						Casella.Type.CAMP,
						null,
						new Recurs[] { },
						new Recurs[] { Recurs.BLAT, Recurs.RAIM, null },
						new int[] { 1, 1, 5 },
						false,
						1),
				new Recepta(
						Casella.Type.HORT,
						Recurs.GRANGER,
						new Recurs[] { Recurs.BLAT },
						new Recurs[] { Recurs.BLAT, Recurs.BLAT, Recurs.BLAT, Recurs.BLAT, Recurs.BLAT },
						new int[] { 0, 0, 0, 0, 0 },
						false,
						1),
				new Recepta(
						Casella.Type.HORT,
						Recurs.GRANGER,
						new Recurs[] { Recurs.RAIM },
						new Recurs[] { Recurs.RAIM, Recurs.RAIM, Recurs.RAIM, null },
						new int[] { 0, 0, 1, 1 },
						false,
						1),
				new Recepta(
						Casella.Type.MOLI_DE_VENT,
						null,
						new Recurs[] { Recurs.BLAT, Recurs.BLAT },
						new Recurs[] { Recurs.FARINA },
						new int[] { 0 },
						false,
						1),
				new Recepta(
						Casella.Type.FLECA,
						null,
						new Recurs[] { Recurs.FARINA, Recurs.AIGUA},
						new Recurs[] { Recurs.PA },
						new int[] { 0 },
						false,
						1),
				new Recepta(
						Casella.Type.GRANJA,
						Recurs.GRANGER,
						new Recurs[] { Recurs.POLLASTRE, Recurs.POLLASTRE },
						new Recurs[] { Recurs.OUS },
						new int[] { 0 },
						false,
						1),
				new Recepta(
						Casella.Type.GRANJA,
						Recurs.GRANGER,
						new Recurs[] { Recurs.AIGUA, Recurs.OUS, Recurs.FARINA },
						new Recurs[] { Recurs.PASTIS },
						new int[] { 0 },
						false,
						1),
				new Recepta(
						Casella.Type.GRANJA,
						Recurs.GRANGER,
						new Recurs[] { Recurs.RAIM, Recurs.RAIM },
						new Recurs[] { Recurs.VI },
						new int[] { 0 },
						false,
						50),
				new Recepta(
						Casella.Type.BAR,
						null,
						new Recurs[] { Recurs.PASTIS, Recurs.VI },
						new Recurs[] { Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES },
						new int[] { 0, 0, 0, 0, 0 },
						false,
						300),
				new Recepta(
						Casella.Type.BAR,
						null,
						new Recurs[] { Recurs.PA, Recurs.VI },
						new Recurs[] { Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES },
						new int[] { 0, 0, 0, 0, 0 },
						false,
						200),
				new Recepta(
						Casella.Type.BAR,
						null,
						new Recurs[] { Recurs.PEIX, Recurs.VI },
						new Recurs[] { Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES },
						new int[] { 0, 0, 0, 0 },
						false,
						100),
				new Recepta(
						Casella.Type.BAR,
						null,
						new Recurs[] { Recurs.PA },
						new Recurs[] { Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES },
						new int[] { 0, 0, 0 },
						false,
						50),
				new Recepta(
						Casella.Type.BAR,
						null,
						new Recurs[] { Recurs.PEIX },
						new Recurs[] { Recurs.MONEDES, Recurs.MONEDES },
						new int[] { 0, 0 },
						false,
						25),
				new Recepta(
						Casella.Type.BAR,
						null,
						new Recurs[] { Recurs.PASTIS },
						new Recurs[] { Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES },
						new int[] { 0, 0, 0, 0, 0 },
						false,
						75),

				new Recepta(
						Casella.Type.CONSTRUCTORA,
						null,
						new Recurs[] { Recurs.FUSTA, Recurs.BLAT },
						new Recurs[] { Recurs.FALC },
						new int[] { 0 },
						false,
						50),
				new Recepta(
						Casella.Type.CONSTRUCTORA,
						null,
						new Recurs[] { Recurs.FUSTA, Recurs.PEDRA },
						new Recurs[] { Recurs.PIC },
						new int[] { 0 },
						false,
						50),
				new Recepta(
						Casella.Type.CONSTRUCTORA,
						null,
						new Recurs[] { Recurs.FUSTA, Recurs.FUSTA },
						new Recurs[] { Recurs.SERRA },
						new int[] { 0 },
						false,
						50),
						
				new Recepta(
						Casella.Type.TEMPLE,
						Recurs.SACERDOTESSA,
						new Recurs[] { Recurs.PA },
						new Recurs[] { Recurs.PA, Recurs.PA, null },
						new int[] { 0,1,1 },
						false,
						1),						
				new Recepta(
						Casella.Type.TEMPLE,
						Recurs.SACERDOTESSA,
						new Recurs[] { Recurs.PEIX },
						new Recurs[] { Recurs.PEIX, Recurs.PEIX, null },
						new int[] { 0,1,1 },
						false,
						1),
						
				new Recepta(
						Casella.Type.TEATRE,
						Recurs.JOGLAR,
						new Recurs[] {  },
						new Recurs[] { Recurs.MONEDES, null },
						new int[] { 1, 1},
						false,
						100),

				new Recepta(
						Casella.Type.INSTITUT,
						Recurs.HABITANT,
						new Recurs[] { Recurs.FALC },
						new Recurs[] { Recurs.GRANGER },
						new int[] { 0 },
						true,
						100),
				new Recepta(
						Casella.Type.INSTITUT,
						Recurs.HABITANT,
						new Recurs[] { Recurs.PIC },
						new Recurs[] { Recurs.MINER },
						new int[] { 0 },
						true,
						100),
				new Recepta(
						Casella.Type.INSTITUT,
						Recurs.HABITANT,
						new Recurs[] { Recurs.SERRA },
						new Recurs[] { Recurs.GUARDABOSC },
						new int[] { 0 },
						true,
						100),
				new Recepta(
						Casella.Type.CONSTRUCTORA,
						Recurs.HABITANT,
						new Recurs[] { Recurs.FUSTA, Recurs.PEDRA, Recurs.ARGILA },
						new Recurs[] { Recurs.CONSTRUCTOR },
						new int[] { 0 },
						true,
						100),
				new Recepta(
						Casella.Type.CABANA_BRUIXA,
						Recurs.HABITANT,
						new Recurs[] { Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES },
						new Recurs[] { Recurs.BRUIXA },
						new int[] { 0 },
						true,
						100),
				new Recepta(
						Casella.Type.TEMPLE,
						Recurs.HABITANT,
						new Recurs[] { Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES },
						new Recurs[] { Recurs.SACERDOTESSA },
						new int[] { 0 },
						true,
						100),
				new Recepta(
						Casella.Type.TEATRE,
						Recurs.HABITANT,
						new Recurs[] { Recurs.VI },
						new Recurs[] { Recurs.JOGLAR },
						new int[] { 0 },
						true,
						100),
				new Recepta(
						Casella.Type.MERCAT,
						Recurs.HABITANT,
						new Recurs[] { Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES, Recurs.MONEDES },
						new Recurs[] { Recurs.MERCADER },
						new int[] { 0 },
						true,
						100),
				
				};
		
	}
	
}

package cat.atridas87.ld27;

public class Recepta {
	public final Casella.Type casella;
	public final Recurs treballador;
	public final Recurs materials[];
	public final Recurs resultats[];
	public final int possibilitatsResultats[];
	public final boolean consumeixTreballador;
	public final boolean requereixGeneratsBuits;
	public final int pv;

	Recepta(Casella.Type _casella, Recurs _treballador, Recurs _materials[],
			Recurs _resultats[], int _possibilitatsResultats[],
			boolean _consumeixTreballador, boolean _requereixGeneratsBuits,
			int _pv) {
		casella = _casella;
		treballador = _treballador;

		materials = _materials.clone();
		resultats = _resultats.clone();
		possibilitatsResultats = _possibilitatsResultats.clone();

		consumeixTreballador = _consumeixTreballador;
		requereixGeneratsBuits = _requereixGeneratsBuits;
		pv = _pv;
	}
}

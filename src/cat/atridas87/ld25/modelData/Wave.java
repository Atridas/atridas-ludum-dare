package cat.atridas87.ld25.modelData;

import java.util.HashMap;

public class Wave {
	private final HashMap<Soul, Integer> wave = new HashMap<Soul, Integer>();
	
	public Wave(int aSouls, int bSouls, int cSouls) {
		wave.put(Soul.A, aSouls);
		wave.put(Soul.B, bSouls);
		wave.put(Soul.C, cSouls);
	}
	
	public int getSouls(Soul kind) {
		return wave.get(kind);
	}
}

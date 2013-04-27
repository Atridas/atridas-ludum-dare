package cat.atridas87.ld26.gameobjects;

import javax.vecmath.Vector2f;

public enum Lane {
	UP(new Vector2f(575,25), new Vector2f(575,300), new Vector2f(575,575), new Vector2f(300,575), new Vector2f(25,575)),
	MIDDLE(new Vector2f(575,25), new Vector2f(300,300), new Vector2f(25,575)),
	BOT(new Vector2f(575,25), new Vector2f(300,25), new Vector2f(25,25), new Vector2f(25,300), new Vector2f(25,575));
	
	public final Vector2f controlPoints[];
	private Lane(Vector2f... _controlPoints) {
		controlPoints = _controlPoints;
	}
}

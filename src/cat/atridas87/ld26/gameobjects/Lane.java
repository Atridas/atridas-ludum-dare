package cat.atridas87.ld26.gameobjects;

import javax.vecmath.Vector2f;

public enum Lane {
	UP(new Vector2f(575,50), new Vector2f(575,175), new Vector2f(575,300), new Vector2f(575,575), new Vector2f(300,575), new Vector2f(175,575), new Vector2f(50,575)),
	MIDDLE(new Vector2f(550,50), new Vector2f(500,100), new Vector2f(300,300), new Vector2f(100,500), new Vector2f(50,550)),
	BOT(new Vector2f(550,25), new Vector2f(475,25), new Vector2f(300,25), new Vector2f(25,25), new Vector2f(25,300), new Vector2f(25,475), new Vector2f(25,550));
	
	public final Vector2f controlPoints[];
	private Lane(Vector2f... _controlPoints) {
		controlPoints = _controlPoints;
	}
}

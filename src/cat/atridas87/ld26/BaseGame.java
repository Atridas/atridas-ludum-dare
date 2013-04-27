package cat.atridas87.ld26;

public abstract class BaseGame {

	protected final int width, height;
	
	protected boolean finished = false;
	
	public BaseGame(int _width, int _height)
	{
		width = _width;
		height = _height;
	}

	public abstract String getWindowName();

	public final void setFinished(boolean _finished) { finished = _finished; };
	public final boolean isFinished() { return finished; };
	
	public abstract void init() throws Exception;
	public abstract void render();
	public abstract void update(float _dt);
	public abstract void cleanup();
	
}

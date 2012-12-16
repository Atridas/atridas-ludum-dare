package cat.atridas87.ld25.modelData;

public enum Soul {
	A("resources/images/icons/a_soul.png"),
	B("resources/images/icons/b_soul.png"),
	C("resources/images/icons/c_soul.png");
	
	public final String imageString;
	
	private Soul(String resource) {
		imageString = resource;
	}
}

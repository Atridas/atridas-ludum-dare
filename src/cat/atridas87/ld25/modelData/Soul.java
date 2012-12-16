package cat.atridas87.ld25.modelData;

public enum Soul {
	A("resources/images/icons/a_soul"),
	B("resources/images/icons/b_soul"),
	C("resources/images/icons/c_soul");
	
	public final String imageString;
	
	private Soul(String resource) {
		imageString = resource;
	}
}

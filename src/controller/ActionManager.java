package controller;

public class ActionManager {

	private static ActionManager instance = null;
	private Create_Shema shema;
	private Load_Shemu ld;
	private Validation v;
	private Parse p;
	
	private ActionManager() {
		super();
		setV(new Validation());
		setP(new Parse());

	    shema = new Create_Shema();
	    ld = new Load_Shemu();
	}
	
	public static ActionManager getInstance() {
		if (instance == null)
			instance = new ActionManager();

		return instance;
	}

	public Create_Shema getShema() {
		return shema;
	}

	public void setShema(Create_Shema shema) {
		this.shema = shema;
	}

	public Load_Shemu getLd() {
		return ld;
	}

	public void setLd(Load_Shemu ld) {
		this.ld = ld;
	}

	public Validation getV() {
		return v;
	}

	public void setV(Validation v) {
		this.v = v;
	}

	public Parse getP() {
		return p;
	}

	public void setP(Parse p) {
		this.p = p;
	}

}

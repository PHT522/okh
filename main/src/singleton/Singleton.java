package singleton;

import controller.PdsController;
import controller.TechbbsController;
import db.DBConnection;

public class Singleton {
	private static Singleton single = null;
	public TechbbsController techCtrl;
	public PdsController pdsCtrl;
	
	private Singleton() {
		DBConnection.initConnection();
		techCtrl = new TechbbsController();
		pdsCtrl=new PdsController();
	}
	
	public static Singleton getInstance() {
		if(single == null) {
			single = new Singleton();
		}
		return single;
	}
	
}

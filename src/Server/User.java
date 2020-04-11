package Server;

import Client.GUI;

public class User implements Runnable { // Can be either admin or regular student
	private RegistrationApp app;
	private String name;
	private int ID;

	public User(RegistrationApp app) {
		this.app = app;
	}

	@Override
	public void run() {
		GUI a = new GUI(this, this.app);
		a.start();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getName() {
		return this.name;
	}

	public int getID() {
		return this.ID;
	}

}

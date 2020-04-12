package Server.Model;

import Client.View.GUI;
import Server.Controller.RegistrationApp;

public class User implements Runnable { // Can be either admin or regular student
	private RegistrationApp app;
	private String name;
	private int ID;

	public User(RegistrationApp app) {
		this.app = app;
	}

	@Override
	public void run() {
		GUI userInterface = new GUI();
		userInterface.askNameID(this);
		userInterface.menu(this);
		app.startup(name, ID);

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

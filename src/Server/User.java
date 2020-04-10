package Server;

public class User implements Runnable { // Can be either admin or regular student
	private Application app;

	public User(Application app) {
		this.app = app;
	}

	@Override
	public void run() {
		app.startApplication();
	}

}

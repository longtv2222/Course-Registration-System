package Server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class User implements Runnable {
	private Application app;
	private Socket socket;
	private BufferedReader socketIn;
	private PrintWriter socketOut;

	public User(Socket socket, Application app) {
		this.socket = socket;
		this.app = app;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}

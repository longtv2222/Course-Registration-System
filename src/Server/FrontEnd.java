package Server;

import Client.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FrontEnd { // This acts as a Server
	private Application app;
	private ServerSocket serverSocket;
	private ExecutorService threadPool;
	private BufferedReader socketInput;
	private PrintWriter socketOutput;

	public FrontEnd(int port, Application app) {
		try {
			serverSocket = new ServerSocket(port);
			threadPool = Executors.newCachedThreadPool();
			this.app = app;
			System.out.println("The server is now running");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startApplication() { // What does it function do?
		while (true) {
			try {
				User user = new User(serverSocket.accept(), this.app);
				threadPool.execute(user);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		CourseCatalogue courseCat = new CourseCatalogue(); // The constructor of courseCat loads data from the database
		Application app = new Application(courseCat);
		FrontEnd server = new FrontEnd(9098, app);
		System.out.println("Server is running...");
		server.startApplication();
	}
}

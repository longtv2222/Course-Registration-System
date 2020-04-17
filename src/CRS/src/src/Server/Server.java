package src.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private ServerSocket serverSocket;
	private ArrayList<User> clients; // Manage list of clients
	private int port;
	private boolean running;
	private ExecutorService pool;

	public Server(int port) {
		this.port = port;
		clients = new ArrayList<User>();
		this.running = true;
		pool = Executors.newCachedThreadPool();
	}

	public void communicateWithClient() {
		try {
			CourseCatalogue courseCat = new CourseCatalogue(); // Assumming that this courseCat has been loaded by
																// DBManager
			serverSocket = new ServerSocket(port);
			while (running) {
				if (!running)
					break;
				User user = new User(serverSocket.accept(), this.clients, courseCat);
				pool.execute(user);
			}
			serverSocket.close(); // Closing the server.
			for (int i = 0; i < clients.size(); i++) { // Closing all the thread in this server.
				clients.get(i).getSocketIn().close();
				clients.get(i).getSocketOut().close();
				clients.get(i).getSocket().close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server(9098);
		System.out.println("Server is running");
		server.communicateWithClient();
	}
}

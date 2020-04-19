package crs.src.server;

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
		this.clients = new ArrayList<User>();
		this.running = true;
		this.pool = Executors.newCachedThreadPool();
	}

	public void communicateWithClient() {
		try {
			Application app = new Application(); // Asumming that this courseCat has been loaded by
																// DBManager
			serverSocket = new ServerSocket(port);
			while (running) {
				if (!running)
					break;
				User user = new User(serverSocket.accept(), this.clients, app);
				pool.execute(user);
			}
			serverSocket.close(); // Closing the server.
			for (int i = 0; i < clients.size(); i++) { // Closing all the thread in this server.
				clients.get(i).getSocketIn().close();
				clients.get(i).getSocketOut().close();
				clients.get(i).getSocket().close();
			}
			app.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int port = 9098;
		//uncomment the below to input a custom server port. would require modification of the client.
//		Scanner scanner = new Scanner(System.in);
//		while (true) {
//			try {
//				System.out.println("Please input the port you would like to run the server on.");
//				String rawInput = scanner.nextLine();
//				port = Integer.parseInt(rawInput);
//				if (port > 0 && port <= 65535) {
//					break;
//				}
//			} catch (Exception e) {
//				System.out.println("Please input a valid port number!");
//			}
//		}
		Server server = new Server(port);
		System.out.println("Server is running");
		server.communicateWithClient();
	}
}

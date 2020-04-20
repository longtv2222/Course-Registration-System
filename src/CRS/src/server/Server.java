package CRS.src.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	/**
	 * serverSocket represents the connection between server and client.
	 */
	private ServerSocket serverSocket;
	/**
	 * A list of clients(Students,Admins) that connects to this server.
	 */
	private ArrayList<User> clientList;
	/**
	 * The port this server is running on.
	 */
	private int port;
	/**
	 * Boolean running that indicates the status of this server.
	 */
	private boolean running;
	/**
	 * ThreadPool for users of this server.
	 */
	private ExecutorService pool;

	/**
	 * Constructor of Server that initializes variables and sets up the server.
	 * 
	 * @param port is the port the server is running on.
	 */
	public Server(int port) {
		this.port = port;
		this.clientList = new ArrayList<User>();
		this.running = true;
		this.pool = Executors.newCachedThreadPool();
	}

	/**
	 * This method establishes connection between server and clients.
	 */
	public void communicateWithClient() {
		try {
			Application app = new Application(); // Asumming that this courseCat has been loaded by
													// DBManager
			serverSocket = new ServerSocket(port);
			while (running) {
				if (!running)
					break;
				User user = new User(serverSocket.accept(), this.clientList, app);
				pool.execute(user);
			}
			serverSocket.close(); // Closing the server.
			for (User user : clientList) {
				user.close(); // Closing all threads of this server.
			}
			app.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int port = 9098;
		// uncomment the below to input a custom server port. would require modification
		// of the client.
		Scanner scanner = new Scanner(System.in);
		while (true) {
			try {
				System.out.println("Please input the port you would like to run the server on.");
				String rawInput = scanner.nextLine();
				port = Integer.parseInt(rawInput);
				if (port > 0 && port <= 65535) {
					break;
				}
			} catch (Exception e) {
				System.out.println("Please input a valid port number!");
			}
		}
		scanner.close();
		Server server = new Server(port);
		System.out.println("Server is running");
		server.communicateWithClient();
	}
}

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
	 * app is the application of this server.
	 */
	private Application app;

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
			app = new Application(); // Asumming that this courseCat has been loaded by
										// DBManager

			System.out.println("Server is running on port " + port + ".");
			serverSocket = new ServerSocket(port);
			System.out.println("Server is starting. Type 'exit' to exit.");
			Thread getExit = new Thread() {
				public void run() {
					try {
						while (running) {
							if (keyboard.hasNextLine()) {
								if (keyboard.nextLine().toLowerCase().equals("exit")) {
									System.out.println("testing exit!");
									running = false;
								}
							}
						}
						// Closing the server.
						System.out.println("Exiting!");
						for (User user : clientList) {
							System.out.println("Stopping user "+ user.getID());
							user.writeErrorMsg("Server is shutting down! Please restart the client to reconnect.");
							user.close(); // Closing all threads of this server.
						}
						app.close();
						serverSocket.close();
						System.exit(0);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};// Closing the server.
			pool.execute(getExit);
			while (running) {
				try {
					User user;
					user = new User(serverSocket.accept(), clientList, app);
					pool.execute(user);
				} catch (IOException e) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Using the keyboard input, we don't want to close it, so this leaves it up to
	 * the JVM to close.!
	 */
	public static final Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) {
		int port = 9098;
		while (true) {
			try {
				System.out.println("Please input the port you would like to run the server on, defaults to 9098.");
				String rawInput = null;
				while (true) {
					if (keyboard.hasNextLine()) {
						rawInput = keyboard.nextLine();
						break;
					}
				}
				if (rawInput.equals("")) {
					break;
				}
				port = Integer.parseInt(rawInput);
				if (port > 0 && port < 65535) {
					break;
				}
			} catch (Exception e) {
				System.out.println("Please input a valid port number!");
			}
		}
		Server server = new Server(port);
		server.communicateWithClient();
	}
}

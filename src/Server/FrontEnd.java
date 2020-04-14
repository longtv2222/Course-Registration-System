package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FrontEnd {
	private Socket socket;
	private ServerSocket serverSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private ExecutorService threadPool;
	private Application app;

	public FrontEnd(int port) {
		try {
			serverSocket = new ServerSocket(port);
			threadPool = Executors.newCachedThreadPool();
			this.app = new Application();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void communicateWithClient() {
		while (true) {
			try {
				socket = serverSocket.accept();
				socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				socketOut = new PrintWriter(socket.getOutputStream(), true);
				socketOut.println("Please enter the username: \0");
				String name = socketIn.readLine();
				socketOut.println("Please enter the user id: \0");
				String rawId = socketIn.readLine();
				int id = Integer.parseInt(rawId);
				User user = new User(this.app, socketIn, socketOut, name, id);
				threadPool.execute(user);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		FrontEnd server = new FrontEnd(9098);
		System.out.println("Server is running...");
		server.communicateWithClient();
	}
}

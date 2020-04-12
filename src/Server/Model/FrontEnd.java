package Server.Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Server.Controller.RegistrationApp;

public class FrontEnd {
	private ServerSocket serverSocket;
	private ExecutorService threadPool;

	public FrontEnd(int port) {
		try {
			serverSocket = new ServerSocket(port);
			threadPool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void communicateWithClient() {
		while (true) {
			try {
				RegistrationApp app = new RegistrationApp(serverSocket.accept());
				User user = new User(app);
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

package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
				Application app = new Application(serverSocket.accept(), new CourseCatalogue());
				// By calling new CourseCatalouge(), it loads data from the database to the app.
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

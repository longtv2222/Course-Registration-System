package src.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import src.Server.Command;

public class Client implements Runnable {
	private ObjectInputStream socketIn; // to read from the socket
	private ObjectOutputStream socketOut; // to write on the socket
	private Socket socket;
	private ClientGUI cg;
	private String server;
	private String username;
	private Integer ID;
	private int port;
	private ExecutorService pool;
	private boolean running;

	public Client(String server, int port, String username, Integer ID, ClientGUI cg) {
		this.server = server;
		this.port = port;
		this.username = username;
		this.ID = ID;
		this.cg = cg;
		this.running = true;
		pool = Executors.newCachedThreadPool();
	}

	public void communicateWithServer() {
		try {
			socket = new Socket(server, port);
			socketIn = new ObjectInputStream(socket.getInputStream());
			socketOut = new ObjectOutputStream(socket.getOutputStream());
			socketOut.writeObject(username);
			socketOut.writeObject(ID);
			pool.execute(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void display(String msg) {
		cg.append(msg + "\n");
	}

	public void sendMessage(Command msg) {
		try {
			socketOut.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (running) {
			try {
				String msg = (String) socketIn.readObject();
				cg.append(msg + " "); // Not sure where this error comes from. Might need to address it later.
			} catch (IOException e) {
				break;
			} catch (ClassNotFoundException e2) {
				break;
			}
		}
		this.closeSocket();
	}

	public void closeSocket() {
		try {
			socket.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package crs.src.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import crs.src.shared.Command;


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
				if (msg.contains("ERROR")) {
					String errorMessage = msg.replace("ERROR", "");
					cg.displayErrorMessage(errorMessage); // Display error message.
				} else
					cg.append(msg); // Normal message
			} catch (IOException e) {
				break;
			} catch (ClassNotFoundException e2) {
				break;
			}
		}
	}

	public void closeSocket() {
		try {
			running = false;
			socket.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

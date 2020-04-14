package CRS.src.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	private ObjectInputStream socketIn; // to read from the socket
	private ObjectOutputStream socketOut; // to write on the socket
	private Socket socket;
	private ClientGUI cg;
	private String server;
	private String username;
	private Integer ID;
	private int port;

	public Client(String server, int port, String username, Integer ID, ClientGUI cg) {
		this.server = server;
		this.port = port;
		this.username = username;
		this.ID = ID;
		this.cg = cg;
	}

	public void communicateWithServer() {
		try {
			socket = new Socket(server, port);
			String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
			display(msg);
			socketIn = new ObjectInputStream(socket.getInputStream());
			socketOut = new ObjectOutputStream(socket.getOutputStream());
			new ListenFromServer().start();
			socketOut.writeObject(username);
			socketOut.writeObject(ID);
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
			display("Exception writing to server: " + e);
		}
	}

	class ListenFromServer extends Thread {

		public void run() {
			while (true) {
				try {
					String msg = (String) socketIn.readObject();
					cg.append(msg);
				} catch (IOException e) {
					display("ERROR: " + e);
					break;
				} catch (ClassNotFoundException e2) {
					break;
				}
			}
		}
	}
}

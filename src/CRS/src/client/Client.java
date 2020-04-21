package CRS.src.client;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

import CRS.src.shared.Command;

public class Client implements Runnable {
	/**
	 * socketIn to read message from server.
	 */
	private ObjectInputStream socketIn; // to read from the socket
	/**
	 * SocketOut to send response to server.
	 */
	private ObjectOutputStream socketOut; // to write on the socket
	/**
	 * socket is the connection between server and client.
	 */
	private Socket socket;
	/**
	 * cg is GUI representation of client.
	 */
	private ClientGUI cg;
	/**
	 * server is the name of the server.
	 */
	private String server;
	/**
	 * username is the name of the user.
	 */
	private String username;
	/**
	 * studentID is the id of the student.
	 */
	private Integer studentID;
	/**
	 * id is the unique id of the client.
	 */
	private Integer id;
	/**
	 * Port is the port the server is running on.
	 */
	private int port;
	/**
	 * Thread pool for client.
	 */
	private ExecutorService pool;
	/**
	 * Running indicates the states of clients.
	 */
	private boolean running;

	/**
	 * Constructor of Client that initializes member variables to given data and
	 * connects to server.
	 * 
	 * @param server   is the name of the server.
	 * @param port     is the port the server is running on.
	 * @param username is the name of the user.
	 * @param ID       is the ID of the user.
	 * @param cg       is the GUI representation of this client.
	 */
	public Client(String server, int port, ClientGUI cg) {
		try {
			this.server = server;
			this.port = port;
			socket = new Socket(this.server, this.port);
			socketIn = new ObjectInputStream(socket.getInputStream());
			socketOut = new ObjectOutputStream(socket.getOutputStream());
			this.id = Integer.parseInt((String) socketIn.readObject());
		} catch (NumberFormatException | ClassNotFoundException | IOException e) {
			cg.displayErrorMessage("Unable to connect to the server!");
			e.printStackTrace();
		}
		this.cg = cg;
		this.running = true;
		this.id = 0;
		pool = Executors.newCachedThreadPool();
	}

	/**
	 * Fetch the username
	 * 
	 * @return the username for the client.
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Set the username
	 * 
	 * @return the username for the client.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Fetch the username
	 * 
	 * @return the username for the client.
	 */
	public int getUserID() {
		return this.studentID;
	}

	/**
	 * Set the username
	 * 
	 * @return the username for the client.
	 */
	public void setUserID(Integer ID) {
		this.studentID = ID;
	}

	/**
	 * Establish communication user between client and server.
	 */
	public void communicateWithServer() {
		try {
			socketOut.writeObject(username);
			socketOut.writeObject(studentID);
			String msg = (String) socketIn.readObject();
			if (msg.contains("ERROR")) {
				String errorMessage = msg.replace("ERROR", "");
				cg.displayErrorMessage(errorMessage); // Display error message.
			} else {
				System.out.println("Connected with client id " + this.id);
				cg.doButtons();
				cg.append(msg);
				pool.execute(this);
			}
		} catch (IOException e) {
			cg.displayErrorMessage("Cannot connect to server. Program terminated.");
			System.exit(0);
		} catch (ClassNotFoundException e) {
			cg.displayErrorMessage("Cannot connect to server. Program terminated.");
			System.exit(0);
		}
	}

	/**
	 * Display the message in GUI.
	 * 
	 * @param msg is the message that needs to be displayed.
	 */
	public void display(String msg) {
		cg.append(msg + "\n");
	}

	/**
	 * Send the command to server.
	 * 
	 * @param msg is the command that is sent to the server.
	 */
	public void sendMessage(Command msg) {
		try {
			socketOut.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Receives response from the server.
	 */
	public void run() {
		while (running) {
			try {
				String msg = (String) socketIn.readObject();
//				System.out.println((String)msg);
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

	/**
	 * Close all the socket.
	 */
	public void closeSocket() {
		try {
			running = false;
			socket.close();
			socketIn.close();
			socketOut.close();
			pool.shutdown();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

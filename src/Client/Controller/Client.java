package Client.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private Socket aSocket;
	private BufferedReader stdIn; // For user input
	private BufferedReader socketIn;
	private PrintWriter socketOut;

	public Client(String serverName, int port) {
		try {
			aSocket = new Socket(serverName, port);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream(), true);

//				socketObjectIn = new ObjectInputStream(aSocket.getInputStream());
//				socketObjectOut = new ObjectOutputStream(aSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void communicateServer() {
		try {
			while (true) {
				String read = "";
				while (true) {
					read = socketIn.readLine();
					if (read.contains("\0")) {
						read = read.replace("\0", "");
						System.out.println(read);
						break;
					}

					if (read.equals("QUIT")) {
						return;
					}
					System.out.println(read);
				}
				read = stdIn.readLine();
				socketOut.println(read);
				socketOut.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {

				stdIn.close();
				socketIn.close();
				aSocket.close();
				socketOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Client aClient = new Client("localhost", 9098);
		aClient.communicateServer();
	}

}
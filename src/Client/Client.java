package Client;

import Server.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
	private PrintWriter socketOut;
	private Socket aSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;

	public Client(String serverName, int port) {
		try {
			aSocket = new Socket(serverName, port);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void communicateServer() {

	}

	public static void main(String[] args) {
		Client aClient = new Client("localhost", 9098);
		System.out.println("CLIENT RUNNING");
		aClient.communicateServer();
	}
}
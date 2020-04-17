package src.Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class adminGUI extends ClientGUI {
	private static final long serialVersionUID = 1L;

	public adminGUI(String host, int port) {
		super(host, port);
		this.adminMenu();
	}

	public void adminMenu() {
		textArea.setFont(new Font("Serif", Font.PLAIN, 14)); // Setting Font and Size for text Area

	}
}

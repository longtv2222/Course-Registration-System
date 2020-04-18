package src.Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import src.Server.Command;

public class adminGUI extends ClientGUI {
	private static final long serialVersionUID = 1L;

	private JButton createCourse = new JButton("Create new course");
	private JButton displayAll = new JButton("Display All Courses");
	private JButton signIn = new JButton("Sign in");
	private JButton deleteCourse = new JButton("Delete a course");

	public adminGUI(String host, int port) {
		super(host, port);
		this.adminMenu();
	}

	public void adminMenu() {
		textArea.setFont(new Font("Serif", Font.PLAIN, 14)); // Setting Font and Size for text Area
		createCourse.setEnabled(false);
		displayAll.setEnabled(false);
		deleteCourse.setEnabled(false);
		this.createCourseButton();
		this.displayAllButton();
		this.deleteCourseButton();
		this.signInButton();

		JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
		subPanel.add(createCourse);
		subPanel.add(displayAll);
		subPanel.add(deleteCourse);
		add(subPanel, BorderLayout.SOUTH);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JTextArea blankRow = new JTextArea("    ");
		scrollPane.setRowHeaderView(blankRow);
		textArea.setEditable(false);
		add(scrollPane, BorderLayout.CENTER);

		JPanel subPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
		subPanel1.add(new JLabel("USERNAME: "));
		subPanel1.add(userName);
		subPanel1.add(new JLabel("ID: "));
		subPanel1.add(ID);
		subPanel1.add(signIn);
		add(subPanel1, BorderLayout.NORTH);
		pack();
		setVisible(true);
	}

	private void displayAllButton() {
		displayAll.addActionListener((ActionEvent e) -> { // Adding action to displayALL
			client.sendMessage(new Command(Command.DISPLAY_ALL, " "));
		});
	}

	private void createCourseButton() { // To be implemeted
		createCourse.addActionListener((ActionEvent e) -> { // Adding action to displayALL
			client.sendMessage(new Command(Command.CREATE_NEW_COURSE, " "));
		});
	}

	private void deleteCourseButton() { // To be implemeted
		deleteCourse.addActionListener((ActionEvent e) -> { // Adding action to displayALL
			client.sendMessage(new Command(Command.DELETE_A_COURSE, " "));
		});
	}

	private void signInButton() {
		signIn.addActionListener((ActionEvent e) -> { // Done
			try {
				String name = userName.getText();
				String userID = ID.getText();
				signIn.setEnabled(false); // Disable sign in button
				userName.setEditable(false);
				ID.setEditable(false);
				client = new Client(host, port, name, Integer.parseInt(userID), this);
				client.communicateWithServer();
				System.out.println("Admin connected");
				createCourse.setEnabled(true);
				displayAll.setEnabled(true);
				deleteCourse.setEnabled(true);
			} catch (NumberFormatException error) {
				displayErrorMessage("ID must be a number. Please try again.");
				userName.setEditable(true);
				ID.setEditable(true);
				signIn.setEnabled(true);
			}
		});
	}
}

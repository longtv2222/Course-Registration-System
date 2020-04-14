package CRS.src.Client;

import javax.swing.*;

import CRS.src.Server.Command;

import java.awt.*;
import java.awt.event.ActionEvent;

public class ClientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton searchCourse = new JButton("Search Course");
	private JButton addCourse = new JButton("Add Course");
	private JButton removeCourse = new JButton("Remove Course");
	private JButton displayAll = new JButton("Display All Courses");
	private JButton courseInCart = new JButton("Course In Cart");
	private JButton signIn = new JButton("Sign in");
	private JTextField userName = new JTextField(10);
	private JTextField ID = new JTextField(10);
	private Client client;
	private String host;
	private int port;
	private JTextArea textArea = new JTextArea();

	public ClientGUI(String host, int port) {
		super("Main Window");
		searchCourse.setEnabled(false);
		addCourse.setEnabled(false);
		removeCourse.setEnabled(false);
		displayAll.setEnabled(false);
		courseInCart.setEnabled(false);
		this.host = host;
		this.port = port;
		this.menu();
		pack();
		this.addActionButtons();
		setVisible(true);
	}

	public ClientGUI() {
		this.menu();
		setVisible(true);
	}

	public void append(String str) {
		textArea.append(str);
		textArea.setCaretPosition(textArea.getText().length() - 1);
	}

	public void menu() {
		JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
		subPanel.add(searchCourse);
		subPanel.add(addCourse);
		subPanel.add(removeCourse);
		subPanel.add(displayAll);
		subPanel.add(courseInCart);
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

	}

	public void addActionButtons() {
		signIn.addActionListener((ActionEvent e) -> { // Done
			String name = userName.getText();
			String IDdd = ID.getText();
			// Disable sign in button
			signIn.setEnabled(false);
			userName.setEditable(false);
			ID.setEditable(false);
			client = new Client(host, port, name, Integer.parseInt(IDdd), this);
			client.communicateWithServer();
			System.out.println("Client connected");
			searchCourse.setEnabled(true);
			// Enable button
			addCourse.setEnabled(true);
			removeCourse.setEnabled(true);
			displayAll.setEnabled(true);
			courseInCart.setEnabled(true);
		});
		searchCourse.addActionListener((ActionEvent e) -> { // Adding action to searchCourse //Done
			JTextField courseName = new JTextField(10); // Create text field for name
			JTextField courseNumber = new JTextField(10); // Create text field for ID
			searchCourseDialogue(courseName, courseNumber);
			System.out.println(courseName);
			System.out.println(courseNumber);
			client.sendMessage(new Command(Command.SEARCH_COURSE,
					courseName.getText() + "SEARCH_COURSE" + courseNumber.getText()));
		});

		addCourse.addActionListener((ActionEvent e) -> { // Adding action to addCourse //
			JTextField courseName = new JTextField(10); // Create text field for name
			JTextField courseNumber = new JTextField(10); // Create text field for ID
			JTextField courseSection = new JTextField(10); // Create text field for course Section
			this.addCourseDialogue(courseName, courseNumber, courseSection);
			client.sendMessage(new Command(Command.ADD_COURSE, courseName.getText() + "ADD_COURSE"
					+ courseNumber.getText() + "ADD_COURSE" + courseSection.getText()));
		});

		removeCourse.addActionListener((ActionEvent e) -> { // Adding action to removeCourse
			client.sendMessage(new Command(Command.REMOVE_COURSE, " "));
		});
		displayAll.addActionListener((ActionEvent e) -> { // Adding action to displayALL
			client.sendMessage(new Command(Command.DISPLAY_ALL, " "));
		});
		courseInCart.addActionListener((ActionEvent e) -> { // Adding action to CourseInCart
			client.sendMessage(new Command(Command.COURSE_IN_CART, " "));
		});
	}

	public void searchCourseDialogue(JTextField courseName, JTextField courseNumber) { // Creating a dialog
																						// box for
		JPanel panel = new JPanel(new BorderLayout());

		JPanel subPanel = new JPanel(new BorderLayout()); // subPanel for Center
		subPanel.add(new JLabel("Course Name: "), BorderLayout.CENTER);
		subPanel.add(courseName, BorderLayout.SOUTH);

		JPanel subPanel2 = new JPanel(new BorderLayout()); // subPanel for South
		subPanel2.add(new JLabel("Course ID:"), BorderLayout.CENTER);
		subPanel2.add(courseNumber, BorderLayout.SOUTH);

		panel.add(subPanel, BorderLayout.CENTER);
		panel.add(subPanel2, BorderLayout.SOUTH);

		int result = JOptionPane.showConfirmDialog(null, panel, "Popup Window", JOptionPane.OK_CANCEL_OPTION);
		try {
			if (result == JOptionPane.CANCEL_OPTION) {
				return;
			} else if (result == JOptionPane.OK_OPTION) {
				Integer.parseInt(courseNumber.getText()); // Check if there any numberformat exception, if there is, it
															// is gonna be catched and the user need to enter the input
															// again.

			}
		} catch (NumberFormatException e) {
			displayErrorMessage("ID must be a number. Please try again!");
			this.searchCourseDialogue(courseName, courseNumber);
		}
	}

	public void addCourseDialogue(JTextField courseName, JTextField courseNumber, JTextField courseSection) {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel subPanel = new JPanel(new BorderLayout()); // subPanel for Center
		subPanel.add(new JLabel("Course Name: "), BorderLayout.CENTER);
		subPanel.add(courseName, BorderLayout.SOUTH);

		JPanel subPanel2 = new JPanel(new BorderLayout()); // subPanel for South
		subPanel2.add(new JLabel("Course ID:"), BorderLayout.CENTER);
		subPanel2.add(courseNumber, BorderLayout.SOUTH);

		JPanel subPanel3 = new JPanel(new BorderLayout()); // subPanel for South
		subPanel3.add(new JLabel("Course Section:"), BorderLayout.CENTER);
		subPanel3.add(courseSection, BorderLayout.SOUTH);

		panel.add(subPanel, BorderLayout.NORTH);
		panel.add(subPanel2, BorderLayout.CENTER);
		panel.add(subPanel3, BorderLayout.SOUTH);
		int result = JOptionPane.showConfirmDialog(null, panel, "Popup Window", JOptionPane.OK_CANCEL_OPTION);
		try {
			if (result == JOptionPane.CANCEL_OPTION) {
				return;
			} else if (result == JOptionPane.OK_OPTION) {
				Integer.parseInt(courseNumber.getText()); // Check if there any numberformat exception, if there is, it
															// is gonna be catched and the user need to enter the input
															// again.
				Integer.parseInt(courseSection.getText());

			}
		} catch (NumberFormatException e) {
			displayErrorMessage("ID must be a number. Please try again!");
			this.addCourseDialogue(courseName, courseNumber, courseSection);
		}
	}

	private void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error Message", JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) {
		ClientGUI a = new ClientGUI("localhost", 9098);
	}

}

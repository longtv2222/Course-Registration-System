package crs.src.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class StudentGUI extends ClientGUI {
	private static final long serialVersionUID = 1L;
	private JButton searchCourse = new JButton("Search Course");
	private JButton addCourse = new JButton("Add Course");
	private JButton removeCourse = new JButton("Remove Course");
	private JButton displayAll = new JButton("Display All Courses");
	private JButton courseInCart = new JButton("Course In Cart");
	private JButton signIn = new JButton("Sign in");

	public StudentGUI(String host, int port) {
		super(host, port);
		this.studentMenu();
	}

	public void studentMenu() { // Creating a menu
		textArea.setFont(new Font("Serif", Font.PLAIN, 14)); // Setting Font and Size for text Area
		searchCourse.setEnabled(false); // Disable buttons
		addCourse.setEnabled(false);
		removeCourse.setEnabled(false);
		displayAll.setEnabled(false);
		courseInCart.setEnabled(false);
		this.searchCourseButton(); // Adding action to buttons
		this.addCourseButton();
		this.removeCourseButton();
		this.displayAllButton();
		this.courseInCartButton();
		this.signInButton();
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
		pack();
		setVisible(true);
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
				System.out.println("Student connected");
				searchCourse.setEnabled(true);
				// Enable button
				addCourse.setEnabled(true);
				removeCourse.setEnabled(true);
				displayAll.setEnabled(true);
				courseInCart.setEnabled(true);
			} catch (NumberFormatException error) {
				displayErrorMessage("ID must be a number. Please try again.");
				userName.setEditable(true);
				ID.setEditable(true);
				signIn.setEnabled(true);
			}
		});

	}

	private void searchCourseButton() {
		searchCourse.addActionListener((ActionEvent e) -> { // Adding action to searchCourse //Done
			JTextField courseName = new JTextField(10); // Create text field for name
			JTextField courseNumber = new JTextField(10); // Create text field for ID
			searchCourseDialogue(courseName, courseNumber);
			if (courseName.getText().equals("") || courseNumber.getText().equals(""))
				return;
			client.sendMessage(new Command(Command.SEARCH_COURSE,
					courseName.getText() + "SEARCH_COURSE" + courseNumber.getText()));
		});
	}

	private void addCourseButton() {
		addCourse.addActionListener((ActionEvent e) -> { // Adding action to addCourse //
			JTextField courseName = new JTextField(10); // Create text field for name
			JTextField courseNumber = new JTextField(10); // Create text field for ID
			JTextField courseSection = new JTextField(10); // Create text field for course Section
			this.courseDialogue(courseName, courseNumber, courseSection);
			if (courseName.getText().equals("") || courseNumber.getText().equals("")
					|| courseSection.getText().equals("")) // This line prevents user sending null string
				return;
			client.sendMessage(new Command(Command.ADD_COURSE, courseName.getText() + "ADD_COURSE"
					+ courseNumber.getText() + "ADD_COURSE" + courseSection.getText()));
		});
	}

	private void removeCourseButton() {
		removeCourse.addActionListener((ActionEvent e) -> { // Adding action to removeCourse
			JTextField courseName = new JTextField(10); // Create text field for name
			JTextField courseNumber = new JTextField(10); // Create text field for ID
			JTextField courseSection = new JTextField(10); // Create text field for course Section
			this.courseDialogue(courseName, courseNumber, courseSection);
			if (courseName.getText().equals("") || courseNumber.getText().equals("")
					|| courseSection.getText().equals("")) // This line prevents user sending null string
				return;
			client.sendMessage(new Command(Command.REMOVE_COURSE, courseName.getText() + "REMOVE_COURSE"
					+ courseNumber.getText() + "REMOVE_COURSE" + courseSection.getText()));
		});
	}

	private void displayAllButton() {
		displayAll.addActionListener((ActionEvent e) -> { // Adding action to displayALL
			client.sendMessage(new Command(Command.DISPLAY_ALL, " "));
		});
	}

	private void courseInCartButton() {
		courseInCart.addActionListener((ActionEvent e) -> { // Adding action to CourseInCart
			client.sendMessage(new Command(Command.COURSE_IN_CART, " "));
		});
	}

}

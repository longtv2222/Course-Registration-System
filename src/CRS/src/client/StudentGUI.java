package CRS.src.client;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import CRS.src.shared.Command;

public class StudentGUI extends ClientGUI {
	private static final long serialVersionUID = 1L;
	/**
	 * Button to search a course.
	 */
	private JButton searchCourse = new JButton("Search Course");
	/**
	 * Button to add a course.
	 */
	private JButton addCourse = new JButton("Add Course");
	/**
	 * Button to remove a course.
	 */
	private JButton removeCourse = new JButton("Remove Course");
	/**
	 * Button to display all courses.
	 */
	private JButton displayAll = new JButton("Display All Courses");
	/**
	 * Button to display all courses in cart.
	 */
	private JButton courseInCart = new JButton("Course In Cart");
	/**
	 * Button to sign in.
	 */
	private JButton signIn = new JButton("Sign in");

	/**
	 * StudentGUI that initalizes variables with given data.
	 * 
	 * @param host is the name of the host.
	 * @param port is the port the host is running on.
	 */
	public StudentGUI(String host, int port) {
		super(host, port);
		this.studentMenu();
	}

	/**
	 * Display student's menu.
	 */
	public void studentMenu() { // Creating a menu
		client = new Client(host, port, this);
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
		subPanel.add(exit);
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

	/**
	 * Adding action to sign In button.
	 */
	private void signInButton() {
		signIn.addActionListener((ActionEvent e) -> { // Done
			try {
				this.client.setUsername(userName.getText());
				this.client.setUserID(Integer.parseInt(ID.getText()));

				client.communicateWithServer();
			} catch (NumberFormatException error) {
				displayErrorMessage("ID must be a number. Please try again.");
			}
		});

	}

	/**
	 * Adding action to search Course button.
	 */
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

	/**
	 * Adding action to add course button.
	 */
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

	/**
	 * Adding action to remove course button.
	 */
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

	/**
	 * Adding action to display all courses button.
	 */
	private void displayAllButton() {
		displayAll.addActionListener((ActionEvent e) -> { // Adding action to displayALL
			client.sendMessage(new Command(Command.DISPLAY_ALL, " "));
		});
	}

	/**
	 * Adding action to courses in cart button.
	 */
	private void courseInCartButton() {
		courseInCart.addActionListener((ActionEvent e) -> { // Adding action to CourseInCart
			client.sendMessage(new Command(Command.COURSE_IN_CART, " "));
		});
	}

	/**
	 * Enable and disable buttons on the client.
	 */
	@Override
	public void doButtons() {
		signIn.setEnabled(false); // Disable sign in button
		userName.setEditable(false);
		ID.setEditable(false);
		searchCourse.setEnabled(true); // Enable client usage buttons
		addCourse.setEnabled(true);
		removeCourse.setEnabled(true);
		displayAll.setEnabled(true);
		courseInCart.setEnabled(true);
	}
	
}

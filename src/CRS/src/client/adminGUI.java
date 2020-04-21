package CRS.src.client;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import CRS.src.shared.Command;

public class adminGUI extends ClientGUI {
	private static final long serialVersionUID = 1L;
	/**
	 * Sign in button for admin.
	 */
	private JButton signIn = new JButton("Sign in");
	/**
	 * add new course button for admin.
	 */
	private JButton addNewCourse = new JButton("Add new Course");
	/**
	 * Display All courses button for admin.
	 */
	private JButton displayAll = new JButton("Display All Courses");
	/**
	 * Search student by ID button for admin.
	 */
	private JButton searchStudentID = new JButton("Search Student By ID");
	/**
	 * Display all student in system for admin.
	 */
	private JButton displayAllStudent = new JButton("Display All Student");
	/**
	 * Add a new lecture button for admin.
	 */
	private JButton addNewLecture = new JButton("Add new lecture");
	/**
	 * 
	 * Add a new student button for admin.
	 */
	private JButton addNewStudent = new JButton("Add new student");

	/**
	 * Constructor of adminGUI that intializes variables with given data.
	 * 
	 * @param host is the server that this admin connects to.
	 * @param port is the port that server is running on.
	 */
	public adminGUI(String host, int port) {
		super(host, port);
		this.adminMenu();
	}

	/**
	 * Display GUI for admin.
	 */
	public void adminMenu() {
		textArea.setFont(new Font("Serif", Font.PLAIN, 14)); // Setting Font and Size for text Area
		buttonEnable(false); // Disable all buttons.
		this.addNewCourseButton(); // Adding actions to button.
		this.displayAllButton();
		this.signInButton();
		this.searchStudentIDButton();
		this.addNewLectureButton();
		this.addNewStudentButton();
		this.viewAllStudentButton();

		JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));

		subPanel.add(displayAll);
		subPanel.add(displayAllStudent);
		subPanel.add(searchStudentID);
		subPanel.add(addNewCourse);
		subPanel.add(addNewLecture);
		subPanel.add(addNewStudent);
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
	 * Adding action to view all student button.
	 */
	private void viewAllStudentButton() {
		displayAllStudent.addActionListener((ActionEvent e) -> {
			client.sendMessage(new Command(Command.VIEW_ALL_STUDENT, " "));
		});
	}

	/**
	 * Adding action to display all button.
	 */
	private void displayAllButton() {
		displayAll.addActionListener((ActionEvent e) -> { // Adding action to displayALL
			client.sendMessage(new Command(Command.DISPLAY_ALL, " "));
		});
	}

	/**
	 * Adding action to add a new course button.
	 */
	private void addNewCourseButton() {
		addNewCourse.addActionListener((ActionEvent e) -> {
			JTextField courseName = new JTextField(10), courseNum = new JTextField(10);
			this.searchCourseDialogue(courseName, courseNum);
			if (courseName.getText().equals("") || courseNum.getText().equals(""))
				return;
			client.sendMessage(
					new Command(Command.ADD_NEW_COURSE, courseName.getText() + "ADD_NEW_COURSE" + courseNum.getText()));
		});
	}

	/**
	 * Adding action to search student by ID button.
	 */
	private void searchStudentIDButton() {
		searchStudentID.addActionListener((ActionEvent e) -> {
			JTextField id = new JTextField(10);
			searchStudentIdDialogue(id);
			if (id.getText().contentEquals(""))
				return;
			client.sendMessage(new Command(Command.SEARCH_STUDENT_ID, id.getText()));
		});
	}

	/**
	 * Adding action to add new lecture button.
	 */
	private void addNewLectureButton() {
		addNewLecture.addActionListener((ActionEvent e) -> { // Adding action to displayALL
			JTextField courseName = new JTextField(10), courseNum = new JTextField(10),
					courseSection = new JTextField(10), courseCap = new JTextField(10);
			this.lectureDialogue(courseName, courseNum, courseSection, courseCap);
			if (courseName.getText().equals("") || courseNum.getText().equals("") || courseSection.getText().equals("")
					|| courseNum.getText().equals(""))
				return;
			client.sendMessage(new Command(Command.ADD_NEW_LECTURE,
					courseName.getText() + "ADD_NEW_LECTURE" + courseNum.getText() + "ADD_NEW_LECTURE"
							+ courseSection.getText() + "ADD_NEW_LECTURE" + courseCap.getText() + "ADD_NEW_LECTURE"));
		});
	}

	/**
	 * Adding action to add new student button.
	 */
	private void addNewStudentButton() {
		addNewStudent.addActionListener((ActionEvent e) -> { // Adding action to displayALL
			JTextField studentName = new JTextField(10), studentID = new JTextField(10);
			this.studentDialogue(studentName, studentID);
			if (studentName.getText().equals("") || studentID.getText().equals(""))
				return;
			client.sendMessage(new Command(Command.ADD_NEW_STUDENT,
					studentName.getText() + "ADD_NEW_STUDENT" + studentID.getText()));
		});
	}

	/**
	 * Disable or enable button.
	 * 
	 * @param bool is the state of the button. True is enabled, false is disabled.
	 */
	private void buttonEnable(boolean bool) {
		addNewCourse.setEnabled(bool);
		displayAll.setEnabled(bool);
		searchStudentID.setEnabled(bool);
		displayAllStudent.setEnabled(bool);
		addNewLecture.setEnabled(bool);
		addNewStudent.setEnabled(bool);
	}

	/**
	 * Adding action to sign in button.
	 */
	private void signInButton() {
		signIn.addActionListener((ActionEvent e) -> {
			try {
				String name = userName.getText();
				String id = ID.getText();
				client = new Client(host, port, this);
				client.setUsername(name);
				client.setUserID(Integer.parseInt(id));
				client.communicateWithServer();
				System.out.println("Admin connected");
			} catch (NumberFormatException error) {
				displayErrorMessage("ID must be a number. Please try again.");
				userName.setEditable(true);
				ID.setEditable(true);
				signIn.setEnabled(true);
			}
		});
	}
	
	@Override
	public void doButtons() {
		signIn.setEnabled(false);
		userName.setEditable(false);
		ID.setEditable(false);
		buttonEnable(true);
	}
}

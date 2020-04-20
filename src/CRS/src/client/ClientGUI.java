package CRS.src.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class ClientGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	/**
	 * Host is the name of the server this client connects to.
	 */
	protected String host;
	/**
	 * Port is the port that the server that is running on.
	 */
	protected int port;
	/**
	 * client is the client of this GUI.
	 */
	protected Client client;
	/**
	 * textArea field.
	 */
	protected JTextArea textArea = new JTextArea(20, 10);
	/**
	 * serverAddress field.
	 */	
	protected JTextField serverAddress = new JTextField("localhost",20);
	/**
	 * serverPort field.
	 */
	protected JTextField serverPort = new JTextField("9098",6);
	/**
	 * userName field.
	 */
	protected JTextField userName = new JTextField(10);
	/**
	 * ID field.
	 */
	protected JTextField ID = new JTextField(10);
	/**
	 * exit is a button to exit the program.
	 */
	protected JButton exit = new JButton("exit");

	/**
	 * Constructor of ClientGUI that initializes variables with given data.
	 * 
	 * @param host is the name of the client.
	 * @param port is the port the client is running on.
	 */
	public ClientGUI(String host, int port) {
		super("Main Window");
		this.host = host;
		this.port = port;
		exitButton();
	}

	/**
	 * Adding action to exit button.
	 */
	private void exitButton() {
		exit.addActionListener((ActionEvent e) -> { // Adding action to displayALL
			System.exit(0);
		});
	}

	/**
	 * Display admin or user dialogue.
	 */
	public void adminUserDialogue() {
		JFrame frame = new JFrame();
		JPanel serverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
		serverPanel.add(new JLabel("SERVER ADDRESS: "));
		serverPanel.add(serverAddress);
		serverPanel.add(new JLabel("SERVER PORT: "));
		serverPanel.add(serverPort);
		String serverPrompt = "Please enter the server address: ";
		while(true) {
			try {
				JOptionPane.showConfirmDialog(frame, serverPanel, serverPrompt,JOptionPane.OK_CANCEL_OPTION);
				this.port = Integer.parseInt(serverPort.getText());
				this.host = serverAddress.getText();
			} catch( NumberFormatException e) {
				serverPrompt = "Please enter a valid server address: ";
				continue;
			}
			if(this.port > 65535 || port < 0) {
				serverPrompt = "Please enter a valid port: ";
			}
			break;
		}
//		System.out.println(serverAddress.getText()+" "+serverPort.getText());
		String[] options = new String[2];
		options[0] = new String("ADMIN");
		options[1] = new String("STUDENT");
		int option = JOptionPane.showOptionDialog(frame, "Please declare who you are.", "Popup Window",
				0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
		if (option == JOptionPane.YES_OPTION) // Yes option is admin.
			new adminGUI(host, port);
		if (option == JOptionPane.NO_OPTION) // No option is student.
			new StudentGUI(host, port);
	}

	/**
	 * Display error message.
	 * 
	 * @param message is the message that needs to be displayed.
	 */
	public void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error Message", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Setting the text area to a string.
	 * 
	 * @param str is the content that needs to be displayed in text Area.
	 */
	public void append(String str) { // Make a string to display on textArea
		textArea.setText("");
		textArea.append(str);
		textArea.setCaretPosition(textArea.getText().length() - 1);
	}

	/**
	 * A dialog that asks for courseName and courseNumber input from user.
	 * 
	 * @param courseName   is the name of the course.
	 * @param courseNumber is the number of the course.
	 */
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
		if (result == JOptionPane.CANCEL_OPTION) {
			courseName.setText("");
			courseNumber.setText("");
			return;
		} else if (result == JOptionPane.OK_OPTION) {
			if (!courseNumber.getText().matches("[0-9]+")) {
				displayErrorMessage("Course Number must contain number only.");
				this.searchCourseDialogue(courseName, courseNumber);
			}

			if (courseName.getText().length() != 4 || courseNumber.getText().length() != 3) {
				displayErrorMessage("Course name must have 4 character and course Number must have 3 character");
				this.searchCourseDialogue(courseName, courseNumber);
			}
		}
	}

	/**
	 * A dialog that asks for courseName, courseNumber and courseSection.
	 * 
	 * @param courseName    is the name of the course.
	 * @param courseNumber  is the course Number of the course.
	 * @param courseSection is the section of the course.
	 */
	public void courseDialogue(JTextField courseName, JTextField courseNumber, JTextField courseSection) {
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

		if (result == JOptionPane.CANCEL_OPTION) {
			courseName.setText("");
			courseNumber.setText("");
			courseSection.setText("");
			return;
		} else if (result == JOptionPane.OK_OPTION) {
			if (!courseNumber.getText().matches("[0-9]+") || !courseSection.getText().matches("[0-9]+")) {
				displayErrorMessage("Course Number and course Section must contain number only.");
				this.courseDialogue(courseName, courseNumber, courseSection);
			}
			if (courseName.getText().length() != 4 || courseNumber.getText().length() != 3) {
				displayErrorMessage("Course name must have 4 character and course Number must have 3 character");
				this.courseDialogue(courseName, courseNumber, courseSection);
			}
		}
	}

	/**
	 * A dialog that ask for student's ID.
	 * 
	 * @param id is the ID of student.
	 */
	public void searchStudentIdDialogue(JTextField id) {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel subPanel = new JPanel(new BorderLayout()); // subPanel for Center
		subPanel.add(new JLabel("ID: "), BorderLayout.CENTER);
		subPanel.add(id, BorderLayout.SOUTH);
		panel.add(subPanel, BorderLayout.CENTER);
		int result = JOptionPane.showConfirmDialog(null, panel, "Popup Window", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.CANCEL_OPTION) {
			id.setText("");
			return;
		} else if (result == JOptionPane.OK_OPTION) {
			if (!id.getText().matches("[0-9]+")) {
				this.searchStudentIdDialogue(id);
				displayErrorMessage("ID must be a number. Please try again!");
			}
		}
	}

	/**
	 * A dialog that asks for course name, course number, course section and section
	 * capacity of a course.
	 * 
	 * @param courseName    is the name of the course.
	 * @param courseNumber  is the number of the course.
	 * @param courseSection is the section of the course.
	 * @param courseCap     is the capacity of a section of the course.
	 */
	public void lectureDialogue(JTextField courseName, JTextField courseNumber, JTextField courseSection,
			JTextField courseCap) {
		JPanel panel = new JPanel(new FlowLayout());

		panel.add(new JLabel("Course Name: "));
		panel.add(courseName);

		panel.add(new JLabel("Course ID:"));
		panel.add(courseNumber);

		panel.add(new JLabel("Course Section:"));
		panel.add(courseSection);

		panel.add(new JLabel("Course Capacity:"));
		panel.add(courseCap);

		int result = JOptionPane.showConfirmDialog(null, panel, "Popup Window", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.CANCEL_OPTION) {
			courseName.setText("");
			courseNumber.setText("");
			courseSection.setText("");
			courseCap.setText("");
			return;
		} else if (result == JOptionPane.OK_OPTION) {
			if (!courseNumber.getText().matches("[0-9]+") || !courseSection.getText().matches("[0-9]+")
					|| !courseCap.getText().matches("[0-9]+")) {
				displayErrorMessage("One of your input was invalid. Please try again.");
				this.lectureDialogue(courseName, courseNumber, courseSection, courseCap);
			}
			if (courseName.getText().length() != 4 || courseNumber.getText().length() != 3) {
				displayErrorMessage("Course name must have 4 character and course Number must have 3 character");
				this.lectureDialogue(courseName, courseNumber, courseSection, courseCap);
			}
		}
	}

	/**
	 * A dialog box that asks for student's name and ID.
	 * 
	 * @param studentName is the name of the student.
	 * @param studentID   is the ID of the student.
	 */
	public void studentDialogue(JTextField studentName, JTextField studentID) {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel subPanel = new JPanel(new BorderLayout()); // subPanel for Center
		subPanel.add(new JLabel("Student Name: "), BorderLayout.CENTER);
		subPanel.add(studentName, BorderLayout.SOUTH);

		JPanel subPanel2 = new JPanel(new BorderLayout()); // subPanel for South
		subPanel2.add(new JLabel("Student ID:"), BorderLayout.CENTER);
		subPanel2.add(studentID, BorderLayout.SOUTH);

		panel.add(subPanel, BorderLayout.CENTER);
		panel.add(subPanel2, BorderLayout.SOUTH);

		int result = JOptionPane.showConfirmDialog(null, panel, "Popup Window", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.CANCEL_OPTION) {
			studentName.setText("");
			studentID.setText("");
			return;
		} else if (result == JOptionPane.OK_OPTION) {
			if (!studentID.getText().matches("[0-9]+")) {
				displayErrorMessage("One of your input was invalid. Please try again.");
				this.studentDialogue(studentName, studentID);
			}
		}

	}

	public static void main(String[] args) {
		ClientGUI client = new ClientGUI("localhost", 9098);
		client.adminUserDialogue();
	}
}

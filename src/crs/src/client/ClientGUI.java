package crs.src.client;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class ClientGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	protected String host;
	protected int port;
	protected JTextArea textArea = new JTextArea(5, 10);
	protected Client client;
	protected JTextField userName = new JTextField(10);
	protected JTextField ID = new JTextField(10);

	public ClientGUI(String host, int port) {
		super("Main Window");
		this.host = host;
		this.port = port;
	}

	public void adminUserDialogue() {
		JFrame frame = new JFrame();
		String[] options = new String[2];
		options[0] = new String("ADMIN");
		options[1] = new String("STUDENT");
		int option = JOptionPane.showOptionDialog(frame.getContentPane(), "Please declare who you are.", "Popup Window",
				0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
		if (option == JOptionPane.YES_OPTION) // Yes option is admin.
			new adminGUI(host, port);
		if (option == JOptionPane.CANCEL_OPTION)
			new StudentGUI(host, port);
	}

	public void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error Message", JOptionPane.ERROR_MESSAGE);
	}

	public void append(String str) { // Make a string to display on textArea
		textArea.setText("");
		textArea.append(str);
		textArea.setCaretPosition(textArea.getText().length() - 1);
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
				courseName.setText("");
				courseNumber.setText("");
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
		try {
			if (result == JOptionPane.CANCEL_OPTION) {
				courseName.setText("");
				courseNumber.setText("");
				courseSection.setText("");
				return;
			} else if (result == JOptionPane.OK_OPTION) {
				Integer.parseInt(courseNumber.getText()); // Check if there any numberformat exception, if there is, it
// is gonna be catched and the user need to enter the input
// again.
				Integer.parseInt(courseSection.getText());

			}
		} catch (NumberFormatException e) {
			displayErrorMessage("ID must be a number. Please try again!");
			this.courseDialogue(courseName, courseNumber, courseSection);
		}
	}

	public static void main(String[] args) {
		ClientGUI client = new ClientGUI("localhost", 9098);
		client.adminUserDialogue();
	}

}

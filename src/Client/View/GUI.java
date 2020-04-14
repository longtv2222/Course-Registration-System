package Client.View;

import Server.Model.User;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
	private static final long serialVersionUID = 7916278598796795258L;
	private JButton searchCourse = new JButton("Search Course");
	private JButton addCourse = new JButton("Add Course");
	private JButton removeCourse = new JButton("Remove Course");
	private JButton displayAll = new JButton("Display All Courses");
	private JButton courseInCart = new JButton("Course In Cart");

	public GUI() {
		super("Main Window");
		setVisible(true);
		super.setLayout(new BorderLayout()); // Creating borderLayout for this frame
		super.setPreferredSize(new Dimension(800, 800));
	}

	public void menu(User a) {
		setVisible(true);
		JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
		subPanel.add(searchCourse);
		subPanel.add(addCourse);
		subPanel.add(removeCourse);
		subPanel.add(displayAll);
		subPanel.add(courseInCart);
		add(subPanel, BorderLayout.SOUTH);

		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JTextArea blankRow = new JTextArea("    ");
		scrollPane.setRowHeaderView(blankRow);
		textArea.setEditable(false);
		add(scrollPane, BorderLayout.CENTER);

		add(new JLabel("<html><br/>Course Registration System<br/><br/>", SwingConstants.CENTER), BorderLayout.NORTH);
	}

	public void askNameID(User user) { // Creating a dialog box for
		JTextField nameField = new JTextField(10); // Create text field for name
		JTextField IDField = new JTextField(10); // Create text field for ID

		JPanel panel = new JPanel(new BorderLayout());

		JPanel subPanel = new JPanel(new BorderLayout()); // subPanel for Center
		subPanel.add(new JLabel("Name: "), BorderLayout.CENTER);
		subPanel.add(nameField, BorderLayout.SOUTH);

		JPanel subPanel2 = new JPanel(new BorderLayout()); // subPanel for South
		subPanel2.add(new JLabel("ID:"), BorderLayout.CENTER);
		subPanel2.add(IDField, BorderLayout.SOUTH);

		panel.add(subPanel, BorderLayout.CENTER);
		panel.add(subPanel2, BorderLayout.SOUTH);

		int result = JOptionPane.showConfirmDialog(null, panel, "Popup Window", JOptionPane.OK_CANCEL_OPTION);
		try {
			if (result == JOptionPane.CANCEL_OPTION) {
				return;
			} else if (result == JOptionPane.OK_OPTION) {
				user.setName(nameField.getText());
				user.setID(Integer.parseInt(IDField.getText()));
			}
		} catch (NumberFormatException e) {
			displayErrorMessage("ID must be a number. Please try again!");
			this.askNameID(user);
		}
	}

	private void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error Message", JOptionPane.ERROR_MESSAGE);
	}

}

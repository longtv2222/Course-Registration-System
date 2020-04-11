package Client;

import Server.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
	private User user;
	private RegistrationApp app;

	public GUI(User user, RegistrationApp app) {
		this.user = user;
		this.app = app;
	}

	public void start() {
		this.askNameID();
		app.startup(user.getName(), user.getID());
	}

	public void askNameID() { // Creating a dialog box for
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
		setVisible(true);
		int result = JOptionPane.showConfirmDialog(null, panel, "Popup Window", JOptionPane.OK_CANCEL_OPTION);
		try {
			if (result == JOptionPane.CANCEL_OPTION) {
				return;
			} else if (result == JOptionPane.OK_OPTION) {
				this.user.setName(nameField.getText());
				int ID = Integer.parseInt(IDField.getText());
				this.user.setID(ID);
			}
		} catch (NumberFormatException e) {
			displayErrorMessage("ID must be a number. Please try again!");
			this.askNameID();
		}
		setVisible(false);
	}

	private void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error Message", JOptionPane.ERROR_MESSAGE);
	}
}

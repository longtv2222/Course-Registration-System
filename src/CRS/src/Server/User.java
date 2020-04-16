package CRS.src.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class User extends Thread {
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	private Socket socket;
	private String username;
	private Integer ID;
	private Command cm;
	private ArrayList<User> clients;
	private Student userStudent;

	public User(Socket socket, ArrayList<User> clients) {
		try {
			this.socket = socket;
			socketOut = new ObjectOutputStream(socket.getOutputStream());
			socketIn = new ObjectInputStream(socket.getInputStream());
			username = (String) socketIn.readObject(); // Read username how??
			ID = (Integer) socketIn.readObject(); // Read ID how??
			this.clients = clients;
			userStudent = new Student(username, ID); // Assume the fact that this user is a student.
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		boolean keepGoing = true;
		while (keepGoing) {
			try {
				cm = (Command) socketIn.readObject();
				this.decodeCommand(cm); // Decode the type of message and call appropriate function
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} catch (ClassNotFoundException e2) {
				break;
			}
		}
		clients.remove(this); // Remove yourself out of user list and close all sockets.
		try {
			socket.close();
			socketOut.close();
			socketIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeMsg(String msg) {
		try {
			socketOut.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void decodeCommand(Command cm) {
		switch (cm.getType()) {
		case Command.SEARCH_COURSE:
			searchCourse(cm.getMessage());
			break;
		case Command.ADD_COURSE:
			addCourse(cm.getMessage());
			break;
		case Command.REMOVE_COURSE:
			removeCourse(cm.getMessage());
			break;
		case Command.DISPLAY_ALL:
			displayAll(cm.getMessage());
			break;
		case Command.COURSE_IN_CART:
			courseInCart(cm.getMessage());
			break;
		}
	}

	private void courseInCart(String message) { // To be implemented

	}

	private void displayAll(String message) {
		writeMsg(new CourseCatalogue().toString()); // Read data from the database through couseCatalogue
	}

	private void removeCourse(String message) { // To be implemented

	}

	private void addCourse(String message) {
		String[] addCourseData = message.replace("ADD_COURSE", " ").split(" ");
		System.out.println(addCourseData);
		Course searchedCourse = new CourseCatalogue().searchCat(addCourseData[0], Integer.parseInt(addCourseData[1]));
		if (searchedCourse != null) { // addCourseData[0] is course name , 1 is course number, 2 is course
										// section
			// I somehow cannot manage to register for student
			// Null Pointer exception smh

			Registration registration = new Registration();
			registration.completeRegistration(userStudent,
					searchedCourse.getCourseOfferingSection(Integer.parseInt(addCourseData[2])));

			writeMsg("Add course successfully");
		}
	}

	private void searchCourse(String message) {
		String[] data = message.split("SEARCH_COURSE"); // Data for search course is seperated by a space
		String search = new CourseCatalogue().searchCatString(data[0], Integer.parseInt(data[1]));
		if (search != null)
			writeMsg(search.toString());
		else
			writeMsg("Course not found!");
	}

	public String getUsername() {
		return this.username;
	}

	public Integer getID() {
		return this.ID;
	}

	public ObjectInputStream getSocketIn() {
		return this.socketIn;
	}

	public ObjectOutputStream getSocketOut() {
		return this.socketOut;
	}

	public Socket getSocket() {
		return this.socket;
	}
}

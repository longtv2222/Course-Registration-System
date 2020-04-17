package src.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class User implements Runnable {
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	private Socket socket;
	private String username;
	private Integer ID;
	private ArrayList<User> clients;
	private CourseCatalogue courseCat; // Agreggation relationship with courseCat.
	private Student st; // Assuming that the user is student. Might make another 2 classes named student
						// and admin that inherit from user.

	public User(Socket socket, ArrayList<User> clients, CourseCatalogue courseCat) {
		try {
			this.socket = socket;
			socketOut = new ObjectOutputStream(socket.getOutputStream());
			socketIn = new ObjectInputStream(socket.getInputStream());
			username = (String) socketIn.readObject(); // Read username
			ID = (Integer) socketIn.readObject(); // Read ID
			this.clients = clients;
			this.courseCat = courseCat;
			st = new Student(username, ID);
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
				Command cm = (Command) socketIn.readObject();
				this.decodeCommand(cm); // Decode the type of message and call appropriate function
			} catch (IOException e) {
				keepGoing = false;
				System.out.println(username + " has disconnected");
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

	public void writeErrorMsg(String msg) {
		try {
			socketOut.writeObject(msg + "ERROR");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void decodeCommand(Command cm) {
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

	private void courseInCart(String message) {
		if (st.getStudentRegList().size() == 0)
			writeErrorMsg("You have no course in your cart rightnow.");
		else
			writeMsg(st.listRegistered());
	}

	private void displayAll(String message) {
		writeMsg(courseCat.toString()); // Read data from the database through couseCatalogue
	}

	private void removeCourse(String message) { // To be implemented
		String[] removeCourseData = message.replace("REMOVE_COURSE", " ").split(" ");
		// removeCourseData[0] is course name , 1 is course number, 2 is course SECTION
		Course searchedCourse = courseCat.searchCat(removeCourseData[0], Integer.parseInt(removeCourseData[1]));
		if (searchedCourse == null) {
			writeErrorMsg("The course you want to remove does not exist in the system.");
		} else {
			CourseOffering course = searchedCourse.getCourseOfferingSection(Integer.parseInt(removeCourseData[2]));
			Registration reg = st.findRegistration(course);
			if (reg != null) {
				writeMsg("You have been removed from the selected course offering.");
				reg.removeRegistration();
			} else {
				writeErrorMsg(
						"You are not in" + removeCourseData[0] + " " + removeCourseData[1] + " " + removeCourseData[2]);
			}
		}
	}

	private void addCourse(String message) {
		try {
			String[] addCourseData = message.split("ADD_COURSE");
			// addCourseData[0] is course name , 1 is course number, 2 is course SECTION
			Course searchedCourse = courseCat.searchCat(addCourseData[0], Integer.parseInt(addCourseData[1]));
			if (searchedCourse.equals(null)) {
				writeErrorMsg("The course you want to add does not exist!");
			} else {
				Registration registration = new Registration();
				writeMsg(registration.completeRegistration(st,
						searchedCourse.getCourseOfferingSection(Integer.parseInt(addCourseData[2]))));
			}
		} catch (NullPointerException e) {
			writeErrorMsg("The course you want to add does not exist!");
		}
	}

	private void searchCourse(String message) {
		try {
			String[] data = message.split("SEARCH_COURSE"); // Data for search course is seperated by a space
			Course search = courseCat.searchCat(data[0], Integer.parseInt(data[1]));
			writeMsg(search.toString());
		} catch (NullPointerException e) {
			writeErrorMsg("The course you searched for does not exist.");
		}
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

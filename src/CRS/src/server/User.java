package CRS.src.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import CRS.src.shared.Command;

public class User implements Runnable {
	/**
	 * socketIn receives output from client.
	 */
	private ObjectInputStream socketIn;
	/**
	 * socketOut sends the response to client.
	 */
	private ObjectOutputStream socketOut;
	/**
	 * socket is the connection between the server and the client.
	 */
	private Socket socket;
	/**
	 * username is the name of the client.
	 */
	private String username;
	/**
	 * id is the id of the client.
	 */
	private Integer ID;
	/**
	 * clients is the total user are connecting to the server.
	 */
	private ArrayList<User> clients;
	/**
	 * app contains all functions that the this class needs to execute the request
	 * from client.
	 */
	private Application app;
	/**
	 * st is an object of type Student.
	 */
	private Student st;
	/**
	 * running indicates the status of the user.
	 */
	private boolean running;

	/**
	 * Constructor of class user that intializes variables accordingly with the
	 * given data.
	 * 
	 * @param socket  is the connection between server and client.
	 * @param clients is the list of clients that are connecting to the server.
	 * @param app     app contains all functions that the this class needs to
	 *                execute the request from client.
	 */
	public User(Socket socket, ArrayList<User> clients, Application app) { // Constructor for student.
		try {
			this.socket = socket;
			socketOut = new ObjectOutputStream(socket.getOutputStream());
			socketIn = new ObjectInputStream(socket.getInputStream());
			this.writeMsg(app.genClientId().toString());
			this.clients = clients;
			this.app = app;
			this.st = null;
			clients.add(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closing all the socket.
	 */
	public void close() {
		try {
//			this.writeErrorMsg("Server is shutting down, please restart the client in a bit.");
			socketIn.close();
			socketOut.close();
			socket.close();
			st.setUser(null);
			running = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Run method communicates between server and clients.
	 */
	public void run() {
		try {
			do {
				username = (String) socketIn.readObject(); // Read username
				ID = (Integer) socketIn.readObject(); // Read ID
				st = app.loadStudent(username, ID);
				if (st == null) {
					this.writeErrorMsg("That user id exists, but the username is not the same!");
					continue;
				}
				if (st.getUser() != null) {
					this.writeErrorMsg("This user is logged in elsewhere! Please log out elsewhere.");
					st = null;
					continue;
				}
				st.setUser(this);
			} while (st == null);
			this.writeMsg("Connected!");
			running = (st != null);
			System.out.println(username + " has connected.");
			// if we have no user, we have no program!
			while (running) {
				try {
					Command cm = (Command) socketIn.readObject();
					this.decodeCommand(cm); // Decode the type of message and call appropriate function
				} catch (IOException e) {
					running = false;
					System.out.println(username + " has disconnected");
				} catch (ClassNotFoundException e2) {
					break;
				}
			}
			clients.remove(this); // Remove yourself out of user list and close all sockets.
			this.close();
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
	}

	public int getID() {
		return this.ID;
	}

	/**
	 * Writing a message to client side.
	 * 
	 * @param msg is the message server wants to send to client.
	 */
	public void writeMsg(String msg) {
		try {
			socketOut.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writing an error message to client side.
	 * 
	 * @param msg is the error message server wants to send to client.
	 */
	public void writeErrorMsg(String msg) {
		try {
			socketOut.writeObject(msg + "ERROR");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Decoding the command that is received from client.Depending on which type of
	 * command, accordingly calls a different function.
	 * 
	 * @param cm is the command client sends to the server.
	 */
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
		case Command.COURSES_TAKEN:
			courseTaken(cm.getMessage());
			break;
		case Command.SEARCH_STUDENT_ID: // Admin
			searchStudentID(cm.getMessage());
			break;
		case Command.ADD_NEW_STUDENT: // Admin
			addNewStudent(cm.getMessage());
			break;
		case Command.VIEW_ALL_STUDENT: // Admin
			viewAllStudent(cm.getMessage());
			break;
		case Command.ADD_NEW_LECTURE: // Admin
			addNewLecture(cm.getMessage());
			break;
		case Command.ADD_NEW_COURSE: // Admin
			addNewCourse(cm.getMessage());
			break;
		}
	}

	/**
	 * Add new course for the student.
	 * 
	 * @param message contains data that will be processed in back end.
	 */
	private void addNewCourse(String message) {
		String[] decodeMessage = message.split("ADD_NEW_COURSE");
		// decodeMessage [0] is course Name, [1] is course Number.
		writeMsg(app.addNewCourse(decodeMessage[0], Integer.parseInt(decodeMessage[1])));
	}

	/**
	 * Add new lecture for the admin.
	 * 
	 * @param message contains data that will be processed in back end.
	 */
	private void addNewLecture(String message) {
		String[] decodeMessage = message.split("ADD_NEW_LECTURE");
		// decodeMessage[0] is course Name, [1] is course Number, [2] is lecture
		// section, [3] is lecture capacity.
		writeMsg(app.createLecture(decodeMessage[0], Integer.parseInt(decodeMessage[1]),
				Integer.parseInt(decodeMessage[2]), Integer.parseInt(decodeMessage[3])));
	}

	/**
	 * View all student that is in the system.
	 * 
	 * @param message contains data that will be processed in back end.
	 */
	private void viewAllStudent(String message) {
		writeMsg(app.viewStudents());
	}

	/**
	 * Add new student to the system.
	 * 
	 * @param message contains data that will be processed in back end.
	 */
	private void addNewStudent(String message) {
		String[] decodeMessage = message.split("ADD_NEW_STUDENT");
		// decodeMessage[0] is name, decodeMessage[1] is ID.
		writeMsg(app.addNewStudent(decodeMessage[0], Integer.parseInt(decodeMessage[1])));
	}

	/**
	 * Find the student with given ID.
	 * 
	 * @param message contains data that will be processed in back end.
	 */
	private void searchStudentID(String id) {
		Student searchedStudent = app.searchStudents(Integer.parseInt(id));
		if (searchedStudent != null)
			writeMsg(searchedStudent.toString() + "\n\n" + searchedStudent.listRegistered()
					+ "\n\n" + searchedStudent.listTaken());
		else
			writeErrorMsg("The student with ID: " + id + " does not exist in the system.");
	}

	/**
	 * Display all the courses student have registered.
	 * 
	 * @param message contains data that will be processed in back end.
	 */
	private void courseInCart(String message) {
		if (st.getStudentRegList().size() == 0)
			writeErrorMsg("You have no course in your cart rightnow.");
		else
			writeMsg(st.listRegistered());
	}

	/**
	 * Display all the courses student has taken.
	 * 
	 * @param message contains data that will be processed in back end.
	 */
	private void courseTaken(String message) {
		if (st.getStudentTakenList().size() == 0)
			writeErrorMsg("You have not taken any courses.");
		else
			writeMsg(st.listTaken());
	}

	/**
	 * Display all the courses in the system.
	 * 
	 * @param message contains data that will be processed in back end.
	 */
	private void displayAll(String message) {
		writeMsg(app.viewCourses()); // Read data from the database through couseCatalogue
	}

	/**
	 * Remove a course out of the system.
	 * 
	 * @param message contains data that will be processed in back end.
	 */
	private void removeCourse(String message) { // To be implemented
		String[] decodeMessage = message.split("REMOVE_COURSE");
		// decodeMessage[0] is course name , 1 is course number, 2 is course SECTION
		Course searchedCourse = app.searchCourses(decodeMessage[0], Integer.parseInt(decodeMessage[1]));
		if (searchedCourse == null) {
			writeErrorMsg("The course you want to remove does not exist in the system.");
		} else {
			Lecture course = searchedCourse.getLectureSection(Integer.parseInt(decodeMessage[2]));
			Registration reg = st.findRegistration(course);
			if (reg != null) {
				writeMsg("You have been removed from the selected course offering.");
				app.removeRegistration(reg);
			} else {
				writeErrorMsg("You are not in" + decodeMessage[0] + " " + decodeMessage[1] + " " + decodeMessage[2]);
			}
		}
	}

	/**
	 * Add a new course to the system.
	 * 
	 * @param message contains data that will be processed in back end.
	 */
	private void addCourse(String message) {
		try {
			String[] decodeMessage = message.split("ADD_COURSE");
			// addCourseData[0] is course name , 1 is course number, 2 is course SECTION
			Course searchedCourse = app.searchCourses(decodeMessage[0], Integer.parseInt(decodeMessage[1]));
			if (searchedCourse.equals(null)) {
				writeErrorMsg("The course you want to add does not exist!");
			} else {
				writeMsg(app.registerStudentCourse(st,
						searchedCourse.getLectureSection(Integer.parseInt(decodeMessage[2]))));
			}
		} catch (NullPointerException e) {
			writeErrorMsg("The course you want to add does not exist!");
		}
	}

	/**
	 * Search for a course in the system.
	 * 
	 * @param message contains data that will be processed in back end.
	 */
	private void searchCourse(String message) {
		try {
			String[] decodeMessage = message.split("SEARCH_COURSE"); // Data for search course is separated by a space
			// decodeMessage[0] is course ID, [1] is course Number.
			Course search = app.searchCourses(decodeMessage[0], Integer.parseInt(decodeMessage[1]));
			writeMsg(search.toString());
		} catch (NullPointerException e) {
			writeErrorMsg("The course you searched for does not exist.");
		}
	}
}

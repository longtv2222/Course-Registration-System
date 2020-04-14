package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

//import Client.GUI;

public class User implements Runnable { // Can be either admin or regular student
	private Application app;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private String name;
	private int ID;
	private Student user;

	public User(Application app, BufferedReader socketIn, PrintWriter socketOut, String name, int id) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		this.app = app;
		this.name = name;
		this.ID = id;
	}

	@Override
	public void run() {
//		GUI userInterface = new GUI();
//		userInterface.askNameID(this);
//		userInterface.menu(this);
		this.startup(name, ID);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getName() {
		return this.name;
	}

	public int getID() {
		return this.ID;
	}


	private void sendString(String message) {
		socketOut.println(message);
//		socketOut.flush();
	}

	private void displayChoices() {

		sendString("1.\tSearch catalogue courses");
		sendString("2.\tAdd course to student courses");
		sendString("3.\tRemove course from student courses.");
		sendString("4.\tList all available courses.");
		sendString("5.\tList all courses the student is taking.");
		sendString("6.\tExit this program.");
		sendString("Please enter your choice: \0");
	}

	private void displayPicked(CourseOffering picked) {
		sendString("Currently selected course:");
		sendString(picked.toString());
	}

	private CourseOffering selectCourseOffering() throws IOException {
		sendString("Please enter the four letter name of the course \0");
		String rawInput = this.socketIn.readLine();
		if (rawInput.length() != 4) {
			sendString("Please enter a valid course name! Returning to menu.");
			return null;
		}
		String courseName = rawInput.toUpperCase();
		sendString("Please enter the three digits of the course \0");
		rawInput = socketIn.readLine();
		int courseNum = Integer.parseInt(rawInput);
		if (rawInput.length() != 3 && courseNum > 0) {
			sendString("Please enter a valid course number! Returning to menu.");
			return null;
		}
		Course course = app.selectCourse(courseName, courseNum);
		if (course == null) {
			sendString("Error! Course not found.");
			return null;
		}
		sendString(course.toString());
		sendString("Please select a section number from the options above.\0");
		rawInput = socketIn.readLine();
		int section = Integer.parseInt(rawInput);
		return app.selectCourseOffering(course, section);
	}

	private void registerStudentCourse(Student st, CourseOffering section) {
		if (app.registerStudentCourse(st, section)) {
			sendString("Registering student into the selected course offering.");
		} else {
			sendString("You need to select a course before you can register!");
		}
	}

	private void deregisterStudentCourse(Student st, CourseOffering course) {
		int deregisterStatus = app.deregisterStudentCourse(st, course);
		if (deregisterStatus >=0) {
			if (deregisterStatus==1) {
				sendString("Removing student from the selected course offering.");
				return;
			}
			sendString("Error! Student is not in the selected course offering.");
		} else {
			sendString("You need to select a course before you can unregister!");
		}
	}

	public void menu(Student st) {
		try {
			sendString("Welcome to the course registration app! Hello to you, you are the following person:");
			sendString(st.toString());
			boolean running = true;
			CourseOffering picked = null;
			String rawOption = "";

			int option;
			while (running) {
				this.displayChoices();
				if (picked != null)
					this.displayPicked(picked);
				rawOption = socketIn.readLine();
				option = Integer.parseInt(rawOption);
				switch (option) {
				default:
					sendString("Please enter a valid option!");
					break;
				case (1):
					picked = this.selectCourseOffering();
					break;// more stuff happens here.
				case (2):
					this.registerStudentCourse(st, picked);
					break;
				case (3):
					this.deregisterStudentCourse(st, picked);
					break;
				case (4):
					sendString("Listing all the courses available:");
					sendString(app.getCourseCatalogue().toString());
					break;
				case (5):
					sendString("Listing call courses taken by the student:");
					st.listRegistered();
					break;
				case (6):
					sendString("Exiting course registration menu!");
					running = false;
					break;
				}

			}
		} catch (SocketException e) {
			return;
		} catch (Exception e) {
			sendString("Error! Something went wrong! Make sure you are entering a valid input.");
			e.printStackTrace();
		}
	}
	
	public void startup(String name, int ID) {
		user = app.startup(name, ID);
		if(user != null) {
			this.menu(user);
			return;
		}
	}
}

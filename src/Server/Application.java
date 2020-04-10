package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;

public class Application {
	private CourseCatalogue courseCat;
	private Socket aSocket;
	private BufferedReader socketIn;
	private PrintWriter socketOut;

	public Application(Socket s, CourseCatalogue courseCat) {
		try {
			this.courseCat = courseCat;
			this.aSocket = s;
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getCourseName() throws IOException { // Ask for name of the course
		sendString("Please enter the name of the course: \0");
		String name = socketIn.readLine();
		return name;
	}

	private int getCourseID() throws NumberFormatException, IOException { // Ask for ID of the course, ID must be a
																			// number
		sendString("Please enter the ID of the course \0 ");
		int ID = Integer.parseInt(socketIn.readLine());
		return ID;

	}

	private void sendString(String message) {
		socketOut.println(message);
		socketOut.flush();
	}

	public void menu() {
		sendString("1. Search catalogue course");
		sendString("2. Add course to student course");
		sendString("3. Remove course to student course");
		sendString("4. View all courses in catalogue");
		sendString("5. View all courses taken by students");
		sendString("6. Quit");
		choice();
	}

	private void choice() {
		int choice = 0;
		try {
			sendString("\nPlease enter your choice: \0");
			choice = Integer.parseInt(socketIn.readLine());

			switch (choice) {
			case 1:
				sendString(courseCat.getCourseNameId(getCourseName(), getCourseID()));
				choice();
				break;
			case 2:
				courseCat.getCourseList().add(new Course(getCourseName(), getCourseID())); // Add course to student
																							// course
				sendString("Course added successfully");
				choice();
				break;
			case 3:
				sendString(courseCat.removeCourse(getCourseName(), getCourseID()));
				choice();
				break;
			case 4:
				sendString(courseCat.toString());
				choice();
				break;
			case 5:
				sendString(courseCat.coursesTakenByStudents());
				choice();
				break;
			case 6:
				sendString("Program terminated..");
				break;
			default:
				sendString("Invalid input.");
				break;
			}
		} catch (NumberFormatException e) {
			sendString("Input must be a number. Try again!");
			choice();
		} catch (InputMismatchException e) {
			sendString("You have entered invalid data. Try again");
			choice();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startApplication() {
		menu();
		choice();
	}
}

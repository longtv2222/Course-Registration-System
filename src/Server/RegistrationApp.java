package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class RegistrationApp {
	private Socket aSocket;
	private BufferedReader socketIn;
	private PrintWriter socketOut;

	public RegistrationApp(Socket s) {
		try {
			this.aSocket = s;
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream(), true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendString(String message) {
		socketOut.println(message);
		socketOut.flush();
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

	private CourseOffering selectCourseOffering(CourseCatalogue cat) throws IOException {
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
		Course course = cat.searchCat(courseName, courseNum);
		if (course == null) {
			sendString("Error! Course not found.");
			return null;
		}
		sendString(course.toString());
		sendString("Please select a section number from the options above.\0");
		rawInput = socketIn.readLine();
		int section = Integer.parseInt(rawInput);
		return course.getCourseOfferingSection(section);
	}

	private void registerStudentCourse(Student st, CourseOffering course) {
		if (course != null) {
			sendString("Registering student into the selected course offering.");
			Registration reg = new Registration();
			reg.completeRegistration(st, course);
		} else {
			sendString("You need to select a course before you can register!");
		}
	}

	private void deregisterStudentCourse(Student st, CourseOffering course) {
		if (course != null) {
			Registration reg = st.findRegistration(course);
			if (reg != null) {
				sendString("Removing student from the selected course offering.");
				reg.removeRegistration();
				return;
			}
			sendString("Error! Student is not in the selected course offering.");
		} else {
			sendString("You need to select a course before you can unregister!");
		}
	}

	public void menu(Student st, CourseCatalogue cat) {
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
					picked = this.selectCourseOffering(cat);
					break;// more stuff happens here.
				case (2):
					this.registerStudentCourse(st, picked);
					break;
				case (3):
					this.deregisterStudentCourse(st, picked);
					break;
				case (4):
					sendString("Listing all the courses available:");
					sendString(cat.toString());
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
		CourseCatalogue cat = new CourseCatalogue();
		Student st = new Student("Sara", 1);
		Student st2 = new Student("Sam", 2);
		Student st3 = new Student("Sophie", 3);
		Student st4 = new Student("Stacy", 4);
		Student st5 = new Student("Steve", 5);
		Student st6 = new Student("Stan", 6);
		Student st7 = new Student("Saul", 7);
		Student st8 = new Student("Sean", 8);
		Student st9 = new Student("Stark", 9);
		Student st10 = new Student("Salem", 10);
		Course myCourse = cat.searchCat("ENGG", 233);
		if (myCourse != null) {
			cat.createCourseOffering(myCourse, 1, 10);
			cat.createCourseOffering(myCourse, 2, 200);
		}
		Course myCourse2 = cat.searchCat("ENSF", 409);
		if (myCourse2 != null) {
			cat.createCourseOffering(myCourse2, 1, 60);
		}
		Course myCourse3 = cat.searchCat("PHYS", 259);
		if (myCourse3 != null) {
			cat.createCourseOffering(myCourse3, 1, 250);
		}

		Registration reg = new Registration();
		Registration reg2 = new Registration();
		Registration reg3 = new Registration();
		Registration reg4 = new Registration();
		Registration reg5 = new Registration();
		Registration reg6 = new Registration();
		Registration reg7 = new Registration();
		Registration reg8 = new Registration();
		Registration reg9 = new Registration();
		Registration reg10 = new Registration();
		reg.completeRegistration(st, myCourse.getCourseOfferingAt(0));
		reg2.completeRegistration(st2, myCourse.getCourseOfferingAt(0));
		reg3.completeRegistration(st3, myCourse.getCourseOfferingAt(0));
		reg4.completeRegistration(st4, myCourse.getCourseOfferingAt(0));
		reg5.completeRegistration(st5, myCourse.getCourseOfferingAt(0));
		reg6.completeRegistration(st6, myCourse.getCourseOfferingAt(0));
		reg7.completeRegistration(st7, myCourse.getCourseOfferingAt(0));
		reg8.completeRegistration(st8, myCourse.getCourseOfferingAt(0));
		reg9.completeRegistration(st9, myCourse.getCourseOfferingAt(0));
		reg10.completeRegistration(st10, myCourse.getCourseOfferingAt(0));

		Student user = new Student(name, ID);
		RegistrationApp regapp = new RegistrationApp(this.aSocket);
		regapp.menu(user, cat);
	}

}

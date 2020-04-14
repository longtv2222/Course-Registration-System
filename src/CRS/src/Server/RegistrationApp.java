package CRS.src.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RegistrationApp {
	private BufferedReader input;
	private CourseCatalogue cat;

	public RegistrationApp(CourseCatalogue cat) {
		input = new BufferedReader(new InputStreamReader(System.in));
		this.cat = cat;
	}

	private void displayChoices() {
		System.out.println("Please enter a choice from the following:");
		System.out.println("1.\tSearch catalogue courses");
		System.out.println("2.\tAdd course to student courses");
		System.out.println("3.\tRemove course from student courses.");
		System.out.println("4.\tList all available courses.");
		System.out.println("5.\tList all courses the student is taking.");
		System.out.println("6.\tExit this program.");
	}

	private void displayPicked(CourseOffering picked) {
		System.out.println("Currently selected course:");
		System.out.println(picked);
	}

	public CourseOffering selectCourseOffering(CourseCatalogue cat, String courseName, Integer courseNum,
			Integer section) throws IOException {
		Course course = cat.searchCat(courseName, courseNum);
		if (course == null) {
			System.err.println("Error! Course not found.");
			return null;
		}
		return course.getCourseOfferingSection(section);
	}

	private void registerStudentCourse(Student st, CourseOffering course) {
		if (course != null) {
			System.out.println("Registering student into the selected course offering.");
			Registration reg = new Registration();
			reg.completeRegistration(st, course);
		} else {
			System.out.println("You need to select a course before you can register!");
		}
	}

	private void deregisterStudentCourse(Student st, CourseOffering course) {
		if (course != null) {
			Registration reg = st.findRegistration(course);
			if (reg != null) {
				System.out.println("Removing student from the selected course offering.");
				reg.removeRegistration();
				return;
			}
			System.out.println("Error! Student is not in the selected course offering.");
		} else {
			System.out.println("You need to select a course before you can unregister!");
		}
	}

	public void menu(Student st, CourseCatalogue cat) {
		try {
			System.out.println("Welcome to the course registration app! Hello to you, you are the following person:");
			System.out.println(st);
			boolean running = true;
			CourseOffering picked = null;
			String rawOption = "";

			int option;
			while (running) {
				this.displayChoices();
				if (picked != null)
					this.displayPicked(picked);
				rawOption = input.readLine();
				option = Integer.parseInt(rawOption);
				switch (option) {
				default:
					System.out.println("Please enter a valid option!");
					break;
				case (1):
//					picked = this.selectCourseOffering(cat); // Make these thing return string
					break;// more stuff happens here.
				case (2):
					this.registerStudentCourse(st, picked); // Make these thing returns string
					break;
				case (3):
					this.deregisterStudentCourse(st, picked);
					break;
				case (4):
					System.out.println("Listing all the courses available:");
					System.out.println(cat);
					break;
				case (5):
					System.out.println("Listing call courses taken by the student:");
					st.listRegistered();
					break;
				case (6):
					System.out.println("Exiting course registration menu!");
					running = false;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error! Something went wrong! Make sure you are entering a valid input.");
			e.printStackTrace();
		}
	}

//	public void startup() {
//		CourseCatalogue cat = new CourseCatalogue();
//		// System.out.println(cat);
//		Student st = new Student("Sara", 1);
//		Student st2 = new Student("Sam", 2);
//		Student st3 = new Student("Sophie", 3);
//		Student st4 = new Student("Stacy", 4);
//		Student st5 = new Student("Steve", 5);
//		Student st6 = new Student("Stan", 6);
//		Student st7 = new Student("Saul", 7);
//		Student st8 = new Student("Sean", 8);
//		Student st9 = new Student("Stark", 9);
//		Student st10 = new Student("Salem", 10);
//		Course myCourse = cat.searchCat("ENGG", 233);
//		if (myCourse != null) {
//			cat.createCourseOffering(myCourse, 1, 10);
//			cat.createCourseOffering(myCourse, 2, 200);
//		}
//		Course myCourse2 = cat.searchCat("ENSF", 409);
//		if (myCourse2 != null) {
//			cat.createCourseOffering(myCourse2, 1, 60);
//		}
//		Course myCourse3 = cat.searchCat("PHYS", 259);
//		if (myCourse3 != null) {
//			cat.createCourseOffering(myCourse3, 1, 250);
//		}
//		// System.out.println(myCourse.getCourseOfferingAt(0));
//
//		Registration reg = new Registration();
//		Registration reg2 = new Registration();
//		Registration reg3 = new Registration();
//		Registration reg4 = new Registration();
//		Registration reg5 = new Registration();
//		Registration reg6 = new Registration();
//		Registration reg7 = new Registration();
//		Registration reg8 = new Registration();
//		Registration reg9 = new Registration();
//		Registration reg10 = new Registration();
//		reg.completeRegistration(st, myCourse.getCourseOfferingAt(0));
//		reg2.completeRegistration(st2, myCourse.getCourseOfferingAt(0));
//		reg3.completeRegistration(st3, myCourse.getCourseOfferingAt(0));
//		reg4.completeRegistration(st4, myCourse.getCourseOfferingAt(0));
//		reg5.completeRegistration(st5, myCourse.getCourseOfferingAt(0));
//		reg6.completeRegistration(st6, myCourse.getCourseOfferingAt(0));
//		reg7.completeRegistration(st7, myCourse.getCourseOfferingAt(0));
//		reg8.completeRegistration(st8, myCourse.getCourseOfferingAt(0));
//		reg9.completeRegistration(st9, myCourse.getCourseOfferingAt(0));
//		reg10.completeRegistration(st10, myCourse.getCourseOfferingAt(0));
//
//		// System.out.println(reg);
//		// System.out.println(cat);
//		Student st11 = new Student("Sarge", 11);
//		RegistrationApp regapp = new RegistrationApp(new CourseCatalogue());
//		regapp.menu(st11, cat);
//	}

}

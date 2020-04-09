package Server;
import java.util.InputMismatchException;
import java.util.Scanner;
//RegistrationApp is front
public class RegistrationApp {
	public static Scanner scanner;
	private CourseCatalogue courseCat;

	public RegistrationApp(CourseCatalogue courseCat) {
		this.courseCat = courseCat;
		scanner = new Scanner(System.in);
	}

	private String getCourseName() {
		System.out.println("Please enter the name of the course: ");
		String name = scanner.nextLine();
		return name;
	}

	private int getCourseID() {
		System.out.println("Please enter the ID of the course ");
		int ID = scanner.nextInt();
		return ID;
	}

	public void menu() {
		System.out.println("1. Search catalogue course");
		System.out.println("2. Add course to student course");
		System.out.println("3. Remove course to student course");
		System.out.println("4. View all courses in catalogue");
		System.out.println("5. View all courses taken by students");
		System.out.println("6. Quit");
		choice();
	}

	private void choice() {
		int choice = 0;
		try {
			System.out.println("\nPlease enter your choice:\n");
			choice = scanner.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("You have entered invalid data. Try again");
			choice();
		}
		scanner.nextLine();
		switch (choice) {
		case 1:
			courseCat.getCourseNameId(getCourseName(), getCourseID());
			choice();
			break;
		case 2:
			courseCat.getCourseList().add(new Course(getCourseName(), getCourseID())); // Add course to student course
			choice();
			break;
		case 3:
			courseCat.removeCourse(getCourseName(), getCourseID());
			choice();
			break;
		case 4:
			courseCat.displayToString();
			choice();
			break;
		case 5:
			courseCat.coursesTakenByStudents();
			choice();
			break;
		case 6:
			System.out.println("Program terminated..");
			break;
		default:
			System.out.println("Invalid input.");
			break;
		}
	}

	public static void main(String[] args) {
		CourseCatalogue cat = new CourseCatalogue();
		RegistrationApp a = new RegistrationApp(cat);

		Student st = new Student("Sara", 1);
		Student st2 = new Student("Sam", 2);
		Student st3 = new Student("Jim", 3);
		Student st4 = new Student("Michael", 4);
		Student st5 = new Student("Jack", 5);
		Student st6 = new Student("David", 6);
		Student st7 = new Student("Cloud", 7);
		Student st8 = new Student("Sammuel", 8);
		Student st9 = new Student("Harley", 9);

		Course myCourse = cat.searchCat("ENGG", 233);
		if (myCourse != null) {
			cat.createCourseOffering(myCourse, 1, 400);
			cat.createCourseOffering(myCourse, 2, 300);
		}

		System.out.println();
		Registration engg233 = new Registration();
		engg233.completeRegistration(st, myCourse.getCourseOfferingAt(0));
		engg233.completeRegistration(st2, myCourse.getCourseOfferingAt(1));
		engg233.completeRegistration(st3, myCourse.getCourseOfferingAt(0));
		engg233.completeRegistration(st4, myCourse.getCourseOfferingAt(1));
		engg233.completeRegistration(st5, myCourse.getCourseOfferingAt(0));
		engg233.completeRegistration(st6, myCourse.getCourseOfferingAt(1));
		engg233.completeRegistration(st7, myCourse.getCourseOfferingAt(0));
		engg233.completeRegistration(st8, myCourse.getCourseOfferingAt(1)); // Test condition if number of student >=8
		engg233.completeRegistration(st9, myCourse.getCourseOfferingAt(1));
		myCourse.conditionStudentNumberThisCourse(); // Test condition if number of student in a course < 8
		System.out.println();

		Course myCourse3 = cat.searchCat("PHYS", 259);
		if (myCourse3 != null) {
			cat.createCourseOffering(myCourse3, 1, 1);
			cat.createCourseOffering(myCourse3, 2, 1);
		}
		Registration phys259 = new Registration();
		phys259.completeRegistration(st, myCourse3.getCourseOfferingAt(0));
		phys259.completeRegistration(st2, myCourse3.getCourseOfferingAt(0)); // phys 259 only has 1 spot, test condition
																				// // if there is no spot in phys 259
		myCourse3.conditionStudentNumberThisCourse();

		Course myCourse2 = cat.searchCat("ENSF", 409);
		myCourse2.addPreReq(myCourse); // adding ENGG 233 as prereq of ENSF 409
		if (myCourse2 != null) {
			cat.createCourseOffering(myCourse2, 1, 100);
			cat.createCourseOffering(myCourse2, 2, 100);
		}
		a.menu();
	}
}

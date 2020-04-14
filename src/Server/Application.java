package Server;

import java.io.IOException;
import java.util.ArrayList;
import Database.Database;

public class Application {
//	private Socket aSocket;
	private ArrayList<Student> students;
	private ArrayList<Course> courses;
	private CourseCatalogue cat;
	private Database db;

	public Application() {// BufferedReader socketIn, PrintWriter socketOut) {
//		this.socketIn = socketIn;
//		this.socketOut = socketOut;
		db = new Database("localhost");
		this.courses = db.getAllCourses();
		this.students = db.getAllStudents();
		cat = new CourseCatalogue();
		for(Course c: courses) {
			cat.addCourse(c);
		}
		for(Student s: students) {
			Registration reg = new Registration();
			reg.completeRegistration(s, cat.getCourseList().get(0).getCourseOfferingAt(0));
		}
	}
	
	public CourseCatalogue getCourseCatalogue() {
		return this.cat;
	}
	public Course selectCourse(String courseName, int courseNum) throws IOException {
		Course course = cat.searchCat(courseName, courseNum);
		return course;
	}
	public CourseOffering selectCourseOffering(Course course, int section) throws IOException {
		return course.getCourseOfferingSection(section);
	}

	public boolean registerStudentCourse(Student st, CourseOffering section) {
		if (section != null) {
			Registration reg = new Registration();
			reg.completeRegistration(st, section);
			return true;
		} else {
			return false;
		}
	}

	public int deregisterStudentCourse(Student st, CourseOffering course) {
		if (course != null) {
			Registration reg = st.findRegistration(course);
			if (reg != null) {
				reg.removeRegistration();
				return 1;
			}
			return 0;
		}
		return -1;
	}

	public Student startup(String name, int ID) {
		Student user = new Student(name, ID);
		for(Student s : students) {
			if(s.compareTo(user)==0) {
				if(s.getStudentName().compareTo(user.getStudentName())==0) {
					return s;
				}
				return null;
			}
		}
		students.add(user);
		return user;
	}

}

package crs.src.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import crs.src.server.database.Database;

//This class is simulating a database for our
//program
public class DBManager {

//	ArrayList<Course> courseList;
	Database db;

	public DBManager() {
		Scanner scanner = new Scanner(System.in);
		String sqlServer = "jdbc:mysql://localhost:3306/";
		String schema = "school_long_cloud";
		String user = "mysql";
		String password = "";

		db = new Database(sqlServer, schema, user, password);
//		System.out.println("Please input the address for the sql server, eg 'jdbc:mysql://localhost:3306/'.");
//		sqlServer = scanner.nextLine();
//		System.out.println("Please input the schema/database the sql server will use, eg 'school_long_cloud'.");
//		schema = scanner.nextLine();
//		System.out.println("Please input the username for the sql server, eg 'mysql'.");
//		user = scanner.nextLine();
//		System.out.println("Please input the password for the sql server, or enter for no password.");
//		password = scanner.nextLine();
//		courseList = new ArrayList<Course>();
	}

	public ArrayList<Course> readCoursesFromDataBase() {
		try {
			return db.getAllCourses();
		} catch (SQLException e) {
			System.err.println("Error! Something went wrong with loading the courses from the database!");
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Student> readStudentsFromDataBase() {
		try {
			return db.getAllStudents();
		} catch (SQLException e) {
			System.err.println("Error! Something went wrong with loading the students from the database!");
			e.printStackTrace();
			return null;
		}
	}

	public void setupStudentsCoursesDatabase(ArrayList<Student> students, ArrayList<Course> courses) {
		try {
			db.loadAllLectures(courses);
			db.loadAllRequisites(courses);
			db.loadAllRegistrations(courses, students);
		} catch (SQLException e) {
			System.err.println("Error! Something went wrong while loading the information from the database!");
			e.printStackTrace();
		}
	}

	public void addLecture(Lecture lecture) {
		try {
			db.addLecture(lecture);
		} catch (SQLException e) {
			System.err.println("Error! Something went wrong with adding a lecture to the database!");
			e.printStackTrace();
		}
	}

	public void addStudent(Student student) {
		try {
			db.addStudent(student);
		} catch (SQLException e) {
			System.err.println("Error! Something went wrong with adding a student to the database!");
			e.printStackTrace();
		}
	}

	public void addCourse(Course course) {
		try {
			db.addCourse(course);
		} catch (SQLException e) {
			System.err.println("Error! Something went wrong with adding a course to the database!");
			e.printStackTrace();
		}
	}

	public void addRegistration(Registration reg) {
		try {
			db.addRegistration(reg);
		} catch (SQLException e) {
			System.err.println("Error! Something went wrong with adding a registration to the database!");
			e.printStackTrace();
		}
	}
	public void removeRegistration(Registration reg) {
		try {
			db.removeRegistration(reg);
		} catch (SQLException e) {
			System.err.println("Error! Something went wrong with adding a registration to the database!");
			e.printStackTrace();
		}
	}
	
	public void addRequisite(Course course, Course prereq) {
		try {
			db.addRequisite(course, prereq);
		} catch (SQLException e) {
			System.err.println("Error! Something went wrong with adding a prerequisite to the database!");
			e.printStackTrace();
		}
	}
	
	public void close() {
		db.close();
	}
}

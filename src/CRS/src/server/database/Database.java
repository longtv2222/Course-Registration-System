package CRS.src.server.database;

import java.sql.SQLException;
import java.util.ArrayList;

import CRS.src.server.Course;
import CRS.src.server.Lecture;
import CRS.src.server.Registration;
import CRS.src.server.Student;

/**
 * Connect between the SQL server and the DBmanager. Simplifies operations for
 * the server, simplify write/read commands for the sql server.
 */
public class Database {

	// sql server connection.
	private SqlServer sqlServer;

	/**
	 * Create and Connect to the SQL server
	 * 
	 * @param sqlServAddress The address to connect to. should be some 'jdbc'
	 * @param database       The database/schema to connect to.
	 * @param user           The username for the sql server
	 * @param pass           The password of the user for the sql server.
	 */
	public Database(String sqlServAddress, String database, String user, String pass) {
		try {
			sqlServer = new SqlServer(sqlServAddress, database, user, pass);
		} catch (SQLException e) {
			System.err.println("Error creating SQL server at specified location!");
			e.printStackTrace();
		}
	}

	/**
	 * Adds a new registration to the sql server
	 * 
	 * @param reg The registration to add
	 * @throws SQLException Communication error / improper connection
	 */
	public void addRegistration(Registration reg) throws SQLException {
		sqlServer.addRegistration(reg.getTheLecture().getTheCourse().getCourseName(),
				reg.getTheLecture().getTheCourse().getCourseNum(), reg.getTheStudent().getStudentId(),
				reg.getTheLecture().getSecNum());
	}

	/**
	 * Removes a registration from the sql server
	 * 
	 * @param reg The registration to remove
	 * @throws SQLException Communication error / improper connection
	 */
	public void removeRegistration(Registration reg) throws SQLException {
		sqlServer.removeRegistration(reg.getTheLecture().getTheCourse().getCourseName(),
				reg.getTheLecture().getTheCourse().getCourseNum(), reg.getTheStudent().getStudentId(),
				reg.getTheLecture().getSecNum());
	}

	/**
	 * Adds a student to the sql server
	 * 
	 * @param student The student to add
	 * @throws SQLException Communication error / improper connection
	 */
	public void addStudent(Student student) throws SQLException {
		sqlServer.addStudent(student.getStudentId(), student.getStudentName());
	}

	/**
	 * Adds a course to the sql server
	 * 
	 * @param course The course to add to the server
	 * @throws SQLException Communication error / improper connection
	 */
	public void addCourse(Course course) throws SQLException {
		sqlServer.addCourse(course.getCourseName(), course.getCourseNum());
	}

	/**
	 * adds a prerequiste for a course to the sql server
	 * 
	 * @param course The course that is getting a new prereq
	 * @param req    The prereq to add.
	 * @throws SQLException Communication error / improper connection
	 */
	public void addRequisite(Course course, Course req) throws SQLException {
		sqlServer.addRequisite(course.getCourseName(), course.getCourseNum(), req.getCourseName(), req.getCourseNum());
	}

	/**
	 * Adds a lecture to the sql server
	 * 
	 * @param lec the lecture to add
	 * @throws SQLException Communication error / improper connection
	 */
	public void addLecture(Lecture lec) throws SQLException {
		sqlServer.addLecture(lec.getTheCourse().getCourseName(), lec.getTheCourse().getCourseNum(), lec.getSecNum(),
				lec.getSecCap());
	}

	/**
	 * Fetches all of the students from the SQL server
	 * 
	 * @return ArrayList<Student>, is all of the students from the sql server
	 * @throws SQLException
	 */
	public ArrayList<Student> getAllStudents() throws SQLException {
		return sqlServer.getStudents();
	}

	/**
	 * Fetches all of the courses from the SQL server
	 * 
	 * @return ArrayList<Course>, is all of the courses from the sql server
	 * @throws SQLException Communication error / improper connection
	 */
	public ArrayList<Course> getAllCourses() throws SQLException {
		return sqlServer.getCourses();
	}

	/**
	 * Loads all of the lectures for all of the courses from the sql server
	 * 
	 * @param courses The list of all courses on the sql server
	 * @throws SQLException Communication error / improper connection
	 */
	public void loadAllLectures(ArrayList<Course> courses) throws SQLException {
		for (Course c : courses) {
			sqlServer.setCourseLectures(c);
		}
	}

	/**
	 * Loads all of the prereqs for the courses from the sql server
	 * 
	 * @param courses The list of all the courses on the sql server
	 * @throws SQLException Communication error / improper connection
	 */
	public void loadAllRequisites(ArrayList<Course> courses) throws SQLException {
		for (Course c : courses) {
			sqlServer.setCourseRequisites(c, courses);
		}
	}

	/**
	 * Loads all of the course registrations, connecting the courses/lectures and
	 * the students.
	 * 
	 * @param courses  The list of all the courses on the sql server
	 * @param students The list of all the students on the SQL server.
	 * @throws SQLException Communication error / improper connection
	 */
	public void loadAllRegistrations(ArrayList<Course> courses, ArrayList<Student> students) throws SQLException {
		for (Course c : courses) {
			sqlServer.setCourseRegistrations(c, students);
		}
	}

	/**
	 * Shutdown the sql server, and the database.
	 */
	public void close() {
		sqlServer.close();
	}

	/**
	 * Debug code, Test connection to a local database and print what is setup in
	 * the SqlServer class.
	 * 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		Database db = new Database("jdbc:mysql://localhost:3306/", "school_long_cloud", "mysql", "");
		ArrayList<Student> students = db.getAllStudents();
		ArrayList<Course> courses = db.getAllCourses();
		db.loadAllLectures(courses);
		db.loadAllRequisites(courses);
		db.loadAllRegistrations(courses, students);
		for (Student s : students) {
			System.out.println(s);
			System.out.println(s.listRegistered());
			System.out.println(s.listTaken());
		}
		for (Course c : courses) {
			System.out.println(c);
		}
	}
}

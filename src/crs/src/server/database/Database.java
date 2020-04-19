package crs.src.server.database;

import java.sql.SQLException;
import java.util.ArrayList;

import crs.src.server.Course;
import crs.src.server.Lecture;
import crs.src.server.Registration;
import crs.src.server.Student;

public class Database {
	private SqlServer sqlServer;

	public Database(String sqlServAddress, String database, String user, String pass) {
		try {
			sqlServer = new SqlServer(sqlServAddress, database, user, pass);
		} catch (SQLException e) {
			System.err.println("Error creating SQL server at specified location!");
			e.printStackTrace();
		}
	}

	public void addRegistration(Registration reg) throws SQLException {
		sqlServer.addRegistration(reg.getTheLecture().getTheCourse().getCourseName(),
				reg.getTheLecture().getTheCourse().getCourseNum(), reg.getTheStudent().getStudentId(),
				reg.getTheLecture().getSecNum());
	}

	public void addStudent(Student student) throws SQLException {
		sqlServer.addStudent(student.getStudentId(), student.getStudentName());
	}

	public void addCourse(Course course) throws SQLException {
		sqlServer.addCourse(course.getCourseName(), course.getCourseNum());
	}

	public void addRequisite(Course course, Course req) throws SQLException {
		sqlServer.addRequisite(course.getCourseName(), course.getCourseNum(), req.getCourseName(), req.getCourseNum());
	}

	public void addLecture(Lecture lec) throws SQLException {
		sqlServer.addLecture(lec.getTheCourse().getCourseName(), lec.getTheCourse().getCourseNum(), lec.getSecNum(),
				lec.getSecCap());
	}

	public ArrayList<Student> getAllStudents() throws SQLException {
		return sqlServer.getStudents();
	}

	public ArrayList<Course> getAllCourses() throws SQLException {
		return sqlServer.getCourses();
	}

	public void loadAllLectures(ArrayList<Course> courses) throws SQLException {
		for (Course c : courses) {
			sqlServer.setCourseLectures(c);
		}
	}

	public void loadAllRequisites(ArrayList<Course> courses) throws SQLException {
		for (Course c : courses) {
			sqlServer.setCourseRequisites(c, courses);
		}
	}

	public void loadAllRegistrations(ArrayList<Course> courses, ArrayList<Student> students) throws SQLException {
		for (Course c : courses) {
			sqlServer.setCourseRegistrations(c, students);
		}
	}

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

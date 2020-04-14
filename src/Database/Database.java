package Database;

import Server.Student;
import java.util.ArrayList;
import Server.Course;

public class Database {
	private SQLServer sqlServer;

	public Database(String sqlLocation) {
		// currently, sqlLocation is unused, but it will be the address for the SQL
		// server
		sqlServer = new SQLServer(sqlLocation);
	}

	public ArrayList<Course> getAllCourses(){
		return sqlServer.getAllCourses();
	}
	public ArrayList<Student> getAllStudents(){
		return sqlServer.getAllStudents();
	}
}
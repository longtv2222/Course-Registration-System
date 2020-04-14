package Database;

import java.util.ArrayList;
import Server.Course;
import Server.Student;
import Server.CourseOffering;

public class SQLServer {
	//to be implemented fully.
	String sqlServerLocation;
	public SQLServer(String location) {
		this.sqlServerLocation = location;
	}
	
	public ArrayList<Course> getAllCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		
		Course myCourse = new Course("ENGG",233);//cat.searchCat("ENGG", 233);
		myCourse.addOffering(new CourseOffering(1, 10));
		myCourse.addOffering(new CourseOffering(2, 200));
		
		Course myCourse2 = new Course("ENSF", 409);
		myCourse2.addOffering(new CourseOffering(1, 60));
		
		Course myCourse3 = new Course("PHYS", 259);
		myCourse3.addOffering(new CourseOffering(1, 250));
		
		courses.add(myCourse);
		courses.add(myCourse2);
		courses.add(myCourse3);
		return courses;
	}
	
	public ArrayList<Student> getAllStudents(){
		ArrayList<Student> students = new ArrayList<Student>();
		
		return students;
	}
	
}

package CRS.src.Server;

import java.util.ArrayList;

public class CourseCatalogue {

	private ArrayList<Course> courseList;

	public CourseCatalogue() {
		loadFromDataBase();
	}

	private void loadFromDataBase() {
		// TODO Auto-generated method stub
		DBManager db = new DBManager();
		setCourseList(db.readFromDataBase());

	}

	public void createCourseOffering(Course c, int secNum, int secCap) {
		if (c != null) {
			CourseOffering theOffering = new CourseOffering(secNum, secCap);
			c.addOffering(theOffering);
		}
	}

	public Course searchCat(String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) && courseNum == c.getCourseNum()) {
				return c;
			}
		}
		displayCourseNotFoundError();
		return null;
	}

	public String searchCatString(String courseName, int courseNum) { // Same as searchCat but returns string
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) && courseNum == c.getCourseNum()) {
				return c.toString();
			}
		}

		return "Could not find the course";
	}

	// Typically, methods that are called from other methods of the class
	// are private and are not exposed for use by other classes.
	// These methods are refereed to as helper methods or utility methods
	private void displayCourseNotFoundError() {
		// TODO Auto-generated method stub
		System.err.println("Course was not found!");

	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

	@Override
	public String toString() {
		String st = "All courses in the catalogue: \n";
		for (Course c : courseList) {
			st += c; // This line invokes the toString() method of Course
			st += "\n";
		}
		return st;
	}

}

package Server;
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
				System.out.println("The course " + courseName + " " + courseNum + " is found");
				return c;
			}
		}
		displayCourseNotFoundError();
		return null;
	}

	public void coursesTakenByStudents() {
		System.out.println("Courses taken by students are");
		for (int i = 0; i < courseList.size(); i++) {
			if (courseList.get(i).checkStudentNumberThisCourse() != 0) {
				System.out.println(courseList.get(i).getCourseName() + " " + courseList.get(i).getCourseNum());
			}
		}
	}

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

	public void removeCourse(String courseName, int courseID) {
		String courseNameUpperCase = courseName.toUpperCase(); // eliminate lower case
		int index = indexOfName(courseNameUpperCase, courseID);
		if (index < 0) {
			System.out.println(courseName + " " + courseID + " is not on the list.");
			return;
		}

		courseList.remove(index);

		System.out.println(courseNameUpperCase + " " + courseID + " has been removed");
		System.out.println("The list after removing " + courseNameUpperCase + " " + courseID + " element is:");
		printAllCourse(); // print course after removing courseName + courseID element
	}

	private int indexOfName(String courseName, int courseID) {

		int index = -1; // Index has negative value to check if courseName and courseId exist
		for (int i = 0; i < courseList.size(); i++) {
			if (courseList.get(i).getCourseNum() == courseID && courseList.get(i).getCourseName().equals(courseName)) {
				index = i;
			}
		}
		return index;
	}

	public void printAllCourse() { // print all current course on the courseList
		System.out.println();
		for (int i = 0; i < courseList.size(); i++) {
			System.out.println(courseList.get(i).getCourseName() + " " + courseList.get(i).getCourseNum());
		}
	}

	public void getCourseNameId(String name, int id) {
		int i = 0;
		for (i = 0; i < courseList.size(); i++) {
			if (name.equalsIgnoreCase(courseList.get(i).getCourseName()) && courseList.get(i).getCourseNum() == id) {
				System.out.println(
						courseList.get(i).getCourseName() + " " + courseList.get(i).getCourseNum() + " is found!");
				break;
			}
		}

		if (i == courseList.size()) {
			System.out.println(name + " " + id + " is not in the catalogue.");
		}

	}

	public void displayToString() {
		System.out.println(toString());
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

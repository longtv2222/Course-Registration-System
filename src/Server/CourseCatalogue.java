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
			Lecture theOffering = new Lecture(secNum, secCap);
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

	public String coursesTakenByStudents() {
		String s = "Courses taken by students are\n";
		for (int i = 0; i < courseList.size(); i++) {
			if (courseList.get(i).checkStudentNumberThisCourse() != 0) {
				s += courseList.get(i).getCourseName() + " " + courseList.get(i).getCourseNum() + "\n";
			}
		}
		return s;
	}

	private void displayCourseNotFoundError() {
		System.err.println("Course was not found!");

	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

	public String removeCourse(String courseName, int courseID) {
		String courseNameUpperCase = courseName.toUpperCase(); // eliminate lower case
		int index = indexOfName(courseNameUpperCase, courseID);
		if (index < 0) {
			return courseName + " " + courseID + " is not on the list.";
		}

		courseList.remove(index);
		String s = courseNameUpperCase + " " + courseID + " has been removed\n";
		s += "The list after removing " + courseNameUpperCase + " " + courseID + " element is:\n";
		s += this.toString();
		return s;
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

	public String getCourseNameId(String name, int id) {
		int i = 0;
		for (i = 0; i < courseList.size(); i++) {
			if (name.equalsIgnoreCase(courseList.get(i).getCourseName()) && courseList.get(i).getCourseNum() == id) {
				return courseList.get(i).getCourseName() + " " + courseList.get(i).getCourseNum() + " is found!";

			}
		}

		if (i == courseList.size()) {
			return name + " " + id + " is not in the catalogue.";
		} else
			return null;

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

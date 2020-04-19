package crs.src.server;

import java.util.ArrayList;

public class CourseCatalogue {

	private ArrayList<Course> courseList;

	public CourseCatalogue() {
		loadFromDataBase();

		Student st = new Student("Sara", 1);
		Student st2 = new Student("Sam", 2);
		Student st3 = new Student("Sophie", 3);
		Student st4 = new Student("Stacy", 4);
		Student st5 = new Student("Steve", 5);
		Student st6 = new Student("Stan", 6);
		Student st7 = new Student("Saul", 7);
		Student st8 = new Student("Sean", 8);
		Student st9 = new Student("Stark", 9);
		Student st10 = new Student("Salem", 10); // Adding students

		Course myCourse = this.searchCat("ENGG", 233);
		if (myCourse != null) { // Creating course Offering
			this.createCourseOffering(myCourse, 1, 10);
			this.createCourseOffering(myCourse, 2, 200);
		}

		Course myCourse2 = this.searchCat("ENSF", 409);
		if (myCourse2 != null) { // Creating course Offering
			this.createCourseOffering(myCourse2, 1, 60);
		}

		Course myCourse3 = this.searchCat("PHYS", 259);
		if (myCourse3 != null) { // Creating course Offering
			this.createCourseOffering(myCourse3, 1, 250);
		}

		Registration reg = new Registration();
		Registration reg2 = new Registration();
		Registration reg3 = new Registration();
		Registration reg4 = new Registration();
		Registration reg5 = new Registration();
		Registration reg6 = new Registration();
		Registration reg7 = new Registration();
		Registration reg8 = new Registration();
		Registration reg9 = new Registration();
		Registration reg10 = new Registration();
		reg.completeRegistration(st, myCourse.getLectureAt(0));
		reg2.completeRegistration(st2, myCourse.getLectureAt(0));
		reg3.completeRegistration(st3, myCourse.getLectureAt(0));
		reg4.completeRegistration(st4, myCourse.getLectureAt(0));
		reg5.completeRegistration(st5, myCourse.getLectureAt(0));
		reg6.completeRegistration(st6, myCourse.getLectureAt(0));
		reg7.completeRegistration(st7, myCourse.getLectureAt(0));
		reg8.completeRegistration(st8, myCourse.getLectureAt(0));
		reg9.completeRegistration(st9, myCourse.getLectureAt(0));
		reg10.completeRegistration(st10, myCourse.getLectureAt(0));
	}

	private void loadFromDataBase() {
		DBManager db = new DBManager();
		setCourseList(db.readFromDataBase());

	}

	public void createCourseOffering(Course c, int secNum, int secCap) {
		if (c != null) {
			Lecture lecture = new Lecture(secNum, secCap);
			c.addLecture(lecture);
		}
	}

	public Course searchCat(String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) && courseNum == c.getCourseNum()) {
				return c;
			}
		}
		return null;
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

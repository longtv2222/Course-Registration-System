package CRS.src.server;

import java.util.ArrayList;

public class Course {
	/**
	 * courseName is the name of the course.
	 */
	private String courseName;
	/**
	 * courseNum is the course ID.
	 */
	private int courseNum;

	/**
	 * preReq is required courses finished to enter this class.
	 */
	private ArrayList<Course> preReq;
	/**
	 * lectures is the total lecture of this course.
	 */
	private ArrayList<Lecture> lectures;

	/**
	 * Constructor of course that intializes member variables accordingly with given
	 * data.
	 * 
	 * @param courseName is the name of the course.
	 * @param courseNum  is the course ID.
	 */
	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		// Both of the following are only association
		preReq = new ArrayList<Course>();
		lectures = new ArrayList<Lecture>();
	}

	/**
	 * Add a prerequisite to this course.
	 * 
	 * @param preReq is new courses required to enter this class.
	 */
	public void addPreReq(Course preReq) {
		if (preReq != null && preReq.getCourseName().contentEquals(this.courseName)) {
			System.err.println("Error! Cannot have course be prerequisite for itself!");
			return;
		}
		this.preReq.add(preReq);
	}

	/**
	 * Get the prerequisite of this course.
	 * 
	 * @return preReq.
	 */
	public ArrayList<Course> getPreReqs() {
		return this.preReq;
	}

	/**
	 * Add a new lecture to this course.
	 * 
	 * @param lecture is the new lecture of this course.
	 */
	public void addLecture(Lecture lecture) {
		if (lecture != null && lecture.getTheCourse() == null) {
			lecture.setTheCourse(this);
			lectures.add(lecture);
			return;
		}
		System.err.println("Error! This section belongs to another course!");
	}

	/**
	 * Get the name of the course.
	 * 
	 * @return courseName.
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Set the name of the course.
	 * 
	 * @param courseName is the new name of the course.
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Get the number of the course.
	 * 
	 * @return courseNum.
	 */
	public int getCourseNum() {
		return courseNum;
	}

	/**
	 * Set the number of the course.
	 * 
	 * @param courseNum is the new number of the course.
	 */
	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}

	/**
	 * Display all necessary data of this course.
	 * 
	 * @return st is a string that contains all the data of this course.
	 */
	@Override
	public String toString() {
		String st = "\n";
		st += getCourseName() + " " + getCourseNum();
		st += "\nAll course lectures:\n";
		for (Lecture c : lectures) {
			st += c;
		}
		st += "\nAll course prerequisites:\n";
		for (Course c : preReq)
			st += "Course: " + c.getCourseName() + " " + c.getCourseNum() + "\n";

		st += "\n-------\n";
		return st;
	}

	/**
	 * Get the lecture with given section of this course.
	 * 
	 * @param section is the section of the lecture that needs to be found
	 * @return object of type Lecture if that lecture exists, otherwise returns
	 *         null.
	 */
	public Lecture getLectureSection(int section) {
		for (Lecture l : this.lectures)
			if (l.getSecNum() == section)
				return l;
		return null;
	}

}

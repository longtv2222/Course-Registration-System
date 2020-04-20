package CRS.src.server;

import java.util.ArrayList;

public class Student {
	/**
	 * studenetName is the name of the student.
	 */
	private String studentName;
	/**
	 * studentId is the ID of the student.
	 */
	private int studentId;
	/**
	 * studentRegList is an array list that contains every registration student
	 * have. made.
	 */
	private ArrayList<Registration> studentRegList;
	/**
	 * studentTakenList is an array list that shows which courses students have
	 * taken.
	 */
	private ArrayList<Course> studentTakenList;

	/**
	 * Constructor of class student that initializes variables of class Student with
	 * according parameters.
	 * 
	 * @param studentName is the name of the student.
	 * @param studentId   is the ID of the student.
	 */
	public Student(String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
		studentTakenList = new ArrayList<Course>();
	}

	/**
	 * Find if student is in certain lecture or not.
	 * 
	 * @param offering is the lecture student may be in.
	 * @return r which is an object of type Registration if student is in that
	 *         lecture. Otherwise return null.
	 */
	public Registration findRegistration(Lecture offering) {
		for (Registration r : studentRegList) {
			if (r.getTheLecture().equals(offering))
				return r;
		}
		return null;
	}

	/**
	 * Check if student has taken a course or not.
	 * 
	 * @param course is the course student may have taken.
	 * @return c which is an object of type Course if student has taken that course.
	 *         Otherwise return null.
	 */
	public Course findTaken(Course course) {
		for (Course c : studentTakenList) {
			if (c.equals(course))
				return c;
		}
		return null;
	}

	/**
	 * Add a course to student taken list of courses.
	 * 
	 * @param course is the course student wants to add.
	 */
	public void addTaken(Course course) {
		this.studentTakenList.add(course);
	}

	/**
	 * Get member variable studentName.
	 * 
	 * @return student's name.
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * Set student's name.
	 * 
	 * @param studentName is a new name of string studentName.
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * Get member variable studentId.
	 * 
	 * @return student's ID.
	 */
	public int getStudentId() {
		return studentId;
	}

	/**
	 * Set student's Id.
	 * 
	 * @param studentId is a new ID of integer studentId.
	 */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	/**
	 * Add a new registration to this student's registration list.
	 * 
	 * @param registration is the new registration student wishes to add.
	 */
	public void addRegistration(Registration registration) {
		studentRegList.add(registration);
	}

	/**
	 * Remove a registration out of this student's registration list.
	 * 
	 * @param registration is the registration student wishes to remove.
	 */
	public void removeRegistration(Registration registration) {
		studentRegList.remove(registration);
	}

	/**
	 * Check if student can register for a course.
	 * 
	 * @param course is the courses student wants to register.
	 * @return true if student can register for that course. False if student
	 *         cannot.
	 */
	public boolean canRegister(Course course) {
		if (this.studentRegList.size() >= 6) {
			System.err.println("Error! Student cannot register, no schedual availablility.");
			return false;
		}
		if (!this.studentTakenList.isEmpty() && this.studentTakenList.contains(course)) {
			if (!this.studentTakenList.containsAll(course.getPreReqs())) {
				System.err.println("Error! Student cannot register, they have not completed all prerequisites.");
				return false;
			}
		}
		return true;
	}

	/**
	 * Display all the courses student has registered in.
	 * 
	 * @return a string that displays all the courses this student has registered
	 *         in.
	 */
	public String listRegistered() {
		String s = "Courses registered in:";
		for (Registration r : this.studentRegList) {
			s += r + "\n";
		}
		return s;
	}

	/**
	 * Display all the courses student has taken.
	 * 
	 * @return a string that displays all the courses this student has taken.
	 */
	public String listTaken() {
		String s = "Courses taken:";
		for (Course c : this.studentTakenList) {
			s += c.getCourseName() + " " + c.getCourseNum() + "\n";
		}
		return s;
	}

	/**
	 * Display student's name and ID.
	 * 
	 * @return a string that display this student's name and ID.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder("Student Name: ");
		s.append(getStudentName());
		s.append("\nStudent Id: ");
		s.append(getStudentId());
		return s.toString();
	}

	/**
	 * Get the registration list of this student.
	 * 
	 * @return Member variable studentRegList.
	 */
	public ArrayList<Registration> getStudentRegList() {
		return this.studentRegList;
	}

}

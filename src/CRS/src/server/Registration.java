package CRS.src.server;

public class Registration {
	/**
	 * theStudent is an object of type Student.
	 */
	private Student theStudent;
	/**
	 * theLecture is an object of type Lecture.
	 */
	private Lecture theLecture;
	/**
	 * Grade of the student in this course.
	 */
	private char grade;

	/**
	 * Complete registration process of a student
	 * 
	 * @param st is the student.
	 * @param of is the lecture.
	 * @return a string that displays the result of registration.
	 */
	public String completeRegistration(Student st, Lecture of) {
		this.setTheStudent(st);
		this.setTheLecture(of);
		return this.addRegistration();
	}

	/**
	 * Add a registration to student.
	 * 
	 * @return a string that displays the result of registration.
	 */
	private String addRegistration() {
		if (theLecture.canRegister() && theStudent.canRegister(theLecture.getTheCourse())) {
			theStudent.addRegistration(this);
			theLecture.addRegistration(this);
			return theLecture.getTheCourse().getCourseName() + " " + theLecture.getTheCourse().getCourseNum()
					+ " section " + theLecture.getSecNum() + " has been added to your cart.";
		} else
			return "ERROR Cannot register for class!";
	}

	/**
	 * Remove a registration.
	 */
	public void removeRegistration() {
		theStudent.removeRegistration(this);
		theLecture.removeRegistration(this);
	}

	/**
	 * Get member variable theStudent.
	 * 
	 * @return theStudent
	 */
	public Student getTheStudent() {
		return theStudent;
	}

	/**
	 * Set the member variable theStudent.
	 * 
	 * @param theStudent is a new object of member variable theStudent.
	 */
	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}

	/**
	 * Get member variable theLecture.
	 * 
	 * @return theLecture.
	 */
	public Lecture getTheLecture() {
		return theLecture;
	}

	/**
	 * Set the member variable theLecture.
	 * 
	 * @param theStudent is a new object of member variable theLecture.
	 */
	public void setTheLecture(Lecture theLecture) {
		this.theLecture = theLecture;
	}

	/**
	 * Get the grade of the student in this course.
	 * 
	 * @return grade.
	 */
	public char getGrade() {
		return grade;
	}

	/**
	 * Displays the student's name, lecture and grade.
	 * 
	 * @return String that display all data in this class.
	 */
	@Override
	public String toString() {
		String st = "\n";
		st += "Student Name: " + getTheStudent() + "\n";
		st += "The Lecture: " + getTheLecture() + "\n";
		st += "Grade: " + getGrade();
		st += "\n-----------\n";
		return st;

	}

}

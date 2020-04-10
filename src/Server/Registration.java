package Server;

public class Registration {
	private Student theStudent;
	private Lecture theOffering;
	private char grade;

	String completeRegistration(Student st, Lecture of) {

		theStudent = st;
		theOffering = of;
		addRegistration();
		if (!theStudent.maximumCourse()) { // if student exceeds course registration limit or current number of student
											// >= Section Cap

			theStudent.removeMaxCourse(); // Undo addRegistration
			theOffering.removeMaxOffering();
			return st.getStudentName() + " cannot register for " + of.getTheCourse().getCourseName() + " "
					+ of.getTheCourse().getCourseNum() + ".This student is only allowed have 6 courses at maximum.";
		}
		if (theOffering.numberOfStudentThisOffering() > theOffering.getSecCap()) {
			return "There is currently no available spot in " + of.getTheCourse().getCourseName() + " "
					+ of.getTheCourse().getCourseNum();
		} else
			return null;
	}

	private void addRegistration() {
		theStudent.addRegistration(this);
		theOffering.addRegistration(this, theStudent);
	}

	public Student getTheStudent() {
		return theStudent;
	}

	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}

	public Lecture getTheOffering() {
		return theOffering;
	}

	public void setTheOffering(Lecture theOffering) {
		this.theOffering = theOffering;
	}

	public char getGrade() {
		return grade;
	}

	public void setGrade(char grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		String st = "\n";
		st += "Student Name: " + getTheStudent() + "\n";
		st += "The Offering: " + getTheOffering() + "\n";
		st += "Grade: " + getGrade();
		st += "\n-----------\n";
		return st;

	}

}

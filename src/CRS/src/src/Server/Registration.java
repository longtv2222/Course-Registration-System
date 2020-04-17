package src.Server;

public class Registration {
	private Student theStudent;
	private CourseOffering theOffering;
	private char grade;

	public String completeRegistration(Student st, CourseOffering of) {
		this.setTheStudent(st);
		this.setTheOffering(of);
		return this.addRegistration();
	}

	private String addRegistration() {
		if (theOffering.canRegister() && theStudent.canRegister()) {
			theStudent.addRegistration(this);
			theOffering.addRegistration(this);
			return "Add course successfully!";
		} else
			return "Error! Cannot register for class!";
	}

	public void removeRegistration() {
		theStudent.removeRegistration(this);
		theOffering.removeRegistration(this);
	}

	public Student getTheStudent() {
		return theStudent;
	}

	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}

	public CourseOffering getTheOffering() {
		return theOffering;
	}

	public void setTheOffering(CourseOffering theOffering) {
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

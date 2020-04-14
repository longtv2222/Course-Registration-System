package CRS.src.Server;

public class Registration {
	private Student theStudent;
	private CourseOffering theOffering;
	private char grade;

	public void completeRegistration(Student st, CourseOffering of) {
		this.setTheStudent(st);
		this.setTheOffering(of);
		this.addRegistration();
	}

	private void addRegistration() {
		if (theOffering.canRegister() && theStudent.canRegister()) {
			theStudent.addRegistration(this);
			theOffering.addRegistration(this);
		} else
			System.out.println("Error! Cannot register for class!");
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

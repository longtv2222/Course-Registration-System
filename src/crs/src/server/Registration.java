package crs.src.server;

public class Registration {
	private Student theStudent;
	private Lecture theLecture;
	private char grade;

	public String completeRegistration(Student st, Lecture of) {
		this.setTheStudent(st);
		this.setTheLecture(of);
		return this.addRegistration();
	}

	private String addRegistration() {
		if (theLecture.canRegister() && theStudent.canRegister(theLecture.getTheCourse())) {
			theStudent.addRegistration(this);
			theLecture.addRegistration(this);
			return theLecture.getTheCourse().getCourseName() + " " + theLecture.getTheCourse().getCourseNum()
					+ " section " + theLecture.getSecNum() + " has been added to your cart.";
		} else
			return "ERROR Cannot register for class!";
	}

	public void removeRegistration() {
		theStudent.removeRegistration(this);
		theLecture.removeRegistration(this);
	}

	public Student getTheStudent() {
		return theStudent;
	}

	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}

	public Lecture getTheLecture() {
		return theLecture;
	}

	public void setTheLecture(Lecture theLecture) {
		this.theLecture = theLecture;
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
		st += "The Lecture: " + getTheLecture() + "\n";
		st += "Grade: " + getGrade();
		st += "\n-----------\n";
		return st;

	}

}

package Server;
import java.util.ArrayList;

public class Lecture {

	private int secNum;
	private int secCap;
	private Course theCourse; // name of the course
	private ArrayList<Student> studentList; // Display every students in this course
	private ArrayList<Registration> offeringRegList; // What does this do?

	public Lecture(int secNum, int secCap) {
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		offeringRegList = new ArrayList<Registration>();
		studentList = new ArrayList<Student>();
	}

	public int getSecNum() {
		return secNum;
	}

	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}

	public int getSecCap() {
		return secCap;
	}

	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}

	public Course getTheCourse() {
		return theCourse;
	}

	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}

	@Override
	public String toString() {
		String st = "\n";
		st += getTheCourse().getCourseName() + " " + getTheCourse().getCourseNum() + "\n";
		st += "Section Num: " + getSecNum() + ", section cap: " + getSecCap() + "\n";
		// We also want to print the names of all students in the section
		return st;
	}

	public void addRegistration(Registration registration, Student t) { // change addRegistration
		offeringRegList.add(registration);
		studentList.add(t);
	}

	public void removeMaxOffering() {
		offeringRegList.remove(offeringRegList.size() - 1);
		studentList.remove(studentList.size() - 1);
	}

	public int numberOfStudentThisOffering() {
		return studentList.size();
	}

}

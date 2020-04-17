package src.Server;

import java.util.ArrayList;

public class CourseOffering {

	private int secNum;
	private int secCap;
	private Course theCourse;
	// private ArrayList<Student> studentList;
	private ArrayList<Registration> offeringRegList;

	public CourseOffering(int secNum, int secCap) {
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		offeringRegList = new ArrayList<Registration>();
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
		st += "Students:\n";
		for (Registration r : this.offeringRegList) {
			st += r.getTheStudent();
		}
		return st;
	}

	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		offeringRegList.add(registration);

	}

	public void removeRegistration(Registration registration) {
		offeringRegList.remove(registration);
	}

	public void runCourse() {
		System.out.println("Trying to run the following course offering:");
		System.out.println(this);
		if (canRun()) {
			System.err.println("Error! Issue with amount of students in this class! Cannot run!");
		}
		System.out.println("Successfully began running the course offering!");
	}

	public boolean canRun() {
		if (this.offeringRegList.size() < 8)
			return false;
		return this.canRegister();
	}

	public boolean canRegister() {
		if (this.offeringRegList.size() >= this.getSecCap())
			return false;
		return true;
	}
}

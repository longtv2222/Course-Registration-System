package crs.src.server;

import java.util.ArrayList;

public class Student {

	private String studentName;
	private int studentId;
	private ArrayList<Registration> studentRegList;
	private ArrayList<Course> studentTakenList;

	public Student(String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
		studentTakenList = new ArrayList<Course>();
	}

	public Registration findRegistration(Lecture offering) {
		for (Registration r : studentRegList) {
			if (r.getTheLecture().equals(offering))
				return r;
		}
		return null;
	}

	public Course findTaken(Course course) {
		for (Course c : studentTakenList) {
			if (c.equals(course))
				return c;
		}
		return null;
	}

	public void addTaken(Course course) {
		this.studentTakenList.add(course);
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	@Override
	public String toString() {
		String st = "Student Name: " + getStudentName() + "\n" + "Student Id: " + getStudentId() + "\n\n";
		return st;
	}

	public void addRegistration(Registration registration) {
		studentRegList.add(registration);
	}

	public void removeRegistration(Registration registration) {
		studentRegList.remove(registration);
	}

	public boolean canRegister(Course course) {
//		System.out.println("start testing!");
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
//		System.out.println("testing worked!");
		return true;
	}

	public String listRegistered() {
		String s = "Courses registered in:";
		for (Registration r : this.studentRegList) {
			s += r + "\n";
		}
		return s;
	}

	public String listTaken() {
		String s = "Courses taken:";
		for (Course c : this.studentTakenList) {
			s += c.getCourseName() + " " + c.getCourseNum() + "\n";
		}
		return s;
	}

	public ArrayList<Registration> getStudentRegList() {
		return this.studentRegList;
	}

}

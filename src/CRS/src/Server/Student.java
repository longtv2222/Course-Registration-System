package CRS.src.Server;

import java.util.ArrayList;

public class Student {

	private String studentName;
	private int studentId;
	private ArrayList<Registration> studentRegList;

	public Student(String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
	}

	public Registration findRegistration(CourseOffering offering) {
		for (Registration r : studentRegList) {
			if (r.getTheOffering().equals(offering))
				return r;
		}
		return null;
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

	public boolean canRegister() {
		if (this.studentRegList.size() < 6)
			return true;
		System.err.println("Error! Student cannot register, no schedual availablility.");
		return false;
	}

	public String listRegistered() {
		String s = "";
		for (Registration r : this.studentRegList) {
			s += r + "\n";
		}
		return s;
	}

}

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
		if (this.studentRegList.size() >= 6) {
			System.err.println("Error! Student cannot register, no schedual availablility.");
			return false;
		}
		if (!this.studentTakenList.containsAll(course.getPreReqs())) {
			System.err.println("Error! Student cannot register, has not taken all prereqs!.");
			return false;
		}
		for(Course sr : (course.getSubReqs())) {
			if(this.studentTakenList.contains(sr)==false) {
				boolean contains = false;
				for(Registration r : this.studentRegList) {
					if(sr.equals(r.getTheLecture().getTheCourse())) {
						contains = true;
					}
				}
				if(!contains) {
					System.err.println("Errorr! Student cannot register, has not taken or is not taking all subreqs!");
					return false;
				}
			}
		}
		return true;
	}

	public String listRegistered() {
		String s = "";
		for (Registration r : this.studentRegList) {
			s += r + "\n";
		}
		return s;
	}

	public ArrayList<Registration> getStudentRegList() {
		return this.studentRegList;
	}

}

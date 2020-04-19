package crs.src.server;

import java.util.ArrayList;

public class Course {

	private String courseName;
	private int courseNum;
	private ArrayList<Course> preReq;
	private ArrayList<Course> subReq;
	private ArrayList<Lecture> lectures;

	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		// Both of the following are only association
		preReq = new ArrayList<Course>();
		lectures = new ArrayList<Lecture>();
	}

	public void addPreReq(Course preReq) {
		if (preReq != null && preReq.getCourseName().contentEquals(this.courseName)) {
			System.err.println("Error! Cannot have course be prerequisite for itself!");
			return;
		}
		this.preReq.add(preReq);
	}
	public void addSubReq(Course subReq) {
		if (preReq != null && subReq.getCourseName().contentEquals(this.courseName)) {
			System.err.println("Error! Cannot have course be subrequisite for itself!");
			return;
		}
		this.subReq.add(subReq);
	}

	public ArrayList<Course> getPreReqs() {
		return this.preReq;
	}
	public ArrayList<Course> getSubReqs() {
		return this.subReq;
	}
	
	public void addLecture(Lecture lecture) {
		if (lecture != null && lecture.getTheCourse() == null) {
			lecture.setTheCourse(this);
			if (!lecture.getTheCourse().getCourseName().equals(courseName)
					|| lecture.getTheCourse().getCourseNum() != courseNum) {
				System.err.println("Error! This section belongs to another course!");
				return;
			}

			lectures.add(lecture);
		}
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}

	@Override
	public String toString() {
		String st = "\n";
		st += getCourseName() + " " + getCourseNum();
		st += "\nAll course sections:\n";
		for (Lecture c : lectures)
			st += c;

		st += "\nAll course prerequisites:\n";
		for (Course c : preReq)
			st += "Course: " + c.getCourseName() + " " + c.getCourseNum() + "\n";

		st += "\n-------\n";
		return st;
	}

	public Lecture getCourseOfferingAt(int i) {
		// TODO Auto-generated method stub
		if (i < 0 || i >= lectures.size())
			return null;
		else
			return lectures.get(i);
	}

	public Lecture getCourseOfferingSection(int section) {
		for (Lecture o : this.lectures)
			if (o.getSecNum() == section)
				return o;
		return null;
	}

}

package src.Server;

import java.util.ArrayList;

public class Course {

	private String courseName;
	private int courseNum;
	private ArrayList<Course> preReq;
	private ArrayList<CourseOffering> offeringList;

	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		// Both of the following are only association
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
	}

	public void addPreReq(Course preReq) {
		if (preReq != null && preReq.getCourseName().contentEquals(this.courseName)) {
			System.err.println("Error! Cannot have course be prerequisite for itself!");
			return;
		}
		this.preReq.add(preReq);
	}

	public void addOffering(CourseOffering offering) {
		if (offering != null && offering.getTheCourse() == null) {
			offering.setTheCourse(this);
			if (!offering.getTheCourse().getCourseName().equals(courseName)
					|| offering.getTheCourse().getCourseNum() != courseNum) {
				System.err.println("Error! This section belongs to another course!");
				return;
			}

			offeringList.add(offering);
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
		for (CourseOffering c : offeringList)
			st += c;

		st += "\nAll course prerequisites:\n";
		for (Course c : preReq)
			st += "Course: " + c.getCourseName() + " " + c.getCourseNum() + "\n";

		st += "\n-------\n";
		return st;
	}

	public CourseOffering getCourseOfferingAt(int i) {
		// TODO Auto-generated method stub
		if (i < 0 || i >= offeringList.size())
			return null;
		else
			return offeringList.get(i);
	}

	public CourseOffering getCourseOfferingSection(int section) {
		for (CourseOffering o : this.offeringList)
			if (o.getSecNum() == section)
				return o;
		return null;
	}

}

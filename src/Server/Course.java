package Server;
import java.util.ArrayList;

public class Course {

	private String courseName;
	private int courseNum;
	private ArrayList<Course> preReq; // List of preReq course for this current course
	private ArrayList<CourseOffering> offeringList; // Section number,etc for this course

	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		// Both of the following are only association
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
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

	public int checkStudentNumberThisCourse() {
		int students = 0;
		for (int i = 0; i < offeringList.size(); i++) {
			students += offeringList.get(i).numberOfStudentThisOffering();
		}
		return students;
	}

	public void conditionStudentNumberThisCourse() {
		int students = checkStudentNumberThisCourse();
		System.out.println("Total number of student in " + courseName + " " + courseNum + " is " + students + ".");
		if (students < 8) {
			System.out.println("Since the number of student in " + courseName + " " + courseNum
					+ " is smaller than 8, this course cannot be run.");
		}
	}


	@Override
	public String toString() {
		String st = "\n";
		st += getCourseName() + " " + getCourseNum();
		st += "\nAll course sections:\n";
		for (CourseOffering c : offeringList)
			st += c;
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

	public void addPreReq(Course preReq) { // Adding preReq for this course
		this.preReq.add(preReq);
	}
}

package Server;
import java.util.ArrayList;

public class Student {

	private String studentName;
	private int studentId;
//	private ArrayList<CourseOffering> offeringList;  //What does this do?
	private ArrayList<Registration> studentRegList; // Every course this student in

	public Student(String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
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

	public boolean maximumCourse() { // Check to see if this student exceeds 6 courses or not, this function is called in completeRegistration
		if (this.studentRegList.size() > 6)
			return false;
		else
			return true;
	}

	public void removeMaxCourse() { // remove when registration failed
		studentRegList.remove(studentRegList.size() - 1);
	}

	public ArrayList<Registration> getStudentRegList() {
		return studentRegList;
	}

	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		studentRegList.add(registration);
	}

}

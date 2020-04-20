package CRS.src.server;

import java.util.ArrayList;

public class Application {

	private ArrayList<Course> courseList;
	private ArrayList<Student> studentList;
	DBManager db;

	public Application() {
		loadFromDataBase();
	}

	private void loadFromDataBase() {
		db = new DBManager();
		this.courseList = db.readCoursesFromDataBase();
		this.studentList = db.readStudentsFromDataBase();
		db.setupStudentsCoursesDatabase(studentList, courseList);
	}

	public String addNewCourse(String courseName, int courseNum) {
		Course course = searchCourses(courseName, courseNum);
		if (course != null)
			return courseName + " " + courseNum + " has already existed in the system.ERROR";
		else {
			Course addedCourse = new Course(courseName, courseNum);
			db.addCourse(new Course(courseName, courseNum));
			courseList.add(addedCourse);
			return courseName + " " + courseNum + " has already added successfully.";
		}
	}

	public String createLecture(String courseName, int courseNum, int secNum, int secCap) { // Gonna implement this.
		Course course = searchCourses(courseName, courseNum);
		if (course != null) {// If the course exist, attempt to create lecture for it
			if (course.getLectureSection(secNum) != null) // If the lecture exists, do not create lecture with same
															// secNum with it.
				return "The section " + secNum + " of " + courseName + " has already existed in the system.ERROR";
			else { // Creating lecture.
				Lecture lecture = new Lecture(secNum, secCap);
				course.addLecture(lecture);
				db.addLecture(lecture);
				return "Added lecture " + secNum + " with capacity of " + secCap + " student successfully.";
			}
		} else
			return courseName + " " + courseNum + " does not exist in the system.";
	}

	public Course searchCourses(String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) && courseNum == c.getCourseNum()) {
				return c;
			}
		}
		return null;
	}

	public Student searchStudents(int id) { // Gonna implement this
		for (Student s : studentList) {
			if (s.getStudentId() == id) {
				return s;
			}
		}
		return null;
	}

	public Student loadStudent(String name, int id) { // Rewrite load student later.
		Student student = this.searchStudents(id);
		if (student != null) {
			// student with same id was found.
			if (student.getStudentName().equals(name)) {
				// they have the same name, so lets return them!
				return student;
			}
			// otherwise, we return null...
			return null;
		}
		// no student with said id was found, so lets go ahead and create a new student!
		student = new Student(name, id);
		this.addStudent(student);
		return student;
	}

	public String addNewStudent(String name, int id) {
		Student student = this.searchStudents(id);
		if (student != null) {
			if (student.getStudentName().equals(name)) {
				// they have the same name, so lets return them!
				return "The student " + student.toString() + " has already existed in the system. ERROR";
			}
			return "The student with ID: " + id + " has already existed in the system. ERROR";
		}
		student = new Student(name, id);
		this.addStudent(student);
		return "Added new student " + student.getStudentName() + " ID: " + student.getStudentId() + " successfully.";
	}

	public void addStudent(Student student) {
		this.studentList.add(student);
		db.addStudent(student);
	}

	public String viewCourses() {
		StringBuilder s = new StringBuilder("All courses in the catalogue: \n");
		for (Course c : courseList) {
			s.append(c + "\n"); // This line invokes the toString() method of Course
		}
		return s.toString();
	}

	public String viewStudentInfo(Student student) { //
		StringBuilder s = new StringBuilder("All information on the selected student in the catalogue: \n");
		s.append(student); // call the toString() method in Student.
		return s.toString();
	}

	public String viewStudents() { // Done
		StringBuilder s = new StringBuilder("All information on the students in the catalogue: \n");
		for (Student student : studentList) {
			s.append(student + "\n"); // call the toString() method in Student.
		}
		return s.toString();
	}

	public String registerStudentCourse(Student student, Lecture lecture) { // Done
		Registration register = new Registration();
		String s = register.completeRegistration(student, lecture);
		db.addRegistration(register);
		return s;
	}

	public void removeRegistration(Registration reg) { // Done
		db.removeRegistration(reg);
		reg.removeRegistration();
	}

	public void close() {
		db.close();
	}

}

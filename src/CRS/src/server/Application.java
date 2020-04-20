package CRS.src.server;

import java.util.ArrayList;

public class Application {
	/**
	 * courseList is a list of courses in the system.
	 */
	private ArrayList<Course> courseList;
	/**
	 * studentList is a list of students in the system.
	 */
	private ArrayList<Student> studentList;
	/**
	 * db is responsible for managing the data from the database.
	 */
	DBManager db;

	/**
	 * Default constructor of class Aplication that loads data from the data base to
	 * member variables courseList and studentList.
	 */
	public Application() {
		loadFromDataBase();
	}

	/**
	 * Load data from the database.
	 */
	private void loadFromDataBase() {
		db = new DBManager();
		this.courseList = db.readCoursesFromDataBase();
		this.studentList = db.readStudentsFromDataBase();
		db.setupStudentsCoursesDatabase(studentList, courseList);
	}

	/**
	 * Add a new course to the database.
	 * 
	 * @param courseName is the name of the new course.
	 * @param courseNum  is the number of the new course.
	 * @return string that indicates the result of adding this new course.
	 */
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

	/**
	 * Add a lecture to a course.
	 * 
	 * @param courseName is the name of the lecture will be added to.
	 * @param courseNum  is the number of the lecture will be added to.
	 * @param secNum     is the section number of this lecture.
	 * @param secCap     is the section capacity of this lecture.
	 * @return string that indicates the result of adding the lecture.
	 */
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

	/**
	 * Search for a course in the course List.
	 * 
	 * @param courseName is the name of the course that needs to be searched.
	 * @param courseNum  is the number of the courses that needs to be searched.
	 * @return object of type course if the searched course is found. Otherwise
	 *         returns null.
	 */
	public Course searchCourses(String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) && courseNum == c.getCourseNum()) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Search for a student.
	 * 
	 * @param id is the id of student that needs to be searched.
	 * @return object of type student if the searched student is found. Otherwise
	 *         returns null.
	 */
	public Student searchStudents(int id) { // Gonna implement this
		for (Student s : studentList) {
			if (s.getStudentId() == id) {
				return s;
			}
		}
		return null;
	}

	/**
	 * Load the data of the student with given name and id.
	 * 
	 * @param name is the name of student.
	 * @param id   is the id of student.
	 * @return If the student with given name and id was not found, create a new
	 *         profile for that student.
	 */
	public Student loadStudent(String name, int id) {
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

	/**
	 * Add a new student to the system.
	 * 
	 * @param name is the name of the new student.
	 * @param id   is the id of the new student.
	 * @return string that indicates the result of adding the student.
	 */
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

	/**
	 * Add a student to student list and to database.
	 * 
	 * @param student is the object of student that needs to be added.
	 */
	public void addStudent(Student student) {
		this.studentList.add(student);
		db.addStudent(student);
	}

	/**
	 * View all the courses in the system.
	 * 
	 * @return string that contains necessary information of all courses.
	 */
	public String viewCourses() {
		StringBuilder s = new StringBuilder("All courses in the catalogue: \n");
		for (Course c : courseList) {
			s.append(c + "\n"); // This line invokes the toString() method of Course
		}
		return s.toString();
	}

	/**
	 * View all the students in the system.
	 * 
	 * @return string that contains necessary information of all students.
	 */
	public String viewStudents() { // Done
		StringBuilder s = new StringBuilder("All information on the students in the catalogue: \n");
		for (Student student : studentList) {
			s.append(student + "\n"); // call the toString() method in Student.
		}
		return s.toString();
	}

	/**
	 * Register a student to a lecture.
	 * 
	 * @param student is the student that needs to be added to the lecture.
	 * @param lecture is the lecture that will accept the student.
	 * @return
	 */
	public String registerStudentCourse(Student student, Lecture lecture) { // Done
		Registration register = new Registration();
		String s = register.completeRegistration(student, lecture);
		db.addRegistration(register);
		return s;
	}

	/**
	 * Remove a registration out of the system.
	 * 
	 * @param reg is the registration that needs to be removed.
	 */
	public void removeRegistration(Registration reg) { // Done
		db.removeRegistration(reg);
		reg.removeRegistration();
	}

	/**
	 * This method close the connection with the database.
	 */
	public void close() {
		db.close();
	}

}

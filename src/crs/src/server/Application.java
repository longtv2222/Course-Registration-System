package crs.src.server;

import java.util.ArrayList;

public class Application {

	private ArrayList<Course> courseList;
	private ArrayList<Student> studentList;
	DBManager db;

	public Application() {
		loadFromDataBase();
//
//		Student st = new Student("Sara", 1);
//		Student st2 = new Student("Sam", 2);
//		Student st3 = new Student("Sophie", 3);
//		Student st4 = new Student("Stacy", 4);
//		Student st5 = new Student("Steve", 5);
//		Student st6 = new Student("Stan", 6);
//		Student st7 = new Student("Saul", 7);
//		Student st8 = new Student("Sean", 8);
//		Student st9 = new Student("Stark", 9);
//		Student st10 = new Student("Salem", 10); // Adding students
//
//		Course myCourse = this.searchCat("ENGG", 233);
//		if (myCourse != null) { // Creating course Offering
//			this.createCourseOffering(myCourse, 1, 10);
//			this.createCourseOffering(myCourse, 2, 200);
//		}
//
//		Course myCourse2 = this.searchCat("ENSF", 409);
//		if (myCourse2 != null) { // Creating course Offering
//			this.createCourseOffering(myCourse2, 1, 60);
//		}
//
//		Course myCourse3 = this.searchCat("PHYS", 259);
//		if (myCourse3 != null) { // Creating course Offering
//			this.createCourseOffering(myCourse3, 1, 250);
//		}
//
//		Registration reg = new Registration();
//		Registration reg2 = new Registration();
//		Registration reg3 = new Registration();
//		Registration reg4 = new Registration();
//		Registration reg5 = new Registration();
//		Registration reg6 = new Registration();
//		Registration reg7 = new Registration();
//		Registration reg8 = new Registration();
//		Registration reg9 = new Registration();
//		Registration reg10 = new Registration();
//		reg.completeRegistration(st, myCourse.getLectureAt(0));
//		reg2.completeRegistration(st2, myCourse.getLectureAt(0));
//		reg3.completeRegistration(st3, myCourse.getLectureAt(0));
//		reg4.completeRegistration(st4, myCourse.getLectureAt(0));
//		reg5.completeRegistration(st5, myCourse.getLectureAt(0));
//		reg6.completeRegistration(st6, myCourse.getLectureAt(0));
//		reg7.completeRegistration(st7, myCourse.getLectureAt(0));
//		reg8.completeRegistration(st8, myCourse.getLectureAt(0));
//		reg9.completeRegistration(st9, myCourse.getLectureAt(0));
//		reg10.completeRegistration(st10, myCourse.getLectureAt(0));
	}

	private void loadFromDataBase() {
		db = new DBManager();
		this.courseList = db.readCoursesFromDataBase();
		this.studentList = db.readStudentsFromDataBase();
		db.setupStudentsCoursesDatabase(studentList, courseList);
	}

	public void createLecture(Course c, int secNum, int secCap) {
		if (c != null) {
			Lecture lecture = new Lecture(secNum, secCap);
			c.addLecture(lecture);
			db.addLecture(lecture);
		}
	}

	public Course searchCourses(String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) && courseNum == c.getCourseNum()) {
				return c;
			}
		}
		return null;
	}

	public Student searchStudents(int id) {
		for (Student s : studentList) {
			if (s.getStudentId() == id) {
				return s;
			}
		}
		return null;
	}

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

	public String viewStudentInfo(Student student) {
		StringBuilder s = new StringBuilder("All information on the selected student in the catalogue: \n");
		s.append(student); // call the toString() method in Student.
		return s.toString();
	}

	public String viewStudents() {
		StringBuilder s = new StringBuilder("All information on the students in the catalogue: \n");
		for (Student student : studentList) {
			s.append(student + "\n"); // call the toString() method in Student.
		}
		return s.toString();
	}

	public String registerStudentCourse(Student student, Lecture lecture) {
		Registration register = new Registration();
		String s =  register.completeRegistration(student, lecture);
		db.addRegistration(register);
		return s;
	}
	public void removeRegistration(Registration reg) {
		db.removeRegistration(reg);
		reg.removeRegistration();
	}
	
	public void close() {
		db.close();
	}
	
	
}

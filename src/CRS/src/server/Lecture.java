package CRS.src.server;

import java.util.ArrayList;

public class Lecture {
	/**
	 * secNum denotes what section is this lecture.
	 */
	private int secNum;
	/**
	 * secCap denotes what is the capacity of this lecture.
	 */
	private int secCap;
	/**
	 * theCourse denotes what course does this lecture belongs to.
	 */
	private Course theCourse;
	/**
	 * offeringRegList denotes how many student have registed in this class.
	 */
	private ArrayList<Registration> offeringRegList;

	/**
	 * Constructor of class Lecture that initializes variables with given data.
	 * 
	 * @param secNum is the section number.
	 * @param secCap is the section capacity.
	 */
	public Lecture(int secNum, int secCap) {
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		offeringRegList = new ArrayList<Registration>();
	}

	/**
	 * Get the section number of this lecture.
	 * 
	 * @return secNum.
	 */
	public int getSecNum() {
		return secNum;
	}

	/**
	 * Set the section of this lecture.
	 * 
	 * @param secNum is the new value of secNum.
	 */
	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}

	/**
	 * Get the section capacity of this lecture.
	 * 
	 * @return secCap.
	 */
	public int getSecCap() {
		return secCap;
	}

	/**
	 * Set the capacity of this lecture.
	 * 
	 * @param secCap is the new value of secNum.
	 */
	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}

	/**
	 * Get the course this lecture is in.
	 * 
	 * @return theCourse.
	 */
	public Course getTheCourse() {
		return theCourse;
	}

	/**
	 * Change the course this lecture is in.
	 * 
	 * @param theCourse is the new course that this lecture is in.
	 */
	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}

	/**
	 * Displays all data of this lecture.
	 * 
	 * @return st is a string that contains all the nescessary data of this lecture.
	 */
	@Override
	public String toString() {
		String st = "\n";
		st += getTheCourse().getCourseName() + " " + getTheCourse().getCourseNum() + "\n";
		st += "Section Num: " + getSecNum() + ", section cap: " + getSecCap() + "\n";
		st += "Students:\n";
		for (Registration r : this.offeringRegList) {
			st += r.getTheStudent();
		}
		return st;
	}

	/**
	 * Add new registration to this lecture.
	 * 
	 * @param registration is the new registration that will be added.
	 */
	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		offeringRegList.add(registration);

	}

	/**
	 * Remove a registration to this lecture.
	 * 
	 * @param registration is the new registration that will be added.
	 */
	public void removeRegistration(Registration registration) {
		offeringRegList.remove(registration);
	}

	/**
	 * Check if this lecture is still available to register.
	 * 
	 * @return true if it still available. False otherwise.
	 */
	public boolean canRegister() {
		if (this.offeringRegList.size() >= this.getSecCap())
			return false;
		return true;
	}
}

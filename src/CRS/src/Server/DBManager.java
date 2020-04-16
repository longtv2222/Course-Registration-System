package CRS.src.Server;

import java.util.ArrayList;

//This class is simulating a database for our
//program
public class DBManager {

	ArrayList<Course> courseList;

	public DBManager() {
		courseList = new ArrayList<Course>();
	}

	public ArrayList<Course> readFromDataBase() {
		courseList.add(new Course("ENGG", 233));
		courseList.add(new Course("ENSF", 409));
		courseList.add(new Course("PHYS", 259));
		courseList.get(1).addPreReq(courseList.get(0));
		return courseList;
	}

}

package Server;

import java.util.ArrayList;

//This class is simulating a database for our
//program
public class DBManager { // This acts as a database for now

	ArrayList<Course> courseList;

	public DBManager() {
		courseList = new ArrayList<Course>();
	}

	public ArrayList readFromDataBase() {
		// TODO Auto-generated method stub
		courseList.add(new Course("ENGG", 233));
		courseList.add(new Course("ENSF", 409));
		courseList.add(new Course("PHYS", 259));
		return courseList;
	}

}

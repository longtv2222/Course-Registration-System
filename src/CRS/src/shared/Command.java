package CRS.src.shared;

import java.io.Serializable;

public class Command implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int SEARCH_COURSE = 0;
	public static final int ADD_COURSE = 1;
	public static final int REMOVE_COURSE = 2;
	public static final int DISPLAY_ALL = 3;
	public static final int COURSE_IN_CART = 4;
	public static final int ADD_NEW_COURSE = 5;
	public static final int SEARCH_STUDENT_ID = 6;
	public static final int VIEW_ALL_STUDENT = 7;
	public static final int ADD_NEW_STUDENT = 8;
	public static final int ADD_NEW_LECTURE = 9;

	private int type;
	private String message;

	public Command(int type, String message) {
		this.type = type;
		this.message = message;
	}

	public int getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}
}
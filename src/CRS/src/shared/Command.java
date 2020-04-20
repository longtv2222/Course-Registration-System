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
	/**
	 * Type of command.
	 */
	private int type;
	/**
	 * message is an message that contains the data sent along with the command.
	 */
	private String message;

	/**
	 * Constructor of class command that initializes variables accordingly with
	 * given data.
	 * 
	 * @param type    is the type of command.
	 * @param message is the message that sent along with the command.
	 */
	public Command(int type, String message) {
		this.type = type;
		this.message = message;
	}

	/**
	 * Get the type of the command.
	 * 
	 * @return type.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Get the message of the command.
	 * 
	 * @return message.
	 */
	public String getMessage() {
		return message;
	}
}
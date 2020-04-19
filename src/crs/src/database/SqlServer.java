package crs.src.database;

import java.sql.*;

/**
 * This was initially programmed for a MariaDB implementation of MYSql. It
 * should still work as expected, even for a Mysql server.
 * 
 * @author cloud
 *
 */
public class SqlServer {

	private String sqlServerLocation;
	private String schema;
	private Connection con;

	public SqlServer(String sqlServerLocation, String schema, String username, String password) {
		// note, passing the password as a string is insecure.
		// but it should not be that horrible for a demo product.
		// PLEASE DONT USE THIS IN PRODUCTION.
		this.sqlServerLocation = sqlServerLocation;
		this.schema = schema;
		try {
			con = DriverManager.getConnection(this.sqlServerLocation, username, password);
			this.setupServer(username, password);
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM courses");
			while (rs.next()) {
				System.out.println(rs.getString("name"));
			}
			rs = statement.executeQuery(
					"SELECT count(*) AS total FROM information_schema.tables WHERE (TABLE_SCHEMA = 'school') AND (TABLE_NAME ='students')");
			while (rs.next()) {
				System.out.println(rs.getInt("total"));
//				System.out.println(rs.getString("course") + " " + rs.getInt("id") + " " + rs.getInt("cap"));
			}
			con.close();
//			statement.executeUpdate("INSERT INTO `students` (`id`, `name`) VALUES ('4', 'aa')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setupServer(String username, String password) throws SQLException {
		this.setupSchemaSQL(username, password);
		Statement statement = con.createStatement();
		statement = con.createStatement();
		this.setupStudentSQL(statement);
		this.setupCoursesSQL(statement);
		this.setupRequisitesSQL(statement);
		this.setupLecturesSQL(statement);
		this.setupRegistrationsSQL(statement);
	}

	public void setupSchemaSQL(String username, String password) throws SQLException {
		Statement statement = this.con.createStatement();
		ResultSet rs = statement.executeQuery(
				"SELECT count(*) AS total FROM information_schema.schemata WHERE (SCHEMA_NAME ='" + this.schema + "')");
		int exists = 0;
		while (rs.next()) {
			exists = rs.getInt("total");
		}
		// if the schema/database does not exist, create it.
		if (exists == 0) {
			System.out.println("Creating database/schema `"+this.schema+"`.");
			statement.executeUpdate("CREATE SCHEMA `" + this.schema + "`");
		}
		statement.close();
		this.con.close();
		this.con = DriverManager.getConnection(this.sqlServerLocation + this.schema, username, password);
	}

	public void setupStudentSQL(Statement statement) throws SQLException {
		ResultSet rs = statement
				.executeQuery("SELECT count(*) AS total FROM information_schema.tables WHERE (TABLE_NAME ='students')");
		int exists = 0;
		while (rs.next()) {
			exists = rs.getInt("total");
		}
		// if the table does not exist, create it.
		if (exists == 0) {
			System.out.println("Creating TABLE 'students'.");
			// generated command from MySQL Workbench
			statement.executeUpdate(
					"CREATE TABLE `students` (`id` INT NOT NULL,`name` VARCHAR(45) NOT NULL, PRIMARY KEY (`id`));");

			// also add in some basic students, to show basics.
			// and yes, we need to do it one at a time...
			statement.executeUpdate("INSERT INTO `students` (`id`, `name`) VALUES ('1', 'test')");
			statement.executeUpdate("INSERT INTO `students` (`id`, `name`) VALUES ('2', 'long')");
			statement.executeUpdate("INSERT INTO `students` (`id`, `name`) VALUES ('3', 'cloud')");
			statement.executeUpdate("INSERT INTO `students` (`id`, `name`) VALUES ('4', 'aaaaaaa')");
		}
	}

	public void setupCoursesSQL(Statement statement) throws SQLException {
		ResultSet rs = statement
				.executeQuery("SELECT count(*) AS total FROM information_schema.tables WHERE (TABLE_NAME ='courses')");
		int exists = 0;
		while (rs.next()) {
			exists = rs.getInt("total");
		}
		// if the table does not exist, create it.
		if (exists == 0) {
			System.out.println("Creating TABLE 'courses'.");
			// generated command from MySQL Workbench
			statement.executeUpdate("CREATE TABLE `courses` (`name` VARCHAR(45) NOT NULL, PRIMARY KEY (`name`));");

			// also add in some basic students, to show basics.
			// and yes, we need to do it one at a time...
			statement.executeUpdate("INSERT INTO `courses` (`name`) VALUES ('ENGG233')");
			statement.executeUpdate("INSERT INTO `courses` (`name`) VALUES ('ENSF409')");
			statement.executeUpdate("INSERT INTO `courses` (`name`) VALUES ('CPSC319')");
			statement.executeUpdate("INSERT INTO `courses` (`name`) VALUES ('TEST123')");
			statement.executeUpdate("INSERT INTO `courses` (`name`) VALUES ('ABCD101')");
		}
	}

	public void setupRequisitesSQL(Statement statement) throws SQLException {
		ResultSet rs = statement.executeQuery(
				"SELECT count(*) AS total FROM information_schema.tables WHERE (TABLE_NAME ='requisites')");
		int exists = 0;
		while (rs.next()) {
			exists = rs.getInt("total");
		}
		// if the table does not exist, create it.
		if (exists == 0) {
			System.out.println("Creating TABLE 'requisites'.");
			// generated command from MySQL Workbench
			statement.executeUpdate(
					"CREATE TABLE `requisites` (`course` VARCHAR(8) NOT NULL, `requisite` VARCHAR(8) NOT NULL, `type` VARCHAR(3) NOT NULL);");

			// also add in some basic students, to show basics.
			// and yes, we need to do it one at a time...
			statement.executeUpdate(
					"INSERT INTO `requisites` (`course`,`requisite`,`type`) VALUES ('ENSF409','ENGG233','PRE')");
			statement.executeUpdate(
					"INSERT INTO `requisites` (`course`,`requisite`,`type`) VALUES ('CPSC319','ENGG233','PRE')");
			statement.executeUpdate(
					"INSERT INTO `requisites` (`course`,`requisite`,`type`) VALUES ('ENGG233','TEST123','SUB')");
		}
	}

	public void setupLecturesSQL(Statement statement) throws SQLException {
		ResultSet rs = statement
				.executeQuery("SELECT count(*) AS total FROM information_schema.tables WHERE (TABLE_NAME ='lectures')");
		int exists = 0;
		while (rs.next()) {
			exists = rs.getInt("total");
		}
		// if the table does not exist, create it.
		if (exists == 0) {
			System.out.println("Creating TABLE 'lectures'.");
			// generated command from MySQL Workbench
			statement.executeUpdate(
					"CREATE TABLE `lectures` (`id` INT NOT NULL, `course` VARCHAR(45) NOT NULL, `cap` INT NOT NULL);");

			// also add in some basic students, to show basics.
			// and yes, we need to do it one at a time...
			statement.executeUpdate("INSERT INTO `lectures` (`id`,`course`,`cap`) VALUES ('1','ENGG233','50')");
			statement.executeUpdate("INSERT INTO `lectures` (`id`,`course`,`cap`) VALUES ('2','ENGG233','200')");
			statement.executeUpdate("INSERT INTO `lectures` (`id`,`course`,`cap`) VALUES ('1','ENSF409','90')");
			statement.executeUpdate("INSERT INTO `lectures` (`id`,`course`,`cap`) VALUES ('1','CPSC319','30')");
			statement.executeUpdate("INSERT INTO `lectures` (`id`,`course`,`cap`) VALUES ('1','ABCD101','10')");
			statement.executeUpdate("INSERT INTO `lectures` (`id`,`course`,`cap`) VALUES ('1','TEST123','25')");
		}
	}

	public void setupRegistrationsSQL(Statement statement) throws SQLException {
		ResultSet rs = statement.executeQuery(
				"SELECT count(*) AS total FROM information_schema.tables WHERE (TABLE_NAME ='registrations')");
		int exists = 0;
		while (rs.next()) {
			exists = rs.getInt("total");
		}
		// if the table does not exist, create it.
		if (exists == 0) {
			System.out.println("Creating TABLE 'registrations'.");
			// generated command from MySQL Workbench
			statement.executeUpdate(
					"CREATE TABLE `registrations` (`student_id` INT NOT NULL, `course` VARCHAR(45) NOT NULL, `lecture_id` INT NOT NULL);");

			// also add in some basic students, to show basics.
			// and yes, we need to do it one at a time...
			// If the lecture_id is 0, assume the student has already taken this course.
			statement.executeUpdate(
					"INSERT INTO `registrations` (`student_id`,`course`,`lecture_id`) VALUES ('1','ENGG233','1')");
			statement.executeUpdate(
					"INSERT INTO `registrations` (`student_id`,`course`,`lecture_id`) VALUES ('2','ENGG233','1')");
			statement.executeUpdate(
					"INSERT INTO `registrations` (`student_id`,`course`,`lecture_id`) VALUES ('3','ENGG233','2')");
			statement.executeUpdate(
					"INSERT INTO `registrations` (`student_id`,`course`,`lecture_id`) VALUES ('1','ENSF409','1')");
			statement.executeUpdate(
					"INSERT INTO `registrations` (`student_id`,`course`,`lecture_id`) VALUES ('1','CPSC319','1')");
			statement.executeUpdate(
					"INSERT INTO `registrations` (`student_id`,`course`,`lecture_id`) VALUES ('1','ABCD101','1')");
			statement.executeUpdate(
					"INSERT INTO `registrations` (`student_id`,`course`,`lecture_id`) VALUES ('1','TEST123','1')");
			statement.executeUpdate(
					"INSERT INTO `registrations` (`student_id`,`course`,`lecture_id`) VALUES ('2','TEST123','0')");
		}
	}

	public static void main(String[] args) {
		System.out.println("Debugging Sql Server.");
		SqlServer s = new SqlServer("jdbc:mysql://localhost:3306/", "school_long_cloud", "mysql", "");

	}
}

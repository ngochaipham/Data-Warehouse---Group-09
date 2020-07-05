package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionBD {
	public static Connection getConnection(String db_name) {
		String host = "jdbc:mysql://localhost:3306/" +  db_name;
		String username = "root";
		String password = "";

		Connection myConnection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConnection = DriverManager.getConnection(host, username, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return myConnection;

	}
public static Connection getConnection(String nameDB, String userName, String passworDB) {

		Connection myConnection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String host = "jdbc:mysql://localhost:3307/" + nameDB;
			String username = "root";
			String password = "1234";
			myConnection = DriverManager.getConnection(host, username, password);

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return myConnection;

	}

	public static void main(String[] args) {
		System.out.println(getConnection("control_db"));
	}

	public static void main(String[] args) {
		System.out.println(getConnection("control_db"));
	}

}

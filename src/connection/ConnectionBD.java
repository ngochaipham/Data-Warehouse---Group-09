package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.*;

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
	

}

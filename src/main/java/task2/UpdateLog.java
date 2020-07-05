package task2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Log;

public class UpdateLog {
	public static void update(String sql, Log log, Connection connection) {
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(sql);
			
			ps.setString(1, log.getStagingLoadCounT());
			ps.setInt(2, log.getId_Log());
			ps.execute();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
}
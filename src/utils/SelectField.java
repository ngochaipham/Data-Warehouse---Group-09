package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionBD;

public class SelectField {
	private ResultSet rs = null;
	PreparedStatement pst = null;

	public String selectField(String field, String tableName) throws Exception {
		String chosenField = null;
		Connection connection = null;
		String sql = "SELECT " + field + " FROM " + tableName;
		try {
			connection = ConnectionBD.getConnection("control");
			pst = connection.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				chosenField = rs.getString(field);
			}
			return chosenField;
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (pst != null)
					pst.close();
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
}

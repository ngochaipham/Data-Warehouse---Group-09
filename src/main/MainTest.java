package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connection.ConnectionBD;
import dto.Config;
import dto.Log;
import utils.ConvertModel;
import utils.LoadStaging;

public class MainTest {
	public static void main(String[] args) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;

		Connection connection = ConnectionBD.getConnection("control");

		for (String id_config : args) {
			System.out.println(id_config);

			sql = "SELECT * FROM control.config WHERE id_config = " + id_config;
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();
			Config config = new Config();
			while (rs.next()) {
				config = ConvertModel.convertToConfig(rs);
			}

			String nameLog = config.getNameConfig();
			sql = "SELECT * FROM control.log WHERE name_log = '" + nameLog + "'";
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();
			ArrayList<Log> logs = new ArrayList<Log>();
			while (rs.next()) {
				logs.add(ConvertModel.convertToLog(rs));
			}
			
			for (Log log : logs) {
				LoadStaging.loadStaging(config, log, connection);
			}
		}
	}
}

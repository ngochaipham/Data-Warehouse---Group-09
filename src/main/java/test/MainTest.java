package test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.stream.StreamSupport;

import connection.ConnectionBD;
import model.Config;
import model.Log;
import task2.InsertStaging;
import task2.UpdateLog;
import utils.ConvertModel;
import utils.SendEmail;
import utils.SystemContain;

public class MainTest {
	public static void main(String[] args) throws SQLException, IOException, ParseException {

		PreparedStatement pre = null;
		ResultSet rs = null;
		Connection conn = ConnectionBD.getConnection("control_db", "root", "1234");
		String sql = "SELECT * FROM config";
		pre = conn.prepareStatement(sql);

		rs = pre.executeQuery();

		ArrayList<Config> listConfig = new ArrayList<Config>();
		while (rs.next()) {
			listConfig.add(ConvertModel.convertToConfig(rs));
		}
		conn.close();

		Config config_getAllLog = listConfig.get(2);
		conn = ConnectionBD.getConnection(config_getAllLog.getNameDatabase(), config_getAllLog.getUserNameDatabase(),
				config_getAllLog.getPassWordDatabse());
		pre = conn.prepareStatement(config_getAllLog.getQuerySQL());
		rs = pre.executeQuery();

		ArrayList<Log> listLog = new ArrayList<Log>();
		while (rs.next()) {
			listLog.add(ConvertModel.convertToLog(rs));
		}
		conn.close();

		Config config_inssertStaging = listConfig.get(3);
		Config config_updateLog = listConfig.get(4);
		conn = ConnectionBD.getConnection(config_inssertStaging.getNameDatabase(),
				config_inssertStaging.getUserNameDatabase(), config_inssertStaging.getPassWordDatabse());
		for (Log item : listLog) {
			if (item.getFileStatus().equals(SystemContain.SUCCESS)
					&& item.getStagingLoadCounT().equals(SystemContain.NR)) {
				item.setStagingLoadCounT(InsertStaging.insertStaging(config_inssertStaging, item, conn));

				String nameFile = splitPath(item.getFileName())[splitPath(item.getFileName()).length - 1];
				SendEmail.send("File upload STAGING is success", "File " + nameFile + "has been uploaded to staging");

				Connection c = ConnectionBD.getConnection(config_updateLog.getNameDatabase(),
						config_updateLog.getUserNameDatabase(), config_updateLog.getPassWordDatabse());
				UpdateLog.update(config_updateLog.getQuerySQL(), item, c);
			}
		}
		conn.close();
	}

	public static String[] splitPath(String pathString) {
		Path path = Paths.get(pathString);
		return StreamSupport.stream(path.spliterator(), false).map(Path::toString).toArray(String[]::new);
	}

}
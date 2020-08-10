package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import dto.Config;
import dto.Log;

public class ConvertModel {
	public static Config convertToConfig(ResultSet rs) throws SQLException {
		Config config = new Config();
		config.setId_config(rs.getInt("id_config"));
		config.setNameConfig(rs.getString("nameConfig"));
		config.setTableName(rs.getString("tableName"));
		config.setUsername(rs.getString("userName"));
		config.setPassword(rs.getString("password"));
		config.setHost(rs.getString("host"));
		config.setPort(rs.getString("port"));
		config.setUser(rs.getString("user"));
		config.setPw(rs.getString("pw"));
		config.setRemotePath(rs.getString("remotePath"));
		config.setLocalPath(rs.getString("localPath"));
		config.setNameDbControl(rs.getString("nameDbControl"));
		config.setNameTbConfig(rs.getString("nameTbConfig"));
		config.setNameTbLog(rs.getString("nameTbLog"));
		config.setNameDbStaging(rs.getString("nameDbStaging"));
		config.setNameTbStaging(rs.getString("nameTbStaging"));
		
		return config;
	}
	
	public static Log convertToLog(ResultSet rs) throws SQLException {
		Log log = new Log();
		log.setId_log(rs.getInt("id_log"));
		log.setName_log(rs.getString("name_log"));

		
		if (rs.getString("status") != null) {
			log.setStatus(rs.getString("status"));
		} else {
			log.setStatus("");
		}
		if (rs.getString("urlLocal") != null) {
			log.setUrlLocal(rs.getString("urlLocal"));
		} else {
			log.setUrlLocal("");
		}

		return log;
	}
	
}

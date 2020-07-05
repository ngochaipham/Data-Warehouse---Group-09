package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Config;
import model.Log;
import model.scp;

public class ConvertModel {
	public static Config convertToConfig(ResultSet rs) throws SQLException {
		Config config = new Config();
		config.setId(rs.getInt("id_config"));
		config.setNameDatabase(rs.getString("name_database"));
		config.setUserNameDatabase(rs.getString("username_database"));
		config.setPassWordDatabse(rs.getString("password_database"));
		if( rs.getString("query") != null) {
			config.setQuerySQL(rs.getString("query"));
		} else {
			config.setQuerySQL("");
		}
		config.setQuerySQL(rs.getString("query"));
		if(rs.getString("username_data") != null) {
			config.setUserNameData(rs.getString("username_data"));
		} else {
			config.setUserNameData("");
		}
		
		if(rs.getString("password_data") != null) {
			config.setPasswordData(rs.getString("password_data"));
		} else {
			config.setPasswordData("");
		}
		
		if(rs.getString("file_format") != null) {
			config.setFileFormat(rs.getString("file_format"));
		} else {
			config.setFileFormat("");
		}
		
		if(rs.getString("remote_dir") != null) {
			config.setRemoteDir(rs.getString("remote_dir"));
		} else {
			config.setRemoteDir("");
		}
		
		if(rs.getString("local_dir") != null) {
			config.setLocalDir(rs.getString("local_dir"));
		} else {
			config.setLocalDir("");
		}
		
		return config;
	}
	
	public static Log convertToLog(ResultSet rs) throws SQLException {
		Log log = new Log();
		log.setId_Log(rs.getInt("id_log"));
		
		if( rs.getString("urlLocal") != null) {
			log.setFileName(rs.getString("urlLocal"));
		} else {
			log.setFileName("");
		}
		
		
		if( rs.getString("status_download") != null) {
			log.setFileStatus(rs.getString("status_download"));
		} else {
			log.setFileStatus("");
		}
		
		if( rs.getString("status_upload_staging") != null) {
			log.setStagingLoadCounT(rs.getString("status_upload_staging"));
		} else {
			log.setStagingLoadCounT("");
		}
		
//		if( rs.getString("status_upload_warehouse") != null) {
//			log.setStatusUploadWarehouse(rs.getString("status_upload_warehouse"));
//		} else {
//			log.setStatusUploadWarehouse("");
//		}
		
		return log;
	}
	public static scp convertToSCP(ResultSet rs) throws SQLException {
		scp scp = new scp();
		
		if( rs.getString("local_path") != null) {
			scp.setLocalPath(rs.getString("local_path"));
		} else {
			scp.setLocalPath("");
		}
		
		
		return scp;
		
	}
	
}

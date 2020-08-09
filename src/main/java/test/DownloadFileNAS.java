package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chilkatsoft.CkGlobal;
import com.chilkatsoft.CkScp;
import com.chilkatsoft.CkSsh;

import connection.ConnectionBD;

public class DownloadFileNAS {
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private String sql;
	static String LOCAL_PATH;
	static {
		try {
			System.loadLibrary("chilkat"); // copy file chilkat.dll vao thu muc project
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
	}

	public void childkatDownload(String hostname, int port, String username_account, String password_account,
			String file_format, String listFileDownLoad ,String remote_dir, String local_dir, int mode) {
		CkSsh ssh = new CkSsh();
		CkGlobal ck = new CkGlobal();
		ck.UnlockBundle("Hello");
		boolean success = ssh.Connect(hostname, port);
		if (success != true) {
			
			System.out.println(ssh.lastErrorText());
			return;
		}
		
		ssh.put_IdleTimeoutMs(5000);
		success = ssh.AuthenticatePw(username_account, password_account);
		if (success != true) {
			System.out.println(ssh.lastErrorText());
			return;
		}
		CkScp scp = new CkScp();

		success = scp.UseSsh(ssh);
		if (success != true) {
			System.out.println(scp.lastErrorText());
			return;
		}
		scp.put_SyncMustMatch(file_format);//Down cac file theo Format
		scp.put_SyncMustNotMatch(listFileDownLoad);
		success = scp.SyncTreeDownload(remote_dir, local_dir, mode, false);
		if (success != true) {
			System.out.println(scp.lastErrorText());
			return;
		}

		ssh.Disconnect();
	}

	public boolean downloadFileOnServer(int id) {
		boolean result = false;
		sql = "SELECT * FROM config where id_config = '" + id + "'";
		try {
			// 1. select fields table SCP in data SCP using download
			pst = new ConnectionBD().getConnection("control").prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				String hostname = rs.getString("hostname");
				int port = rs.getInt("port");
				String username_account = rs.getString("username_account");
				String password_account = rs.getString("password_account");
				String file_format = rs.getString("file_format");
				String remote_dir = rs.getString("remote_dir");
				LOCAL_PATH = rs.getString("local_dir");
				int mode = rs.getInt("mode");
				String listFileDownLoad = getFileName(id);
				childkatDownload(hostname, port, username_account, password_account, file_format, listFileDownLoad, remote_dir,
						LOCAL_PATH, mode);
				result = true;
				return result;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return result;

		} finally {
			try {
				if (pst != null)
					pst.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return result;
	}
	public String getFileName(int id) {
		String result = "";
		sql = "SELECT NAME_LOG FROM LOG WHERE DATA_FILE_CONFIG_ID = '" + id + "'";
		try {
			pst = new ConnectionBD().getConnection("control").prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				result += rs.getString("file_name") + ";";
			}
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (pst != null)
					pst.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return result;
		
		
	}
	
}

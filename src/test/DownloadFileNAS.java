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
			String file_format, String remote_dir, String local_dir, int mode) {
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
		scp.put_SyncMustMatch(file_format);// down tat ca cac file bat dau bang sinhvien
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
			// 1. select fields table config in data config using download
			pst = new ConnectionBD().getConnection("control").prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				String nameConfig = rs.getString("nameConfig");
				String hostname = rs.getString("host");
				int port = rs.getInt("port");
				String username_account = rs.getString("user");
				String password_account = rs.getString("pw");
				String file_format = rs.getString("file_format");
				String remote_dir = rs.getString("remotePath");
				LOCAL_PATH = rs.getString("localPath");
				int mode = rs.getInt("mode");
				childkatDownload(hostname, port, username_account, password_account, file_format, remote_dir,
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

	
}

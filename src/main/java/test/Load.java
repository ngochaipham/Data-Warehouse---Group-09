package test;

import connection.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Load {
	// ConnectionBD connControlDB = null;
	// private Connection conn = null;
	static final String NUMBER_REGEX = "^[0-9]+$";
	private Connection connectionDB1;
	// //==jdbc:mysql://localhost:3306/dwh_1
	private static String USER = "root";
	private static String PASS = "";

	private static String hostName = "localhost";
	private static String driver = "jdbc:mysql:";
	private static String addressConfig = "localhost:3306/controldb";
	private static String addressSource = "localhost:3306/datawarehouse";

	private static Connection con;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	String timestamp = dtf.format(now);
	public static String LOCAL_PATH = "D:\\DW_LAB";

	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private String sql;

	public static boolean sendMail(String to, String subject, String bodyMail) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("ngochaipham07@gmail.com", "gsdkoanwiiwyofkn");
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			message.setFrom(new InternetAddress("ngochaipham07@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject, "UTF-8");
			message.setText(bodyMail, "UTF-8");
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
//
	public boolean insertDataLog() {
		int rs = 0;
		boolean check = false;
		if (!new DownloadFileNAS().downloadFileOnServer()) {
			return check;
		}
		sql = "INSERT INTO log (file_name_,data_file_config_id,file_status,staging_load_counT, timestamp_download, timestamp_insert_staging, timestamp_insert_datawarehouse)"
				+ " values (?,?,?,?,?,?,?)";

		File localPath = new File(LOCAL_PATH);
		File[] listFileLog = localPath.listFiles();
		for (int i = 0; i < listFileLog.length; i++) {
			try {
				pst = new ConnectionBD().getConnection("control_db").prepareStatement(sql);
				pst.setString(1,localPath + "\\" + listFileLog[i].getName());
				if (listFileLog[i].getName().substring(listFileLog[i].getName().lastIndexOf(".")).equals(".txt")) {
					pst.setInt(2, 1);
				} else if (listFileLog[i].getName().substring(listFileLog[i].getName().lastIndexOf("."))
						.equals(".xlsx")) {
					pst.setInt(2, 2);
				} else {
					pst.setInt(2, 3);
				}
				pst.setString(3, "NR");
				pst.setInt(4, 0);
				pst.setString(5, timestamp);
				pst.setString(6, null);
				pst.setString(7, null);
				rs = pst.executeUpdate();
				check = true;

			} catch (Exception e) {
				e.printStackTrace();
				return check;
			} finally {
				try {
					if (pst != null)
						pst.close();
					if (this.rs != null)
						this.rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return check;

	}

	public void sendMailInsertLog() {
		if (insertDataLog() != true) {
			sendMail("17130052@st.hcmuaf.edu.vn", "DATA WAREHOUSE Notification 2020", "Download file & ghi log fail rồi đại ca");
			System.out.println("Send Email- fail...!");
		} else {
			sendMail("17130052@st.hcmuaf.edu.vn", "DATA WAREHOUSE Notification 2020", "Downoad file & ghi log success rồi nè ");
			System.out.println("Send Email- success...!");
		}
	}

	public static void main(String[] args) {
		Load ex = new Load();
		ex.sendMailInsertLog();

	}

}

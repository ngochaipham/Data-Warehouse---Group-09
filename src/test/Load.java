package test;

import connection.*;
import dto.Config;
import dto.Log;
import utils.Status;

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
	Status status = new Status();

	private static Connection con;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	String timestamp = dtf.format(now);
//	public static String LOCAL_PATH = "D:\\DW_LAB";

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
	public boolean insertDataLog(Config config) {
		boolean check = false;
		if (!new DownloadFileNAS().downloadFileOnServer(config.getId_config())) {
			return check;
		}
		sql = "INSERT INTO log (name_log,status,urlLocal,data_file_config_id, time_stamp_download)" + " values (?,?,?,?,?)";

		File localPath = new File(DownloadFileNAS.LOCAL_PATH);
		File[] listFileLog = localPath.listFiles();
//		for (int i = 0; i < listFileLog.length; i++) {
		for (File file : listFileLog) {
			if(file.isFile()) {
				Log log = new Log();
				log.setName_log(config.getNameConfig());
				log.setId_log(config.getId_config());
			}
		
			try {
				pst = new ConnectionBD().getConnection("control").prepareStatement(sql);
				pst.setString(1, config.getNameConfig());
				pst.setString(2, status.DOWNLOAD);	
				pst.setString(3, localPath+File.separator + file.getName());
				pst.setInt(4, config.getId_config());
				pst.setString(5, timestamp);
				pst.executeUpdate();
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

	public void sendMailInsertLog(int id) {
//		String idSV = "1";
//		String idMH = "2";
//		String idLP = "3";
		Config config = new Config();
		config.setId_config(id);
		if(id == 3) {
			config.setNameConfig("Load_classess");
		}else if(id == 2) {
			config.setNameConfig("Load_subjects");
		}else {
			config.setNameConfig("Load_students");
		}
		if (insertDataLog(id) != true) {
			sendMail("17130052@st.hcmuaf.edu.vn", "DATA WAREHOUSE Notification 2020",
					"Fail rồi đại ca");
			System.out.println("Loading On Log and Send Email FAIL...!");
		} else {
			sendMail("17130052@st.hcmuaf.edu.vn", "DATA WAREHOUSE Notification 2020",
					"Ok rồi nè ");
			System.out.println("Load On Log and Send Email SUCCESS...!");
		}
	}

	public static void main(String[] args) {
		Load ex = new Load();
		ex.sendMailInsertLog(2);
	}

}

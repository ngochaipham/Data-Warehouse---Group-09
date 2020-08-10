package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
	//gui den ai_tieude_noidungmail
	public static boolean sendMail(String to, String title, String text) {
        final String FROM = "datawarehousenhom06@gmail.com";
        final String PASS = "datawarehouse12345@";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASS);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/plain ; charset=UTF-8");
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(title);
            message.setText(text);
            Transport.send(message);
            System.out.println("Gửi Mail Thành Công");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gửi Mail Thất Bại");
            return false;
        }
        return true;
    }
}

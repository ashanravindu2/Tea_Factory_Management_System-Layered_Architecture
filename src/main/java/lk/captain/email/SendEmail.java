package lk.captain.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SendEmail {
    private Session newSession = null;
    private MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(new Properties(), null));

    public void sendEmail(String... data) throws MessagingException {
        setUpServerProperties();
        draftEmail(data);
        sendEmail();
    }

    public void setUpServerProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.port", "587"); // Use TLS port
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

        newSession = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("alokagreenofficial@gmail.com", "izue ksnw vqye lhbi");
            }
        });
    }

    public void draftEmail(String[] data) throws MessagingException {
        mimeMessage.setFrom(new InternetAddress("alokagreenofficial@gmail.com"));
        String recipients = data[0];
        String subject = data[1];
        String body = data[2];

        mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(recipients)));
        mimeMessage.setSubject(subject);

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(body, "text/html");

        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);

        mimeMessage.setContent(multipart);
    }

    public void sendEmail() throws MessagingException {
        String host = "smtp.gmail.com";
        String userName = "alokagreenofficial@gmail.com";
        String password = "izue ksnw vqye lhbi"; // Replace with your actual Gmail password or App Password

        Transport transport = newSession.getTransport("smtp");
        transport.connect(host, userName, password);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
        System.out.println("Email Sent Successfully!");
}
}
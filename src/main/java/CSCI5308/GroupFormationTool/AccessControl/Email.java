package CSCI5308.GroupFormationTool.AccessControl;

import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email implements IUserNotifications {

    private static final String from = "pavansaikuramsetti8@yahoo.com";
    private static final String password = "hcqnicqwydippfkp";
    private static final String host = "smtp.mail.yahoo.com";

    Logger myLogger = LoggerUtil.getLoggerInstance(IUserNotifications.class);

    public Session session() {
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        session.setDebug(true);
        return session;
    }

    @Override
    public void sendUserLoginCredentials(IUser user, String rawPassword) throws Exception {
        Session session = session();

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Your account details FOR CatMe GFT are");
            message.setText(("Hi your account details is\n" + "username: " + user.getBannerID() + "\npassword is: "
                    + rawPassword));

            Transport.send(message);
            myLogger.info(String.format("Reset Password token sent via email successfully to user with bannerID %s",
                    user.getBannerID()));
        } catch (MessagingException mex) {
            myLogger.error(String.format("Messaging Exception encountered while sending email to user with bannerId %s", user.getBannerID()), mex);
            throw mex;
        }
    }

}

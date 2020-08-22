package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.AccessControl.IUserNotifications;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EmailMock implements IUserNotifications {

    private static final String from = "pavansaikuramsetti8@yahoo.com";
    private static final String password = "hcqnicqwydippfkp";
    private static final String host = "smtp.mail.yahoo.com";

    @Test
    public void sessionTest() {
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

        assertEquals(properties.getProperty("mail.smtp.host"), host);
        assertEquals(properties.getProperty("mail.smtp.port"), "587");
        assertEquals(properties.getProperty("mail.smtp.starttls.enable"), "true");
        assertEquals(properties.getProperty("mail.smtp.auth"), "true");
        assertNotNull(session);
    }

    @Override
    public void sendUserLoginCredentials(IUser user, String rawPassword) {
        assertNotNull(user);
        assertNotNull(rawPassword);
    }

}

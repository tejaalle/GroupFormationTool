package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.*;
import CSCI5308.GroupFormationTool.SystemConfig;
import CSCI5308.GroupFormationTool.SystemConfigTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@SuppressWarnings("deprecation")
public class UserTest {

    @Test
    public void ConstructorTests() throws Exception {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        AccessControlTestAbstractFactory abstractFactory = SystemConfigTest.instance().getAccessControlTestConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        Assert.isTrue(user.getBannerID().isEmpty());
        Assert.isTrue(user.getFirstName().isEmpty());
        Assert.isTrue(user.getLastName().isEmpty());
        Assert.isTrue(user.getEmail().isEmpty());

        IUserPersistence userDBMock = abstractFactory.makeUserDBMock();
        user = new User(1, userDBMock);
        Assert.isTrue(user.getBannerID().equals("B00000000"));

        user = new User("B00000000", userDBMock);
        Assert.isTrue(user.getBannerID().equals("B00000000"));
    }

    @Test
    public void setIDTest() {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        user.setID(10);
        Assert.isTrue(10 == user.getID());
    }

    @Test
    public void getIDTest() {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        user.setID(10);
        Assert.isTrue(10 == user.getID());
    }

    @Test
    public void setBannerIDTest() {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        user.setBannerID("B00000000");
        Assert.isTrue(user.getBannerID().equals("B00000000"));
    }

    @Test
    public void getBannerIDTest() {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        user.setBannerID("B00000000");
        Assert.isTrue(user.getBannerID().equals("B00000000"));
    }

    @Test
    public void setFirstNameTest() {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        user.setFirstName("Rob");
        Assert.isTrue(user.getFirstName().equals("Rob"));
    }

    @Test
    public void getFirstNameTest() {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        user.setFirstName("Rob");
        Assert.isTrue(user.getFirstName().equals("Rob"));
    }

    @Test
    public void setLastNameTest() {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        user.setLastName("Hawkey");
        Assert.isTrue(user.getLastName().equals("Hawkey"));
    }

    @Test
    public void getLastNameTest() {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        user.setLastName("Hawkey");
        Assert.isTrue(user.getLastName().equals("Hawkey"));
    }

    @Test
    public void setEmailTest() {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        user.setEmail("rhawkey@dal.ca");
        Assert.isTrue(user.getEmail().equals("rhawkey@dal.ca"));
    }

    @Test
    public void getEmailTest() {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        user.setEmail("rhawkey@dal.ca");
        Assert.isTrue(user.getEmail().equals("rhawkey@dal.ca"));
    }

    @Test
    public void createUser() throws Exception {
        AccessControlTestAbstractFactory abstractFactory = SystemConfigTest.instance().getAccessControlTestConcreteFactoryState();
        IUserPersistence userDB = abstractFactory.makeUserDBMock();
        IUserNotifications notification = abstractFactory.makeEmailMock();
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        userDB.createUser(user);
        notification.sendUserLoginCredentials(user, user.getPassword());
        Assert.isTrue(user.getId() == 0);
        Assert.isTrue(user.getBannerID().equals("B00000000"));
        Assert.isTrue(user.getPassword().equals("Pass@123"));
    }

    @Test
    public void isBannerIDValidTest() {
        Assert.isTrue(User.isBannerIDValid("B000000000"));
        assertFalse(User.isBannerIDValid(null));
        assertFalse(User.isBannerIDValid(""));
    }

    @Test
    public void isFirstNameValidTest() {
        Assert.isTrue(User.isFirstNameValid("rob"));
        assertFalse(User.isFirstNameValid(null));
        assertFalse(User.isFirstNameValid(""));
    }

    @Test
    public void isLastNameValidTest() {
        Assert.isTrue(User.isLastNameValid("hawkey"));
        assertFalse(User.isLastNameValid(null));
        assertFalse(User.isLastNameValid(""));
    }

    @Test
    public void isEmailValidTest() {
        Assert.isTrue(User.isEmailValid("rhawkey@dal.ca"));
        assertFalse(User.isEmailValid(null));
        assertFalse(User.isEmailValid(""));
        assertFalse(User.isEmailValid("@dal.ca"));
        assertFalse(User.isEmailValid("rhawkey@"));
    }

}

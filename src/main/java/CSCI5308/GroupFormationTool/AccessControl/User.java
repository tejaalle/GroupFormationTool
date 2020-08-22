package CSCI5308.GroupFormationTool.AccessControl;

import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements IUser {
    // This regex comes from here:
    // https://howtodoinjava.com/regex/java-regex-validate-email-address/
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    Logger myLogger = LoggerUtil.getLoggerInstance(UserDB.class);

    private long id;
    private String password;
    private String bannerID;
    private String firstName;
    private String lastName;
    private String email;

    public User() {
        setDefaults();
    }

    public User(long id, IUserPersistence persistence) throws Exception {
        setDefaults();
        persistence.loadUserByID(id, this);
    }

    public User(String bannerID, IUserPersistence persistence) throws Exception {
        setDefaults();
        persistence.loadUserByBannerID(bannerID, this);
    }

    public void setDefaults() {
        id = -1;
        password = "";
        bannerID = "";
        firstName = "";
        lastName = "";
        email = "";
    }

    public void setID(long id) {
        this.id = id;
    }

    public long getID() {
        return id;
    }

    // These are here for the Thymeleaf / Spring binding nonsense.
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setBannerID(String bannerID) {
        this.bannerID = bannerID;
    }

    public String getBannerID() {
        return bannerID;
    }

    // Also here for Thymeleaf nonsense.
    public String getBanner() {
        return bannerID;
    }

    public void setFirstName(String name) {
        firstName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String name) {
        lastName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public boolean isValidUser() {
        return id != -1;
    }

    public boolean isInvalidUser() {
        return id == -1;
    }

    public void createUser(IUserPersistence userDB, IPasswordEncryption passwordEncryption, IUserNotifications notification) throws Exception {
        String rawPassword = password;
        this.password = passwordEncryption.encryptPassword(this.password);
        userDB.createUser(this);
        notification.sendUserLoginCredentials(this, rawPassword);
    }

    private static boolean isStringNullOrEmpty(String s) {
        if (null == s) {
            return true;
        }
        return s.isEmpty();
    }

    public static boolean isBannerIDValid(String bannerID) {
        return !isStringNullOrEmpty(bannerID);
    }

    public static boolean isFirstNameValid(String name) {
        return !isStringNullOrEmpty(name);
    }

    public static boolean isLastNameValid(String name) {
        return !isStringNullOrEmpty(name);
    }

    public static boolean isEmailValid(String email) {
        if (isStringNullOrEmpty(email)) {
            return false;
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public List<List<IUser>> getDetailsOfStudents(List<List<Long>> studentIdGroups, IUserPersistence userDB) throws Exception {
        AccessControlAbstractFactory abstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        List<List<IUser>> students = new ArrayList<>();

        for (List<Long> group : studentIdGroups) {
            List<IUser> users = new ArrayList<>();
            for (long groupMember : group) {
                IUser user = abstractFactory.makeUser();
                try {
                    userDB.loadUserByID(groupMember, user);
                } catch (Exception e) {
                    myLogger.error(String.format("SQL Exception encountered while loading details for user with userID %s", groupMember), e);
                    throw e;
                }
                users.add(user);
            }
            students.add(users);
        }

        return students;
    }

    public List<IUser> getStudentsDetails(List<Long> studentIds, IUserPersistence userDB, AccessControlAbstractFactory abstractFactory) throws Exception {
        List<IUser> students = new ArrayList<>();
        for (long student : studentIds) {
            IUser user = abstractFactory.makeUser();
            try {
                userDB.loadUserByID(student, user);
            } catch (Exception e) {
                myLogger.error(String.format("SQL Exception encountered while loading details for user with userID %s", student), e);
                throw e;
            }
            students.add(user);
        }
        return students;
    }

}

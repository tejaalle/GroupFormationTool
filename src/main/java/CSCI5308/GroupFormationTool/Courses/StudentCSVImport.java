package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.AccessControlAbstractFactory;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.AccessControl.IUserNotifications;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;
import CSCI5308.GroupFormationTool.Security.SecurityAbstractFactory;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class StudentCSVImport {

    private List<String> successResults;
    private List<String> failureResults;
    private ICourse course;
    private IUserPersistence userDB;
    private IPasswordEncryption passwordEncryption;
    private IUserNotifications notificationService;
    private IStudentCSVParser parser;

    Logger myLogger = LoggerUtil.getLoggerInstance(StudentCSVImport.class);

    public StudentCSVImport(IStudentCSVParser parser, ICourse course) {
        AccessControlAbstractFactory abstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        SecurityAbstractFactory securityAbstractFactory = SystemConfig.instance().getSecurityConcreteFactoryState();
        this.course = course;
        successResults = new ArrayList<String>();
        failureResults = new ArrayList<String>();
        userDB = abstractFactory.makeUserDB();
        passwordEncryption = securityAbstractFactory.makeBCryptPasswordEncryption();
        notificationService = abstractFactory.makeEmail();
        this.parser = parser;
        enrollStudentFromRecord();
    }

    private void enrollStudentFromRecord() {
        List<IUser> studentList = parser.parseCSVFile(failureResults);
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();

        for (IUser loadedUser : studentList) {
            String bannerID = loadedUser.getBanner();
            String firstName = loadedUser.getFirstName();
            String lastName = loadedUser.getLastName();
            String email = loadedUser.getEmail();
            String userDetails = bannerID + " " + firstName + " " + lastName + " " + email;
            IUser user = accessControlAbstractFactory.makeUser();

            try {
                userDB.loadUserByBannerID(bannerID, user);
            } catch (Exception e1) {
                myLogger.warn(String.format("SQL Exception encountered while loading"
                        + " user details with bannerID %s", bannerID), e1);
                failureResults.add("Unable to save this user to DB: " + userDetails);
                //Not throwing an error since the failure for one user shouldnt throw an exception and stop the
                //execution of entire code
                return;
            }

            if (user.isInvalidUser()) {
                user.setBannerID(bannerID);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);

                try {
                    user.createUser(userDB, passwordEncryption, notificationService);
                    successResults.add("Created: " + userDetails);
                    userDB.loadUserByBannerID(bannerID, user);
                } catch (Exception e) {
                    myLogger.error(String.format("SQL Exception encountered while loading"
                            + " user details with bannerID %s", bannerID), e);
                    failureResults.add("Unable to save this user to DB: " + userDetails);
                    //Not throwing an error since the failure for one user shouldnt throw an exception and stop the
                    //execution of entire code
                    return;
                }
            }

            try {
                course.enrollUserInCourse(Role.STUDENT, user);
                successResults.add("User enrolled in course: " + userDetails);
            } catch (Exception e) {
                myLogger.error(String.format("SQL Exception encountered while enrolling"
                        + " user details with bannerID %s", bannerID), e);
                //Not throwing an error since the failure for one user shouldnt throw an exception and stop the
                //execution of entire code
                failureResults.add("Unable to enroll user in course: " + userDetails);
            }
        }
    }

    public List<String> getSuccessResults() {
        return successResults;
    }

    public List<String> getFailureResults() {
        return failureResults;
    }

}

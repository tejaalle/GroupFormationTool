package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.AccessControlAbstractFactory;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseUserRelationshipDB implements ICourseUserRelationshipPersistence {

    Logger myLogger = LoggerUtil.getLoggerInstance(CourseUserRelationshipDB.class);

    public List<IUser> findAllUsersWithoutCourseRole(Role role, long courseID) throws Exception {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        List<IUser> users = new ArrayList<>();
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spFindUsersWithoutCourseRole(?, ?)");
            proc.setParameter(1, role.toString());
            proc.setParameter(2, courseID);
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    long userID = results.getLong(1);
                    String bannerID = results.getString(2);
                    String firstName = results.getString(3);
                    String lastName = results.getString(4);
                    IUser user = accessControlAbstractFactory.makeUser();
                    user.setID(userID);
                    user.setBannerID(bannerID);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while loading all"
                    + " users without Course role for course %d and role %s", courseID, role.toString()), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return users;
    }

    public List<IUser> findAllUsersWithCourseRole(Role role, long courseID) throws Exception {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        List<IUser> users = new ArrayList<>();
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spFindUsersWithCourseRole(?, ?)");
            proc.setParameter(1, role.toString());
            proc.setParameter(2, courseID);
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    long userID = results.getLong(1);
                    IUser user = accessControlAbstractFactory.makeUser();
                    user.setID(userID);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while loading all"
                    + " users Course role for course %d and role %s", courseID, role.toString()), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return users;
    }

    public void enrollUser(ICourse course, IUser user, Role role) throws Exception {
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spEnrollUser(?, ?, ?)");
            proc.setParameter(1, course.getId());
            proc.setParameter(2, user.getID());
            proc.setParameter(3, role.toString());
            proc.execute();
            myLogger.info(String.format("Enrolled "
                            + " users with bannerID %s for Course with id %d with Role %s"
                    , user.getBannerID(), course.getId(), role.toString()));
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered enrolling "
                            + " users with bannerID %s for Course with id %d and role %s"
                    , user.getBannerID(), course.getId(), role.toString()), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
    }

    public List<Role> loadUserRolesForCourse(ICourse course, IUser user) throws Exception {
        List<Role> roles = new ArrayList<Role>();
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spLoadUserRolesForCourse(?, ?)");
            proc.setParameter(1, course.getId());
            proc.setParameter(2, user.getID());
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    Role role = Role.valueOf(results.getString(1).toUpperCase());
                    roles.add(role);
                }
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception occurred while loading "
                    + " user roles of user with id %s for course id %d", user.getBannerID(), course.getId()), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return roles;
    }

}

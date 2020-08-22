package CSCI5308.GroupFormationTool.AccessControl;

import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB implements IUserPersistence {

    Logger myLogger = LoggerUtil.getLoggerInstance(UserDB.class);

    public void loadUserByID(long id, IUser user) throws Exception {
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spLoadUser(?)");
            proc.setParameter(1, id);
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    long userID = results.getLong(1);
                    String bannerID = results.getString(2);
                    String password = results.getString(3);
                    String firstName = results.getString(4);
                    String lastName = results.getString(5);
                    String email = results.getString(6);
                    user.setID(userID);
                    user.setBannerID(bannerID);
                    user.setPassword(password);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);
                }
            }
        } catch (SQLException e) {
            //logging it in the calling method since we dont know the banner id here
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
    }

    public void loadUserByBannerID(String bannerID, IUser user) throws Exception {
        CallStoredProcedure proc = null;
        long userID = -1;

        try {
            proc = new CallStoredProcedure("spFindUserByBannerID(?)");
            proc.setParameter(1, bannerID);
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    userID = results.getLong(1);
                }
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while searching for user with bannerId %s", bannerID), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
        // If we found the ID load the full details.
        if (userID > -1) {
            try {
                loadUserByID(userID, user);
            } catch (Exception e) {
                myLogger.error(String.format("SQL Exception encountered while loading details for user with bannerId %s", bannerID), e);
                throw e;
            }
        }
    }

    public void createUser(IUser user) throws Exception {
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spCreateUser(?, ?, ?, ?, ?, ?)");
            proc.setParameter(1, user.getBannerID());
            proc.setParameter(2, user.getPassword());
            proc.setParameter(3, user.getFirstName());
            proc.setParameter(4, user.getLastName());
            proc.setParameter(5, user.getEmail());
            proc.registerOutputParameterLong(6);
            proc.execute();
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while creating new user with bannerId %s", user.getBannerID()), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
    }

    public void changePassword(String bannerID, String bPassword) throws Exception {
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spUpdatePasswordforUser(?,?)");
            proc.setParameter(1, bannerID);
            proc.setParameter(2, bPassword);
            proc.execute();
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while updating password for "
                    + "user with bannerId %s", bannerID), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
    }

}

package CSCI5308.GroupFormationTool.AccessControl;

import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {

    private static CurrentUser uniqueInstance = null;
    Logger myLogger = LoggerUtil.getLoggerInstance(CurrentUser.class);

    private CurrentUser() {

    }

    public static CurrentUser instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new CurrentUser();
        }
        return uniqueInstance;
    }

    public IUser getCurrentAuthenticatedUser() throws Exception {
        AccessControlAbstractFactory abstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUserPersistence userDB = abstractFactory.makeUserDB();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            String bannerID = authentication.getPrincipal().toString();
            IUser user = abstractFactory.makeUser();
            try {
                userDB.loadUserByBannerID(bannerID, user);
                if (user.isValidUser()) {
                    myLogger.info(String.format("Retrieved current authenticated user "
                            + "current user details with bannerId %s", bannerID));
                    return user;
                }
            } catch (Exception e) {
                myLogger.error(String.format("SQL Exception encountered while fetching "
                        + "current user details with bannerId %s", bannerID), e);
                throw e;
            }
        }

        return null;
    }

}

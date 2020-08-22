package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.AccessControlAbstractFactory;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;
import CSCI5308.GroupFormationTool.SystemConfig;
import CSCI5308.GroupFormationTool.SystemConfigTest;

public class CurrentUserMock {

    public IUser getCurrentAuthenticatedUser() throws Exception {
        AccessControlTestAbstractFactory abstractFactory = SystemConfigTest.instance().getAccessControlTestConcreteFactoryState();
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUserPersistence userDB = abstractFactory.makeUserDBMock();
        String bannerID = "B00000000";
        IUser user = accessControlAbstractFactory.makeUser();
        userDB.loadUserByBannerID(bannerID, user);
        return user;
    }

}

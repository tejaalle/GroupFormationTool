package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.AccessControlAbstractFactory;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;
import CSCI5308.GroupFormationTool.SystemConfig;

public class UserDBMock implements IUserPersistence {

    public void loadUserByID(long id, IUser user) {
        user.setID(id);
        user.setBannerID("B00000000");
        user.setPassword("Pass@123");
        user.setFirstName("Rob");
        user.setLastName("Hawkey");
        user.setEmail("rhawkey@dal.ca");
    }

    public void loadUserByBannerID(String bannerID, IUser user) {
        user.setID(1);
        user.setBannerID(bannerID);
        user.setPassword("Pass@123");
        user.setFirstName("Rob");
        user.setLastName("Hawkey");
        user.setEmail("rhawkey@dal.ca");
    }

    public void createUser(IUser user) {
        user.setID(0);
        user.setBannerID("B00000000");
        user.setPassword("Pass@123");
        user.setFirstName("Rob");
        user.setLastName("Hawkey");
        user.setEmail("rhawkey@dal.ca");
    }

    public boolean updateUser(IUser user) {
        user.setID(0);
        user.setBannerID("B00000000");
        user.setPassword("Pass@123");
        user.setFirstName("Rob");
        user.setLastName("Hawkey");
        user.setEmail("rhawkey@dal.ca");
        return true;
    }

    @Override
    public void changePassword(String bannerID, String bPassword) {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        user.setBannerID(bannerID);
        user.setPassword(bPassword);
    }

}

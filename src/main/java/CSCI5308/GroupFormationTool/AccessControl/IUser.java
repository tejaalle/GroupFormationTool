package CSCI5308.GroupFormationTool.AccessControl;

import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;

import java.util.List;

public interface IUser {

    public void setDefaults();

    public void setID(long id);

    public long getID();

    public void setId(long id);

    public long getId();

    public void setPassword(String password);

    public String getPassword();

    public void setBannerID(String bannerID);

    public String getBannerID();

    public String getBanner();

    public void setFirstName(String name);

    public String getFirstName();

    public void setLastName(String name);

    public String getLastName();

    public void setEmail(String email);

    public String getEmail();

    public boolean isValidUser();

    public boolean isInvalidUser();

    public void createUser(IUserPersistence userDB, IPasswordEncryption passwordEncryption, IUserNotifications notification) throws Exception;

    public List<List<IUser>> getDetailsOfStudents(List<List<Long>> studentIdGroups, IUserPersistence userDB) throws Exception;

    public List<IUser> getStudentsDetails(List<Long> studentIds, IUserPersistence userDB, AccessControlAbstractFactory abstractFactory) throws Exception;

}

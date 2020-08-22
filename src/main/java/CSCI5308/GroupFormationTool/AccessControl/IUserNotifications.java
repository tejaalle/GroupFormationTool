package CSCI5308.GroupFormationTool.AccessControl;

public interface IUserNotifications {

    public void sendUserLoginCredentials(IUser user, String rawPassword) throws Exception;

}

package CSCI5308.GroupFormationTool.AccessControl;

public class AccessControlConcreteFactory extends AccessControlAbstractFactory {

    @Override
    public IUserNotifications makeEmail() {
        return new Email();
    }

    @Override
    public IUserPersistence makeUserDB() {
        return new UserDB();
    }

    @Override
    public IUser makeUser() {
        return new User();
    }

}

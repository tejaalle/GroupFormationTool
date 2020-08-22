package CSCI5308.GroupFormationTool.AccessControl;

public abstract class AccessControlAbstractFactory {

    private static AccessControlAbstractFactory abstractFactory;

    public abstract IUser makeUser();

    public abstract IUserNotifications makeEmail();

    public abstract IUserPersistence makeUserDB();

    public static AccessControlAbstractFactory getInstance(AccessControlState state) {
        abstractFactory = state.concreteMethod();
        return abstractFactory;
    }

}

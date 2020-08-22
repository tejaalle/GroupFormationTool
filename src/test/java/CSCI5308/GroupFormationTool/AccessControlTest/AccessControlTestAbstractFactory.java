package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.IUserNotifications;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;

public abstract class AccessControlTestAbstractFactory {

    private static AccessControlTestAbstractFactory abstractFactorty;

    public abstract IUserPersistence makeUserDBMock();

    public abstract IUserNotifications makeEmailMock();

    public static AccessControlTestAbstractFactory getInstance(AccessControlTestState state) {
        abstractFactorty = state.concreteMethod();
        return abstractFactorty;
    }

}

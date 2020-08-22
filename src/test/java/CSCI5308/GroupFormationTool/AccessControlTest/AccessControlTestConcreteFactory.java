package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.IUserNotifications;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;

public class AccessControlTestConcreteFactory extends AccessControlTestAbstractFactory {

    @Override
    public IUserPersistence makeUserDBMock() {
        return new UserDBMock();
    }

    @Override
    public IUserNotifications makeEmailMock() {
        return new EmailMock();
    }

}

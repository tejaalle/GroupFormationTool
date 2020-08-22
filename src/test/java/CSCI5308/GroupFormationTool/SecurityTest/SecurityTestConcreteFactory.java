package CSCI5308.GroupFormationTool.SecurityTest;

import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;
import CSCI5308.GroupFormationTool.Security.IRandomString;

public class SecurityTestConcreteFactory extends SecurityTestAbstractFactory {

    @Override
    public IPasswordEncryption makePasswordEncryptionMock() {
        return new PasswordEncryptionMock();
    }

    @Override
    public IRandomString makeRandomStringMOck() {
        return new RandomStringMock();
    }

}

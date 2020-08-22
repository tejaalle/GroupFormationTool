package CSCI5308.GroupFormationTool.SecurityTest;

import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;
import CSCI5308.GroupFormationTool.Security.IRandomString;

public abstract class SecurityTestAbstractFactory {

    private static SecurityTestAbstractFactory abstractFactory;

    public abstract IPasswordEncryption makePasswordEncryptionMock();

    public abstract IRandomString makeRandomStringMOck();

    public static SecurityTestAbstractFactory getInstance(SecurityTestState state) {
        abstractFactory = state.concreteMethod();
        return abstractFactory;
    }

}

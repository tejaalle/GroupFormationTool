package CSCI5308.GroupFormationTool.Security;

public abstract class SecurityAbstractFactory {

    private static SecurityAbstractFactory abstractFactory;

    public abstract IPasswordEncryption makeBCryptPasswordEncryption();

    public abstract IRandomString makeRandomString();

    public static SecurityAbstractFactory getInstance(SecurityState state) {
        abstractFactory = state.concreteMethod();
        return abstractFactory;
    }

}

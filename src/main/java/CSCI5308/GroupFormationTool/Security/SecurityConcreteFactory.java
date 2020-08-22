package CSCI5308.GroupFormationTool.Security;

public class SecurityConcreteFactory extends SecurityAbstractFactory {

    @Override
    public IPasswordEncryption makeBCryptPasswordEncryption() {
        return new BCryptPasswordEncryption();
    }

    @Override
    public IRandomString makeRandomString() {
        return new RandomString();
    }

}

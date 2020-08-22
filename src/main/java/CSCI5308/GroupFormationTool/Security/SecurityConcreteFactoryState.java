package CSCI5308.GroupFormationTool.Security;

public class SecurityConcreteFactoryState extends SecurityState {

    @Override
    public SecurityAbstractFactory concreteMethod() {
        return new SecurityConcreteFactory();
    }

}

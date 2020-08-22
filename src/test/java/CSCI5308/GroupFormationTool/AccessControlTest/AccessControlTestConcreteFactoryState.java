package CSCI5308.GroupFormationTool.AccessControlTest;

public class AccessControlTestConcreteFactoryState extends AccessControlTestState {

    @Override
    public AccessControlTestAbstractFactory concreteMethod() {
        return new AccessControlTestConcreteFactory();
    }

}

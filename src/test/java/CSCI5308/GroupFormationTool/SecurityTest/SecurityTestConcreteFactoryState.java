package CSCI5308.GroupFormationTool.SecurityTest;

public class SecurityTestConcreteFactoryState extends SecurityTestState {

    @Override
    public SecurityTestAbstractFactory concreteMethod() {
        return new SecurityTestConcreteFactory();
    }

}

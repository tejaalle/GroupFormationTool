package CSCI5308.GroupFormationTool.AccessControl;

public class AccessControlConcreteFactoryState extends AccessControlState {

    @Override
    public AccessControlAbstractFactory concreteMethod() {
        return new AccessControlConcreteFactory();
    }

}

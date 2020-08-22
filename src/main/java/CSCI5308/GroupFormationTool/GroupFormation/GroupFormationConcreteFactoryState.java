package CSCI5308.GroupFormationTool.GroupFormation;

public class GroupFormationConcreteFactoryState extends GroupFormationState {

    @Override
    public GroupFormationAbstractFactory concreteMethod() {

        return new GroupFormationConcreteFactory();
    }

}

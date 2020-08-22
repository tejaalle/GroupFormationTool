package CSCI5308.GroupFormationTool.GroupFormation;

public abstract class GroupFormationAbstractFactory {

    private static GroupFormationAbstractFactory abstractFactory;

    public abstract IAlgorithm makeGroupFormationAlgorithm();

    public static GroupFormationAbstractFactory getInstance(GroupFormationState state) {
        abstractFactory = state.concreteMethod();
        return abstractFactory;
    }

}

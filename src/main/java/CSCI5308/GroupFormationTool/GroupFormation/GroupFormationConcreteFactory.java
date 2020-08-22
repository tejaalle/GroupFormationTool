package CSCI5308.GroupFormationTool.GroupFormation;

public class GroupFormationConcreteFactory extends GroupFormationAbstractFactory {

    @Override
    public IAlgorithm makeGroupFormationAlgorithm() {

        return new GroupFormationAlgorithm();
    }

}

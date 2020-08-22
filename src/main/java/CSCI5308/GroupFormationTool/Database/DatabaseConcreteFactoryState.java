package CSCI5308.GroupFormationTool.Database;

public class DatabaseConcreteFactoryState extends DatabaseState {

    @Override
    public DatabaseAbstractFactory concreteMethod() {
        return new DatabaseConcreteFactory();
    }

}

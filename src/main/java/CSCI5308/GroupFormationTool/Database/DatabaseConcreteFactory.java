package CSCI5308.GroupFormationTool.Database;

public class DatabaseConcreteFactory extends DatabaseAbstractFactory {

    @Override
    public IDatabaseConfiguration makeDefaultDatabaseConfiguration() {
        return new DefaultDatabaseConfiguration();
    }

}

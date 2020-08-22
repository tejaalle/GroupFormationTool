package CSCI5308.GroupFormationTool.Database;

public abstract class DatabaseAbstractFactory {

    private static DatabaseAbstractFactory abstractFactory;

    public abstract IDatabaseConfiguration makeDefaultDatabaseConfiguration();

    public static DatabaseAbstractFactory getInstance(DatabaseState state) {
        abstractFactory = state.concreteMethod();
        return abstractFactory;
    }

}

package CSCI5308.GroupFormationTool.Database;

public class DefaultDatabaseConfiguration implements IDatabaseConfiguration {

    private static final String URL = System.getenv("database_url");

    private static final String USER = System.getenv("database_username");

    private static final String PASSWORD = System.getenv("database_password");

    public String getDatabaseUserName() {
        return USER;
    }

    public String getDatabasePassword() {
        return PASSWORD;
    }

    public String getDatabaseURL() {
        return URL;
    }

}

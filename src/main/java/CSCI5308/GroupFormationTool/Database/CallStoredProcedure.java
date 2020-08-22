package CSCI5308.GroupFormationTool.Database;

import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class CallStoredProcedure {

    private String storedProcedureName;
    private Connection connection;
    private CallableStatement statement;

    Logger myLogger = LoggerUtil.getLoggerInstance(CallStoredProcedure.class);

    public CallStoredProcedure(String storedProcedureName) throws SQLException {
        this.storedProcedureName = storedProcedureName;
        connection = null;
        statement = null;
        openConnection();
        createStatement();
    }

    private void createStatement() throws SQLException {
        statement = connection.prepareCall("{call " + storedProcedureName + "}");
    }

    private void openConnection() throws SQLException {
        connection = ConnectionManager.instance().getDBConnection();
    }

    public void cleanup() {
        try {
            if (null != statement) {
                statement.close();
            }
            if (null != connection) {
                connection.close();
            }
        } catch (Exception e) {
            myLogger.error(String.format("SQL Exception encountered"
                    + "while attempting to cleanup database connections"), e);
        }
    }

    public void setParameter(int paramIndex, String value) throws SQLException {
        statement.setString(paramIndex, value);
    }

    public void registerOutputParameterString(int paramIndex) throws SQLException {
        statement.registerOutParameter(paramIndex, java.sql.Types.VARCHAR);
    }

    public void setParameter(int paramIndex, long value) throws SQLException {
        statement.setLong(paramIndex, value);
    }

    public void setDate(int paramIndex, Date value) throws SQLException {
        statement.setDate(paramIndex, value);
    }

    public void registerOutputParameterLong(int paramIndex) throws SQLException {
        statement.registerOutParameter(paramIndex, java.sql.Types.BIGINT);
    }

    public ResultSet executeWithResults() throws SQLException {
        if (statement.execute()) {
            return statement.getResultSet();
        }
        return null;
    }

    public void execute() throws SQLException {
        statement.execute();
    }

}

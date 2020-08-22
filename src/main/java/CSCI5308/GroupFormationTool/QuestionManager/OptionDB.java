package CSCI5308.GroupFormationTool.QuestionManager;

import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionDB implements IOptionsPersistence {

    Logger myLogger = LoggerUtil.getLoggerInstance(OptionDB.class);

    @Override
    public List<Options> loadOptionsByQuestionID(long questionID) throws SQLException {
        List<Options> optionsList = new ArrayList<>();
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spLoadOptionsbyQuestionID(?)");
            proc.setParameter(1, questionID);
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    Options options = new Options();
                    options.setOptions(results.getString(1));
                    options.setStoredAs(results.getString(2));
                    optionsList.add(options);
                }
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while loading options"
                    + " by questionID %d", questionID), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return optionsList;
    }

}

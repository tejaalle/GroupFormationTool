package CSCI5308.GroupFormationTool.QuestionManager;

import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionTypesDB implements IQuestionTypesPersistence {

    Logger myLogger = LoggerUtil.getLoggerInstance(QuestionTypesDB.class);

    @Override
    public List<IQuestionType> getTypes() throws Exception {
        QuestionManagerAbstractFactory abstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        List<IQuestionType> questionTypesList = new ArrayList<>();
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spLoadQuestionTypes()");
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    int typeID = results.getInt(1);
                    String typeName = results.getString(2);
                    IQuestionType questionType = abstractFactory.makeQuestionType();
                    questionType.setTypeID(typeID);
                    questionType.setTypeName(typeName);
                    questionTypesList.add(questionType);
                }
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while loading all"
                    + " question Types"), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return questionTypesList;
    }

    @Override
    public int getTypeOfQuestion(long questionID) throws Exception {
        int type = 0;
        CallStoredProcedure proc = null;
        try {
            proc = new CallStoredProcedure("spFetchTypeOfQuestion(?)");
            proc.setParameter(1, questionID);
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    type = results.getInt(1);
                }
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while retrieving"
                    + " question Type for questionId %d", questionID), e);
            throw e;

        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
        return type;
    }

}

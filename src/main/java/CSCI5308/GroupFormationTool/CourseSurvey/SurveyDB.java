package CSCI5308.GroupFormationTool.CourseSurvey;

import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SurveyDB implements ISurveyPersistence {

    Logger myLogger = LoggerUtil.getLoggerInstance(SurveyDB.class);

    @Override
    public void surveyPublishState(long courseID, int state) throws Exception {
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spSurveyPublishState(?,?)");
            proc.setParameter(1, courseID);
            proc.setParameter(2, state);
            proc.execute();
            myLogger.info(String.format("Survey publish state done"
                    + "successfully for course with id %d", courseID));
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while publishing survey "
                    + "state" + " for course with id %d", courseID), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
    }

    @Override
    public boolean isSurveyPublished(long courseID) throws Exception {
        boolean isPublished = false;
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spCheckPublishByCourseID(?)");
            proc.setParameter(1, courseID);
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    isPublished = results.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered checking published survey "
                    + "state" + " for course with id %d", courseID), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return isPublished;
    }

    @Override
    public void addQuestionsToSurvey(long courseID, long questionID) throws Exception {
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spAddQuestionToSurvey(?, ?)");
            proc.setParameter(1, courseID);
            proc.setParameter(2, questionID);
            proc.execute();
            myLogger.info(String.format("Added Question with questionID"
                    + " %d to Survey Successfully" + " %d", questionID, courseID));
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while publishing survey "
                    + "state" + " for course with id %d", courseID), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
    }

    @Override
    public List<Integer> loadQuestionFromSurvey(long courseID) throws Exception {
        List<Integer> questionIDs = new ArrayList<>();
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spLoadQuestionFromSurvey(?)");
            proc.setParameter(1, courseID);
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    questionIDs.add(results.getInt(1));
                }
                myLogger.info(String.format("Loaded Questions from survey successfully for"
                        + " course with courseID" + " %d", courseID));
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while loading questions "
                    + " for course with id %d", courseID), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return questionIDs;
    }

    @Override
    public void removeQuestionsFromSurvey(long questionID) throws Exception {
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spRemoveQuestionFromSurvey(?)");
            proc.setParameter(1, questionID);
            proc.execute();
            myLogger.info(String.format("Removed Question from survey successfully "
                    + " with questionID %d", questionID));
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while removing question "
                    + " from course with questionID %d", questionID), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
    }

}

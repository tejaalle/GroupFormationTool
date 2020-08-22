package CSCI5308.GroupFormationTool.QuestionManager;

import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDB implements IQuestionPersistence {

    Logger myLogger = LoggerUtil.getLoggerInstance(QuestionDB.class);

    @Override
    public List<Integer> loadQuestionByInstructorID(long userID) throws Exception {
        List<Integer> questionIDs = new ArrayList<>();
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spLoadQuestionIDs(?)");
            proc.setParameter(1, userID);
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    questionIDs.add(results.getInt(1));
                }
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while loading"
                    + " questions for instructor with instructorID %d", userID), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return questionIDs;
    }

    @Override
    public Question loadQuestions(long id) throws Exception {
        CallStoredProcedure proc = null;
        Question question = new Question();

        try {
            proc = new CallStoredProcedure("spLoadQuestions(?)");
            proc.setParameter(1, id);
            ResultSet results = proc.executeWithResults();

            if (null != results) {
                while (results.next()) {
                    question.setID(results.getLong(1));
                    question.setTitle(results.getString(2));
                    question.setText(results.getString(3));
                    question.setDate(results.getDate(4));
                    question.setType(results.getString(5));
                }
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while loading"
                    + " question with id %d ", id), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return question;
    }

    @Override
    public void deleteQuestionByID(long questionID, long instructorID) throws Exception {
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spDeleteQuestionByID(?, ?)");
            proc.setParameter(1, questionID);
            proc.setParameter(2, instructorID);
            proc.execute();
            myLogger.info(String.format("Deleted"
                            + " question with questionID %d for instructorID %d", questionID
                    , instructorID));
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while deleting"
                            + " question with questionID %d for instructorID %d", questionID
                    , instructorID), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
    }

    @Override
    public void insertQuestionMC(String title, String text, int type, Date date, long userID, String optionValues, String optionKeys) throws Exception {
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spInsertQuestion(?, ?,?,?,?,?,?)");
            proc.setParameter(1, title);
            proc.setParameter(2, text);
            proc.setParameter(3, type);
            proc.setDate(4, date);
            proc.setParameter(5, userID);
            proc.setParameter(6, optionValues);
            proc.setParameter(7, optionKeys);
            proc.execute();
            myLogger.info(String.format("Inserted new question successfully"
                    + " with text %s for userID %d", text, userID));
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while inserting"
                    + " new question with title %s for userID %d", text, userID), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
    }

    @Override
    public boolean insertCriteriaForQuestion(long courseID, long questionID, String criteria) throws Exception {
        CallStoredProcedure proc = null;
        boolean status = false;

        try {
            proc = new CallStoredProcedure("spCreateCriteria(?, ?,?)");
            proc.setParameter(1, courseID);
            proc.setParameter(2, questionID);
            proc.setParameter(3, criteria);
            proc.execute();
            status = true;
            myLogger.info(String.format("Inserted new criteria successfully"
                    + " with courseid %d and questionid %d with criteria %s", courseID, questionID, criteria));
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while inserting"
                    + " new question criteria with courseid %d and questionid %d and criteria %s", courseID, questionID, criteria), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return status;
    }

    @Override
    public boolean deleteQuestionCriteria(long courseID) throws Exception {
        CallStoredProcedure proc = null;
        boolean status = false;

        try {
            proc = new CallStoredProcedure("spDeleteCriteria(?)");
            proc.setParameter(1, courseID);
            proc.execute();
            status = true;
            myLogger.info(String.format("Deleted new criteria successfully"
                    + " with courseid %d ", courseID));
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while deleting"
                    + " criteria with courseid %d", courseID), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return status;
    }

    @Override
    public List<IQuestion> fetchQuestionCriteria(long courseID) throws Exception {
        QuestionManagerAbstractFactory abstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        List<IQuestion> questionList = new ArrayList<>();
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spFetchCriteria(?)");
            proc.setParameter(1, courseID);
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    IQuestion question = abstractFactory.makeQuestion();
                    question.setID(results.getLong(1));
                    question.setCriteria(results.getString(2));
                    questionList.add(question);
                }
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while fetching"
                    + " criteria with courseid %d", courseID), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return questionList;
    }

}

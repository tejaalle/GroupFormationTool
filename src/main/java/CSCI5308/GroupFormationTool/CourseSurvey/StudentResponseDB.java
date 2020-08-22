package CSCI5308.GroupFormationTool.CourseSurvey;

import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionManagerAbstractFactory;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentResponseDB implements IStudentResponsePersistence {

    Logger myLogger = LoggerUtil.getLoggerInstance(StudentResponseDB.class);

    @Override
    public List<IQuestion> loadStudentResponse(long courseId, long studentId) throws Exception {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        List<IQuestion> questionList = new ArrayList<>();
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spLoadStudentResponse(?,?)");
            proc.setParameter(1, courseId);
            proc.setParameter(2, studentId);
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    IQuestion question = questionManagerAbstractFactory.makeQuestion();
                    question.setID(results.getLong(1));
                    question.setAnswer(results.getString(2));
                    questionList.add(question);
                }
                myLogger.info(String.format("Loaded student response "
                        + " for course with id %d and student with id %d", courseId, studentId));
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while loading student response "
                    + " for course with id %d and student with id %d", courseId, studentId), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return questionList;
    }

    @Override
    public boolean insertStudentResponse(long courseId, long studentId, long questionId, String answers) throws Exception {
        boolean status = false;
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spInsertStudentResponse(?, ?,?,?)");
            proc.setParameter(1, courseId);
            proc.setParameter(2, studentId);
            proc.setParameter(3, questionId);
            proc.setParameter(4, answers);
            proc.execute();
            myLogger.info(String.format("Inserted student response "
                    + " for course with id %d , student with id %d "
                    + "questionId %d and answers %s", courseId, studentId, questionId, answers));
            status = true;
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while inserting student response "
                    + " for course with id %d , student with id %d "
                    + "questionId %d and answers %s", courseId, studentId, questionId, answers), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return status;
    }

    @Override
    public List<Long> getAnsweredStudentId(long courseId) throws Exception {
        Set<Long> studentIDs = new HashSet<>();
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spFindAnsweredStudentID(?)");
            proc.setParameter(1, courseId);
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    long userId = results.getInt(1);
                    studentIDs.add(userId);
                }
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while loading answered "
                    + " studentId for course with id %d", courseId), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        List<Long> studentIDsList = new ArrayList<>(studentIDs);
        return studentIDsList;
    }

}

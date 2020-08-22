package CSCI5308.GroupFormationTool.QuestionManager;

import java.sql.Date;
import java.util.List;

public interface IQuestionPersistence {

    public List<Integer> loadQuestionByInstructorID(long userID) throws Exception;

    public Question loadQuestions(long id) throws Exception;

    public void deleteQuestionByID(long questionID, long instructorID) throws Exception;

    public void insertQuestionMC(String title, String text, int type, Date date, long userID, String optionValues, String optionKeys) throws Exception;

    boolean insertCriteriaForQuestion(long courseID, long questionID, String criteria) throws Exception;

    boolean deleteQuestionCriteria(long courseID) throws Exception;

    List<IQuestion> fetchQuestionCriteria(long courseID) throws Exception;

}

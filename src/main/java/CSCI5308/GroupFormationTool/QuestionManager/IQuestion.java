package CSCI5308.GroupFormationTool.QuestionManager;

import CSCI5308.GroupFormationTool.CourseSurvey.IStudentResponsePersistence;

import java.sql.Date;
import java.util.List;

public interface IQuestion {

    String getCriteria();

    void setCriteria(String criteria);

    String getAnswer();

    void setAnswer(String answer);

    String getTitle();

    void setTitle(String title);

    List<Options> getOptionsList();

    void setOptionsList(List<Options> optionsList);

    long getID();

    void setID(long id);

    String getText();

    void setText(String text);

    Date getDate();

    void setDate(Date date);

    String getType();

    void setType(String type);

    void addOption(Options option, int count);

    List<Question> allQuestionsData(long userID, IQuestionPersistence questionDB, IOptionsPersistence optionDB) throws Exception;

    List<Question> questionsData(List<Integer> questionIDs, IQuestionPersistence questionDB, IOptionsPersistence optionDB) throws Exception;

    List<Question> sortByTitle(long userID, IQuestionPersistence questionDB, IOptionsPersistence optionDB) throws Exception;

    List<Question> sortByDate(long userID, IQuestionPersistence questionDB, IOptionsPersistence optionDB) throws Exception;

    List<Question> unAddedQuestionsData(List<Question> questions, List<Integer> questionids);

    void questionCriteria(List<Question> questionList, long courseID, IQuestionPersistence questionDB) throws Exception;

    boolean isQuestionCriteriaCreated(IQuestionPersistence questionDB, long courseID) throws Exception;

    List<List<IQuestion>> getStudentsAnswers(List<Long> studentIdList, IStudentResponsePersistence studentResponseDB, long courseID) throws Exception;

    public List<IQuestion> getStudentAnswers(long studentId, IStudentResponsePersistence studentResponseDB, long courseID, IQuestionPersistence questionsDB, QuestionManagerAbstractFactory abstractFactory) throws Exception;

}
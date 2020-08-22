package CSCI5308.GroupFormationTool.QuestionManager;

import CSCI5308.GroupFormationTool.CourseSurvey.IStudentResponsePersistence;

import java.util.List;

public interface IQuestions {

    List<Question> getQuestionsList();

    public void setQuestionsList(List<Question> questionsList);

    public void addQuestion(int count);

    public boolean insertAllQuestions(IStudentResponsePersistence studentResponseDB, long studentID, long courseID, IQuestions questions) throws Exception;

}

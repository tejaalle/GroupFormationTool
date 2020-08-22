package CSCI5308.GroupFormationTool.CourseSurvey;

import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;

import java.util.List;

public interface IStudentResponsePersistence {

    public List<IQuestion> loadStudentResponse(long courseId, long studentId) throws Exception;

    public boolean insertStudentResponse(long courseId, long studentId, long questionId, String answers) throws Exception;

    public List<Long> getAnsweredStudentId(long courseId) throws Exception;

}

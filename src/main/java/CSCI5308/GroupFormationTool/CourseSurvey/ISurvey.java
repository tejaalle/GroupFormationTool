package CSCI5308.GroupFormationTool.CourseSurvey;

public interface ISurvey {

    public boolean isAttemptedSurvey(IStudentResponsePersistence studentResponseDB, long studentId, long courseId) throws Exception;
}

package CSCI5308.GroupFormationTool.CourseSurvey;

import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;

import java.util.List;

public class Survey implements ISurvey {

    @Override
    public boolean isAttemptedSurvey(IStudentResponsePersistence studentResponseDB, long studentId, long courseId)
            throws Exception {
        List<IQuestion> questions = studentResponseDB.loadStudentResponse(courseId, studentId);

        for (IQuestion question : questions) {
            if (question.getAnswer() != null) {
                return true;
            }
        }

        return false;
    }

}

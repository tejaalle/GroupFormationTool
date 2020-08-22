package CSCI5308.GroupFormationTool.CourseSurvey;

import java.util.List;

public interface ISurveyPersistence {

    public void surveyPublishState(long courseID, int state) throws Exception;

    public boolean isSurveyPublished(long courseID) throws Exception;

    public void addQuestionsToSurvey(long courseID, long questionID) throws Exception;

    public void removeQuestionsFromSurvey(long questionID) throws Exception;

    public List<Integer> loadQuestionFromSurvey(long courseID) throws Exception;

}

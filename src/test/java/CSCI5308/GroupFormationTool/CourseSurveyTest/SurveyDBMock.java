package CSCI5308.GroupFormationTool.CourseSurveyTest;

import CSCI5308.GroupFormationTool.CourseSurvey.ISurveyPersistence;
import CSCI5308.GroupFormationTool.Courses.CoursesAbstractFactory;
import CSCI5308.GroupFormationTool.Courses.ICourse;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionManagerAbstractFactory;
import CSCI5308.GroupFormationTool.SystemConfig;

import java.util.ArrayList;
import java.util.List;

public class SurveyDBMock implements ISurveyPersistence {

    @Override
    public void surveyPublishState(long courseID, int state) {
        CoursesAbstractFactory coursesAbstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractFactory.makeCourse();
        course.setId(courseID);
    }

    @Override
    public boolean isSurveyPublished(long courseID) {
        CoursesAbstractFactory coursesAbstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractFactory.makeCourse();
        course.setId(courseID);
        return true;
    }

    @Override
    public void addQuestionsToSurvey(long courseID, long questionID) {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setID(questionID);
    }

    @Override
    public void removeQuestionsFromSurvey(long questionID) {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setID(questionID);
    }

    @Override
    public List<Integer> loadQuestionFromSurvey(long courseID) {
        List<Integer> questionIDs = new ArrayList<>();
        questionIDs.add(1);
        return questionIDs;
    }

}

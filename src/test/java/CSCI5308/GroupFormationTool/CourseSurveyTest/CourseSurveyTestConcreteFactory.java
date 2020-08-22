package CSCI5308.GroupFormationTool.CourseSurveyTest;

import CSCI5308.GroupFormationTool.CourseSurvey.IStudentResponsePersistence;
import CSCI5308.GroupFormationTool.CourseSurvey.ISurveyPersistence;

public class CourseSurveyTestConcreteFactory extends CourseSurveyTestAbstractFactory {

    @Override
    public ISurveyPersistence makeSurveyDBMock() {

        return new SurveyDBMock();
    }

    @Override
    public IStudentResponsePersistence makeStudentResponseDBMock() {

        return new StudentResponseDBMock();
    }

}

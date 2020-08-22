package CSCI5308.GroupFormationTool.CourseSurveyTest;

import CSCI5308.GroupFormationTool.CourseSurvey.IStudentResponsePersistence;
import CSCI5308.GroupFormationTool.CourseSurvey.ISurveyPersistence;

public abstract class CourseSurveyTestAbstractFactory {

    private static CourseSurveyTestAbstractFactory abstractFactory;

    public abstract ISurveyPersistence makeSurveyDBMock();

    public abstract IStudentResponsePersistence makeStudentResponseDBMock();

    public static CourseSurveyTestAbstractFactory getInstance(CourseSurveyTestState state) {
        abstractFactory = state.concreteMethod();
        return abstractFactory;

    }
}

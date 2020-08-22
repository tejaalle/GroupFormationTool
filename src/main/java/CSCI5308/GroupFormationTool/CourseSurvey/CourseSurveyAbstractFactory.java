package CSCI5308.GroupFormationTool.CourseSurvey;

public abstract class CourseSurveyAbstractFactory {

    private static CourseSurveyAbstractFactory abstractFactory;

    public abstract IStudentResponsePersistence makeStudentResponseDB();

    public abstract ISurveyPersistence makeSurveyDB();

    public abstract ISurvey makeSurvey();

    public static CourseSurveyAbstractFactory getInstance(CourseSurveyState state) {
        abstractFactory = state.concreteMethod();
        return abstractFactory;
    }

}

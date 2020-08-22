package CSCI5308.GroupFormationTool.CourseSurvey;

public class CourseSurveyConcreteFactory extends CourseSurveyAbstractFactory {

    @Override
    public IStudentResponsePersistence makeStudentResponseDB() {

        return new StudentResponseDB();
    }

    @Override
    public ISurveyPersistence makeSurveyDB() {

        return new SurveyDB();
    }

    @Override
    public ISurvey makeSurvey() {

        return new Survey();
    }

}

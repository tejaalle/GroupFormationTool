package CSCI5308.GroupFormationTool.CourseSurvey;

public class CourseSurveyConcreteFactoryState extends CourseSurveyState {

    @Override
    public CourseSurveyAbstractFactory concreteMethod() {

        return new CourseSurveyConcreteFactory();
    }

}

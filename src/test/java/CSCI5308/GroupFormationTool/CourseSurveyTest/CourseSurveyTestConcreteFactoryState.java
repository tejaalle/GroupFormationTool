package CSCI5308.GroupFormationTool.CourseSurveyTest;

public class CourseSurveyTestConcreteFactoryState extends CourseSurveyTestState {

    @Override
    public CourseSurveyTestAbstractFactory concreteMethod() {

        return new CourseSurveyTestConcreteFactory();
    }

}

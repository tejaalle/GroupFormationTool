package CSCI5308.GroupFormationTool.CoursesTest;

public class CoursesTestConcreteFactoryState extends CoursesTestState {

    @Override
    public CoursesTestAbstractFactory concreteMethod() {
        return new CoursesTestConcreteFactory();
    }

}

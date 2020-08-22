package CSCI5308.GroupFormationTool.Courses;

public class CoursesConcreteFactoryState extends CoursesState {

    @Override
    public CoursesAbstractFactory concreteMethod() {
        return new CoursesConcreteFactory();
    }

}

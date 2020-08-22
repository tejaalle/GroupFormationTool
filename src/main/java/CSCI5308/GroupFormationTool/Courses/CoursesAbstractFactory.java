package CSCI5308.GroupFormationTool.Courses;

public abstract class CoursesAbstractFactory {

    private static CoursesAbstractFactory abstractFactory;

    public abstract ICourse makeCourse();

    public abstract ICoursePersistence makeCourseDB();

    public abstract ICourseUserRelationshipPersistence makeCourseUserRelationshipDB();

    public abstract ICourseUserRelationship makeCourseUserRelationship();

    public static CoursesAbstractFactory getInstance(CoursesState state) {
        abstractFactory = state.concreteMethod();
        return abstractFactory;
    }

}

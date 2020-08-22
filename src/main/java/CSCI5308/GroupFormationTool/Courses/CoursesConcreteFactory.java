package CSCI5308.GroupFormationTool.Courses;

public class CoursesConcreteFactory extends CoursesAbstractFactory {

    @Override
    public ICourse makeCourse() {
        return new Course();
    }

    @Override
    public ICoursePersistence makeCourseDB() {
        return new CourseDB();
    }

    @Override
    public ICourseUserRelationshipPersistence makeCourseUserRelationshipDB() {
        return new CourseUserRelationshipDB();
    }

    @Override
    public ICourseUserRelationship makeCourseUserRelationship() {
        return new CourseUserRelationship();
    }

}

package CSCI5308.GroupFormationTool.CoursesTest;

import CSCI5308.GroupFormationTool.Courses.ICoursePersistence;
import CSCI5308.GroupFormationTool.Courses.ICourseUserRelationshipPersistence;

public class CoursesTestConcreteFactory extends CoursesTestAbstractFactory {

    @Override
    public ICourseUserRelationshipPersistence makeCoursesUserRelationshipDBMock() {
        return new CourseUserRelationshipDBMock();
    }

    @Override
    public ICoursePersistence makeCourseDBMock() {
        return new CourseDBMock();
    }

}

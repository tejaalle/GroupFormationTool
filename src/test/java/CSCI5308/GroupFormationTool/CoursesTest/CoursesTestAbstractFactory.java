package CSCI5308.GroupFormationTool.CoursesTest;

import CSCI5308.GroupFormationTool.Courses.ICoursePersistence;
import CSCI5308.GroupFormationTool.Courses.ICourseUserRelationshipPersistence;

public abstract class CoursesTestAbstractFactory {

    private static CoursesTestAbstractFactory abstractFactory;

    public abstract ICourseUserRelationshipPersistence makeCoursesUserRelationshipDBMock();

    public abstract ICoursePersistence makeCourseDBMock();

    public static CoursesTestAbstractFactory getInstance(CoursesTestState state) {
        abstractFactory = state.concreteMethod();
        return abstractFactory;
    }

}

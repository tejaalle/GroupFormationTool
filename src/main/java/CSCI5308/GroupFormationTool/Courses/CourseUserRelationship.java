package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.SystemConfig;

import java.util.List;

public class CourseUserRelationship implements ICourseUserRelationship {

    public boolean userHasRoleInCourse(IUser user, Role role, ICourse course) throws Exception {
        if (null == user || !user.isValidUser()) {
            return false;
        }
        if (null == role) {
            return false;
        }
        if (null == course) {
            return false;
        }

        CoursesAbstractFactory abstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourseUserRelationshipPersistence userCourseRelationshipDB = abstractFactory.makeCourseUserRelationshipDB();
        List<Role> roles = userCourseRelationshipDB.loadUserRolesForCourse(course, user);

        if (null != roles && roles.contains(role)) {
            return true;
        }

        return false;
    }

    public List<Role> loadAllRolesForUserInCourse(IUser user, ICourse course) throws Exception {
        CoursesAbstractFactory abstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourseUserRelationshipPersistence userCourseRelationshipDB = abstractFactory.makeCourseUserRelationshipDB();
        List<Role> roles = userCourseRelationshipDB.loadUserRolesForCourse(course, user);
        return roles;
    }

    public void enrollUserInCourse(IUser user, ICourse course, Role role) throws Exception {
        CoursesAbstractFactory abstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourseUserRelationshipPersistence userCourseRelationshipDB = abstractFactory.makeCourseUserRelationshipDB();
        userCourseRelationshipDB.enrollUser(course, user, role);
    }

}

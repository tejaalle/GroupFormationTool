package CSCI5308.GroupFormationTool.CoursesTest;

import CSCI5308.GroupFormationTool.AccessControl.AccessControlAbstractFactory;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.Courses.ICourse;
import CSCI5308.GroupFormationTool.Courses.ICourseUserRelationshipPersistence;
import CSCI5308.GroupFormationTool.Courses.Role;
import CSCI5308.GroupFormationTool.SystemConfig;

import java.util.ArrayList;
import java.util.List;

class CourseUserRelationshipDBMock implements ICourseUserRelationshipPersistence {

    public List<IUser> findAllUsersWithoutCourseRole(Role role, long courseID) {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        List<IUser> userList = new ArrayList<>();
        IUser user = accessControlAbstractFactory.makeUser();
        user.setId(0);
        userList.add(user);
        user = accessControlAbstractFactory.makeUser();
        user.setId(1);
        userList.add(user);
        return userList;
    }

    public List<IUser> findAllUsersWithCourseRole(Role role, long courseID) {
        List<IUser> userList = new ArrayList<>();
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        IUser user = accessControlAbstractFactory.makeUser();
        user.setId(0);
        userList.add(user);
        user = accessControlAbstractFactory.makeUser();
        user.setId(1);
        userList.add(user);
        return userList;
    }

    public void enrollUser(ICourse course, IUser user, Role role) {
        user.setId(0);
        course.setId(0);
        course.setTitle("Software Engineering");
    }

    public List<Role> loadUserRolesForCourse(ICourse course, IUser user) {
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.STUDENT);
        userRoles.add(Role.TA);
        return userRoles;
    }

}

package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.IUser;

import java.util.List;

public interface ICourseUserRelationship {

    public boolean userHasRoleInCourse(IUser user, Role role, ICourse course) throws Exception;

    public List<Role> loadAllRolesForUserInCourse(IUser user, ICourse course) throws Exception;

    public void enrollUserInCourse(IUser user, ICourse course, Role role) throws Exception;

}

package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.IUser;

import java.util.List;

public interface ICourse {

    public void setDefaults();

    public void setId(long id);

    public long getId();

    public void setTitle(String title);

    public String getTitle();

    public void delete(ICoursePersistence courseDB) throws Exception;

    public void createCourse(ICoursePersistence courseDB) throws Exception;

    public void enrollUserInCourse(Role role, IUser user) throws Exception;

    public boolean isCurrentUserEnrolledAsRoleInCourse(Role role) throws Exception;

    public boolean isCurrentUserEnrolledAsRoleInCourse(String role) throws Exception;

    public List<Role> getAllRolesForCurrentUserInCourse() throws Exception;

}

package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.CurrentUser;
import CSCI5308.GroupFormationTool.AccessControl.IUser;

import java.util.List;

public class Course implements ICourse {

    private long id;
    private String title;
    private ICourseUserRelationship userRoleDecider;

    public Course() {
        setDefaults();
    }

    public Course(long id, ICoursePersistence courseDB) throws Exception {
        setDefaults();
        courseDB.loadCourseByID(id, this);
    }

    public void setDefaults() {
        id = -1;
        title = "";
        userRoleDecider = new CourseUserRelationship();
    }

    // I don't want to name this method this way, but unfortunately Spring and Thymeleaf are
    // full of magical underneath the hood connection mechanisms that force me to name it this way.
    public void setId(long id) {
        this.id = id;
    }

    // I don't want to name this method this way, but unfortunately Spring and Thymeleaf are
    // full of magical underneath the hood connection mechanisms that force me to name it this way.
    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void delete(ICoursePersistence courseDB) throws Exception {
        courseDB.deleteCourse(id);
    }

    public void createCourse(ICoursePersistence courseDB) throws Exception {
        courseDB.createCourse(this);
    }

    public void enrollUserInCourse(Role role, IUser user) throws Exception {
        userRoleDecider.enrollUserInCourse(user, this, role);
    }

    public boolean isCurrentUserEnrolledAsRoleInCourse(Role role) throws Exception {
        return userRoleDecider.userHasRoleInCourse(CurrentUser.instance().getCurrentAuthenticatedUser(), role, this);
    }

    public boolean isCurrentUserEnrolledAsRoleInCourse(String role) throws Exception {
        Role r = Role.valueOf(role.toUpperCase());
        return isCurrentUserEnrolledAsRoleInCourse(r);
    }

    public List<Role> getAllRolesForCurrentUserInCourse() throws Exception {
        return userRoleDecider.loadAllRolesForUserInCourse(CurrentUser.instance().getCurrentAuthenticatedUser(), this);
    }

}

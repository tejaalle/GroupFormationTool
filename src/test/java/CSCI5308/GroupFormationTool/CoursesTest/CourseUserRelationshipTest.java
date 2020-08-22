package CSCI5308.GroupFormationTool.CoursesTest;

import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.AccessControlTest.CurrentUserMock;
import CSCI5308.GroupFormationTool.Courses.CoursesAbstractFactory;
import CSCI5308.GroupFormationTool.Courses.ICourse;
import CSCI5308.GroupFormationTool.Courses.ICourseUserRelationshipPersistence;
import CSCI5308.GroupFormationTool.Courses.Role;
import CSCI5308.GroupFormationTool.SystemConfig;
import CSCI5308.GroupFormationTool.SystemConfigTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SuppressWarnings("deprecation")
class CourseUserRelationshipTest {

    private ICourseUserRelationshipPersistence courseUserRelationshipDB;
    private CoursesTestAbstractFactory abstractFactory = SystemConfigTest.instance().getCoursesTestConcreteFactoryState();

    public CourseUserRelationshipTest() {
        courseUserRelationshipDB = abstractFactory.makeCoursesUserRelationshipDBMock();
    }

    @Test
    public void userHasRoleInCourse() throws Exception {
        CoursesAbstractFactory coursesAbstractfactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractfactory.makeCourse();
        course.setId(0);
        CurrentUserMock currentUser = new CurrentUserMock();
        IUser user = currentUser.getCurrentAuthenticatedUser();
        List<Role> roles = courseUserRelationshipDB.loadUserRolesForCourse(course, user);
        assertThat(roles).isNotNull();
        assertThat(roles).isNotEmpty();
        Assert.isTrue(roles.size() > 0);
    }

    @Test
    public void loadAllRoluesForUserInCourse() throws Exception {
        CoursesAbstractFactory coursesAbstractfactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractfactory.makeCourse();
        course.setId(0);
        CurrentUserMock currentUser = new CurrentUserMock();
        IUser user = currentUser.getCurrentAuthenticatedUser();
        List<Role> roles = courseUserRelationshipDB.loadUserRolesForCourse(course, user);
        Assert.isTrue(roles.size() > 0);
    }

    @Test
    public void enrollUserInCourse() throws Exception {
        CoursesAbstractFactory coursesAbstractfactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractfactory.makeCourse();
        CurrentUserMock currentUser = new CurrentUserMock();
        IUser user = currentUser.getCurrentAuthenticatedUser();
        courseUserRelationshipDB.enrollUser(course, user, Role.STUDENT);
    }

}

package CSCI5308.GroupFormationTool.CoursesTest;

import CSCI5308.GroupFormationTool.Courses.Course;
import CSCI5308.GroupFormationTool.Courses.CoursesAbstractFactory;
import CSCI5308.GroupFormationTool.Courses.ICourse;
import CSCI5308.GroupFormationTool.Courses.ICoursePersistence;
import CSCI5308.GroupFormationTool.SystemConfig;
import CSCI5308.GroupFormationTool.SystemConfigTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@SuppressWarnings("deprecation")
class CourseTest {

    @Test
    public void ConstructorTests() throws Exception {
        CoursesTestAbstractFactory abstractFactory = SystemConfigTest.instance().getCoursesTestConcreteFactoryState();
        CoursesAbstractFactory coursesAbstractfactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractfactory.makeCourse();
        Assert.isTrue(course.getId() == -1);
        Assert.isTrue(course.getTitle().isEmpty());

        ICoursePersistence courseDB = abstractFactory.makeCourseDBMock();
        Course courses = new Course(0, courseDB);
        Assert.isTrue(courses.getId() == 0);
        Assert.isTrue(courses.getTitle().equals("Software Engineering"));
    }

    @Test
    public void setIdTest() {
        CoursesAbstractFactory coursesAbstractfactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractfactory.makeCourse();
        course.setId(7);
        Assert.isTrue(course.getId() == 7);
    }

    @Test
    public void getIdTest() {
        CoursesAbstractFactory coursesAbstractfactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractfactory.makeCourse();
        course.setId(7);
        Assert.isTrue(course.getId() == 7);
    }

    @Test
    public void setTitleTest() {
        CoursesAbstractFactory coursesAbstractfactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractfactory.makeCourse();
        course.setTitle("Advanced Topics in Software Development");
        Assert.isTrue(course.getTitle().equals("Advanced Topics in Software Development"));
    }

    @Test
    public void getTitleTest() {
        CoursesAbstractFactory coursesAbstractfactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractfactory.makeCourse();
        course.setTitle("Advanced Topics in Software Development");
        Assert.isTrue(course.getTitle().equals("Advanced Topics in Software Development"));
    }

    @Test
    public void deleteCourseTest() throws Exception {
        CoursesTestAbstractFactory abstractFactory = SystemConfigTest.instance().getCoursesTestConcreteFactoryState();
        ICoursePersistence courseDB = abstractFactory.makeCourseDBMock();
        courseDB.deleteCourse(0);
    }

    @Test
    public void createCourseTest() throws Exception {
        CoursesTestAbstractFactory abstractFactory = SystemConfigTest.instance().getCoursesTestConcreteFactoryState();
        ICoursePersistence courseDB = abstractFactory.makeCourseDBMock();
        CoursesAbstractFactory coursesAbstractfactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractfactory.makeCourse();
        course.setId(0);
        course.setTitle("Software Engineering");
        courseDB.createCourse(course);
        Assert.isTrue(course.getId() == 0);
        Assert.isTrue(course.getTitle().equals("Software Engineering"));
    }

}

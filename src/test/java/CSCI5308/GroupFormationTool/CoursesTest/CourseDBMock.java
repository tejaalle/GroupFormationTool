package CSCI5308.GroupFormationTool.CoursesTest;

import CSCI5308.GroupFormationTool.Courses.CoursesAbstractFactory;
import CSCI5308.GroupFormationTool.Courses.ICourse;
import CSCI5308.GroupFormationTool.Courses.ICoursePersistence;
import CSCI5308.GroupFormationTool.SystemConfig;

import java.util.ArrayList;
import java.util.List;

public class CourseDBMock implements ICoursePersistence {
    public List<ICourse> loadAllCourses() {
        List<ICourse> courseList = new ArrayList<>();
        CoursesAbstractFactory coursesAbstractfactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractfactory.makeCourse();
        course.setId(0);
        course.setTitle("Software Engineering");
        courseList.add(course);
        course = coursesAbstractfactory.makeCourse();
        course.setId(1);
        course.setTitle("Advanced Topics in Software Development");
        courseList.add(course);
        return courseList;
    }

    public void loadCourseByID(long id, ICourse course) {
        course.setId(id);
        course.setTitle("Software Engineering");
    }

    public void createCourse(ICourse course) {
        course.setId(0);
        course.setTitle("Software Engineering");
    }

    public void deleteCourse(long id) {
        CoursesAbstractFactory coursesAbstractfactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractfactory.makeCourse();
        course.setId(id);
        course.setTitle("Software Engineering");
        course.setDefaults();
    }

}

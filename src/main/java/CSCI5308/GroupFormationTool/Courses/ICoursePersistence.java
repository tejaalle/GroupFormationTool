package CSCI5308.GroupFormationTool.Courses;

import java.util.List;

public interface ICoursePersistence {

    public List<ICourse> loadAllCourses() throws Exception;

    public void loadCourseByID(long id, ICourse course) throws Exception;

    public void createCourse(ICourse course) throws Exception;

    public void deleteCourse(long id) throws Exception;

}

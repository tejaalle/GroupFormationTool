package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDB implements ICoursePersistence {

    Logger myLogger = LoggerUtil.getLoggerInstance(CourseDB.class);

    public List<ICourse> loadAllCourses() throws Exception {
        List<ICourse> courses = new ArrayList<>();
        CoursesAbstractFactory abstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spLoadAllCourses()");
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    long id = results.getLong(1);
                    String title = results.getString(2);
                    ICourse course = abstractFactory.makeCourse();
                    course.setId(id);
                    course.setTitle(title);
                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while loading all"
                    + " courses"), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }

        return courses;
    }

    public void loadCourseByID(long id, ICourse course) throws Exception {
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spFindCourseByID(?)");
            proc.setParameter(1, id);
            ResultSet results = proc.executeWithResults();
            if (null != results) {
                while (results.next()) {
                    String title = results.getString(2);
                    course.setId(id);
                    course.setTitle(title);
                }
                myLogger.info(String.format("Fetched course details for"
                        + " course with id %d", id));

            }
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while loading"
                    + " course with id %d", id), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
    }

    public void createCourse(ICourse course) throws Exception {
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spCreateCourse(?, ?)");
            proc.setParameter(1, course.getTitle());
            proc.registerOutputParameterLong(2);
            proc.execute();
            myLogger.info(String.format("Created new"
                    + " course with title %s", course.getTitle()));
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while creating"
                    + " course with title %s", course.getTitle()), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
    }

    public void deleteCourse(long id) throws Exception {
        CallStoredProcedure proc = null;

        try {
            proc = new CallStoredProcedure("spDeleteCourse(?)");
            proc.setParameter(1, id);
            proc.execute();
            myLogger.info(String.format("Deleted"
                    + " course with id %d", id));
        } catch (SQLException e) {
            myLogger.error(String.format("SQL Exception encountered while deleting"
                    + " course with id %d", id), e);
            throw e;
        } finally {
            if (null != proc) {
                proc.cleanup();
            }
        }
    }
}

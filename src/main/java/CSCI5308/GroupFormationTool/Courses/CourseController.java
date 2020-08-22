package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.CurrentUser;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorCodes;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CourseController implements ErrorCodes {

    private static final String ID = "id";

    @GetMapping("/course/course")
    public String course(Model model, @RequestParam(name = ID) long courseID) {
        CoursesAbstractFactory abstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICoursePersistence courseDB = abstractFactory.makeCourseDB();
        IUser user = null;

        try {
            user = CurrentUser.instance().getCurrentAuthenticatedUser();
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_008);
            return "course/course";
        }

        ICourse course = abstractFactory.makeCourse();
        try {
            courseDB.loadCourseByID(courseID, course);
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_012);
            return "course/course";
        }

        model.addAttribute("course", course);
        model.addAttribute("user", user);
        List<Role> userRoles = null;

        try {
            userRoles = course.getAllRolesForCurrentUserInCourse();
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_012);
            return "course/course";
        }

        if (null == userRoles) {
            model.addAttribute("instructor", false);
            model.addAttribute("ta", false);
            model.addAttribute("student", false);
            model.addAttribute("guest", true);
        } else {
            model.addAttribute("instructor", userRoles.contains(Role.INSTRUCTOR));
            model.addAttribute("ta", userRoles.contains(Role.TA));
            model.addAttribute("student", userRoles.contains(Role.STUDENT));
            model.addAttribute("guest", userRoles.isEmpty());
        }

        return "course/course";
    }

}

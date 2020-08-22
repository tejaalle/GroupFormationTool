package CSCI5308.GroupFormationTool.WelcomePage;

import CSCI5308.GroupFormationTool.Courses.CoursesAbstractFactory;
import CSCI5308.GroupFormationTool.Courses.ICourse;
import CSCI5308.GroupFormationTool.Courses.ICoursePersistence;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorCodes;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController implements ErrorCodes {

    @GetMapping("/")
    public String greeting(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            CoursesAbstractFactory coursesAbstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
            ICoursePersistence courseDB = coursesAbstractFactory.makeCourseDB();
            List<ICourse> allCourses = null;
            try {
                allCourses = courseDB.loadAllCourses();
            } catch (Exception e) {
                model.addAttribute("errorMessage", GFT_ERR_COURSE_007);
                return "index";
            }
            model.addAttribute("courses", allCourses);
        }
        return "index";
    }

}
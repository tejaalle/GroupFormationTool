package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.ErrorHandling.ErrorCodes;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class InstructorAdminController implements ErrorCodes {

    private static final String ID = "id";
    private static final String FILE = "file";
    private static final String SUCCESSFUL = "successful";
    private static final String FAILURES = "failures";
    private static final String DISPLAY_RESULTS = "displayresults";

    @GetMapping("/course/instructoradmin")
    public String instructorAdmin(Model model, @RequestParam(name = ID) long courseID) {
        CoursesAbstractFactory abstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICoursePersistence courseDB = abstractFactory.makeCourseDB();
        ICourse course = abstractFactory.makeCourse();

        try {
            courseDB.loadCourseByID(courseID, course);
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_012);
            return "course/instructoradmin";
        }

        model.addAttribute("course", course);
        model.addAttribute("displayresults", false);
        try {
            if (course.isCurrentUserEnrolledAsRoleInCourse(Role.INSTRUCTOR) ||
                    course.isCurrentUserEnrolledAsRoleInCourse(Role.TA)) {
                return "course/instructoradmin";
            } else {
                return "logout";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_008);
            return "course/instructoradmin";
        }
    }

    @GetMapping("/course/instructoradminresults")
    public String instructorAdmin(
            Model model,
            @RequestParam(name = ID) long courseID,
            @RequestParam(name = SUCCESSFUL, required = false) List<String> successful,
            @RequestParam(name = FAILURES, required = false) List<String> failures,
            @RequestParam(name = DISPLAY_RESULTS) boolean displayResults) {
        CoursesAbstractFactory abstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICoursePersistence courseDB = abstractFactory.makeCourseDB();
        ICourse course = abstractFactory.makeCourse();

        try {
            courseDB.loadCourseByID(courseID, course);
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_012);
            return "course/instructoradmin";
        }

        model.addAttribute("course", course);
        model.addAttribute("displayresults", false);
        model.addAttribute(SUCCESSFUL, successful);
        model.addAttribute(FAILURES, failures);
        model.addAttribute(DISPLAY_RESULTS, displayResults);
        try {
            if (course.isCurrentUserEnrolledAsRoleInCourse(Role.INSTRUCTOR) ||
                    course.isCurrentUserEnrolledAsRoleInCourse(Role.TA)) {
                return "course/instructoradmin";
            } else {
                return "logout";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_008);
            return "course/instructoradmin";
        }
    }


    @GetMapping("/course/enrollta")
    public String enrollTA(Model model, @RequestParam(name = ID) long courseID) {
        CoursesAbstractFactory abstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICoursePersistence courseDB = abstractFactory.makeCourseDB();
        ICourse course = abstractFactory.makeCourse();

        try {
            courseDB.loadCourseByID(courseID, course);
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_012);
            return "course/enrollta";
        }

        model.addAttribute("course", course);
        try {
            if (course.isCurrentUserEnrolledAsRoleInCourse(Role.INSTRUCTOR) ||
                    course.isCurrentUserEnrolledAsRoleInCourse(Role.TA)) {
                return "course/enrollta";
            } else {
                return "logout";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_008);
            return "course/enrollta";
        }
    }

    @RequestMapping(value = "/course/uploadcsv", consumes = {"multipart/form-data"})
    public ModelAndView upload(@RequestParam(name = FILE) MultipartFile file, @RequestParam(name = ID) long courseID) {
        CoursesAbstractFactory abstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICoursePersistence courseDB = abstractFactory.makeCourseDB();
        ICourse course = abstractFactory.makeCourse();

        try {
            courseDB.loadCourseByID(courseID, course);
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("course/instructoradmin");
            modelAndView.addObject("errorMessage", GFT_ERR_COURSE_008);
            return modelAndView;
        }

        IStudentCSVParser parser = new StudentCSVParser(file);
        StudentCSVImport importer = new StudentCSVImport(parser, course);
        ModelAndView modelAndView = new ModelAndView("redirect:/course/instructoradminresults?id=" + Long.toString(courseID));
        modelAndView.addObject("successful", importer.getSuccessResults());
        modelAndView.addObject("failures", importer.getFailureResults());
        modelAndView.addObject("displayresults", true);

        return modelAndView;
    }

}

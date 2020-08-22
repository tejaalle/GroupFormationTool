package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.AccessControlAbstractFactory;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorCodes;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.List;

@Controller
public class CourseAdminController implements ErrorCodes {

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String INSTRUCTOR = "instructor";

    @GetMapping("/admin/course")
    public String course(Model model) {
        CoursesAbstractFactory abstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICoursePersistence courseDB = abstractFactory.makeCourseDB();
        List<ICourse> allCourses = null;

        try {
            allCourses = courseDB.loadAllCourses();
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_007);
            return "admin/course";
        }

        model.addAttribute("courses", allCourses);
        return "admin/course";
    }

    @GetMapping("/admin/assigninstructor")
    public String assignInstructor(Model model, @RequestParam(name = ID) long courseID) {
        CoursesAbstractFactory abstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICoursePersistence courseDB = abstractFactory.makeCourseDB();
        ICourse course = abstractFactory.makeCourse();

        try {
            courseDB.loadCourseByID(courseID, course);
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_008);
            return "admin/assigninstructor";
        }

        model.addAttribute("course", course);
        ICourseUserRelationshipPersistence courseUserRelationshipDB = abstractFactory.makeCourseUserRelationshipDB();
        List<IUser> allUsersNotCurrentlyInstructors = null;

        try {
            allUsersNotCurrentlyInstructors = courseUserRelationshipDB.findAllUsersWithoutCourseRole(Role.INSTRUCTOR, courseID);
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_008);
            return "admin/assigninstructor";
        }

        model.addAttribute("users", allUsersNotCurrentlyInstructors);
        return "admin/assigninstructor";
    }

    @GetMapping("/admin/deletecourse")
    public ModelAndView deleteCourse(@RequestParam(name = ID) long courseID) {
        CoursesAbstractFactory abstractfactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICoursePersistence courseDB = abstractfactory.makeCourseDB();
        ICourse course = abstractfactory.makeCourse();
        course.setId(courseID);

        try {
            course.delete(courseDB);
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("admin/deletecourse");
            modelAndView.addObject("errorMessage", GFT_ERR_COURSE_009);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/course");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/createcourse", method = RequestMethod.POST)
    public ModelAndView createCourse(@RequestParam(name = TITLE) String title) {
        CoursesAbstractFactory abstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICoursePersistence courseDB = abstractFactory.makeCourseDB();
        ICourse course = abstractFactory.makeCourse();
        course.setTitle(title);

        try {
            course.createCourse(courseDB);
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("admin/createcourse");
            modelAndView.addObject("errorMessage", GFT_ERR_COURSE_010);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/course");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/assigninstructor", method = RequestMethod.POST)
    public ModelAndView assignInstructorToCourse(@RequestParam(name = INSTRUCTOR) List<Integer> instructor,
                                                 @RequestParam(name = ID) long courseID) {
        CoursesAbstractFactory abstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = abstractFactory.makeCourse();
        course.setId(courseID);
        Iterator<Integer> iterator = instructor.iterator();
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        ICourseUserRelationshipPersistence courseUserRelationshipDB = abstractFactory.makeCourseUserRelationshipDB();

        while (iterator.hasNext()) {
            IUser user = accessControlAbstractFactory.makeUser();
            user.setId(iterator.next().longValue());
            try {
                courseUserRelationshipDB.enrollUser(course, user, Role.INSTRUCTOR);
            } catch (Exception e) {
                ModelAndView modelAndView = new ModelAndView("admin/assigninstructor");
                modelAndView.addObject("errorMessage", GFT_ERR_COURSE_011);
                return modelAndView;
            }
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/course");
        return modelAndView;
    }

}

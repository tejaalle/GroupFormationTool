package CSCI5308.GroupFormationTool.CourseSurvey;

import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.Courses.CoursesAbstractFactory;
import CSCI5308.GroupFormationTool.Courses.ICourseUserRelationshipPersistence;
import CSCI5308.GroupFormationTool.Courses.Role;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorCodes;
import CSCI5308.GroupFormationTool.QuestionManager.*;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionSurveyController implements ErrorCodes {

    private static final String COURSEID = "courseid";
    private static final String QUESTIONID = "questionid";

    @GetMapping("/questionsurvey/coursequestions/createsurvey")
    public String createSurvey(Model model, @RequestParam(name = COURSEID) long courseID) {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        CoursesAbstractFactory coursesAbstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        CourseSurveyAbstractFactory abstractFactory = SystemConfig.instance().getCourseSurveyConcreteFactoryState();
        ICourseUserRelationshipPersistence courseDB = coursesAbstractFactory.makeCourseUserRelationshipDB();
        IQuestionPersistence questionDB = questionManagerAbstractFactory.makeQuestionDB();
        IOptionsPersistence optionDB = questionManagerAbstractFactory.makeOptionDB();
        ISurveyPersistence surveyDB = abstractFactory.makeSurveyDB();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();

        List<IUser> users = new ArrayList<>();
        try {
            users = courseDB.findAllUsersWithCourseRole(Role.INSTRUCTOR, courseID);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_SURVEY_018);
            return "questionmanager/qmerror";
        }

        List<Integer> surveyquestions = new ArrayList<Integer>();
        try {
            surveyquestions = surveyDB.loadQuestionFromSurvey(courseID);
        } catch (Exception e1) {
            model.addAttribute("message", GFT_ERR_SURVEY_018);
            return "questionmanager/qmerror";
        }

        boolean ispublished = false;
        try {
            ispublished = surveyDB.isSurveyPublished(courseID);
        } catch (Exception e1) {
            model.addAttribute("message", GFT_ERR_SURVEY_018);
            return "questionmanager/qmerror";
        }

        List<Question> questions = new ArrayList<>();
        try {
            questions = question.allQuestionsData(users.get(0).getID(), questionDB, optionDB);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_SURVEY_018);
            return "questionmanager/qmerror";
        }

        List<Question> unQuestions = question.unAddedQuestionsData(questions, surveyquestions);
        if (ispublished) {
            model.addAttribute("courseID", courseID);
            return "questionmanager/coursesurveypublished";
        } else {
            model.addAttribute("courseID", courseID);
            model.addAttribute("questions", unQuestions);
            return "questionmanager/coursequestions";
        }
    }

    @PostMapping("/questionsurvey/coursequestions/addquestion")
    public String addQuestion(RedirectAttributes redirectAttributes, Model model,
                              @RequestParam(name = QUESTIONID) long questionID, @RequestParam(name = COURSEID) long courseID) {
        CourseSurveyAbstractFactory abstractFactory = SystemConfig.instance().getCourseSurveyConcreteFactoryState();
        ISurveyPersistence surveyDB = abstractFactory.makeSurveyDB();

        try {
            surveyDB.addQuestionsToSurvey(courseID, questionID);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_SURVEY_019);
            return "questionmanager/qmerror";
        }

        redirectAttributes.addAttribute("courseid", courseID);
        return "redirect:/questionsurvey/coursequestions/createsurvey";
    }

    @GetMapping("/questionsurvey/surveyquestions")
    public String displaySurvey(Model model, @RequestParam(name = COURSEID) long courseID) {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        CourseSurveyAbstractFactory abstractFactory = SystemConfig.instance().getCourseSurveyConcreteFactoryState();
        IQuestionPersistence questionDB = questionManagerAbstractFactory.makeQuestionDB();
        IOptionsPersistence optionDB = questionManagerAbstractFactory.makeOptionDB();
        ISurveyPersistence surveyDB = abstractFactory.makeSurveyDB();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        List<Question> questions = new ArrayList<>();

        try {
            List<Integer> surveyQuestions = surveyDB.loadQuestionFromSurvey(courseID);
            questions = question.questionsData(surveyQuestions, questionDB, optionDB);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_SURVEY_020);
            return "questionmanager/qmerror";
        }

        model.addAttribute("courseID", courseID);
        model.addAttribute("questions", questions);
        return "questionmanager/surveyquestions";
    }

    @PostMapping("/questionsurvey/surveyquestions/removequestion")
    public String removeQuestion(RedirectAttributes redirectAttributes, Model model,
                                 @RequestParam(name = QUESTIONID) long questionID, @RequestParam(name = COURSEID) long courseID) {
        CourseSurveyAbstractFactory abstractFactory = SystemConfig.instance().getCourseSurveyConcreteFactoryState();
        ISurveyPersistence surveyDB = abstractFactory.makeSurveyDB();

        try {
            surveyDB.removeQuestionsFromSurvey(questionID);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_SURVEY_021);
            return "questionmanager/qmerror";
        }

        redirectAttributes.addAttribute("courseid", courseID);
        return "redirect:/questionsurvey/surveyquestions";
    }

    @PostMapping("/questionsurvey/surveyquestions/savetosurvey")
    public String addToSurvey(Model model, @RequestParam(name = COURSEID) long courseID) {
        final int state = 0;
        CourseSurveyAbstractFactory abstractFactory = SystemConfig.instance().getCourseSurveyConcreteFactoryState();
        ISurveyPersistence surveyDB = abstractFactory.makeSurveyDB();

        try {
            surveyDB.surveyPublishState(courseID, state);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_SURVEY_022);
            return "questionmanager/qmerror";
        }

        model.addAttribute("courseID", courseID);
        model.addAttribute("notpublished", "Question are saved to survey");
        return "questionmanager/surveystate";
    }

    @PostMapping("/questionsurvey/surveyquestions/publishsurvey")
    public String publishSurvey(Model model, @RequestParam(name = COURSEID) long courseID) {
        final int state = 1;
        CourseSurveyAbstractFactory abstractFactory = SystemConfig.instance().getCourseSurveyConcreteFactoryState();
        ISurveyPersistence surveyDB = abstractFactory.makeSurveyDB();

        try {
            surveyDB.surveyPublishState(courseID, state);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_SURVEY_023);
            return "questionmanager/qmerror";
        }

        model.addAttribute("courseID", courseID);
        model.addAttribute("published", "sucessfully published");
        return "questionmanager/surveystate";
    }

}

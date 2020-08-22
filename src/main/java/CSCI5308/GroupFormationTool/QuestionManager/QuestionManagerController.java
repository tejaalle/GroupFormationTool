package CSCI5308.GroupFormationTool.QuestionManager;

import CSCI5308.GroupFormationTool.ErrorHandling.ErrorCodes;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionManagerController implements ErrorCodes {

    private static final String ID = "id";
    private static final String COURSEID = "courseid";
    Logger myLogger = LoggerUtil.getLoggerInstance(this.getClass());

    @GetMapping("/questionmanager/instructorquestions")
    public String questionsPage(Model model, @RequestParam(name = ID) long userID, @RequestParam(name = COURSEID) long courseID) {
        QuestionManagerAbstractFactory abstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestionPersistence questionDB = abstractFactory.makeQuestionDB();
        IOptionsPersistence optionDB = abstractFactory.makeOptionDB();
        IQuestion question = abstractFactory.makeQuestion();

        List<Question> questions = new ArrayList<>();
        try {
            questions = question.allQuestionsData(userID, questionDB, optionDB);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_COURSE_015);
            return "questionmanager/qmerror";
        }

        model.addAttribute("UserID", userID);
        model.addAttribute("questions", questions);
        model.addAttribute("courseID", courseID);

        return "questionmanager/instructorquestions";
    }

    @PostMapping("/questionmanager/instructorquestions/deletequestion")
    public String deleteQuestion(RedirectAttributes redirectAttributes, @RequestParam(name = "questionid") long questionID, @RequestParam(name = COURSEID) long courseID, @RequestParam(name = ID) long userID) {
        QuestionManagerAbstractFactory abstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestionPersistence questionDB = abstractFactory.makeQuestionDB();

        try {
            questionDB.deleteQuestionByID(questionID, userID);
            redirectAttributes.addAttribute("id", userID);
            redirectAttributes.addAttribute("courseid", courseID);
            return "redirect:/questionmanager/instructorquestions";
        } catch (Exception e) {
            redirectAttributes.addAttribute("id", userID);
            redirectAttributes.addAttribute("courseid", courseID);
            redirectAttributes.addAttribute("errorMessage", GFT_ERR_QUESTION_016);
            return "redirect:/questionmanager/instructorquestions";
        }
    }

    @PostMapping("/questionmanager/instructorquestions/sortquestionbytitle")
    public String sortQuestionByTitle(Model model, @RequestParam(name = COURSEID) long courseID, @RequestParam(name = ID) long userID) {
        QuestionManagerAbstractFactory abstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestionPersistence questionDB = abstractFactory.makeQuestionDB();
        IOptionsPersistence optionDB = abstractFactory.makeOptionDB();
        IQuestion question = abstractFactory.makeQuestion();

        try {
            List<Question> questions = question.sortByTitle(userID, questionDB, optionDB);
            model.addAttribute("UserID", userID);
            model.addAttribute("questions", questions);
            model.addAttribute("courseID", courseID);
            return "questionmanager/instructorquestions";
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_QUESTION_018);
            return "questionmanager/qmerror";
        }
    }

    @PostMapping("/questionmanager/instructorquestions/sortquestionbydate")
    public String sortQuestionByDate(Model model, @RequestParam(name = COURSEID) long courseID, @RequestParam(name = ID) long userID) {
        QuestionManagerAbstractFactory abstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestionPersistence questionDB = abstractFactory.makeQuestionDB();
        IOptionsPersistence optionDB = abstractFactory.makeOptionDB();
        IQuestion question = abstractFactory.makeQuestion();
        List<Question> questions;

        try {
            questions = question.sortByDate(userID, questionDB, optionDB);
            model.addAttribute("UserID", userID);
            model.addAttribute("questions", questions);
            model.addAttribute("courseID", courseID);
            return "questionmanager/instructorquestions";
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_QUESTION_018);
            return "questionmanager/qmerror";
        }
    }

}

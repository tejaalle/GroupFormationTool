package CSCI5308.GroupFormationTool.QuestionManager;

import CSCI5308.GroupFormationTool.AccessControl.CurrentUser;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorCodes;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GenerateQuestionController implements ErrorCodes {

    public static final String MCO = "mcq-one";
    public static final String MCM = "mcq-multiple";
    public static final int intialOptionCount = 2;

    @RequestMapping("/generatequestion")
    public String questionTypesDeclare(Model model) {
        QuestionManagerAbstractFactory abstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestionType questionType = abstractFactory.makeQuestionType();

        IQuestionTypesPersistence questionTypesDB = abstractFactory.makeQuestionTypesDB();
        List<String> questionsList = new ArrayList<String>();

        try {
            questionsList = questionType.getQuestionTypes(questionTypesDB);
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_013);
            return "questionmanager/type";
        }

        model.addAttribute("questions", questionsList);
        model.addAttribute("question", abstractFactory.makeQuestion());
        return "questionmanager/type";
    }

    @PostMapping("/questiontype")
    public String questionType(Model model, Question question) {
        QuestionManagerAbstractFactory abstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IUser user = null;

        try {
            user = CurrentUser.instance().getCurrentAuthenticatedUser();
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_COURSE_008);
            return "questionmanager/qmerror";
        }

        if (question.getType().equals(MCO) || question.getType().equals(MCM)) {
            question.addOption(new Options(), intialOptionCount);
            model.addAttribute("question", question);
            return "questionmanager/mc";
        }

        IQuestionType questionType = abstractFactory.makeQuestionType();
        IQuestionTypesPersistence questionTypesDB = abstractFactory.makeQuestionTypesDB();
        int type = 0;

        try {
            type = questionType.getQuestionID(question.getType(), questionTypesDB);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_COURSE_013);
            return "questionmanager/qmerror";
        }

        IDateProvider dateProvider = abstractFactory.makeDateProvider();
        Date date = dateProvider.generateDate();
        IQuestionPersistence questionDB = abstractFactory.makeQuestionDB();

        try {
            questionDB.insertQuestionMC(question.getTitle(), question.getText(), type, date, user.getId(), "", "");
            model.addAttribute("question", question);
            return "questionmanager/saveform";
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_COURSE_013);
            return "questionmanager/qmerror";
        }
    }

    @RequestMapping("/addmore")
    public ModelAndView addMore(ModelAndView modelAndView, @RequestParam String title, @RequestParam String text, @RequestParam String type, @RequestParam int options) {
        QuestionManagerAbstractFactory abstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = abstractFactory.makeQuestion();
        question.setTitle(title);
        question.setText(text);
        question.setType(type);
        question.addOption(new Options(), options + 1);

        modelAndView.addObject("question", question);
        modelAndView.setViewName("questionmanager/mc");
        return modelAndView;
    }

    @PostMapping("/questionsave")
    public String questionSave(Model model, Question question) {
        QuestionManagerAbstractFactory abstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IUser user = null;

        try {
            user = CurrentUser.instance().getCurrentAuthenticatedUser();
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_COURSE_008);
            return "questionmanager/qmerror";
        }

        IOptions options = abstractFactory.makeOptions();
        String optionValuesParam = options.getOptionsString(question.getOptionsList());
        String optionKeysParam = options.getStoredAsString(question.getOptionsList());
        IQuestionType questionType = abstractFactory.makeQuestionType();
        IQuestionTypesPersistence questionTypesDB = abstractFactory.makeQuestionTypesDB();
        int type = 0;

        try {
            type = questionType.getQuestionID(question.getType(), questionTypesDB);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_COURSE_008);
            return "questionmanager/qmerror";
        }

        IDateProvider dateProvider = abstractFactory.makeDateProvider();
        Date date = dateProvider.generateDate();
        IQuestionPersistence questionDB = abstractFactory.makeQuestionDB();

        try {
            questionDB.insertQuestionMC(question.getTitle(), question.getText(), type, date, user.getId(), optionValuesParam, optionKeysParam);
            return "questionmanager/saveform";
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_COURSE_014);
            return "questionmanager/qmerror";
        }
    }

}

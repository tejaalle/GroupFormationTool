package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.CourseSurvey.CourseSurveyAbstractFactory;
import CSCI5308.GroupFormationTool.CourseSurvey.ISurveyPersistence;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorCodes;
import CSCI5308.GroupFormationTool.QuestionManager.*;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class GroupsCriteriaController implements ErrorCodes {

    private static final String COURSEID = "courseid";

    @GetMapping("/groupcriteria/createcriteria")
    public String GroupCriteria(Model model, @RequestParam(name = COURSEID) long courseID) {
        List<Criteria> criteriaType = new ArrayList<Criteria>();
        criteriaType = Arrays.asList(Criteria.values());
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        CourseSurveyAbstractFactory courseSurveyAbstractFactory = SystemConfig.instance().getCourseSurveyConcreteFactoryState();
        IQuestionPersistence questionDB = questionManagerAbstractFactory.makeQuestionDB();
        IOptionsPersistence optionDB = questionManagerAbstractFactory.makeOptionDB();
        ISurveyPersistence surveyDB = courseSurveyAbstractFactory.makeSurveyDB();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        List<Question> questions = null;
        boolean ispublished = false;

        try {
            ispublished = surveyDB.isSurveyPublished(courseID);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_SURVEY_025);
            return "questionmanager/qmerror";
        }

        IQuestions questionList = questionManagerAbstractFactory.makeQuestions();

        try {
            List<Integer> surveyQuestions = surveyDB.loadQuestionFromSurvey(courseID);
            questions = question.questionsData(surveyQuestions, questionDB, optionDB);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_SURVEY_025);
            return "questionmanager/qmerror";
        }

        questionList.setQuestionsList(questions);
        if (ispublished) {
            model.addAttribute("courseID", courseID);
            model.addAttribute("questions", questionList);
            model.addAttribute("criteriaTypes", criteriaType);
            return "questionmanager/createcriteria";
        } else {
            model.addAttribute("notpublishedcriteria", "Set criteria after survey is published");
            model.addAttribute("courseID", courseID);
            return "questionmanager/surveystate";
        }
    }

    @RequestMapping("/groupcriteria/submitcriteria")
    public String submitCriteria(Model model, Questions questions, @RequestParam(name = COURSEID) long courseID) {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        List<Question> questionList = questions.getQuestionsList();
        IQuestionPersistence questionDB = questionManagerAbstractFactory.makeQuestionDB();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();

        try {
            question.questionCriteria(questionList, courseID, questionDB);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_SURVEY_025);
            return "questionmanager/qmerror";
        }

        model.addAttribute("courseID", courseID);
        model.addAttribute("criteriasubmitted", true);
        return "questionmanager/criteriasubmitted";
    }

}

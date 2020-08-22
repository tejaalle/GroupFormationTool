package CSCI5308.GroupFormationTool.CourseSurvey;

import CSCI5308.GroupFormationTool.AccessControl.CurrentUser;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorCodes;
import CSCI5308.GroupFormationTool.QuestionManager.*;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentResponseController implements ErrorCodes {

    @RequestMapping("providesurvey/questionsdisplay")
    public String questionDisplay(Model model, @RequestParam(name = "courseid") long courseId) {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        CourseSurveyAbstractFactory abstractFactory = SystemConfig.instance().getCourseSurveyConcreteFactoryState();
        ISurveyPersistence surveyDB = abstractFactory.makeSurveyDB();
        boolean surveyPublished = false;

        try {
            surveyPublished = surveyDB.isSurveyPublished(courseId);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_SURVEY_020);
            return "questionmanager/qmerror";
        }

        if (surveyPublished) {
            IUser user = null;

            try {
                user = CurrentUser.instance().getCurrentAuthenticatedUser();
            } catch (Exception e) {
                model.addAttribute("message", GFT_ERR_SURVEY_020);
                return "questionmanager/qmerror";
            }

            IQuestion question = questionManagerAbstractFactory.makeQuestion();
            IQuestions questionList = questionManagerAbstractFactory.makeQuestions();
            ISurvey survey = abstractFactory.makeSurvey();
            IStudentResponsePersistence studentResponseDB = abstractFactory.makeStudentResponseDB();
            boolean attemptedSurvey = false;

            try {
                attemptedSurvey = survey.isAttemptedSurvey(studentResponseDB, user.getId(), courseId);
            } catch (Exception e) {
                model.addAttribute("message", GFT_ERR_SURVEY_020);
                return "questionmanager/qmerror";
            }

            if (attemptedSurvey) {
                model.addAttribute("courseID", courseId);
                return "Students/attemptedsurvey";
            } else {
                List<Integer> questionId = new ArrayList<Integer>();
                try {
                    questionId = surveyDB.loadQuestionFromSurvey(courseId);
                } catch (Exception e) {
                    model.addAttribute("message", GFT_ERR_SURVEY_020);
                    return "questionmanager/qmerror";
                }

                IQuestionPersistence questionDB = questionManagerAbstractFactory.makeQuestionDB();
                IOptionsPersistence optionDB = questionManagerAbstractFactory.makeOptionDB();
                List<Question> questions = new ArrayList<>();
                try {
                    questions = question.questionsData(questionId, questionDB, optionDB);
                } catch (Exception e) {
                    model.addAttribute("message", GFT_ERR_SURVEY_020);
                    return "questionmanager/qmerror";
                }

                questionList.setQuestionsList(questions);
                model.addAttribute("questionList", questionList);
                model.addAttribute("courseID", courseId);
                return "Students/studentsurvey";
            }
        } else {
            model.addAttribute("courseID", courseId);
            return "Students/nosurvey";
        }
    }

    @RequestMapping("providesurvey/savesurvey")
    public String saveSurvey(Model model, Questions questionList, @RequestParam(name = "courseid") long courseId) {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        CourseSurveyAbstractFactory abstractFactory = SystemConfig.instance().getCourseSurveyConcreteFactoryState();
        IQuestions questions = questionManagerAbstractFactory.makeQuestions();
        IUser user = null;

        try {
            user = CurrentUser.instance().getCurrentAuthenticatedUser();
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_SURVEY_024);
            return "questionmanager/qmerror";
        }

        IStudentResponsePersistence studentResponseDB = abstractFactory.makeStudentResponseDB();
        boolean inserted = false;
        try {
            inserted = questions.insertAllQuestions(studentResponseDB, user.getId(), courseId, questionList);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_SURVEY_024);
            return "questionmanager/qmerror";
        }

        if (inserted) {
            model.addAttribute("inserted", inserted);
        } else {
            model.addAttribute("notinserted", true);
        }

        model.addAttribute("courseID", courseId);
        return "Students/insertedresponse";
    }

}

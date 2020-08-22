package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.AccessControl.AccessControlAbstractFactory;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;
import CSCI5308.GroupFormationTool.CourseSurvey.CourseSurveyAbstractFactory;
import CSCI5308.GroupFormationTool.CourseSurvey.IStudentResponsePersistence;
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
public class GroupFormationController implements ErrorCodes {

    private static final String COURSEID = "courseid";
    private static final String GROUPCOUNT = "groupCount";

    @RequestMapping("formgroups/checkcriteria")
    public String checkCriteria(Model model, @RequestParam(name = COURSEID) long courseID) {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        CourseSurveyAbstractFactory courseSurveyAbstractFactory = SystemConfig.instance().getCourseSurveyConcreteFactoryState();
        IQuestionPersistence questionDB = questionManagerAbstractFactory.makeQuestionDB();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        boolean criteriaCreated = false;

        try {
            criteriaCreated = question.isQuestionCriteriaCreated(questionDB, courseID);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_GRPFORMATION_026);
            return "questionmanager/qmerror";
        }

        if (criteriaCreated) {
            List<Long> studentIdList = new ArrayList<>();
            IStudentResponsePersistence studentResponseDB = courseSurveyAbstractFactory.makeStudentResponseDB();
            try {
                studentIdList = studentResponseDB.getAnsweredStudentId(courseID);
            } catch (Exception e1) {
                model.addAttribute("message", GFT_ERR_GRPFORMATION_026);
                return "questionmanager/qmerror";
            }
            AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
            IUser user = accessControlAbstractFactory.makeUser();
            IUserPersistence userDB = accessControlAbstractFactory.makeUserDB();
            List<IUser> students = null;
            try {
                students = user.getStudentsDetails(studentIdList, userDB, accessControlAbstractFactory);
            } catch (Exception e) {
                model.addAttribute("message", GFT_ERR_GRPFORMATION_026);
                return "questionmanager/qmerror";
            }
            List<IQuestion> questions = new ArrayList<>();
            model.addAttribute("questions", questions);
            model.addAttribute("students", students);
            model.addAttribute("courseID", courseID);
            return "groupformation/groupcount";
        } else {
            model.addAttribute("courseID", courseID);
            model.addAttribute("criterianotsubmitted", true);
            return "questionmanager/criteriasubmitted";
        }
    }

    @RequestMapping("formgroups/showquestions")
    public String showQuestions(Model model, @RequestParam long courseID, @RequestParam long studentId) throws Exception {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        CourseSurveyAbstractFactory courseSurveyAbstractFactory = SystemConfig.instance().getCourseSurveyConcreteFactoryState();
        IStudentResponsePersistence studentResponseDB = courseSurveyAbstractFactory.makeStudentResponseDB();
        List<Long> studentIdList = new ArrayList<>();
        studentIdList = studentResponseDB.getAnsweredStudentId(courseID);
        IUserPersistence userDB = accessControlAbstractFactory.makeUserDB();
        IUser user = accessControlAbstractFactory.makeUser();
        List<IUser> students = user.getStudentsDetails(studentIdList, userDB, accessControlAbstractFactory);
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        IQuestionPersistence questionsDB = questionManagerAbstractFactory.makeQuestionDB();
        List<IQuestion> questions = question.getStudentAnswers(studentId, studentResponseDB, courseID, questionsDB, questionManagerAbstractFactory);
        model.addAttribute("questions", questions);
        model.addAttribute("students", students);
        model.addAttribute("courseID", courseID);
        return "groupformation/groupcount";

    }

    @RequestMapping("formgroups/generategroups")
    public String generateGroups(Model model, @RequestParam(name = COURSEID) long courseID, @RequestParam(name = GROUPCOUNT) int groupCount) {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        CourseSurveyAbstractFactory courseSurveyAbstractFactory = SystemConfig.instance().getCourseSurveyConcreteFactoryState();
        GroupFormationAbstractFactory abstractFactory = SystemConfig.instance().getGroupFormationConcreteFactoryState();

        IStudentResponsePersistence studentResponseDB = courseSurveyAbstractFactory.makeStudentResponseDB();
        IQuestionPersistence questionDB = questionManagerAbstractFactory.makeQuestionDB();
        List<Long> studentIdList = new ArrayList<>();
        List<List<IQuestion>> questionsList = null;
        List<IQuestion> criteriaQuestions = null;

        try {
            studentIdList = studentResponseDB.getAnsweredStudentId(courseID);
        } catch (Exception e1) {
            model.addAttribute("message", GFT_ERR_GRPFORMATION_026);
            return "questionmanager/qmerror";
        }

        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        IQuestionType questionType = questionManagerAbstractFactory.makeQuestionType();
        IQuestionTypesPersistence questionTypesDB = questionManagerAbstractFactory.makeQuestionTypesDB();
        try {
            questionsList = question.getStudentsAnswers(studentIdList, studentResponseDB, courseID);
            criteriaQuestions = questionDB.fetchQuestionCriteria(courseID);
            criteriaQuestions = questionType.setQuestionType(criteriaQuestions, questionTypesDB);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_GRPFORMATION_026);
            return "questionmanager/qmerror";
        }

        IAlgorithm algorithm = abstractFactory.makeGroupFormationAlgorithm();
        List<List<Long>> studentIdGroups = algorithm.createGroups(studentIdList, questionsList, criteriaQuestions, groupCount);
        IUserPersistence userDB = accessControlAbstractFactory.makeUserDB();
        IUser user = accessControlAbstractFactory.makeUser();
        List<List<IUser>> students = null;

        try {
            students = user.getDetailsOfStudents(studentIdGroups, userDB);
        } catch (Exception e) {
            model.addAttribute("message", GFT_ERR_GRPFORMATION_026);
            return "questionmanager/qmerror";
        }

        model.addAttribute("studentsList", students);
        model.addAttribute("courseID", courseID);
        return "groupformation/displaygroups";
    }

}

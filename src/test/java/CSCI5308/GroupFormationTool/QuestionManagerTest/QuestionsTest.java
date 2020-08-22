package CSCI5308.GroupFormationTool.QuestionManagerTest;

import CSCI5308.GroupFormationTool.CourseSurvey.IStudentResponsePersistence;
import CSCI5308.GroupFormationTool.CourseSurveyTest.CourseSurveyTestAbstractFactory;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestions;
import CSCI5308.GroupFormationTool.QuestionManager.Question;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionManagerAbstractFactory;
import CSCI5308.GroupFormationTool.SystemConfig;
import CSCI5308.GroupFormationTool.SystemConfigTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@SuppressWarnings("deprecation")
public class QuestionsTest {

    @Test
    public void setQuestionListTest() {
        List<Question> questionsList = new ArrayList<>();
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestions questions = questionManagerAbstractFactory.makeQuestions();
        Question question = new Question();
        question.setID(1);
        questionsList.add(question);
        questions.setQuestionsList(questionsList);
        Assert.isTrue(questions.getQuestionsList().get(0).getID() == 1);
    }

    @Test
    public void getQuestionsListTest() {
        List<Question> questionsList = new ArrayList<>();
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestions questions = questionManagerAbstractFactory.makeQuestions();
        Question question = new Question();
        question.setText("question1");
        questionsList.add(question);
        questions.setQuestionsList(questionsList);
        Assert.isTrue(questions.getQuestionsList().get(0).getText() == "question1");
    }

    @Test
    public void addQuestionTest() {
        List<Question> questionsList = new ArrayList<>();
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestions questions = questionManagerAbstractFactory.makeQuestions();
        Question question = new Question();
        question.setText("question1");
        questionsList.add(question);
        question = new Question();
        question.setAnswer("answer1");
        questionsList.add(question);
        questions.setQuestionsList(questionsList);
        Assert.isTrue(questions.getQuestionsList().get(0).getText() == "question1");
        Assert.isTrue(questions.getQuestionsList().get(1).getAnswer() == "answer1");
    }

    @Test
    public void insertAllQuestionsTest() throws Exception {
        CourseSurveyTestAbstractFactory courseSurveyTestAbstractFactory = SystemConfigTest.instance().getCourseSurveyTestConcreteFactoryState();
        IStudentResponsePersistence studentResponseDBMock = courseSurveyTestAbstractFactory.makeStudentResponseDBMock();
        boolean response = studentResponseDBMock.insertStudentResponse(1, 1, 1, "answers");
        Assert.isTrue(response);
    }
}

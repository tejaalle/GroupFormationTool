package CSCI5308.GroupFormationTool.QuestionManagerTest;

import CSCI5308.GroupFormationTool.CourseSurvey.IStudentResponsePersistence;
import CSCI5308.GroupFormationTool.CourseSurvey.ISurveyPersistence;
import CSCI5308.GroupFormationTool.CourseSurveyTest.CourseSurveyTestAbstractFactory;
import CSCI5308.GroupFormationTool.Courses.CoursesAbstractFactory;
import CSCI5308.GroupFormationTool.Courses.ICourse;
import CSCI5308.GroupFormationTool.QuestionManager.*;
import CSCI5308.GroupFormationTool.SystemConfig;
import CSCI5308.GroupFormationTool.SystemConfigTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@SuppressWarnings("deprecation")
public class QuestionTest {

    @Test
    public void setIDTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setID(1);
        Assert.isTrue(question.getID() == 1);
    }

    @Test
    public void getIDTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setID(1);
        Assert.isTrue(question.getID() == 1);
    }

    @Test
    public void setTitleTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setTitle("Title");
        Assert.isTrue(Objects.equals(question.getTitle(), "Title"));
    }

    @Test
    public void getTitleTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setTitle("Title");
        Assert.isTrue(Objects.equals(question.getTitle(), "Title"));
    }

    @Test
    public void setTextTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setText("This is a Question");
        Assert.isTrue(question.getText().equals("This is a Question"));
    }

    @Test
    public void getTextTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setText("This is a Question");
        Assert.isTrue(Objects.equals(question.getText(), "This is a Question"));
    }

    @Test
    public void setDateTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        question.setDate(date);
        Assert.isTrue(question.getDate() == date);
    }

    @Test
    public void getDateTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        question.setDate(date);
        Assert.isTrue(question.getDate() == date);
    }

    @Test
    public void setTypeTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setType("freetext");
        Assert.isTrue(Objects.equals(question.getType(), "freetext"));
    }

    @Test
    public void getTypeTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setType("freetext");
        Assert.isTrue(Objects.equals(question.getType(), "freetext"));
    }

    @Test
    public void addOptionTest() {
        List<IOptions> options = new ArrayList<>();
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IOptions option = questionManagerAbstractFactory.makeOptions();
        option.setOptions("option1");
        option.setStoredAs("1");
        options.add(option);
        Assert.isTrue(Objects.equals(options.get(0).getOptions(), "option1"));
    }

    @Test
    public void allQuestionsDataTest() throws Exception {
        QuestionManagerTestAbstractFactory abstractFactory = SystemConfigTest.instance().getQuestionManagerTestConcreteFactoryState();
        IQuestionPersistence questionDB = abstractFactory.makeQuestionDBMock();
        IQuestion question = questionDB.loadQuestions(1);
        Assert.isTrue(Objects.equals(question.getTitle(), "Title"));
    }

    @Test
    public void questionsDataTest() throws SQLException {
        QuestionManagerTestAbstractFactory abstractFactory = SystemConfigTest.instance().getQuestionManagerTestConcreteFactoryState();
        IOptionsPersistence option = abstractFactory.makeOptionsDBMock();
        List<Options> options = option.loadOptionsByQuestionID(1);
        Assert.isTrue(options.get(0).getOptions().equals("option1"));
    }

    @Test
    public void sortByTitleTest() throws Exception {
        QuestionManagerTestAbstractFactory abstractFactory = SystemConfigTest.instance().getQuestionManagerTestConcreteFactoryState();
        IQuestionPersistence questionDB = abstractFactory.makeQuestionDBMock();
        IOptionsPersistence option = abstractFactory.makeOptionsDBMock();
        IQuestion question = questionDB.loadQuestions(1);
        List<Options> options = option.loadOptionsByQuestionID(1);
        Assert.isTrue(Objects.equals(question.getText(), "What"));
        Assert.isTrue(options.get(0).getStoredAs().equals("1"));
    }

    @Test
    public void sortByDateTest() throws Exception {
        QuestionManagerTestAbstractFactory abstractFactory = SystemConfigTest.instance().getQuestionManagerTestConcreteFactoryState();
        IQuestionPersistence questionDB = abstractFactory.makeQuestionDBMock();
        IOptionsPersistence option = abstractFactory.makeOptionsDBMock();
        IQuestion question = questionDB.loadQuestions(1);
        List<Options> options = option.loadOptionsByQuestionID(1);
        Assert.isTrue(Objects.equals(question.getType(), "freetext"));
        Assert.isTrue(options.get(0).getStoredAs().equals("1"));
    }

    @Test
    public void unAddedQuestionsDataTest() throws Exception {
        QuestionManagerTestAbstractFactory abstractFactory = SystemConfigTest.instance().getQuestionManagerTestConcreteFactoryState();
        CourseSurveyTestAbstractFactory courseTestAbstractFactory = SystemConfigTest.instance().getCourseSurveyTestConcreteFactoryState();
        IQuestionPersistence question = abstractFactory.makeQuestionDBMock();
        ISurveyPersistence survey = courseTestAbstractFactory.makeSurveyDBMock();
        IQuestion q = question.loadQuestions(1);
        List<Integer> questionID = survey.loadQuestionFromSurvey(1);
        Assert.isTrue(Objects.equals(q.getTitle(), "Title"));
        Assert.isTrue(questionID.get(0).equals(1));
    }

    @Test
    public void setAnswerTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setAnswer("java");
        Assert.isTrue(Objects.equals(question.getAnswer(), "java"));

    }

    @Test
    public void getAnswerTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setAnswer("C");
        Assert.isTrue(Objects.equals(question.getAnswer(), "C"));
    }

    @Test
    public void setCriteriaTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setCriteria("similar");
        Assert.isTrue(Objects.equals(question.getCriteria(), "similar"));

    }

    @Test
    public void getCriteriaTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setCriteria("dissimilar");
        Assert.isTrue(Objects.equals(question.getCriteria(), "dissimilar"));

    }

    @Test
    public void questionCriteriaTest() throws Exception {
        QuestionManagerTestAbstractFactory abstractFactory = SystemConfigTest.instance().getQuestionManagerTestConcreteFactoryState();
        IQuestionPersistence questionDBMock = abstractFactory.makeQuestionDBMock();
        CoursesAbstractFactory coursesAbstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractFactory.makeCourse();
        course.setId(1);
        questionDBMock.deleteQuestionCriteria(1);
        Assert.isTrue(course.getId() == 1);
    }

    @Test
    public void isCriteriaCreatedTest() throws Exception {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        QuestionManagerTestAbstractFactory abstractFactory = SystemConfigTest.instance().getQuestionManagerTestConcreteFactoryState();
        IQuestionPersistence questionDBMock = abstractFactory.makeQuestionDBMock();
        question.setCriteria("similar");
        List<IQuestion> questionList = questionDBMock.fetchQuestionCriteria(1);
        Assert.isTrue(questionList.get(0).getID() == 1);

    }

    @Test
    public void getStudentsAnswersTest() throws Exception {
        CourseSurveyTestAbstractFactory courseTestAbstractFactory = SystemConfigTest.instance().getCourseSurveyTestConcreteFactoryState();
        IStudentResponsePersistence studentResponseDBMock = courseTestAbstractFactory.makeStudentResponseDBMock();
        List<IQuestion> questionsList = new ArrayList<>();
        questionsList = studentResponseDBMock.loadStudentResponse(1, 1);
        Assert.isTrue(questionsList.get(0).getID() == 1);
    }

}

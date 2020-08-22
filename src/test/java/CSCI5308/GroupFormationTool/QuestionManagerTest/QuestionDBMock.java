package CSCI5308.GroupFormationTool.QuestionManagerTest;

import CSCI5308.GroupFormationTool.Courses.CoursesAbstractFactory;
import CSCI5308.GroupFormationTool.Courses.ICourse;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionPersistence;
import CSCI5308.GroupFormationTool.QuestionManager.Question;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionManagerAbstractFactory;
import CSCI5308.GroupFormationTool.SystemConfig;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class QuestionDBMock implements IQuestionPersistence {

    @Override
    public List<Integer> loadQuestionByInstructorID(long userID) {
        List<Integer> questionIDs = new ArrayList<>();
        questionIDs.add(1);
        return questionIDs;
    }

    @Override
    public Question loadQuestions(long id) {
        Question question = new Question();
        question.setID(id);
        question.setTitle("Title");
        question.setText("What");
        question.setType("freetext");
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        question.setDate(date);
        return question;
    }

    @Override
    public void deleteQuestionByID(long questionID, long instructorID) {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setID(questionID);
        question.setText("Question 2");
        question.setType("freetext");
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        question.setDate(date);
    }

    @Override
    public void insertQuestionMC(String title, String text, int type, Date date, long userID, String optionValues, String optionKeys) {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setID(1);
        question.setTitle(title);
        question.setType("freetext");
        question.setDate(date);
    }

    @Override
    public boolean insertCriteriaForQuestion(long courseID, long questionID, String criteria) throws Exception {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setID(questionID);
        return true;
    }

    @Override
    public boolean deleteQuestionCriteria(long courseID) throws Exception {
        CoursesAbstractFactory coursesAbstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractFactory.makeCourse();
        course.setId(courseID);
        return true;
    }

    @Override
    public List<IQuestion> fetchQuestionCriteria(long courseID) throws Exception {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        List<IQuestion> questionList = new ArrayList<>();
        question.setID(1);
        questionList.add(question);
        return questionList;
    }
}

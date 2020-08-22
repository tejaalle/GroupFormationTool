package CSCI5308.GroupFormationTool.CourseSurveyTest;

import CSCI5308.GroupFormationTool.CourseSurvey.IStudentResponsePersistence;
import CSCI5308.GroupFormationTool.Courses.CoursesAbstractFactory;
import CSCI5308.GroupFormationTool.Courses.ICourse;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionManagerAbstractFactory;
import CSCI5308.GroupFormationTool.SystemConfig;

import java.util.ArrayList;
import java.util.List;

public class StudentResponseDBMock implements IStudentResponsePersistence {

    @Override
    public List<IQuestion> loadStudentResponse(long courseId, long studentId) throws Exception {
        QuestionManagerAbstractFactory questionManagerabstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        List<IQuestion> questionList = new ArrayList<>();
        IQuestion question = questionManagerabstractFactory.makeQuestion();
        question.setID(1);
        questionList.add(question);
        return questionList;
    }

    @Override
    public boolean insertStudentResponse(long courseId, long studentId, long questionId, String answers) throws Exception {
        CoursesAbstractFactory coursesAbstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractFactory.makeCourse();
        course.setId(courseId);
        return true;
    }

    @Override
    public List<Long> getAnsweredStudentId(long courseId) throws Exception {
        CoursesAbstractFactory coursesAbstractFactory = SystemConfig.instance().getCoursesConcreteFactoryState();
        ICourse course = coursesAbstractFactory.makeCourse();
        course.setId(courseId);
        List<Long> studentID = new ArrayList<>();
        return studentID;
    }

}

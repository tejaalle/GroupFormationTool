package CSCI5308.GroupFormationTool.CourseSurveyTest;

import CSCI5308.GroupFormationTool.CourseSurvey.IStudentResponsePersistence;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;
import CSCI5308.GroupFormationTool.SystemConfigTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
@SuppressWarnings("deprecation")
public class SurveyTest {

    @Test
    public void isAttemptedSurveyTest() throws Exception {
        CourseSurveyTestAbstractFactory abstractFactory = SystemConfigTest.instance().getCourseSurveyTestConcreteFactoryState();
        IStudentResponsePersistence studentResponseDBMock = abstractFactory.makeStudentResponseDBMock();
        List<IQuestion> questions = studentResponseDBMock.loadStudentResponse(1, 1);
        Assert.isTrue(questions.get(0).getID() == 1);
    }

}

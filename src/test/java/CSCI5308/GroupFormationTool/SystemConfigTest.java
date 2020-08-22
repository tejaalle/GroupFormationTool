package CSCI5308.GroupFormationTool;

import CSCI5308.GroupFormationTool.AccessControlTest.AccessControlTestAbstractFactory;
import CSCI5308.GroupFormationTool.AccessControlTest.AccessControlTestConcreteFactoryState;
import CSCI5308.GroupFormationTool.AccessControlTest.AccessControlTestState;
import CSCI5308.GroupFormationTool.CourseSurveyTest.CourseSurveyTestAbstractFactory;
import CSCI5308.GroupFormationTool.CourseSurveyTest.CourseSurveyTestConcreteFactoryState;
import CSCI5308.GroupFormationTool.CourseSurveyTest.CourseSurveyTestState;
import CSCI5308.GroupFormationTool.CoursesTest.CoursesTestAbstractFactory;
import CSCI5308.GroupFormationTool.CoursesTest.CoursesTestConcreteFactoryState;
import CSCI5308.GroupFormationTool.CoursesTest.CoursesTestState;
import CSCI5308.GroupFormationTool.QuestionManagerTest.QuestionManagerTestAbstractFactory;
import CSCI5308.GroupFormationTool.QuestionManagerTest.QuestionManagerTestConcreteFactoryState;
import CSCI5308.GroupFormationTool.QuestionManagerTest.QuestionManagerTestState;
import CSCI5308.GroupFormationTool.SecurityTest.SecurityTestAbstractFactory;
import CSCI5308.GroupFormationTool.SecurityTest.SecurityTestConcreteFactoryState;
import CSCI5308.GroupFormationTool.SecurityTest.SecurityTestState;

public class SystemConfigTest {

    private static SystemConfigTest uniqueInstance = null;

    private AccessControlTestState accessControlTestConcreteFactoryState;
    private CoursesTestState coursesTestConcreteFactoryState;
    private QuestionManagerTestState questionManagerTestConcreteFactoryState;
    private SecurityTestState securityTestConcreteFactoryState;
    private CourseSurveyTestState courseSurveyTestConcreteFactoryState;

    private SystemConfigTest() {
        accessControlTestConcreteFactoryState = new AccessControlTestConcreteFactoryState();
        coursesTestConcreteFactoryState = new CoursesTestConcreteFactoryState();
        questionManagerTestConcreteFactoryState = new QuestionManagerTestConcreteFactoryState();
        securityTestConcreteFactoryState = new SecurityTestConcreteFactoryState();
        courseSurveyTestConcreteFactoryState = new CourseSurveyTestConcreteFactoryState();
    }

    public static SystemConfigTest instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new SystemConfigTest();
        }
        return uniqueInstance;
    }

    public AccessControlTestAbstractFactory getAccessControlTestConcreteFactoryState() {
        return AccessControlTestAbstractFactory.getInstance(accessControlTestConcreteFactoryState);
    }

    public CoursesTestAbstractFactory getCoursesTestConcreteFactoryState() {
        return CoursesTestAbstractFactory.getInstance(coursesTestConcreteFactoryState);
    }

    public QuestionManagerTestAbstractFactory getQuestionManagerTestConcreteFactoryState() {
        return QuestionManagerTestAbstractFactory.getInstance(questionManagerTestConcreteFactoryState);
    }

    public SecurityTestAbstractFactory getSecurityTestConcreteFactoryState() {
        return SecurityTestAbstractFactory.getInstance(securityTestConcreteFactoryState);
    }

    public CourseSurveyTestAbstractFactory getCourseSurveyTestConcreteFactoryState() {
        return CourseSurveyTestAbstractFactory.getInstance(courseSurveyTestConcreteFactoryState);
    }

}

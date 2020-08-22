package CSCI5308.GroupFormationTool;

import CSCI5308.GroupFormationTool.AccessControl.AccessControlAbstractFactory;
import CSCI5308.GroupFormationTool.AccessControl.AccessControlConcreteFactoryState;
import CSCI5308.GroupFormationTool.AccessControl.AccessControlState;
import CSCI5308.GroupFormationTool.CourseSurvey.CourseSurveyAbstractFactory;
import CSCI5308.GroupFormationTool.CourseSurvey.CourseSurveyConcreteFactoryState;
import CSCI5308.GroupFormationTool.CourseSurvey.CourseSurveyState;
import CSCI5308.GroupFormationTool.Courses.CoursesAbstractFactory;
import CSCI5308.GroupFormationTool.Courses.CoursesConcreteFactoryState;
import CSCI5308.GroupFormationTool.Courses.CoursesState;
import CSCI5308.GroupFormationTool.Database.DatabaseAbstractFactory;
import CSCI5308.GroupFormationTool.Database.DatabaseConcreteFactoryState;
import CSCI5308.GroupFormationTool.Database.DatabaseState;
import CSCI5308.GroupFormationTool.GroupFormation.GroupFormationAbstractFactory;
import CSCI5308.GroupFormationTool.GroupFormation.GroupFormationConcreteFactoryState;
import CSCI5308.GroupFormationTool.GroupFormation.GroupFormationState;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionManageState;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionManagerAbstractFactory;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionManagerConcreteFactoryState;
import CSCI5308.GroupFormationTool.Security.SecurityAbstractFactory;
import CSCI5308.GroupFormationTool.Security.SecurityConcreteFactoryState;
import CSCI5308.GroupFormationTool.Security.SecurityState;

/*
 * The single responsibility of this singleton is to store concrete classes
 * selected by the system for use in the rest of the system. This will allow
 * a form of dependency injection in places where we cannot use normal
 * dependency injection (for example classes that override or extend existing
 * library classes in the framework).
 */
public class SystemConfig {
    private static SystemConfig uniqueInstance = null;

    private QuestionManageState questionManagerConcreteFactoryState;
    private AccessControlState accessControlConcreteFactoryState;
    private CoursesState coursesConcreteFactoryState;
    private SecurityState securityConcreteFactoryState;
    private DatabaseState databaseConcreteFactoryState;
    private CourseSurveyState courseSurveyConcreteFactoryState;
    private GroupFormationState groupFormationConcreteFactoryState;

    // This private constructor ensures that no class other than System can allocate
    // the System object. The compiler would prevent it.
    private SystemConfig() {
        // The default instantiations are the choices that would be used in the
        // production application. These choices can all be overridden by test
        // setup logic when necessary.
        questionManagerConcreteFactoryState = new QuestionManagerConcreteFactoryState();
        accessControlConcreteFactoryState = new AccessControlConcreteFactoryState();
        coursesConcreteFactoryState = new CoursesConcreteFactoryState();
        securityConcreteFactoryState = new SecurityConcreteFactoryState();
        databaseConcreteFactoryState = new DatabaseConcreteFactoryState();
        courseSurveyConcreteFactoryState = new CourseSurveyConcreteFactoryState();
        groupFormationConcreteFactoryState = new GroupFormationConcreteFactoryState();
    }

    // This is the way the rest of the application gets access to the System object.
    public static SystemConfig instance() {
        // Using lazy initialization, this is the one and only place that the System
        // object will be instantiated.
        if (null == uniqueInstance) {
            uniqueInstance = new SystemConfig();
        }
        return uniqueInstance;
    }

    public QuestionManagerAbstractFactory getQuestionManagerConcreteFactoryState() {
        return QuestionManagerAbstractFactory.getInstance(questionManagerConcreteFactoryState);
    }

    public AccessControlAbstractFactory getAccessControlConcreteFactoryState() {
        return AccessControlAbstractFactory.getInstance(accessControlConcreteFactoryState);
    }

    public CoursesAbstractFactory getCoursesConcreteFactoryState() {
        return CoursesAbstractFactory.getInstance(coursesConcreteFactoryState);
    }

    public SecurityAbstractFactory getSecurityConcreteFactoryState() {
        return SecurityAbstractFactory.getInstance(securityConcreteFactoryState);
    }

    public DatabaseAbstractFactory getDatabaseConcreteFactoryState() {
        return DatabaseAbstractFactory.getInstance(databaseConcreteFactoryState);
    }

    public CourseSurveyAbstractFactory getCourseSurveyConcreteFactoryState() {
        return CourseSurveyAbstractFactory.getInstance(courseSurveyConcreteFactoryState);
    }

    public GroupFormationAbstractFactory getGroupFormationConcreteFactoryState() {
        return GroupFormationAbstractFactory.getInstance(groupFormationConcreteFactoryState);
    }

}

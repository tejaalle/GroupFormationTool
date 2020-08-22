package CSCI5308.GroupFormationTool.QuestionManagerTest;

import CSCI5308.GroupFormationTool.QuestionManager.IDateProvider;
import CSCI5308.GroupFormationTool.QuestionManager.IOptionsPersistence;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionPersistence;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionTypesPersistence;

public abstract class QuestionManagerTestAbstractFactory {

    private static QuestionManagerTestAbstractFactory abstractFactory;

    public abstract IQuestionPersistence makeQuestionDBMock();

    public abstract IOptionsPersistence makeOptionsDBMock();

    public abstract IDateProvider makeDataProviderMock();

    public abstract IQuestionTypesPersistence makeQuestionTypesDBMock();

    public static QuestionManagerTestAbstractFactory getInstance(QuestionManagerTestState state) {
        abstractFactory = state.concreteMethod();
        return abstractFactory;
    }

}

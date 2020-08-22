package CSCI5308.GroupFormationTool.QuestionManagerTest;

import CSCI5308.GroupFormationTool.QuestionManager.IDateProvider;
import CSCI5308.GroupFormationTool.QuestionManager.IOptionsPersistence;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionPersistence;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionTypesPersistence;

public class QuestionManagerTestConcreteFactory extends QuestionManagerTestAbstractFactory {

    @Override
    public IQuestionPersistence makeQuestionDBMock() {
        return new QuestionDBMock();
    }

    @Override
    public IOptionsPersistence makeOptionsDBMock() {
        return new OptionsDBMock();
    }

    @Override
    public IDateProvider makeDataProviderMock() {
        return new DateProviderMock();
    }

    @Override
    public IQuestionTypesPersistence makeQuestionTypesDBMock() {
        return new QuestionTypesDBMock();
    }

}

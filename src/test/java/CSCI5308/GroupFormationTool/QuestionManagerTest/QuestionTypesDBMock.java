package CSCI5308.GroupFormationTool.QuestionManagerTest;

import CSCI5308.GroupFormationTool.QuestionManager.IQuestionType;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionTypesPersistence;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionManagerAbstractFactory;
import CSCI5308.GroupFormationTool.SystemConfig;

import java.util.ArrayList;
import java.util.List;

public class QuestionTypesDBMock implements IQuestionTypesPersistence {

    @Override
    public List<IQuestionType> getTypes() {
        List<IQuestionType> types = new ArrayList<>();
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestionType type = questionManagerAbstractFactory.makeQuestionType();
        type.setTypeID(1);
        type.setTypeName("freetext");
        types.add(type);
        return types;
    }

    @Override
    public int getTypeOfQuestion(long questionID) {
        int types = 1;
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestionType type = questionManagerAbstractFactory.makeQuestionType();
        type.setTypeID(1);
        return types;
    }

}

package CSCI5308.GroupFormationTool.QuestionManagerTest;

import CSCI5308.GroupFormationTool.QuestionManager.IQuestionType;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionTypesPersistence;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionManagerAbstractFactory;
import CSCI5308.GroupFormationTool.SystemConfig;
import CSCI5308.GroupFormationTool.SystemConfigTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@SpringBootTest
@SuppressWarnings("deprecation")
public class QuestionTypeTest {

    @Test
    public void setTypeIDTest() {

        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestionType type = questionManagerAbstractFactory.makeQuestionType();
        type.setTypeID(1);
        Assert.isTrue(type.getTypeID() == 1);
    }

    @Test
    public void getTypeIDTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestionType type = questionManagerAbstractFactory.makeQuestionType();
        type.setTypeID(1);
        Assert.isTrue(type.getTypeID() == 1);
    }

    @Test
    public void setTypeNameTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestionType type = questionManagerAbstractFactory.makeQuestionType();
        type.setTypeName("freetext");
        Assert.isTrue(Objects.equals(type.getTypeName(), "freetext"));
    }

    @Test
    public void getTypeNameTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IQuestionType type = questionManagerAbstractFactory.makeQuestionType();
        type.setTypeName("freetext");
        Assert.isTrue(Objects.equals(type.getTypeName(), "freetext"));
    }

    @Test
    public void getQuestionTypesTest() throws Exception {
        QuestionManagerTestAbstractFactory abstractFactory = SystemConfigTest.instance().getQuestionManagerTestConcreteFactoryState();
        IQuestionTypesPersistence type = abstractFactory.makeQuestionTypesDBMock();
        List<IQuestionType> types = type.getTypes();
        Assert.isTrue(Objects.equals(types.get(0).getTypeName(), "freetext"));
    }

    @Test
    public void getQuestionIDTest() throws Exception {
        QuestionManagerTestAbstractFactory abstractFactory = SystemConfigTest.instance().getQuestionManagerTestConcreteFactoryState();
        IQuestionTypesPersistence type = abstractFactory.makeQuestionTypesDBMock();
        List<IQuestionType> types = type.getTypes();
        Assert.isTrue(types.get(0).getTypeID() == 1);
    }

    @Test
    public void setQuestionTypeTest() throws Exception {
        QuestionManagerTestAbstractFactory abstractFactory = SystemConfigTest.instance().getQuestionManagerTestConcreteFactoryState();
        IQuestionTypesPersistence type = abstractFactory.makeQuestionTypesDBMock();
        int types = type.getTypeOfQuestion(1);
        Assert.isTrue(Objects.equals(types, 1));

    }
}

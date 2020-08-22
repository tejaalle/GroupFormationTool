package CSCI5308.GroupFormationTool.QuestionManagerTest;

import CSCI5308.GroupFormationTool.QuestionManager.IOptions;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionManagerAbstractFactory;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Objects;

@SpringBootTest
@SuppressWarnings("deprecation")
public class OptionsTest {

    @Test
    public void setOptionsTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IOptions option = questionManagerAbstractFactory.makeOptions();
        option.setOptions("Java");
        Assert.isTrue(Objects.equals(option.getOptions(), "Java"));
    }

    @Test
    public void getOptionsTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IOptions option = questionManagerAbstractFactory.makeOptions();
        option.setOptions("Java");
        Assert.isTrue(Objects.equals(option.getOptions(), "Java"));
    }

    @Test
    public void setStoredAsTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IOptions option = questionManagerAbstractFactory.makeOptions();
        option.setStoredAs("1");
        Assert.isTrue(Objects.equals(option.getStoredAs(), "1"));
    }

    @Test
    public void getStoredAsTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IOptions option = questionManagerAbstractFactory.makeOptions();
        option.setStoredAs("1");
        Assert.isTrue(Objects.equals(option.getStoredAs(), "1"));
    }

    @Test
    public void getOptionStringTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IOptions option = questionManagerAbstractFactory.makeOptions();
        option.setOptions("Java");
        Assert.isTrue(Objects.equals(option.getOptions(), "Java"));
    }

    @Test
    public void getStoredAsStringTest() {
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        IOptions option = questionManagerAbstractFactory.makeOptions();
        option.setStoredAs("1");
        Assert.isTrue(Objects.equals(option.getStoredAs(), "1"));
    }

}

package CSCI5308.GroupFormationTool.QuestionManagerTest;

public class QuestionManagerTestConcreteFactoryState extends QuestionManagerTestState {

    @Override
    public QuestionManagerTestAbstractFactory concreteMethod() {
        return new QuestionManagerTestConcreteFactory();
    }

}

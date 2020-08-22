package CSCI5308.GroupFormationTool.QuestionManager;

public class QuestionManagerConcreteFactoryState extends QuestionManageState {

    @Override
    public QuestionManagerAbstractFactory concreteMethod() {
        return new QuestionManagerConcreteFactory();
    }

}

package CSCI5308.GroupFormationTool.QuestionManager;

public abstract class QuestionManagerAbstractFactory {

    private static QuestionManagerAbstractFactory abstractFactory;

    public abstract IQuestion makeQuestion();

    public abstract IQuestions makeQuestions();

    public abstract IQuestionType makeQuestionType();

    public abstract IOptions makeOptions();

    public abstract IDateProvider makeDateProvider();

    public abstract IOptionsPersistence makeOptionDB();

    public abstract IQuestionPersistence makeQuestionDB();

    public abstract IQuestionTypesPersistence makeQuestionTypesDB();

    public static QuestionManagerAbstractFactory getInstance(QuestionManageState state) {
        abstractFactory = state.concreteMethod();
        return abstractFactory;
    }

}

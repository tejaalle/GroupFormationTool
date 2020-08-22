package CSCI5308.GroupFormationTool.QuestionManager;

public class QuestionManagerConcreteFactory extends QuestionManagerAbstractFactory {

    @Override
    public IQuestion makeQuestion() {
        return new Question();
    }

    @Override
    public IOptions makeOptions() {
        return new Options();
    }

    @Override
    public IDateProvider makeDateProvider() {
        return new DateProvider();
    }

    @Override
    public IOptionsPersistence makeOptionDB() {
        return new OptionDB();
    }

    @Override
    public IQuestionPersistence makeQuestionDB() {
        return new QuestionDB();
    }

    @Override
    public IQuestionTypesPersistence makeQuestionTypesDB() {
        return new QuestionTypesDB();
    }

    @Override
    public IQuestionType makeQuestionType() {
        return new QuestionType();
    }

    @Override
    public IQuestions makeQuestions() {
        return new Questions();
    }

}

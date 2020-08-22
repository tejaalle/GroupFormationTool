package CSCI5308.GroupFormationTool.QuestionManager;

import java.util.List;

public interface IQuestionTypesPersistence {

    public List<IQuestionType> getTypes() throws Exception;

    public int getTypeOfQuestion(long questionID) throws Exception;

}

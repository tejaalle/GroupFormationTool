package CSCI5308.GroupFormationTool.QuestionManager;

import java.util.List;

public interface IQuestionType {

    public int getTypeID();

    public void setTypeID(int typeID);

    public String getTypeName();

    public void setTypeName(String typeName);

    public List<String> getQuestionTypes(IQuestionTypesPersistence questionTypesDB) throws Exception;

    public int getQuestionID(String typeName, IQuestionTypesPersistence questionTypesDB) throws Exception;

    public List<IQuestion> setQuestionType(List<IQuestion> questions, IQuestionTypesPersistence questionTypesDB) throws Exception;

}

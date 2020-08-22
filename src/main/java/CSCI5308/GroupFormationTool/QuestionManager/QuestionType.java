package CSCI5308.GroupFormationTool.QuestionManager;

import java.util.ArrayList;
import java.util.List;

public class QuestionType implements IQuestionType {

    private int typeID;
    private String typeName;

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<String> getQuestionTypes(IQuestionTypesPersistence questionTypesDB) throws Exception {
        List<String> questionTypesList = new ArrayList<>();
        List<IQuestionType> types = questionTypesDB.getTypes();

        for (IQuestionType type : types) {
            questionTypesList.add(type.getTypeName());
        }

        return questionTypesList;
    }

    public int getQuestionID(String typeName, IQuestionTypesPersistence questionTypesDB) throws Exception {
        int result = 0;
        List<IQuestionType> questionTypesList = questionTypesDB.getTypes();

        for (IQuestionType type : questionTypesList) {
            if (type.getTypeName().equals(typeName)) {
                result = type.getTypeID();
            }
        }

        return result;
    }

    public List<IQuestion> setQuestionType(List<IQuestion> questions, IQuestionTypesPersistence questionTypesDB) throws Exception {
        List<IQuestionType> questionTypesList = questionTypesDB.getTypes();
        for (IQuestion question : questions) {
            int type = questionTypesDB.getTypeOfQuestion(question.getID());
            for (IQuestionType qType : questionTypesList) {
                if (qType.getTypeID() == type) {
                    question.setType(qType.getTypeName());
                }
            }
        }
        return questions;
    }

}

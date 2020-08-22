package CSCI5308.GroupFormationTool.QuestionManager;


import CSCI5308.GroupFormationTool.CourseSurvey.IStudentResponsePersistence;

import java.util.ArrayList;
import java.util.List;

public class Questions implements IQuestions {

    private List<Question> questionsList = new ArrayList<>();

    public List<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Question> questionsList) {
        this.questionsList = questionsList;
    }

    public void addQuestion(int count) {
        for (int i = 0; i < count; i++) {
            this.questionsList.add(new Question());
        }
    }

    public boolean insertAllQuestions(IStudentResponsePersistence studentResponseDB, long studentID, long courseID, IQuestions questions) throws Exception {
        List<Question> questionsList = questions.getQuestionsList();
        boolean allInserted = false;

        for (IQuestion question : questionsList) {
            boolean inserted = studentResponseDB.insertStudentResponse(courseID, studentID, question.getID(), question.getAnswer());
            if (inserted) {
                allInserted = true;
            }
        }

        return allInserted;
    }

}

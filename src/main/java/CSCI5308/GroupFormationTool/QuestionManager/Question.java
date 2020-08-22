package CSCI5308.GroupFormationTool.QuestionManager;

import CSCI5308.GroupFormationTool.CourseSurvey.IStudentResponsePersistence;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Question implements IQuestion {

    private long id;
    private String title;
    private String text;
    private Date date;
    private String type;
    private List<Options> optionsList = new ArrayList<>();
    private String answer;
    private String criteria;

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Options> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(List<Options> optionsList) {
        this.optionsList = optionsList;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addOption(Options option, int count) {
        for (int i = 0; i < count; i++) {
            this.optionsList.add(option);
        }
    }

    public List<Question> allQuestionsData(long userID, IQuestionPersistence questionDB, IOptionsPersistence optionDB) throws Exception {
        List<Integer> questionIDs = questionDB.loadQuestionByInstructorID(userID);
        List<Question> questionList = questionsData(questionIDs, questionDB, optionDB);

        return questionList;
    }

    public List<Question> questionsData(List<Integer> questionIDs, IQuestionPersistence questionDB, IOptionsPersistence optionDB) throws Exception {
        List<Question> questionsList = new ArrayList<>();
        List<Options> optionsByQuestionID;

        for (Integer questionID : questionIDs) {
            Question question = questionDB.loadQuestions(questionID);
            optionsByQuestionID = optionDB.loadOptionsByQuestionID(questionID);
            question.setOptionsList(optionsByQuestionID);
            questionsList.add(question);
        }

        return questionsList;
    }


    public List<Question> sortByTitle(long userID, IQuestionPersistence questionDB, IOptionsPersistence optionDB) throws Exception {
        List<Question> questions = allQuestionsData(userID, questionDB, optionDB);
        questions.sort(new Comparator<IQuestion>() {
            public int compare(IQuestion q1, IQuestion q2) {
                if (q1.getTitle() == null || q2.getTitle() == null) {
                    return 0;
                }
                return q1.getTitle().compareToIgnoreCase(q2.getTitle());
            }
        });

        return questions;
    }

    public List<Question> sortByDate(long userID, IQuestionPersistence questionDB, IOptionsPersistence optionDB) throws Exception {
        List<Question> questions = allQuestionsData(userID, questionDB, optionDB);
        questions.sort(new Comparator<IQuestion>() {
            public int compare(IQuestion q1, IQuestion q2) {
                if (q1.getDate() == null || q2.getDate() == null) {
                    return 0;
                }
                return q1.getDate().compareTo(q2.getDate());
            }
        });

        return questions;
    }

    public List<Question> unAddedQuestionsData(List<Question> questions, List<Integer> questionIDs) {
        List<Question> questionlist = new ArrayList<>();

        for (Question question : questions) {
            boolean notfound = true;
            for (Integer questionID : questionIDs) {
                if (question.getID() == questionID) {
                    notfound = false;
                    break;
                }
            }
            if (notfound) {
                questionlist.add(question);
            }
        }

        return questionlist;
    }

    public void questionCriteria(List<Question> questionList, long courseID, IQuestionPersistence questionDB) throws Exception {
        questionDB.deleteQuestionCriteria(courseID);
        for (IQuestion question : questionList) {
            questionDB.insertCriteriaForQuestion(courseID, question.getID(), question.getCriteria());
        }
    }

    public boolean isQuestionCriteriaCreated(IQuestionPersistence questionDB, long courseID) throws Exception {
        List<IQuestion> questionList = questionDB.fetchQuestionCriteria(courseID);

        for (IQuestion question : questionList) {
            if (question.getCriteria() != null) {
                return true;
            }
        }

        return false;
    }

    public List<List<IQuestion>> getStudentsAnswers(List<Long> studentIDList, IStudentResponsePersistence studentResponseDB, long courseID) throws Exception {
        List<List<IQuestion>> questionsList = new ArrayList<>();

        for (long studentID : studentIDList) {
            questionsList.add(studentResponseDB.loadStudentResponse(courseID, studentID));
        }

        return questionsList;
    }

    public List<IQuestion> getStudentAnswers(long studentID, IStudentResponsePersistence studentResponseDB, long courseID, IQuestionPersistence questionsDB, QuestionManagerAbstractFactory abstractFactory) throws Exception {
        List<IQuestion> questionList = new ArrayList<>();
        List<IQuestion> questionList1 = new ArrayList<>();
        questionList = studentResponseDB.loadStudentResponse(courseID, studentID);
        for (IQuestion question : questionList) {
            IQuestion que = abstractFactory.makeQuestion();
            que = questionsDB.loadQuestions(question.getID());
            que.setAnswer(question.getAnswer());
            questionList1.add(que);
        }
        return questionList1;
    }

}

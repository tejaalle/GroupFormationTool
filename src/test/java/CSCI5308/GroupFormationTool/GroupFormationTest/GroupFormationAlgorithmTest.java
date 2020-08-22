package CSCI5308.GroupFormationTool.GroupFormationTest;

import CSCI5308.GroupFormationTool.GroupFormation.GroupFormationAbstractFactory;
import CSCI5308.GroupFormationTool.GroupFormation.IAlgorithm;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionManagerAbstractFactory;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@SuppressWarnings("deprecation")
class GroupFormationAlgorithmTest {

    @Test
    void test() {
        List<Long> studentIdList = new ArrayList<>();
        studentIdList.add((long) 1);
        studentIdList.add((long) 2);
        studentIdList.add((long) 3);
        studentIdList.add((long) 4);
        List<List<IQuestion>> questionsList = new ArrayList<>();
        List<IQuestion> questions = new ArrayList<>();
        QuestionManagerAbstractFactory questionManagerAbstractFactory = SystemConfig.instance().getQuestionManagerConcreteFactoryState();
        GroupFormationAbstractFactory groupFormationAbstractFactory = SystemConfig.instance().getGroupFormationConcreteFactoryState();
        IQuestion question = questionManagerAbstractFactory.makeQuestion();
        question.setID(1);
        question.setAnswer("mco1");
        questions.add(question);
        question = questionManagerAbstractFactory.makeQuestion();
        question.setID(2);
        question.setAnswer("mcm1");
        questions.add(question);
        question = questionManagerAbstractFactory.makeQuestion();
        question.setID(3);
        question.setAnswer("freetext1");
        questions.add(question);
        question = questionManagerAbstractFactory.makeQuestion();
        question.setID(4);
        question.setAnswer("numeric1");
        questions.add(question);
        questionsList.add(questions);
        questionsList.add(questions);
        questions = new ArrayList<>();
        question = questionManagerAbstractFactory.makeQuestion();
        question.setID(1);
        question.setAnswer("mco2");
        questions.add(question);
        question = questionManagerAbstractFactory.makeQuestion();
        question.setID(2);
        question.setAnswer("mcm2");
        questions.add(question);
        question = questionManagerAbstractFactory.makeQuestion();
        question.setID(3);
        question.setAnswer("freetext2");
        questions.add(question);
        question = questionManagerAbstractFactory.makeQuestion();
        question.setID(4);
        question.setAnswer("numeric2");
        questions.add(question);
        questionsList.add(questions);
        questionsList.add(questions);
        List<IQuestion> criteriaQuestions = new ArrayList<>();
        IQuestion criteriaQuestion = questionManagerAbstractFactory.makeQuestion();
        criteriaQuestion.setID(1);
        criteriaQuestion.setCriteria("SIMILAR");
        criteriaQuestion.setType("mcq-one");
        criteriaQuestions.add(criteriaQuestion);
        criteriaQuestion = questionManagerAbstractFactory.makeQuestion();
        criteriaQuestion.setID(2);
        criteriaQuestion.setCriteria("SIMILAR");
        criteriaQuestion.setType("mcq-multiple");
        criteriaQuestions.add(criteriaQuestion);
        criteriaQuestion = questionManagerAbstractFactory.makeQuestion();
        criteriaQuestion.setID(3);
        criteriaQuestion.setCriteria("SIMILAR");
        criteriaQuestion.setType("freetext");
        criteriaQuestions.add(criteriaQuestion);
        criteriaQuestion = questionManagerAbstractFactory.makeQuestion();
        criteriaQuestion.setID(4);
        criteriaQuestion.setCriteria("SIMILAR");
        criteriaQuestion.setType("numeric");
        criteriaQuestions.add(criteriaQuestion);
        IAlgorithm algorithm = groupFormationAbstractFactory.makeGroupFormationAlgorithm();
        List<List<Long>> studentIdGroups = algorithm.createGroups(studentIdList, questionsList, criteriaQuestions, 2);
        List<List<Long>> resultExpected = new ArrayList<>();
        List<Long> student = new ArrayList<>();
        student.add((long) 1);
        student.add((long) 2);
        resultExpected.add(student);
        student = new ArrayList<>();
        student.add((long) 3);
        student.add((long) 4);
        resultExpected.add(student);
        Assert.isTrue(Objects.equals(studentIdGroups, resultExpected));
    }

}

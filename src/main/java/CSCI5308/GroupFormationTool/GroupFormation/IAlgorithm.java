package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;

import java.util.List;

public interface IAlgorithm {

    public List<List<Long>> createGroups(List<Long> studentIdList, List<List<IQuestion>> questionsList, List<IQuestion> criteriaQuestions, int groupCount);

}

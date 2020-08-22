package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;

import java.util.List;

public interface ICriteriaPersistence {

    boolean insertCriteriaForQuestion(long courseID, long questionID, String criteria) throws Exception;

    boolean deleteCriteria(long courseID) throws Exception;

    List<IQuestion> fetchCriteria(long courseID) throws Exception;
}

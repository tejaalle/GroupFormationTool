package CSCI5308.GroupFormationTool.QuestionManager;

import java.sql.SQLException;
import java.util.List;

public interface IOptionsPersistence {

    public List<Options> loadOptionsByQuestionID(long questionID) throws SQLException;

}

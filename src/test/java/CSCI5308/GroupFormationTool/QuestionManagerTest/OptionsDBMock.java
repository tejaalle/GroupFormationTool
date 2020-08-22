package CSCI5308.GroupFormationTool.QuestionManagerTest;


import CSCI5308.GroupFormationTool.QuestionManager.IOptionsPersistence;
import CSCI5308.GroupFormationTool.QuestionManager.Options;

import java.util.ArrayList;
import java.util.List;

public class OptionsDBMock implements IOptionsPersistence {

    @Override
    public List<Options> loadOptionsByQuestionID(long questionID) {
        List<Options> optionsList = new ArrayList<>();
        Options options = new Options();
        options.setOptions("option1");
        options.setStoredAs("1");
        optionsList.add(options);
        return optionsList;
    }

}

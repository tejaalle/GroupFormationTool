package CSCI5308.GroupFormationTool.QuestionManager;

import java.util.List;

public interface IOptions {

    public String getOptions();

    public void setOptions(String options);

    public String getStoredAs();

    public void setStoredAs(String storedAs);

    public String getOptionsString(List<Options> options);

    public String getStoredAsString(List<Options> options);

}

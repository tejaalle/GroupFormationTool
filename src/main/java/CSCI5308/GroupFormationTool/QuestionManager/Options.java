package CSCI5308.GroupFormationTool.QuestionManager;

import java.util.ArrayList;
import java.util.List;

public class Options implements IOptions {

    private String options;
    private String storedAs;

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getStoredAs() {
        return storedAs;
    }

    public void setStoredAs(String storedAs) {
        this.storedAs = storedAs;
    }

    public String getOptionsString(List<Options> options) {
        List<String> optionValues = new ArrayList<>();
        for (IOptions name : options) {
            optionValues.add(name.getOptions());
        }

        String optionValuesParam = String.join(",", optionValues);
        return optionValuesParam;
    }

    public String getStoredAsString(List<Options> options) {
        List<String> optionKeys = new ArrayList<>();
        for (IOptions name : options) {
            optionKeys.add(name.getStoredAs());
        }

        String optionKeysParam = String.join(",", optionKeys);
        return optionKeysParam;
    }

}

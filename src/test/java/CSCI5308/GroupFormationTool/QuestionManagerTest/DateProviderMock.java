package CSCI5308.GroupFormationTool.QuestionManagerTest;

import CSCI5308.GroupFormationTool.QuestionManager.IDateProvider;

import java.sql.Date;

public class DateProviderMock implements IDateProvider {

    @Override
    public Date generateDate() {
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        return date;
    }

}

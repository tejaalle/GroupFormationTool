package CSCI5308.GroupFormationTool.QuestionManager;

import java.sql.Date;

public class DateProvider implements IDateProvider {

    @Override
    public Date generateDate() {
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        return date;
    }

}

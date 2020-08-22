package CSCI5308.GroupFormationTool.CoursesTest;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderMock {

    public List<String[]> readAll(Reader reader) {
        List<String[]> recordList = new ArrayList<String[]>();
        String[] records = new String[]{"Record 1", "Record 2"};
        recordList.add(records);
        return recordList;
    }

}

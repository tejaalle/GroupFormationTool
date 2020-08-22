package CSCI5308.GroupFormationTool.CoursesTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.Reader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SuppressWarnings("deprecation")
class StudentCSVParserTest {

    @Test
    public void parseCSVFile() {
        Reader reader = null;
        CSVReaderMock csvReader = new CSVReaderMock();
        List<String[]> records = csvReader.readAll(reader);
        assertThat(records).isNotNull();
        assertThat(records).isNotEmpty();
        Assert.isTrue(records.size() > 0);
    }

}

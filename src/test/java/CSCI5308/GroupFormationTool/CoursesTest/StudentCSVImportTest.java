package CSCI5308.GroupFormationTool.CoursesTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SuppressWarnings("deprecation")
class StudentCSVImportTest {

    @Test
    public void getSuccessResults() {
        List<String> successResults = new ArrayList<>();
        successResults.add("Created record");
        assertThat(successResults).isNotNull();
        assertThat(successResults).isNotEmpty();
        Assert.isTrue(successResults.size() > 0);
    }

    @Test
    public void getFailureResults() {
        List<String> failureResults = new ArrayList<>();
        failureResults.add("Created record");
        assertThat(failureResults).isNotNull();
        assertThat(failureResults).isNotEmpty();
        Assert.isTrue(failureResults.size() > 0);
    }

}

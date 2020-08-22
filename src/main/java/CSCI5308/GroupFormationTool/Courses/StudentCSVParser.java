package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.AccessControlAbstractFactory;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import CSCI5308.GroupFormationTool.SystemConfig;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentCSVParser implements IStudentCSVParser {

    private MultipartFile uploadedFile;
    private List<IUser> studentList = new ArrayList<>();

    public StudentCSVParser(MultipartFile file) {
        this.uploadedFile = file;
    }

    Logger myLogger = LoggerUtil.getLoggerInstance(StudentCSVImport.class);

    @Override
    public List<IUser> parseCSVFile(List<String> failureResults) {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();

        try {
            Reader reader = new InputStreamReader(uploadedFile.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(reader).build();
            List<String[]> records = csvReader.readAll();
            Iterator<String[]> iter = records.iterator();
            IUser user;

            while (iter.hasNext()) {
                String[] record = iter.next();

                String bannerID = record[0];
                String firstName = record[1];
                String lastName = record[2];
                String email = record[3];

                user = accessControlAbstractFactory.makeUser();
                user.setBannerID(bannerID);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                studentList.add(user);
            }

        } catch (IOException e) {
            myLogger.error(String.format("IO Exception encountered while parsing"
                    + " uploaded CSV file"), e);
            failureResults.add("Failure reading uploaded file: " + e.getMessage());
        } catch (Exception e) {
            myLogger.error(String.format("Exception encountered while parsing"
                    + " uploaded CSV file"), e);
            failureResults.add("Failure parsing CSV file: " + e.getMessage());
        }

        return studentList;
    }

}

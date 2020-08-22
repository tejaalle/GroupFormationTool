package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.IUser;

import java.util.List;

public interface IStudentCSVParser {

    public List<IUser> parseCSVFile(List<String> failureResults);

}

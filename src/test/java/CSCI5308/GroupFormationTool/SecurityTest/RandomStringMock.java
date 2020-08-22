package CSCI5308.GroupFormationTool.SecurityTest;

import CSCI5308.GroupFormationTool.Security.IRandomString;

public class RandomStringMock implements IRandomString {

    @Override
    public String generateRandomString() {
        String random = "randomString";
        return random;
    }

}

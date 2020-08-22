package CSCI5308.GroupFormationTool.Security;

public class RandomString implements IRandomString {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // generate random token for forgot password.
    public String generateRandomString() {
        StringBuilder builder = new StringBuilder();
        int count = 7;

        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }

        return builder.toString();
    }

}

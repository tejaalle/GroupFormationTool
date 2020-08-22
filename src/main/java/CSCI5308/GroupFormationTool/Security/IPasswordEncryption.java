package CSCI5308.GroupFormationTool.Security;

// Classes implementing this interface decide how the application will encrypt passwords.
// The intent is that the implementation details are entirely hidden from the rest of
// the application. We do not know which encryption algorithm is used.
public interface IPasswordEncryption {

    // This method encrypts a raw password entered by the user in a signup form
    // and encrypts it into a string to store in our persistence layer.
    public String encryptPassword(String rawPassword);

    // This method determines whether a raw password entered by the user matches
    // the encrypted password from a persistence layer for the user.
    public boolean matches(String rawPassword, String encryptedPassword);

}

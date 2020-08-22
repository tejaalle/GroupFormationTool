package CSCI5308.GroupFormationTool.Security;

import CSCI5308.GroupFormationTool.AccessControl.*;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorCodes;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignupController implements ErrorCodes {

    private final String USERNAME = "username";
    private final String PASSWORD = "password";
    private final String PASSWORD_CONFIRMATION = "passwordConfirmation";
    private final String FIRST_NAME = "firstName";
    private final String LAST_NAME = "lastName";
    private final String EMAIL = "email";

    Logger myLogger = LoggerUtil.getLoggerInstance(CustomAuthenticationManager.class);

    @GetMapping("/signup")
    public String displaySignup(Model model) {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView processSignup(
            @RequestParam(name = USERNAME) String bannerID,
            @RequestParam(name = PASSWORD) String password,
            @RequestParam(name = PASSWORD_CONFIRMATION) String passwordConfirm,
            @RequestParam(name = FIRST_NAME) String firstName,
            @RequestParam(name = LAST_NAME) String lastName,
            @RequestParam(name = EMAIL) String email) {

        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        SecurityAbstractFactory abstractFactory = SystemConfig.instance().getSecurityConcreteFactoryState();
        ModelAndView modelAndView;

        if (User.isBannerIDValid(bannerID) && User.isEmailValid(email) && User.isFirstNameValid(firstName) &&
                User.isLastNameValid(lastName) && password.equals(passwordConfirm)) {

            IUser loadedUser = accessControlAbstractFactory.makeUser();
            loadedUser.setBannerID(bannerID);
            loadedUser.setPassword(password);
            loadedUser.setFirstName(firstName);
            loadedUser.setLastName(lastName);
            loadedUser.setEmail(email);
            IUserPersistence userDB = accessControlAbstractFactory.makeUserDB();
            IPasswordEncryption passwordEncryption = abstractFactory.makeBCryptPasswordEncryption();
            IUserNotifications notification = accessControlAbstractFactory.makeEmail();

            try {
                loadedUser.createUser(userDB, passwordEncryption, notification);
            } catch (Exception e) {
                modelAndView = new ModelAndView("signup");
                modelAndView.addObject("errorMessage", GFT_ERR_USER_001);
                return modelAndView;
            }
        } else {
            modelAndView = new ModelAndView("signup");
            modelAndView.addObject("errorMessage", GFT_ERR_USER_002);
            return modelAndView;
        }

        myLogger.info(String.format("Registered New User with bannerID %s successfully", bannerID));
        modelAndView = new ModelAndView("login");

        return modelAndView;
    }

}

package CSCI5308.GroupFormationTool.Security;

import CSCI5308.GroupFormationTool.AccessControl.*;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorCodes;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ForgotPasswordController implements ErrorCodes {

    AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
    SecurityAbstractFactory abstractFactory = SystemConfig.instance().getSecurityConcreteFactoryState();
    IUserNotifications notification = accessControlAbstractFactory.makeEmail();
    IRandomString randomString = abstractFactory.makeRandomString();
    Logger myLogger = LoggerUtil.getLoggerInstance(ForgotPasswordController.class);

    @RequestMapping("/password")
    public String password(Model model) {
        model.addAttribute("user", new User());
        return "forgotpassword";
    }

    @PostMapping("/password/forgotpassword")
    public String
    forgotPassword(Model model, User user) throws Exception {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        String bannerID = user.getBannerID();
        IUser loadedUser = accessControlAbstractFactory.makeUser();
        IUserPersistence userDB = accessControlAbstractFactory.makeUserDB();

        try {
            userDB.loadUserByBannerID(bannerID, loadedUser);
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_USER_003);
            return "forgotpassword";
        }

        String generatedString = randomString.generateRandomString();
        try {
            notification.sendUserLoginCredentials(loadedUser, generatedString);
        } catch (Exception e) {
            model.addAttribute("errorMessage", GFT_ERR_USER_004);
            return "forgotpassword";
        }

        model.addAttribute("EmailString", generatedString);
        model.addAttribute("banner", bannerID);
        return "token";
    }

    @PostMapping("/password/confirmpassword")
    public ModelAndView confirmPassword(@RequestParam String username, @RequestParam String password,
                                        @RequestParam String email, @RequestParam String bannerId, ModelAndView modelAndView) {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        SecurityAbstractFactory abstractFactory = SystemConfig.instance().getSecurityConcreteFactoryState();
        IPasswordEncryption passwordEncryption = abstractFactory.makeBCryptPasswordEncryption();
        IUserPersistence userDB = accessControlAbstractFactory.makeUserDB();

        if (username.equals(email)) {
            String bPassword = passwordEncryption.encryptPassword(password);
            try {
                userDB.changePassword(bannerId, bPassword);
                myLogger.info(String.format("Password changed successfully for user with"
                        + " bannerID %s", bannerId));
            } catch (Exception exception) {
                modelAndView.addObject("errorMessage", GFT_ERR_USER_005);
                modelAndView.setViewName("token");
                return modelAndView;
            }
            modelAndView.addObject("confirmationMessageSuccess", "Password has been changed Go to login page");
            modelAndView.setViewName("token");
            return modelAndView;
        }
        myLogger.info(String.format("Password not changed for user with"
                + " bannerID %s because of invalid token", bannerId));

        modelAndView.addObject("errorMessage", GFT_ERR_USER_006);
        modelAndView.setViewName("token");
        return modelAndView;
    }

}

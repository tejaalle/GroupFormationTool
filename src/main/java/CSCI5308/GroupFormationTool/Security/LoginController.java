package CSCI5308.GroupFormationTool.Security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        return "login.html";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        return "login-error.html";
    }

}
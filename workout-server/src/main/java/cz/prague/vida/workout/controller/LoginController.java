package cz.prague.vida.workout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String showHome() {
        return "home";
    }

    @GetMapping("/showLogin")
    public String showLogin() {
        return "access/login";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access/access-denied";
    }
}
